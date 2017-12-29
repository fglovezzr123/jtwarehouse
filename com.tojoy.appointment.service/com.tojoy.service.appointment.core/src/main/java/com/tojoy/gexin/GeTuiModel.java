package com.tojoy.gexin;

public class GeTuiModel {
    /**
     * 个推接口地址
     */
    public static final String HOST = "http://sdk.open.api.igexin.com/apiex.htm";
    //企服云app正式
    /**
     * 个推申请的应用ID
     */
    public static final  String APPID = "7Qur7l0gYb6sKSvp5EJhu4";
    /**
     * 个推申请的应用KEY
     */
    public static final String appKey = "TamFF4F88wA44L6dHtRkj3";
    
    /**
     * 个推申请的安全密钥mastersecret
     */
    public static final String master = "7rFKlHnO4x50JAKIWR9Js";
    //测试
//    /**
//     * 个推申请的应用ID
//     */
//    public static final  String APPID = "5MfTRv7Bvj6Iictj0OHx0A";
//    /**
//     * 个推申请的应用KEY
//     */
//    public static final String appKey = "mZWgmolZQqAvG8enx3OrN8";
//    
//    /**
//     * 个推申请的安全密钥mastersecret
//     */
//    public static final String master = "BFcFJb94Qw771Qa1eA2FL9";
    /**
     * 离线消息有效时间（ms）
     */
    public static final long OFFLINE_EXPLIRE_TIME = 24 * 3600 * 1000;
    /**
     * 通知的标题
     */
    private String title = "";
    
    /**
     * 推送的内容
     */
    private String content;
    
    /**
     * 透传内容
     */
    private String transContent;
    /**
     * 自定义参数key
     */
    private String customMsgKey;
    
    /**
     * 自定义参数value
     */
    private String customMsgValue;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTransContent() {
        return transContent;
    }

    public void setTransContent(String transContent) {
        this.transContent = transContent;
    }

    public String getCustomMsgKey() {
        return customMsgKey;
    }

    public void setCustomMsgKey(String customMsgKey) {
        this.customMsgKey = customMsgKey;
    }

    public String getCustomMsgValue() {
        return customMsgValue;
    }

    public void setCustomMsgValue(String customMsgValue) {
        this.customMsgValue = customMsgValue;
    }

    public GeTuiModel(String title, String content,
            String transContent,String customMsgKey,String customMsgValue) {
        super();
        this.title = title;
        this.content = content;
        this.transContent = transContent;
        this.customMsgKey = customMsgKey;
        this.customMsgValue = customMsgValue;
    }

    public GeTuiModel() {}
    
}