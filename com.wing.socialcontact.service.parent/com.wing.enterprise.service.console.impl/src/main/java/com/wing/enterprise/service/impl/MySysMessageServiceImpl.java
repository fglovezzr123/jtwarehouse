package com.wing.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.MySysMessage;
import org.com.wing.enterprise.service.IMySysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.MySysMessageDao;
import com.wing.socialcontact.common.model.PageParam;

@Service
public class MySysMessageServiceImpl extends BaseServiceImpl<MySysMessage> implements IMySysMessageService{

    @Autowired
    private MySysMessageDao mySysMessageDao;
    
    
    @Override
    public List<Map> selectMyMessage(PageParam param, MySysMessage mySysMessage) {
        List<Map> lst = null;
        if(param!=null){
            PageHelper.startPage(param.getPage(), param.getRows());
        }
        Map parm = new HashMap();
        parm.put("ssUserId", mySysMessage.getSsUserId());
        parm.put("status", mySysMessage.getStatus());
        lst=mySysMessageDao.selectMyMessage(parm);
        return lst;
    }

    @Override
    public boolean insertMyMsgBatch(List<MySysMessage> myMsgs) {
        return mySysMessageDao.insertMyMsgBatch(myMsgs);
    }

    @Override
    public boolean updateMyMsgBatch(String uids) {
        Map parm = new HashMap();
        parm.put("uids", uids);
        return mySysMessageDao.updateMyMsgBatch(parm);
    }

    @Override
    public List<Map> selWillPushUser() {
       return mySysMessageDao.selWillPushUser();
    }

}
