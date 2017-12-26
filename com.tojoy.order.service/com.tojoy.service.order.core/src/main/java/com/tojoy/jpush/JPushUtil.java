package com.tojoy.jpush;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.tojoy.config.BaseConfig;
import com.tojoy.util.SpringContextUtil;

public class JPushUtil {

    protected static final Logger LOG = LoggerFactory.getLogger(JPushUtil.class);
    
    /**
     * 推送消息
     * //TODO 添加方法功能描述
     * @param users      推送用户集合，如果为空默认推所有
     * @param content    内容
     * @return
     */
    public static Long Push(List<String> alias,String content){
        
        BaseConfig baseConfig = (BaseConfig) SpringContextUtil.getBean("baseConfig");
        JPushClient jpushClient = new JPushClient(baseConfig.getJpushSecret(), baseConfig.getJpushAppKey(), 3);
        
        PushPayload payload = buildPushObject_all_all_alert(content);
        
        if(null != alias){
            payload = buildPushObject_all_registrationid_alert(alias, content);
        }

        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println("Got result - " + result);
            return result.msg_id;
             
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. " + e);
            return Long.valueOf(999999);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. " + e);
            LOG.error("HTTP Status: " + e.getStatus());
            LOG.error("Error Code: " + e.getErrorCode());
            LOG.error("Error Message: " + e.getErrorMessage());
            LOG.error("Msg ID: " + e.getMsgId());
            return Long.valueOf(999999);
        }
    }
    
    public static PushPayload buildPushObject_all_all_alert(String content) {
        return PushPayload.alertAll(content); 
    }
    /**
     * 发送给一个客户端
     * //TODO 添加方法功能描述
     * @param users      推送用户
     * @param content    内容
     * @return
     */
    public static PushPayload buildPushObject_all_registrationid_alert(List<String> alias,String content) {
        return PushPayload.newBuilder()
            .setPlatform(Platform.all()) //设置平台-所有平台
            .setAudience(Audience.registrationId(alias)) //向选定的人推送
            .setNotification(Notification.alert(content)) //设置通知 - 消息
            .build();
    }
}
