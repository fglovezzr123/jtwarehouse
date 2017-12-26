package com.tojoy.wechat.api;

import java.util.SortedMap;
import java.util.TreeMap;

import com.tojoy.util.ConfigUtil;
import com.tojoy.wechat.entity.ApplyBack;
import com.tojoy.wechat.entity.MchOrderquery;
import com.tojoy.wechat.entity.Unifiedorder;
import com.tojoy.wechat.resp.ApplyBackResult;
import com.tojoy.wechat.resp.UnifiedorderResult;
import com.tojoy.util.HttpClientUtil;
import com.tojoy.util.PayCommonUtil;
import com.tojoy.wechat.resp.MchOrderInfoResult;
import com.tojoy.wechat.utils.XMLConverUtil;



/**
 * 【企业号】微信支付
 * @author liangwj
 *
 */
public class PayMchAPI {
    /**
     * 统一下单
     * @param unifiedorder
     * @param key
     * @return
     */
    public static UnifiedorderResult payUnifiedorder(Unifiedorder unifiedorder){
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", unifiedorder.getAppid());
		parameters.put("mch_id", unifiedorder.getMch_id());
		parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
		parameters.put("body", unifiedorder.getBody());
		parameters.put("out_trade_no", unifiedorder.getOut_trade_no());
		parameters.put("total_fee", unifiedorder.getTotal_fee());
		parameters.put("spbill_create_ip", unifiedorder.getSpbill_create_ip());
		parameters.put("notify_url", unifiedorder.getNotify_url());
		parameters.put("trade_type",unifiedorder.getTrade_type());
		parameters.put("openid", unifiedorder.getOpenid());
		
		parameters.put("sign",PayCommonUtil.createSign("UTF-8", parameters));
        
       // String unifiedorderXML = XMLConverUtil.convertToXML(unifiedorder);
        String unifiedorderXML = PayCommonUtil.getRequestXml(parameters);
        System.out.println("统一下单：" + unifiedorderXML);
        String result = HttpClientUtil.sendPostRequest(ConfigUtil.UNIFIED_ORDER_URL, unifiedorderXML, true);
        System.out.println("微信返回：" + result);
        return XMLConverUtil.convertToObject(UnifiedorderResult.class, result);
    }
    /**
     * 查询订单
     * @param mchOrderquery
     * @param key
     * @return
     */
    public static MchOrderInfoResult payOrderquery(MchOrderquery mchOrderquery){
    	SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", mchOrderquery.getAppid());
		parameters.put("mch_id", mchOrderquery.getMch_id());
		parameters.put("transaction_id", mchOrderquery.getTransaction_id());
		parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
		mchOrderquery.setNonce_str(parameters.get("nonce_str").toString());
        
		mchOrderquery.setSign(PayCommonUtil.createSign("UTF-8", parameters));
        
		String closeorderXML = XMLConverUtil.convertToXML(mchOrderquery);
		System.out.println("订单查询请求：" + closeorderXML);
		
		String result = HttpClientUtil.sendPostRequest(ConfigUtil.CHECK_ORDER_URL, closeorderXML, true);
        System.out.println("微信返回：" + result);
        
        return XMLConverUtil.convertToObject(MchOrderInfoResult.class, result);
    }

    /**
     * 申请退款
     * @param applyBack
     * @param key
     * @return
     */
    public static ApplyBackResult applyBack(ApplyBack sendData){
		
    	
    	SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
 		parameters.put("appid", sendData.getAppid());
 		parameters.put("mch_id", sendData.getMch_id());
 		parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
 		parameters.put("op_user_id", sendData.getMch_id());
 		parameters.put("out_refund_no", sendData.getOut_refund_no());
 		parameters.put("out_trade_no", sendData.getOut_trade_no());
 		parameters.put("refund_fee", sendData.getRefund_fee());
 		parameters.put("total_fee", sendData.getTotal_fee());
 		parameters.put("transaction_id", sendData.getTransaction_id());
 		
 		String sign = PayCommonUtil.createSign("UTF-8", parameters);
		parameters.put("sign", sign);
		System.out.println("parameters:" + parameters);
		String requestXML = PayCommonUtil.getRequestXml(parameters);
 		
 		
 		String result = HttpClientUtil.sendPostRequestCa(ConfigUtil.REFUND_URL, requestXML, true);
    	
		//String result =ApplyBackUtil.sendGetRequestCa(ConfigUtil.REFUND_URL, requestXML, true);
        System.out.println("微信返回：" + result);
        
        return XMLConverUtil.convertToObject(ApplyBackResult.class, result);
    }
}
