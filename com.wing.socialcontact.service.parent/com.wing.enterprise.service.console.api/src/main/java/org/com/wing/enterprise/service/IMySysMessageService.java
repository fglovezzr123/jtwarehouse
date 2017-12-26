package org.com.wing.enterprise.service;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.MySysMessage;

import com.wing.socialcontact.common.model.PageParam;

/**
 * 我的系统消息
 * 
 * 
 * @ClassName: IMySysMessageService
 * @Description: TODO
 * @author: sino
 * @date:2017年5月12日
 */
public interface IMySysMessageService {

    
    /**
     * 条件查询
     * @param param
     * @param role
     * @return
     */
    public List<Map> selectMyMessage(PageParam param, MySysMessage mySysMessage);
    
    /**
     * 批量保存
     * //TODO 添加方法功能描述
     * @param entryPhoneAdressList
     * @return
     */
    boolean insertMyMsgBatch(List<MySysMessage > mySysMessage);
    
    /**
     * 
     * //TODO 更新
     * @param QuickDoor
     * @return
     */
    boolean updateMyMsgBatch(String uids);
    /**
     * 需要推送的用户
     * //TODO 添加方法功能描述
     * @return
     */
    List<Map> selWillPushUser();
}
