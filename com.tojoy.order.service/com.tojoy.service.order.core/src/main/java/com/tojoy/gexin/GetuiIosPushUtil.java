package com.tojoy.gexin;

import java.io.IOException;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.APNTemplate;

public class GetuiIosPushUtil {
    /** <p class="detail">
     * 功能：IOS单个推送(限制2kb)
     * </p>
     * @author sino
     * @param token
     * @param model
     * @return
     * @throws IOException 
     * @see com.tsou.funny.common.helper.PushMsgInfo#iosSinglePush(java.lang.String, com.tsou.funny.common.model.GeTuiModel) 
     */ 
    public static String iosSinglePush(String token,GeTuiModel model) throws IOException {
        IGtPush push = new IGtPush(GeTuiModel.HOST, GeTuiModel.appKey,GeTuiModel.master);
        // 建立连接，开始鉴权
        push.connect();
        APNTemplate template = new APNTemplate();
        APNPayload apnpayload = new APNPayload();
        apnpayload.setSound("default");
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setTitle(model.getTitle());
        alertMsg.setBody(model.getContent());
        apnpayload.setAlertMsg(alertMsg);
        //自定义参数
        apnpayload.addCustomMsg(model.getCustomMsgKey(), model.getCustomMsgValue());
        
        template.setAPNInfo(apnpayload);
        SingleMessage singleMessage = new SingleMessage();
        singleMessage.setOffline(true);
        singleMessage.setOfflineExpireTime(GeTuiModel.OFFLINE_EXPLIRE_TIME);
        singleMessage.setData(template);
        IPushResult ret = push.pushAPNMessageToSingle(GeTuiModel.APPID, token, singleMessage);
        
        return ret.getResponse().toString();
    }
    /**
     * 
     * //TODO IOS 批量推送
     * @param tokens
     * @param model
     * @return
     * @throws IOException
     */
    public static String iosListPush(List<String> tokens,GeTuiModel model) throws IOException{
        IGtPush push = new IGtPush(GeTuiModel.HOST, GeTuiModel.appKey,GeTuiModel.master);
        // 建立连接，开始鉴权
        push.connect();
        APNTemplate template = new APNTemplate();
        APNPayload apnpayload = new APNPayload();
        apnpayload.setSound("default");
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setTitle(model.getTitle());
        alertMsg.setBody(model.getContent());
        apnpayload.setAlertMsg(alertMsg);
        //自定义参数
        apnpayload.addCustomMsg(model.getCustomMsgKey(), model.getCustomMsgValue());
        template.setAPNInfo(apnpayload);
        
        ListMessage message = new ListMessage();
        message.setData(template);
        // 设置消息离线，并设置离线时间
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(GeTuiModel.OFFLINE_EXPLIRE_TIME);
        
        String contentId = push.getAPNContentId(GeTuiModel.APPID, message);
        
        IPushResult ret = push.pushAPNMessageToList(GeTuiModel.APPID, contentId, tokens);
        
        return ret.getResponse().toString();
    }
}
