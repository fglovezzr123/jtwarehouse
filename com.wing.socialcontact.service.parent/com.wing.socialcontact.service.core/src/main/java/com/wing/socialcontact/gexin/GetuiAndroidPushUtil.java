package com.wing.socialcontact.gexin;

import java.io.IOException;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;

/**
 * android推送--个推
 * 
 * 
 * @ClassName: GetuiAndroidPushUtil
 * @Description: TODO
 * @author: sino
 * @date:2017年5月12日
 */
public class GetuiAndroidPushUtil {
    /**
     * 
     * //TODO 向单个安卓客户端发送消息，需app采集clientid  通知
     * @param clientId
     * @param model
     * @return
     * @throws IOException
     */
    public static String androidSinglePush(String clientId,GeTuiModel model) throws IOException{
        IGtPush push = new IGtPush(GeTuiModel.HOST, GeTuiModel.appKey,GeTuiModel.master);
        push.connect();

        NotificationTemplate template  = PushTemplate.notificationTemplate(GeTuiModel.APPID, GeTuiModel.appKey, model.getTitle(), model.getContent(), model.getTransContent());
        SingleMessage message = new SingleMessage();
        message.setOffline(false);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(GeTuiModel.OFFLINE_EXPLIRE_TIME);
        message.setData(template);
        Target target = new Target();
        target.setAppId(GeTuiModel.APPID);
        target.setClientId(clientId);
        
        template.setTransmissionContent("{"+model.getCustomMsgKey()+":"+model.getCustomMsgValue()+"}");
        
        IPushResult ret = push.pushMessageToSingle(message, target);
        
        return ret.getResponse().toString();
    }
    /**
     * 
     * //TODO android批量推送
     * @param cidTargets
     * @param model
     * @return
     * @throws IOException
     */
    public static String androidListPush(List<Target> cidTargets,GeTuiModel model) throws IOException{
        IGtPush push = new IGtPush(GeTuiModel.HOST, GeTuiModel.appKey,GeTuiModel.master);
        push.connect();
        
        NotificationTemplate template  = PushTemplate.notificationTemplate(GeTuiModel.APPID, GeTuiModel.appKey, model.getTitle(), model.getContent(), model.getTransContent());
        ListMessage message = new ListMessage();
        message.setOffline(false);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(GeTuiModel.OFFLINE_EXPLIRE_TIME);
        message.setData(template);
        
        template.setTransmissionContent("{"+model.getCustomMsgKey()+":"+model.getCustomMsgValue()+"}");
        
        
        String contentId = push.getContentId(message);
        
        IPushResult ret = push.pushMessageToList(contentId, cidTargets);
        
        return ret.getResponse().toString();
    }
}
