package com.wing.socialcontact.vhall.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebinarListResp extends BaseResp {
	private int total = 0;
	private List<Webinar> list = new ArrayList<Webinar>();
	
	public int getTotal() {
		return total;
	}
	public List<Webinar> getList() {
		return list;
	}
	@SuppressWarnings("unchecked")
	public WebinarListResp(Map<String, ?> map) {
		super(map);
		if(isSuccess()){
			Map<String,Object> data = (Map<String, Object>) map.get("data");
			this.total = Integer.valueOf(data.get("total").toString()).intValue();
			
			List<Map<String,Object>> lists = (List<Map<String, Object>>) data.get("lists");
			for(Map<String,Object> m : lists){
				this.list.add(Webinar.parse(m));
			}
		}
	}
	/**
	 *     "desc": "123456",
	 *     "pv": "14",
	 *     "start_time": "2017-04-24 15:53:00",
	 *     "status": 3,
	 *     "subject": "测试",
	 *     "thumb": "http://cnstatic01.e.vhall.com/upload/webinars/img_url/22/40/224005140ccd342bf01d175602b924b5.jpg",
	 *     "user_id": "17002233",
	 *     "verify": 3,
	 *     "webinar_id": 426922778
	 */
	public static class Webinar  implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private String desc;
		private String pv;
		private String start_time;
		private String status;
		private String subject;
		private String thumb;
		private String user_id;
		private String verify;
		private String webinar_id;
		
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public String getPv() {
			return pv;
		}
		public void setPv(String pv) {
			this.pv = pv;
		}
		public String getStart_time() {
			return start_time;
		}
		public void setStart_time(String start_time) {
			this.start_time = start_time;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getThumb() {
			return thumb;
		}
		public void setThumb(String thumb) {
			this.thumb = thumb;
		}
		public String getUser_id() {
			return user_id;
		}
		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}
		public String getVerify() {
			return verify;
		}
		public void setVerify(String verify) {
			this.verify = verify;
		}
		public String getWebinar_id() {
			return webinar_id;
		}
		public void setWebinar_id(String webinar_id) {
			this.webinar_id = webinar_id;
		}
		
		public static  final Webinar parse(Map<String,?> map){
			Webinar o = new Webinar();
			o.setDesc(map.get("desc")==null?"":map.get("desc").toString());
			o.setPv(map.get("pv")==null?"":map.get("pv").toString());
			o.setStart_time(map.get("start_time")==null?"":map.get("start_time").toString());
			o.setStatus(map.get("status")==null?"":map.get("status").toString());
			o.setSubject(map.get("subject")==null?"":map.get("subject").toString());
			o.setThumb(map.get("thumb")==null?"":map.get("thumb").toString());
			o.setUser_id(map.get("user_id")==null?"":map.get("user_id").toString());
			o.setVerify(map.get("verify")==null?"":map.get("verify").toString());
			o.setWebinar_id(map.get("webinar_id")==null?"":map.get("webinar_id").toString());
			
			return o;
		}
	}
}