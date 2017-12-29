package com.tojoy.vhall.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecordListResp extends BaseResp  {
	private int total = 0;
	private List<Record> list = new ArrayList<Record>();
	
	public int getTotal() {
		return total;
	}
	public List<Record> getList() {
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public RecordListResp(Map<String, ?> map) {
		super(map);
		if(isSuccess()){
			Map<String,Object> data = (Map<String, Object>) map.get("data");
			this.total = Integer.valueOf(data.get("total").toString()).intValue();
			
			List<Map<String,Object>> lists = (List<Map<String, Object>>) data.get("lists");
			for(Map<String,Object> m : lists){
				this.list.add(Record.parse(m));
			}
		}
	}
	
	/**
	 * id	int	否	回放ID
	 *subject	string	否	回放主题
	 *create_time	string	否	创建时间,形式如2013-10-01 10:10:10
	 *url	string	否	回放观看地址
	 *webinar_id	int	否	活动id
	 *duration	int	否	回放时长，单位为秒
	 *webinar_subject	string	否	活动主题
	 *status	int	否	回放生成状态，0表示生成中，1表示生成成功，2表示生成失败
	 *is_default【新】	否	是否为默认回放，0表示否，1表示是	
	 */
	public static class Record  implements Serializable{
		private static final long serialVersionUID = 1L;
		private String id;//	int	否	回放ID
		private String subject;//	string	否	回放主题
		private String create_time;//	string	否	创建时间,形式如2013-10-01 10:10:10
		private String url;//	string	否	回放观看地址
		private String webinar_id;//	int	否	活动id
		private String duration;//	int	否	回放时长，单位为秒
		private String webinar_subject;//	string	否	活动主题
		private String status;//	int	否	回放生成状态，0表示生成中，1表示生成成功，2表示生成失败
		private String is_default;//	否	是否为默认回放，0表示否，1表示是
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getCreate_time() {
			return create_time;
		}
		public void setCreate_time(String create_time) {
			this.create_time = create_time;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getWebinar_id() {
			return webinar_id;
		}
		public void setWebinar_id(String webinar_id) {
			this.webinar_id = webinar_id;
		}
		public String getDuration() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration = duration;
		}
		public String getWebinar_subject() {
			return webinar_subject;
		}
		public void setWebinar_subject(String webinar_subject) {
			this.webinar_subject = webinar_subject;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getIs_default() {
			return is_default;
		}
		public void setIs_default(String is_default) {
			this.is_default = is_default;
		}
		public static final Record parse(Map<String,?> map){
			Record o = new Record();
			
			o.setId(map.get("id")==null?"":map.get("id").toString());
			o.setSubject(map.get("subject")==null?"":map.get("subject").toString());
			o.setCreate_time(map.get("create_time")==null?"":map.get("create_time").toString());
			o.setUrl(map.get("url")==null?"":map.get("url").toString());
			o.setWebinar_id(map.get("webinar_id")==null?"":map.get("webinar_id").toString());
			o.setDuration(map.get("duration")==null?"":map.get("duration").toString());
			o.setWebinar_subject(map.get("webinar_subject")==null?"":map.get("webinar_subject").toString());
			o.setStatus(map.get("status")==null?"":map.get("status").toString());
			o.setIs_default(map.get("is_default")==null?"":map.get("is_default").toString());
			
			return o;
		}
	}
}
