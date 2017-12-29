package com.tojoy.vhall.api;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.tojoy.vhall.resp.RecordDefaultResp;
import com.tojoy.vhall.resp.RecordListResp;

/**
 * 微吼内容API
 * @author liangwj
 * @version 1.0
 */
public class RecordAPI extends BaseAPI {
	private static final String LIST_URL = "http://e.vhall.com/api/vhallapi/v2/record/list";
	private static final String DEFAULT_URL = "http://e.vhall.com/api/vhallapi/v2/record/default";
	/**
	 * 获取回放列表
	 * @return
	 */
	public final static RecordListResp listRecord(String webinar_id){
		Map<String, String> params = new HashMap<String, String>();
		params.put("webinar_id", webinar_id);
		return new RecordListResp(doPostVhall(LIST_URL, params));
	}
	/**
	 * 将回放设置为默认活动回放
	 * @return
	 */
	public final static RecordDefaultResp defaultRecord(String record_id){
		Map<String, String> params = new HashMap<String, String>();
		params.put("record_id", record_id);
		return new RecordDefaultResp(doPostVhall(DEFAULT_URL, params));
	}
	
	public static void main(String[] args){
		String webinar_id = "710282432";
		String record_id = "262364";
		RecordListResp p1 = listRecord(webinar_id);
		RecordDefaultResp p2 = defaultRecord(record_id);
		System.out.println(JSON.toJSON(p1));
		System.out.println(JSON.toJSON(p2));
	}
}
