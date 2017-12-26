package com.tojoy.vhall.resp;

import java.util.Map;

public class WebinarFetchResp extends BaseResp  {
	private String alias_name;//"",
	private String auto_record;//1,
	private String buffer;//5,
	private String category;//"1",
	private String end_time;//"2017-04-21 11:24:47",
	private String host;//"天九云",
	private String id;//655535544,
	private String img_url;//"http://cnstatic01.e.vhall.com/upload/webinars/img_url/86/de/86ded7a2978cab35d36cdf52843b9b46.jpg",
	private String introduction;//"<p><font size=\"3\">　　“杰出华商投资洽谈会”由中国商业联合会与天九幸福控股集团共同主办，旨在为全球华商构建广泛的人脉网络与合作平台，促进全球华商强强联手，和合共荣。迄今已成功举办126届投洽会，30多位海内外国家领导人、200多位城市首脑先后出席，受益企业12万余家，促成签约一万多亿元。这些丰硕的成果充分表明，杰出华商投洽会已经成为全球最具价值的全球通的超级商务平台。</font></p><p></p><p style=\"text-align: center; \"><b><font size=\"3\">第127届杰出华商投资洽谈会议日程</font></b></p><p><font size=\"3\">09:30-12:00</font></p><p><font size=\"3\"><b>统一教育“智慧校园”、天九·丞诺酒店全国联营项目发布会暨与会者优质项目发布对接会</b></font></p><p><font size=\"3\">1、统一教育“智慧校园”全国联营项目发布会暨签约仪式</font></p><p><font size=\"3\">2、天九·丞诺酒店全国联营项目发布会暨签约仪式</font></p><p><font size=\"3\">3、与会者优质项目发布暨分行业合作对接洽谈会</font></p>",
	private String is_chat;
	private String is_iframe;
	private String is_open;
	private String is_single_video;
	private String layout;//1,
	private String live_start_time;//"2017-04-21 09:17:23",
	private String password;//"tojoy127",
	private String subject;//"第127届杰出华商投资洽谈会",
	private String t_start;//"2017-04-21 09:30:00",
	private String type;//4,
	private String user_id;//"17002233",
	private String verify;//1
	
	@SuppressWarnings("unchecked")
	public WebinarFetchResp(Map<String, ?> map) {
		super(map);
		if(isSuccess()){
			Map<String,Object> data = (Map<String, Object>) map.get("data");
			this.alias_name = data.get("alias_name")==null?"":data.get("alias_name").toString();
			this.auto_record = data.get("auto_record")==null?"":data.get("auto_record").toString();
			this.buffer = data.get("buffer")==null?"":data.get("buffer").toString();
			this.category = data.get("category")==null?"":data.get("category").toString();
			this.end_time = data.get("end_time")==null?"":data.get("end_time").toString();
			this.host = data.get("host")==null?"":data.get("host").toString();
			this.id = data.get("id")==null?"":data.get("id").toString();
			this.img_url = data.get("img_url")==null?"":data.get("img_url").toString();
			this.introduction = data.get("introduction")==null?"":data.get("introduction").toString();
			this.is_chat = data.get("is_chat")==null?"":data.get("is_chat").toString();
			this.is_iframe = data.get("is_iframe")==null?"":data.get("is_iframe").toString();
			this.is_open = data.get("is_open")==null?"":data.get("is_open").toString();
			this.is_single_video = data.get("is_single_video")==null?"":data.get("is_single_video").toString();
			this.layout = data.get("layout")==null?"":data.get("layout").toString();
			this.live_start_time = data.get("live_start_time")==null?"":data.get("live_start_time").toString();
			this.password = data.get("password")==null?"":data.get("password").toString();
			this.subject = data.get("subject")==null?"":data.get("subject").toString();
			this.t_start = data.get("t_start")==null?"":data.get("t_start").toString();
			this.type = data.get("type")==null?"":data.get("type").toString();
			this.user_id = data.get("user_id")==null?"":data.get("user_id").toString();
			this.verify = data.get("verify")==null?"":data.get("verify").toString();
			
		}
	}
	public String getAlias_name() {
		return alias_name;
	}
	public void setAlias_name(String alias_name) {
		this.alias_name = alias_name;
	}
	public String getAuto_record() {
		return auto_record;
	}
	public void setAuto_record(String auto_record) {
		this.auto_record = auto_record;
	}
	public String getBuffer() {
		return buffer;
	}
	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getIs_chat() {
		return is_chat;
	}
	public void setIs_chat(String is_chat) {
		this.is_chat = is_chat;
	}
	public String getIs_iframe() {
		return is_iframe;
	}
	public void setIs_iframe(String is_iframe) {
		this.is_iframe = is_iframe;
	}
	public String getIs_open() {
		return is_open;
	}
	public void setIs_open(String is_open) {
		this.is_open = is_open;
	}
	public String getIs_single_video() {
		return is_single_video;
	}
	public void setIs_single_video(String is_single_video) {
		this.is_single_video = is_single_video;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public String getLive_start_time() {
		return live_start_time;
	}
	public void setLive_start_time(String live_start_time) {
		this.live_start_time = live_start_time;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getT_start() {
		return t_start;
	}
	public void setT_start(String t_start) {
		this.t_start = t_start;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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

}
