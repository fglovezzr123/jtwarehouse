package com.tojoy.vhall.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebinarChatResp extends BaseResp {
	private int total = 0;
	private List<WebinarChat> list = new ArrayList<WebinarChat>();
	
	public int getTotal() {
		return total;
	}
	public List<WebinarChat> getList() {
		return list;
	}
	@SuppressWarnings("unchecked")
	public WebinarChatResp(Map<String, ?> map) {
		super(map);
		if(isSuccess()){
			Map<String,Object> data = (Map<String, Object>) map.get("data");
			this.total = Integer.valueOf(data.get("total").toString()).intValue();
			
			List<Map<String,Object>> lists = (List<Map<String, Object>>) data.get("lists");
			for(Map<String,Object> m : lists){
				this.list.add(WebinarChat.parse(m));
			}
		}
	}
	/**
	 *     "name": "123456",
	 *     "email": "14",
	 */
	public static class WebinarChat  implements Serializable{
		private static final long serialVersionUID = 1L;
		private String webinar_id;
		private String third_user_id;
		private String name;
		private String email;
		private String text;
		private String create_time;
		
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


		public String getWebinar_id() {
			return webinar_id;
		}

		public void setWebinar_id(String webinar_id) {
			this.webinar_id = webinar_id;
		}

		public String getThird_user_id() {
			return third_user_id;
		}

		public void setThird_user_id(String third_user_id) {
			this.third_user_id = third_user_id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getCreate_time() {
			return create_time;
		}

		public void setCreate_time(String create_time) {
			this.create_time = create_time;
		}

		public static  final WebinarChat parse(Map<String,?> map){
			WebinarChat o = new WebinarChat();
			o.setWebinar_id(map.get("webinar_id")==null?"":map.get("webinar_id").toString());
			o.setThird_user_id(map.get("third_user_id")==null?"":map.get("third_user_id").toString());
			o.setText(map.get("text")==null?"":map.get("text").toString());
			o.setCreate_time(map.get("create_time")==null?"":map.get("create_time").toString());
			o.setName(map.get("name")==null?"":map.get("name").toString());
			o.setEmail(map.get("email")==null?"":map.get("email").toString());
			
			return o;
		}
	}
}