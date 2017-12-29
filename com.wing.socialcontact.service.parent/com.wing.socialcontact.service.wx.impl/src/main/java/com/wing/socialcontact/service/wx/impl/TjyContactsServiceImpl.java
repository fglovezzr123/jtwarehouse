package com.wing.socialcontact.service.wx.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.service.wx.dao.*;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.api.ITjyContactsService;

/**
 * @author devil
 * @desicription: 通讯录列表接口实现
 * @date Created in 2017/12/14 11:07
 */
@Service
public class TjyContactsServiceImpl implements ITjyContactsService {

    @Resource
    private TjyContactsVersionDao tjyContactsVersionDao;
    @Resource
    private TjyContactsDao tjyContactsDao;
    @Resource
    private TjyContactsLogDao tjyContactsLogDao;
    @Resource
    private TjyUserDao tjyUserDao;
    @Resource
    private WxUserDao wxUserDao;
    @Resource
    private AppInviteLogDao appInviteLogDao;

    /** 分表前缀 **/
    private String tablePrefix = "tjy_contacts_";

    /**
     * @see ITjyContactsService#getVersionByUserId(Long)
     */
    @Override
    public Map getVersionByUserId(Long userId) throws Exception {
        Map map = new HashMap(1);
        TjyContactsVersion tjyContactsVersion = tjyContactsVersionDao.selectByPrimaryKey(userId);
        map.put("version", tjyContactsVersion!=null ? tjyContactsVersion.getVersion() : 0);
        return map;
    }

    /**
     * @see ITjyContactsService#uploadContacts(Long, String, String)
     */
    @Override
    public int uploadContacts(Long userId, String mobiles, String isLast) throws Exception {
        //求模入表
        Integer tableNum = userId.intValue() % 10;
        String table = tablePrefix + tableNum;
        //手机号字符串转数组
        String[] mobileArray = mobiles.split(",");
        //分片处理，100条以上不处理
        if (mobileArray.length > 100) {
            return -2;
        }
        //上传参数
        Map<String, Object> addMap = new HashMap<>(4);
        addMap.put("table", table);
        List<Map> addMapList = new ArrayList<>();
        for (int i = 0; i < mobileArray.length; i++) {
            Map<String, Object> addAddressListMap = new HashMap(6);
            String mobile = mobileArray[i];
            //查询是否为系统用户
            WxUser wuser = wxUserDao.selectByMobile(mobile);
            //是系统用户
            if (wuser != null) {
                //如果是自己不处理
                if ((wuser.getId() + "").equals(userId)) {
                    continue;
                }
                //查询是否为好友
                Map param = new HashMap(2);
                param.put("uid", userId);
                param.put("fuid", wuser.getId() + "");
                Map m = tjyContactsVersionDao.selectByUserIdAndFriendId(param);
                //非好友
                if (m == null || m.get("friend_user") == null || "".equals(m.get("friend_user"))) {
                    addAddressListMap.put("status", 2);
                }
                //是好友
                else {
                    addAddressListMap.put("status", 3);
                }
            }
            //不是系统用户
            else {
                //查询是否邀请过
                AppInviteLog inviteLog = new AppInviteLog();
                inviteLog.setMobile(mobile);
                inviteLog.setUserId(String.valueOf(userId));
                Integer count = appInviteLogDao.selectCount(inviteLog);
                if (count == null || count == 0) {
                    //未发送过注册邀请
                    addAddressListMap.put("status", 0);
                } else {
                    //发送过注册邀请
                    addAddressListMap.put("status", 1);
                }
            }
            addAddressListMap.put("id", Long.valueOf(String.valueOf(userId) + mobile));
            addAddressListMap.put("userId", userId);
            addAddressListMap.put("mobile", mobile);
            addMapList.add(addAddressListMap);
        }
        addMap.put("list", addMapList);
        int result = tjyContactsDao.ignoreInsertContacts(addMap);
        if (result > 0) {
            Date now = new Date();
            //添加日志 ：用户  在时间新添加了多少条通讯录信息
            TjyUser tjyuser = tjyUserDao.selectByPrimaryKey(String.valueOf(userId));
            TjyContactsLog tjyAddressListLog = new TjyContactsLog();
            String content = tjyuser.getNickname() + "在" + getNowDate(now) + "上传了" + result + "条通讯录数据";
            tjyAddressListLog.setContent(content);
            tjyAddressListLog.setCreateTime(now);
            tjyAddressListLog.setUserId(userId);
            tjyContactsLogDao.insert(tjyAddressListLog);
            if ("1".equals(isLast)) {
                //上传通讯录版本号
                TjyContactsVersion tjyContactsVersion = tjyContactsVersionDao.selectByPrimaryKey(userId);
                if (tjyContactsVersion == null) {
                    TjyContactsVersion addModel = new TjyContactsVersion();
                    addModel.setUserId(userId);
                    addModel.setVersion(1);
                    addModel.setCreateTime(now);
                    addModel.setUpdateTime(now);
                    tjyContactsVersionDao.insert(addModel);
                } else {
                    int version = tjyContactsVersion.getVersion();
                    tjyContactsVersion.setVersion(version+1);
                    tjyContactsVersion.setUpdateTime(now);
                    tjyContactsVersionDao.updateByPrimaryKey(tjyContactsVersion);
                }
            }
        }
        return result;
    }

    /**
     * @see ITjyContactsService#getContacts(Long, int, int, String, boolean)
     */
    @Override
    public List<TjyContacts> getContacts(Long userId, int pageNum, int pageSize, String status, boolean randomFlag) throws Exception {
        Integer tableNum = userId.intValue() % 10;
        String table = tablePrefix + tableNum;
        Map searchMap = new HashMap(4);
        searchMap.put("table", table);
        searchMap.put("userId", userId);
        searchMap.put("status", status);
        searchMap.put("randomFlag", randomFlag);
        PageHelper.startPage(pageNum, pageSize);
        return tjyContactsDao.getContacts(searchMap);
    }

    @Override
    public int removeByUserId(Long userId) throws Exception {
        if (userId == null) {return 0;}
        TjyContacts tjyContacts = new TjyContacts();
        tjyContacts.setUserId(userId);
        return tjyContactsDao.delete(tjyContacts);
    }

    public String getNowDate(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

}
