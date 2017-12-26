package com.wing.socialcontact.service.wx.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.OpenHzbOrder;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.service.wx.dao.TjyUserDao;
import com.wing.socialcontact.service.wx.dao.WxUserDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
import com.wing.socialcontact.util.ConfigUtil;
import com.wing.socialcontact.util.RedisCache;

/**
 * 
 * @author zengmin
 * @date 2017-04-03 23:44:43
 * @version 1.0
 */
@Service
public class WxUserServiceImpl extends BaseServiceImpl<WxUser> implements IWxUserService
{
	/**
	 * 
	 */
	private static final String cache_name = "\"DB:WxUser:\" + ";
	private static final String mall_cache_name = "\"DB:User:\" + ";

	@Resource
	private WxUserDao wxUserDao;
	@Resource
	private TjyUserDao tjyUserDao;
	@Autowired
	private ITjyUserService tjyUserService;

	@Resource
	protected RedisCache redisCache;

	@Override
	public DataGrid selectAllHzbWxUser(PageParam param, WxUser wxUser, Integer amountlow, Integer amounthigh)
	{
		DataGrid data = new DataGrid();
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map<String, Object> parm = new HashMap<String, Object>();
		// parm.put("columnType", openHzbOrder.getColumnType());
		if (StringUtils.hasLength(orderStr))
		{
			parm.put("orderby", orderStr);
		}
		else
		{
			parm.put("orderby", "U.hzb_open_time desc");
		}
		if (null != wxUser.getHzbOpenFlag())
		{
			parm.put("hzbOpenFlag", wxUser.getHzbOpenFlag());
		}
		if (StringUtils.hasLength(wxUser.getKeyword()))
		{
			parm.put("keyword", wxUser.getKeyword());
		}
		if (StringUtils.hasLength(wxUser.getShUser()))
		{
			parm.put("shUser", wxUser.getShUser());
		}
		if (null != wxUser.getHzbLevel())
		{
			parm.put("hzbLevel", wxUser.getHzbLevel());
		}
		parm.put("amountlow", amountlow);
		parm.put("amounthigh", amounthigh);
		List lst = wxUserDao.queryByHzbUser(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public WxUser selectById(String id)
	{
		return wxUserDao.selectByPrimaryKey(Long.parseLong(id));
	}

	@Override
	public WxUser selectByPrimaryKey(String key)
	{
		// 取消用户缓存
		// return super.selectByPrimaryKeyCache(Long.parseLong(key),
		// WxUser.class);
		return selectById(key);
	}

	@Override
	public WxUser selectByWxUserId(String wxUserId)
	{
		return wxUserDao.selectByWxUserId(wxUserId);
	}

	@Override
	public WxUser selectByUserId(String userId)
	{
		return wxUserDao.selectByUserId(userId);
	}

	@Override
	public Map<String, Object> queryUsersByid(String userId)
	{
		return wxUserDao.queryUsersByid(userId);
	}

	@Override
	public WxUser selectByUserName(String userName)
	{
		return wxUserDao.selectByUserName(userName);
	}

	@Override
	public WxUser selectByMobile(String mobile)
	{
		return wxUserDao.selectByMobile(mobile);
	}

	@Override
	public boolean addWxUser(WxUser wxUser)
	{
		wxUser.setPassword("e10adc3949ba59abbe56e057f20f883e");
		wxUser.setUserrole("BUYER");
		wxUser.setStatus(1);
		wxUser.setSex(wxUser.getSex()!=null ? wxUser.getSex() : 1);
		wxUser.setUsertype(Byte.valueOf("2"));
		wxUser.setAvailablebalance(0d);
		wxUser.setJbAmount(0d);
		wxUser.setHzbAmount(0d);
		if (StringUtils.isEmpty(wxUser.getImgUrl()))
		{
			// 设置默认头像
			wxUser.setImgUrl(ConfigUtil.DOMAIN + "/wxfront/resource/img/icons/weixinHeader.jpg");
		}
		int res = wxUserDao.insert(wxUser);
		if (res > 0)
		{
			WxUser wxUserNew = wxUserDao.selectByMobile(wxUser.getMobile());
			wxUserNew.setWxUserId(wxUserNew.getId() + "");
			wxUserDao.updateByPrimaryKey(wxUserNew);
			System.out.println("------注册用户id------" + wxUserNew.getId());
			TjyUser tjyUser = new TjyUser();
			tjyUser.setId(wxUserNew.getId() + "");
			tjyUser.setMallUser(wxUserNew.getId());
			tjyUser.setNickname(wxUserNew.getNickName());
			tjyUser.setIsRealname(0);
			tjyUser.setStatus(1);
			tjyUser.setIsdk(0);
			tjyUser.setMobile(wxUserNew.getMobile());
			tjyUser.setHeadPortrait(wxUser.getImgUrl());
			tjyUser.setOpenId(wxUserNew.getQqOpenid());
			tjyUser.setReconStatus(0);
			tjyUser.setIsztdh(0);
			tjyUser.setSort(0);
			if (null != wxUser.getTjyUser())
			{
				String wxUniqueId = wxUser.getTjyUser().getWxUniqueId();
				if (StringUtils.hasLength(wxUniqueId))
				{
					tjyUser.setWxUniqueId(wxUniqueId);
					tjyUser.setBindPhone(wxUser.getMobile());
					tjyUser.setFirstBindTime(wxUser.getAddtime());
					/*
					 * tjyUser.setBindPhone(wxUser.getTjyUser().getBindPhone());
					 * tjyUser.setFirstBindTime(wxUser.getTjyUser().
					 * getFirstBindTime());
					 */tjyUser.setLastBindTime(wxUser.getTjyUser().getLastBindTime());
				}
			}
			res = tjyUserDao.insert(tjyUser);
			if (res > 0)
			{
				return true;
			}
			else
			{
				System.out.println("同步天九云用户失败");
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean updateWxUser(WxUser wxUser)
	{
		redisCache.removeall(mall_cache_name + wxUser.getId().toString());
		return super.updateByPrimaryKeyCache(wxUser, wxUser.getId());
	}

	@Override
	public String addWxUser(WxUser wxUser, TjyUser tjyUser)
	{

		wxUser.setPassword("e10adc3949ba59abbe56e057f20f883e");
		wxUser.setUserrole("BUYER");
		wxUser.setStatus(1);
		wxUser.setDeletestatus(false);
		wxUser.setUsertype(Byte.valueOf("2"));
		wxUser.setAvailablebalance(0d);
		wxUser.setJbAmount(0d);
		wxUser.setHzbAmount(0d);
		Integer sexInt = wxUser.getSex();
		int res = wxUserDao.insert(wxUser);
		if (res > 0)
		{
			WxUser wxUserNew = wxUserDao.selectByMobile(wxUser.getMobile());
			wxUserNew.setWxUserId(wxUserNew.getId() + "");
			wxUserNew.setSex(sexInt);
			wxUserDao.updateByPrimaryKey(wxUserNew);
			System.out.println("------注册用户id------" + wxUserNew.getId());
			// TjyUser tjyUser = new TjyUser();
			tjyUser.setId(wxUserNew.getId() + "");
			tjyUser.setMallUser(wxUserNew.getId());
			tjyUser.setNickname(wxUserNew.getNickName());
			tjyUser.setIsRealname(tjyUser.getIsRealname()!=null ? tjyUser.getIsRealname() : 0);
			tjyUser.setStatus(1);
			tjyUser.setIsdk(0);
			tjyUser.setIsdisturb(0);
			tjyUser.setMobile(wxUserNew.getMobile());
			tjyUser.setHeadPortrait(wxUser.getImgUrl());
			tjyUser.setOpenId(wxUserNew.getQqOpenid());
			tjyUser.setReconStatus(tjyUser.getReconStatus() != null ? tjyUser.getReconStatus() : 0);
			if (null != wxUser.getTjyUser())
			{
				String wxUniqueId = wxUser.getTjyUser().getWxUniqueId();
				if (StringUtils.hasLength(wxUniqueId))
				{
					tjyUser.setWxUniqueId(wxUniqueId);
					tjyUser.setBindPhone(wxUser.getTjyUser().getBindPhone());
					tjyUser.setFirstBindTime(wxUser.getTjyUser().getFirstBindTime());
					tjyUser.setLastBindTime(wxUser.getTjyUser().getLastBindTime());
				}
			}
			res = tjyUserDao.insert(tjyUser);
			if (res > 0)
			{
				return MsgConfig.MSG_KEY_SUCCESS;
			}
			else
			{
				System.out.println("同步天九云用户失败");
				return MsgConfig.MSG_KEY_FAIL;
			}
		}
		else
		{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String updateWxUser(WxUser wxUser, TjyUser tjyUser)
	{
		super.updateByPrimaryKeyCache(wxUser, wxUser.getId());
		redisCache.removeall(mall_cache_name + wxUser.getId().toString());
		return MsgConfig.MSG_KEY_SUCCESS;
	}

	@Override
	public List<Map<String, Object>> selectByManager(String userRole)
	{
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("deletestatus", 0);
		parm.put("userRole", userRole);
		return wxUserDao.selectByParam(parm);
	}

	@Override
	public WxUser selectByNickName(String nickName)
	{
		return wxUserDao.selectByNickName(nickName);
	}

	@Override
	public WxUser selectByOpenId(String reg_openId)
	{
		return wxUserDao.selectByOpenId(reg_openId);
	}

	@Override
	public void updateMobile(Long userid, String mobile)
	{
		WxUser user = this.selectByPrimaryKey(userid + "");
		String oldMobile = "";
		user.setMobile(mobile);
		user.setUsername(mobile);
		wxUserDao.updateByPrimaryKey(user);
		redisCache.removeall(mall_cache_name + user.getId().toString());
		TjyUser tu = tjyUserDao.selectByPrimaryKey(userid + "");
		oldMobile = tu.getMobile();
		tu.setMobile(mobile);
		tu.setLastBindTime(new Date());
		tjyUserDao.update(tu);
		try
		{
			tjyUserService.remotingUpdateTjyUser(tu, oldMobile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String catchkey = "\"DB:" + WxUser.class.getName() + ":\" + " + userid;
		super.redisCache.evict(catchkey);
		catchkey = "\"DB:" + TjyUser.class.getName() + ":\" + " + userid;
		super.redisCache.evict(catchkey);
	}

	@Override
	public List<Map<String, Object>> selectTaskHzbWxUser()
	{
		return wxUserDao.queryTaskHzbWxUser();
	}

	@Override
	public long selectHzbNdcByUserId(String userId)
	{
		return wxUserDao.queryHzbNdcByUserId(userId);
	}

}