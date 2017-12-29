package com.wing.enterprise.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.com.wing.enterprise.bean.EntryServiceTag;
import org.com.wing.enterprise.bean.MySysMessage;
import org.com.wing.enterprise.bean.SysMessage;
import org.com.wing.enterprise.service.ISysMessageService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.gexin.rp.sdk.base.impl.Target;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.MySysMessageDao;
import com.wing.enterprise.service.dao.SysMessageDao;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.gexin.GeTuiModel;
import com.wing.socialcontact.gexin.GetuiAndroidPushUtil;
import com.wing.socialcontact.gexin.GetuiIosPushUtil;

/**
 * 
 * <p>Title: 企服云系统消息管理</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月10日 上午11:56:29
 */
@Service	
public class SysMessageServiceImpl extends BaseServiceImpl<SysMessage> implements
		ISysMessageService {

	@Resource
    private  SysMessageDao sysMessageDao;
	@Resource
	private MySysMessageDao mySysMessageDao; 
	
	@Override
	public Object selsysMessage(PageParam param, SysMessage sysMessage) {
		 DataGrid data= new DataGrid();
	        PageHelper.startPage(param.getPage(), param.getRows());
	        
	        Map parm = new HashMap();
	        parm.put("content", sysMessage.getContent());
	        List lst = sysMessageDao.selsysMessage(parm);
	        PageInfo page = new PageInfo(lst);
	        data.setRows(lst);
	        data.setTotal(page.getTotal());
	        return data;
	}
	/**
	 * 消息新增
	 * @throws IOException 
	 */
	@Override
	public String addsysMessage(SysMessage dto) throws IOException {
	    
	    String content = dto.getContent();
	    
	    List<Map> wPushs = mySysMessageDao.selWillPushUser();
	    
	    List<MySysMessage> myMsgs = new ArrayList<>();
	    
	    Date currentDate = new Date();
	    //iods
	    List<String> tokens = new ArrayList<>();
	    //android
	    List<Target> tags = new ArrayList<>();
	    
	    if(!CollectionUtils.isEmpty(wPushs)){
	        for (Map map : wPushs) {
	            MySysMessage msg = new MySysMessage();
	            msg.setId(UUID.randomUUID().toString().replace("-", ""));
	            msg.setCreateTime(currentDate);
	            msg.setStatus(0);
	            msg.setContent(content);
	            msg.setSsUserId((String)map.get("username"));
	            msg.setCreateUserId(dto.getCreateUserId());
	            myMsgs.add(msg);
	            
	            String device = (String) map.get("device");
	            String token = (String) map.get("token");
	            String dnd = (String)map.get("dnd");
	            if("F".equals(dnd)){
    	            if("I".equals(device)){
    	                //ios
    	                tokens.add(token);
    	            }else{
    	                //android
    	                Target t = new Target();
    	                t.setAppId(GeTuiModel.APPID);
    	                t.setClientId(token);
    	                tags.add(t);
    	            }
	            }
            }
	    }
	    //批量插入我的系统消息
	    mySysMessageDao.insertMyMsgBatch(myMsgs);
	    
	    GeTuiModel model = new GeTuiModel();
	    model.setContent(content);
	    model.setCustomMsgKey("type");
	    model.setCustomMsgValue("7");
	    
	    //android批量推送
	    model.setTitle("企服联盟");
	    GetuiAndroidPushUtil.androidListPush(tags, model);
        
	    //ios批量推送
	    GetuiIosPushUtil.iosListPush(tokens, model);
	    
	    
		int res = sysMessageDao.insert(dto);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
	}
	@Override
    public boolean deleteSysMsg(String[] ids) {
        boolean flag = true;
        for(String id:ids){
            if(StringUtils.isNotBlank(id)){
               SysMessage sysMsg = sysMessageDao.selectByPrimaryKey(id);
                if(sysMsg !=null){
                    sysMsg.setStatus(1);
                    if(super.updateByPrimaryKeyCache(sysMsg, id)){
                        
                    }else{
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }
}
