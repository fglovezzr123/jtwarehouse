package com.tojoy.gexin;

import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class PushTemplate {
    

    /**
     * 点击通知打开模板
     * //TODO 添加方法功能描述
     * @param appId
     * @param appKey
     * @param title
     * @param text
     * @param transText
     * @return
     */
    public static final NotificationTemplate notificationTemplate(String appId,String appKey,String title,String text,String transText)
    {
        NotificationTemplate template = new NotificationTemplate();
        
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        
        // 设置通知栏标题与内容
        template.setTitle(title);
        template.setText(text);
        
        // 配置通知栏图标
        template.setLogo("icon.png");
        
        // 配置通知栏网络图标
        //if( Tool.notEmpty(logoUrl) ){
        //  template.setLogoUrl( logoUrl );
        //}
        
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);       //收到通知是否响铃：true响铃，false不响铃。默认响铃。
        template.setIsVibrate(true);    //收到通知是否振动：true振动，false不振动。默认振动。
        template.setIsClearable(true);  //通知是否可清除：true可清除，false不可清除。默认可清除。
        
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(transText); //透传内容，不支持转义字符
        // 设定定时展示时间
        //template.setDuration("2015-01-16 11:40:00", ""2015-01-16 12:24:00"");
        return template;
    }

    
    
   /**
    * 苹果穿透模板
    * //TODO 添加方法功能描述
    * @param appId
    * @param appKey
    * @param title
    * @param msgStr
    * @param tranMsg
    * @return
    */
    public static TransmissionTemplate transmissionTemplate(String appId,String appKey,String title,String msgStr,String tranMsg) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId );
        template.setAppkey(appKey);
        template.setTransmissionType(2);                    //收到消息是否立即启动应用：1为立即启动，2则广播等待客户端自启动
        template.setTransmissionContent(tranMsg);       //透传内容，不支持转义字符
        try {
        
            APNPayload payload = new APNPayload();
            payload.setContentAvailable(1);
            payload.setSound("default");
            APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
            alertMsg.setTitle(title);
            alertMsg.setBody(msgStr);
            payload.setAlertMsg(alertMsg);
            template.setAPNInfo(payload);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return template;
    }

}