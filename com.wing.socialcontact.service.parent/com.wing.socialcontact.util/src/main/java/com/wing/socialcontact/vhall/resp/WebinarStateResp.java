package com.wing.socialcontact.vhall.resp;

import java.util.Map;

public class WebinarStateResp extends BaseResp  {
	private String state;//当前活动的状态码,1直播进行中;参加者可以进入观看直播;2预约中 , 活动预约中,尚未开始,3活动已结束但无默认回放,3活动已结束有默认回放;
	public WebinarStateResp(Map<String, ?> map) {
		super(map);
		if(isSuccess()){
			this.state = map.get("data")==null?"":map.get("data").toString();
		}
	}
	public String getState() {
		return state;
	}
}
