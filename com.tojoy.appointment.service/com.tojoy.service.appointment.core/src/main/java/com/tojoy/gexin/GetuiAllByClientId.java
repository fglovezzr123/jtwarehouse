package com.tojoy.gexin;

import java.io.IOException;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class GetuiAllByClientId {

    /**
     * 单个推送使用透传推送！(苹果安卓都可以用) 替代安卓单个推送和苹果单个推送
     * //TODO 添加方法功能描述
     * @param cid
     * @param model
     * @return
     * @throws IOException
     */
   public String singlePushByTransmission(String cid, GeTuiModel model) throws IOException {
    
       IGtPush push = new IGtPush(GeTuiModel.HOST, GeTuiModel.appKey,GeTuiModel.master);
       push.connect();
       TransmissionTemplate template = PushTemplate.transmissionTemplate(GeTuiModel.APPID,  GeTuiModel.appKey, model.getTitle(), model.getContent(),model.getTransContent());
       SingleMessage message = new SingleMessage(); 
       message.setOffline(true);
       message.setOfflineExpireTime(GeTuiModel.OFFLINE_EXPLIRE_TIME);
       message.setPushNetWorkType(0);
       message.setData(template);
       Target target = new Target();
       target.setAppId(GeTuiModel.APPID);
       target.setClientId(cid);
       IPushResult ret = null;
       try {
           ret = push.pushMessageToSingle(message, target);
       } catch (RequestException e) {
           e.printStackTrace();
           ret = push.pushMessageToSingle(message, target, e.getRequestId());
       }
       if (ret != null) {
           return ret.getResponse().toString();
       } else {
           return "0";
       }
   }
}
