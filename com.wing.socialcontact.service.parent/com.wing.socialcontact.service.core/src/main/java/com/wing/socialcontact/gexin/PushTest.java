package com.wing.socialcontact.gexin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;

public class PushTest {
    public static void main(String[] args) {
        /**
         * ios单个推送
         */
        /*
        String token = "5d11f2fac986937fac17486aeebf824297f2d8a2b71e6805f157f3b194ce3488";
        PushMsgInfoHelper pm = new PushMsgInfoHelper();
        
        GeTuiModel m = new GeTuiModel();
        m.setContent("个推iosapp推送测试");
        
        try {
            String s = pm.iosSinglePush(token, m);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        
        /**
         * IOS批量推送
         */
        /*List tokens = new ArrayList<>();
        tokens.add("5d11f2fac986937fac17486aeebf824297f2d8a2b71e6805f157f3b194ce3488");
        tokens.add("c2bd2ea0fe4ce86cd369a25798b07360b9e9e689309fc49acd7770d690c2ef1c");
        
        GeTuiModel m = new GeTuiModel();
        m.setContent("个推iosapp推送测试---群推");
        m.setCustomMsgKey("type");
        m.setCustomMsgValue("7");
        
        String s = "";
        try {
            s = GetuiIosPushUtil.iosListPush(tokens, m);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(s);*/
        /**
         * android 单推
         */
        GeTuiModel m = new GeTuiModel();
        m.setTitle("企服联盟");
        m.setContent("个推android推送测试---单推");
        m.setCustomMsgKey("type");
        m.setCustomMsgValue("7");
        
        try {
            String s = GetuiAndroidPushUtil.androidSinglePush("56b6d6d7587a00f1637214587461667f", m);
            //System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /**
         * android--群推
         */
        
       /* GeTuiModel m = new GeTuiModel();
        m.setTitle("企服联盟");
        m.setContent("个推android推送测试---群推");
        m.setCustomMsgKey("type");
        m.setCustomMsgValue("7");
        
        List<Target> tags = new ArrayList<>();
        Target t1 = new Target();
        t1.setAppId(GeTuiModel.APPID);
        t1.setClientId("56b6d6d7587a00f1637214587461667f");
        Target t2 = new Target();
        t2.setAppId(GeTuiModel.APPID);
        t2.setClientId("56b6d6d7587a00f1637214587461667f");
        tags.add(t1);
        tags.add(t2);
        try {
            String s = GetuiAndroidPushUtil.androidListPush(tags, m);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
       
    }
}
