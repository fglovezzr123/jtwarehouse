package com.wing.socialcontact.vhall.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebinarTrackResp extends BaseResp {
	private int total = 0;
	private List<WebinarTrack> list = new ArrayList<WebinarTrack>();
	
	public int getTotal() {
		return total;
	}
	public List<WebinarTrack> getList() {
		return list;
	}
	@SuppressWarnings("unchecked")
	public WebinarTrackResp(Map<String, ?> map) {
		super(map);
		if(isSuccess()){
			Map<String,Object> data = (Map<String, Object>) map.get("data");
			this.total = Integer.valueOf(data.get("total").toString()).intValue();
			
			List<Map<String,Object>> lists = (List<Map<String, Object>>) data.get("lists");
			for(Map<String,Object> m : lists){
				this.list.add(WebinarTrack.parse(m));
			}
		}
	}
	/**
	 *     "name": "123456",
	 *     "email": "14",
	 */
	public static class WebinarTrack  implements Serializable{
		private static final long serialVersionUID = 1L;
		private String name;
		private String email;
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}


		public static  final WebinarTrack parse(Map<String,?> map){
			WebinarTrack o = new WebinarTrack();
			o.setName(map.get("name")==null?"":map.get("name").toString());
			o.setEmail(map.get("email")==null?"":map.get("email").toString());
			
			return o;
		}
	}
}