package com.tojoy.vhall.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.tojoy.vhall.resp.WebinarChatResp;
import com.tojoy.vhall.resp.WebinarFetchResp;
import com.tojoy.vhall.resp.WebinarListResp;
import com.tojoy.vhall.resp.WebinarStateResp;
import com.tojoy.vhall.resp.WebinarTrackResp;
import com.tojoy.vhall.resp.WebinarWholeAuthUrlResp;

/**
 * 微活动(也称会议)API
 * @author liangwj
 * @version 1.0
 */
public class WebinarAPI extends BaseAPI {
	private static final String LIST_URL = "http://e.vhall.com/api/vhallapi/v2/webinar/list";
	private static final String FETCH_URL = "http://e.vhall.com/api/vhallapi/v2/webinar/fetch";
	private static final String STATE_URL = "http://e.vhall.com/api/vhallapi/v2/webinar/state";
	private static final String TRACK_URL = "http://e.vhall.com/api/vhallapi/v2/report/track";
	private static final String CHAT_URL = "http://e.vhall.com/api/vhallapi/v2/chat/history";
	private static final String WHOLE_AUTH_URL = "http://e.vhall.com/api/vhallapi/v2/webinar/whole-auth-url";
	/**
	 * 获取活动列表
	 * @return
	 */
	public final static WebinarListResp listWebinar(Integer page, Integer rows){
		Map<String, String> params = new HashMap<String, String>();
		Integer start = (page-1)*rows;
		params.put("limit", rows.toString());
		params.put("pos", start.toString());
		return new WebinarListResp(doPostVhall(LIST_URL, params));
	}
	/**
	 * 获取活动信息
	 * @param webinar_id 活动ID
	 * @return
	 */
	public final static WebinarFetchResp fetchWebinar(String webinar_id){
		Map<String, String> params = new HashMap<String, String>();
		params.put("webinar_id", webinar_id);
		params.put("fields", "id,alias_name,user_id,subject,introduction,img_url,category,is_open,layout,verify,password"
				+ ",type,is_single_video,is_iframe,auto_record,is_chat,buffer,t_start,end_time,host,live_start_time");
		Map<String, ?> result = doPostVhall(FETCH_URL, params);
		return new WebinarFetchResp(result);
	}
	
	/**
	 * 获取活动状态
	 * @param webinar_id 活动ID
	 * @return
	 */
	public final static WebinarStateResp stateWebinar(String webinar_id){
		Map<String, String> params = new HashMap<String, String>();
		params.put("webinar_id", webinar_id);
		Map<String, ?> result = doPostVhall(STATE_URL, params);
		return new WebinarStateResp(result);
	}
	
	/**
	 * 获取活动观众观看记录
	 * @param webinar_id 活动ID
	 * @param type 类型 1为直播，2为回放，默认为直播
	 * @return
	 */
	public final static WebinarTrackResp trackWebinar(String webinar_id, int type){
		Map<String, String> params = new HashMap<String, String>();
		params.put("webinar_id", webinar_id);
		params.put("type", String.valueOf(type==2?2:1));
		//params.put("pos", "1");
		//params.put("limit", "1000");
		Map<String, ?> result = doPostVhall(TRACK_URL, params);
		return new WebinarTrackResp(result);
	}
	
	/**
	 * 获取活动历史聊天记录
	 * @param webinar_id 活动ID
	 * @param startTime 形如2016-11-30 10:16:43，只获取在该时间后的
	 * @param endTime 形如2016-11-30 10:16:43，只获取在该时间前的
	 * @param pos 分页开始 数字
	 * @param limit 返回条数 数字
	 * @return
	 */
	public final static WebinarChatResp chatWebinar(String webinar_id, String startTime, String endTime, int pos, int limit){
		Map<String, String> params = new HashMap<String, String>();
		params.put("webinar_id", webinar_id);
		if(StringUtils.isNotBlank(startTime)){
			params.put("start_time", startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			params.put("end_time", endTime);
		}
		params.put("pos", String.valueOf(pos<=0?1:pos));
		params.put("limit", String.valueOf(limit<=0||limit>200?200:limit));
		Map<String, ?> result = doPostVhall(CHAT_URL, params);
		return new WebinarChatResp(result);
	}
	/**
	 * 全局配置第三方K值验证URL，针对所有的活动配置生效，如果针对单个活动再做配置，以单个活动配置为最终配置
	 * @param exist_3rd_auth默认为0不开启，1为开启,是否开启第三方K值验证查看说明
	 * @param auth_url   http://domain,<256个字符,第三方K值验证接口URL(exist_3rd_auth为1必填)
	 * @param failure_url http://domain,<256个字符,第三方K值验证失败跳转URL(可选)
	 * @param cover_child 是否覆盖子账号，1为覆盖，0为不覆盖，默认为0
	 * @return
	 */
	public final static WebinarWholeAuthUrlResp wholeAuthUrlWebinar (int exist_3rd_auth,String auth_url,String failure_url,int cover_child){
		Map<String, String> params = new HashMap<String, String>();
		params.put("exist_3rd_auth", String.valueOf(exist_3rd_auth));
		params.put("auth_url", auth_url==null?"":auth_url);
		params.put("failure_url", failure_url==null?"":failure_url);
		params.put("cover_child", String.valueOf(cover_child));
		Map<String, ?> result = doPostVhall(WHOLE_AUTH_URL, params);
		return new WebinarWholeAuthUrlResp(result);
	}
	/**
	 * 获取上传视频列表
	 * @return
	 */
	public final static WebinarListResp listUploadWebinar(Integer page,Integer rows){
		Map<String, String> params = new HashMap<String, String>();
		Integer start = (page-1)*rows;
		params.put("limit", rows.toString());
		params.put("pos", start.toString());
		params.put("status", "4");
		return new WebinarListResp(doPostVhall(LIST_URL, params));
	}
	
	public static void main(String[] args){
		String webinar_id = "710282432";
//		WebinarFetchResp p1 = fetchWebinar(webinar_id);
//		WebinarStateResp p2 = stateWebinar(webinar_id);
//		WebinarTrackResp p3 = trackWebinar(webinar_id,1);
//		WebinarChatResp p4 = chatWebinar(webinar_id,null,null,0,1000);
		WebinarWholeAuthUrlResp p5 = wholeAuthUrlWebinar(1,"http://www.51adven.com/wxfront/m/vhall/validk.jhtml","",1);
		
		
//		System.out.println(JSON.toJSON(p1));
//		System.out.println(JSON.toJSON(p2));
//		System.out.println(JSON.toJSON(p3));
//		System.out.println(JSON.toJSON(p4));
		System.out.println(JSON.toJSON(p5));
		
	}
}
