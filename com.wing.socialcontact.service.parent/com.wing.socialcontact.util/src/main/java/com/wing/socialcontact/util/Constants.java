package com.wing.socialcontact.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	// 测试开关
	public static final boolean TEST = false;
	// 前端接口domin
	public static final String domain = ConfigUtil.DOMAIN+ "/wxconsole";
	// 前端接口sessionid
	public static final String SESSION_WXUSER_ID = "WXUserId";
	public static final String SESSION_WXUSER_NICKNAME = "WXUserNickName";
	public static final String SESSION_WXUSER_HDPIC = "WXUserHeadPic";

	// 删除标识
	public static final String DELETE_TRUE = "-1";
	public static final String DELETE_FALSE = "0";
	// 上传图片类型
	public static final String UPLOAD_IMG_EXT = "*.jpg;*.jpeg;*.gif;*.png;*.bmp;";
	// 上传文档类型
	public static final String UPLOAD_DOC_EXT = "*.txt;*.doc;*.docx;*.ppt;*.pptx;*.pdf;*.xls;*.swf;";
	// 上传视频类型
	public static final String UPLOAD_VIDEO_EXT = "*.rm;*.rmvb;*.wmv;*.avi;*.mpg;*.mpeg;*.mp4;";
	// 上传音频类型
	public static final String UPLOAD_VOICE_EXT = "*.mp3;*.wma;*.wav;*.amr;";
	// 上传文档大小30M
	public static final int UPLOAD_DOC_SIZE = 1024 * 1024 * 30;

	public static final String AJAX_CODE_SUCCESS = "0";
	// 提示性错误
	public static final String AJAX_CODE_ERROR_INFO = "999";
	// SEESION超时，需要登陆
	public static final String AJAX_CODE_ERROR_NOTLOGIN = "302";
	// 登录时密码错误
	public static final String AJAX_CODE_ERROR_PWD = "301";
	// 参数错误
	public static final String AJAX_CODE_ERROR_PARAM = "401";
	public static final String AJAX_MSG_SUCCESS = "操作成功！";
	public static final String AJAX_MSG_ERROR = "操作异常！";

	// 资讯分类id
	public static final String NEWS_CLASS_ID = "eb9aeb09061c4f76b6242bd196d9fe7a";
	// 静态页
	public static final String NEWS_CLASS_ID_HTML = "fe4790ad85264d0eaf3388eff383dd64";
	// 商友专访
	public static final String NEWS_CLASS_ID_VIEW = "3c1821af5fa8472c89ac716ed5f002ff";
	// 关于天九(id与classid，types相同)
	public static final String NEWS_CLASS_ID_TIANJIU = "06dac8488cb94f6ea7de7611e35c9d27";
	// 分享配置(id与classid，types相同)
	public static final String NEWS_CLASS_ID_SHARE = "978a258146304645b39703f93de2a4fb";
	// 注册手机验证码超时时间（单位秒）
	public static final int REG_VALIDATE_CODE_TIMEOUT =  5*60;

	// 缓存公众号token
	public static Map<String, Object> accessTokenMap = new HashMap<String, Object>();
	
	// 轮播图  首页id
	public static final String BANNER_INDEX_ID = "3d1ba858eaab4f93b6d4d5ead09014c8";
	// 轮播图  资讯id
	public static final String BANNER_NEWS_ID = "c00da457080843fab86291754b8f5be1";
	// 轮播图  话题pk id
	public static final String BANNER_PK_ID = "bf6ad33dd02f4739ab9a201d1969c9b4";
	// 轮播图  合作洽谈 id
	public static final String BANNER_BUSINESS_ID = "d7976b895f034da7bfe7e306fb050e7a";
	// 轮播图 老友重逢 id
	public static final String BANNER_OLDFRIEND_ID = "63cc67027f2c410c8a7cf096fc2d34d4";
	//轮播图 新友结缘id
	public static final String BANNER_NEWFRIEND_ID = "2b5e4e10b7634683be1c0e9f6f095c67";
	//约见广告
	public static final String BANNER_ADVERTISEMENT_ID = "093dd28c27d745659b822cdc0dbe4545";
	//轮播图--活动--以玩会友id
    public static final String BANNER_ACTIVITY_PALY_ID = "cb692aecc45f47efb7f2fe9999854582";
    //轮播图--活动--以书会友id
    public static final String BANNER_ACTIVITY_BOOK_ID = "38c72bfc4f774ebe89860ca7834c98a0";
    //轮播图--投资--id
    public static final String BANNER_INVENTION_ID = "e42a166136414028aac06c2ca6a2288b";
    //轮播图--商城--id
    public static final String BANNER_MALL_ID = "e56e54e01e124f89945bc2eb96587cff";
    //轮播图--人脉圈--id
    public static final String BANNER_NETWORK_ID = "55a34dcafbcc4057ae33f70c37eae7c1";
    //轮播图--智囊团首页轮播图--id
    public static final String BANNER_WISDOM_ID = "2142b885a7654fc391167350a2f5b125";
    //轮播图--知识秘书首页轮播图--id
    public static final String BANNER_KNOWLEDGE_ID = "40a665bf0226467ca693a7f32dac7475";
    //轮播图--诸葛解惑轮播图--id
    public static final String BANNER_REWARD_ID = "888b24e041ac4695a58577522c45d49c";
    //轮播图--与总统谈心轮播图--id
    public static final String BANNER_ZTTX_ID = "0e21860b1bf94b8fa690eba546d0ed03";
    //轮播图--俊卿解惑轮播图--id
    public static final String BANNER_JQJH_ID = "7960cba500d94c36914877b63b2cc899";
    //轮播图--老板新闻三分钟轮播图--id
    public static final String BANNER_LBXWSFZ_ID = "c8f587aa1fc340dcbc8f3fa73b54719a";
    
    
	//创建群、添加、删除用户通知
	//public static final String CREATE_GROUP_NOTIFY_URL="http://ims.tojoycloud.com:5281/api/reload_group";
	//删除群通知
	//public static final String DELETE_GROUP_NOTIFY_URL="http://ims.tojoycloud.com:5281/api/purge_group";
	//接口用户信息
	public static final String AUTHORIZATION = "Basic MTAwMUA0Ny45My4xLjI3OjExMTE=";
	//接口私钥
	public static final String PRIVATE_KEY = "MTAwMUA0Ny45My4xLjI3OjExMTE=";
	
	//天九云前台用户
	public static final String TJY_FRONT_USERID = "1000";
	
	/** 连连支付商户号（商户需要替换自己的商户号）. */
	public static final String LIAN_OID_PARTNERWAP = "201705231001761503";
	/** 商户私钥 商户通过openssl工具生成公私钥，公钥通过商户站上传，私钥用于加签，替换下面的值 . */
    public static final String LIAN_BUSINESS_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANkKIqbkLt6Vax8A+01L5Hd7O6xwMHmcaNsQUWknBMDL6pgvriHNnL8C2dNmmpViLkhlWgFs5JNbaYWOUroPMxpN+ytot8UGjFZ2OM35Vqaa62iwo/aVE5+2uMh7EYz6foZAPkecgzK+9rbL49ioS0BWrZxdS6AkPtRjUqiIG/Q/AgMBAAECgYBe5n+gWcXkrZDZTaSUd8q+M7Q0AhowEqQGpWvzfowlgQRi5vZM8YrIOobkWMje6L80CJ0CYorPo/jP3RtvH5ZH6/c1Bgc4MVHyF7h8Vet4rxCQ+VJnmNZ7og8T73NlnCrZSWNXFnf4EkCr1IoF+yIvH2QD1YCJv63J8XVU738MAQJBAPLEFaDaqqWZ33PSTuAIrVMLJpwRtUjGKL5G02DgGNI9Xo6rQmDcrC0sgacyF735TTxLn/o9JHXwLvVCbpIKPmECQQDk3wbwol8ysByWPt3hWLTc4GaVeGASbihHZMDxiJmcUK+7Uvr/MlS8cy0XooIetHwFxZWHwJbWQyATlz6ijvafAkBUhhUJtlin49U7HEQ+GIdQXJeKy1eVCPlylnJOTngV6ywa/GHWG78O02oo7nX5vO05SxNeKx+guImA9ypMSqjBAkEAjVzP7Yts32L1lTtpUom21IJp+kJqnJebm1U48zabIniXwk0jqMJOokNFg8alNg/mVq5OiJXE1LyLMG6HimJhIwJAMnlzlNkYBHujkTSWyMpQJ/MNjkz1jB5bi++w+9097AeRVeniK3uezfKT6TVO3jCBBIPOlVXuTZyaeRNMyts6IA==";
    /** 连连银通公钥（不需替换，这是连连公钥，用于报文加密和连连返回响应报文时验签，不是商户生成的公钥. */
    public static final String LIAN_PUBLIC_KEY_ONLINE = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZCiKm5C7elWsfAPtNS+R3ezuscDB5nGjbEFFpJwTAy+qYL64hzZy/AtnTZpqVYi5IZVoBbOSTW2mFjlK6DzMaTfsraLfFBoxWdjjN+VammutosKP2lROftrjIexGM+n6GQD5HnIMyvva2y+PYqEtAVq2cXUugJD7UY1KoiBv0PwIDAQAB";
    /** 实时付款签名方式. */
    public static final String LIAN_LIANLIANSIGN_TYPE_RSA = "RSA";
    /** 实时付款api版本. */
    public static final String LIAN_API_VERSION = "1.1";
    public static final String LIAN_SIGN_TYPE_MD5 = "MD5";
    // MD5 KEY
    public static final String LIAN_MD5_KEY = "tojoycloud";
    public final static String LIAN_SERVER_URL = "https://wap.lianlianpay.com/payment.htm";
    public final static String LIAN_NOTIFY_URL = ApplicationPath.getParameter("domain") + "/wxfront/m/my/notify_wy.do";
    public final static String LIAN_RETURN_URL = ApplicationPath.getParameter("domain")+"/wxfront/m/my/personal_wallet.do";
    public final static String LIAN_BACK_URL = ApplicationPath.getParameter("domain")+"/wxfront/m/my/wallet_cz.do";
    
    public final static String APP_LIAN_NOTIFY_URL = ApplicationPath.getParameter("domain") + "/appfront/m/app/recharge/notifyWy";
    public final static String IM_MESSAGE_PUSH_ACCOUNT = "tojoycloudadmin9999";
    
    
}
