package com.wing.socialcontact.taglib;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.wing.socialcontact.service.wx.bean.Meeting;

@SuppressWarnings("serial")
public class MeetingTag extends TagSupport {
	private Meeting meeting;
	private Integer type;
	
	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int doEndTag() throws JspException {
		if(meeting==null){
			return EVAL_PAGE;
		}
		if(1==type){
			doEndTag1();
		}else if(2==type){
			doEndTag2();
		}
		return EVAL_PAGE;
	}
	
	private void doEndTag1(){
		JspWriter out = pageContext.getOut();
		try {
			if(meeting==null){
				return;
			}
			//计算会议状态
			int status = calcStatus();
			String path = ((HttpServletRequest)this.pageContext.getRequest()).getContextPath();
			String detailUrl = path+"/m/meeting/detail/index.do?id="+meeting.getId();
			String signupUrl = path+"/m/my/meeting/signup/index.do?id="+meeting.getId();
			String vedioUrl = path+"/m/meeting/liveuseweb.do?id="+meeting.getId();
			
			int signupStatus = meeting.getExtProp("signupStatus",0);//报名状态
			int types = meeting.getTypes();
			
			boolean canSignup  = false;
			Date curDate = new Date();
			if(meeting.getStartSignupTime()!=null&&meeting.getEndSignupTime()!=null&&curDate.compareTo(meeting.getStartSignupTime())>0
					&&curDate.compareTo(meeting.getEndSignupTime())<=0){
				canSignup = true;
			}
			
			
			if(1==status){
				out.println("<a href=\""+detailUrl+"\" class=\"active_A\">预告中</a>");
			}else if(2==status){
				if(signupStatus==1){
					out.println("<a href=\""+detailUrl+"\" class=\"active_A\">已报名</a>");
				}else{
					out.println("<a href=\""+signupUrl+"\" class=\"active_A\">立即报名</a>");
				}
			}else if(3==status){
				out.println("<a href=\""+detailUrl+"\" class=\"active_A\">报名结束</a>");
			}else if(4==status){
				if(1==types||3==types){
					if(signupStatus==1){
						out.println("<a href=\""+vedioUrl+"\" class=\"active_A\">进入直播室</a>");
					}else{
						if(canSignup){
							out.println("<a href=\""+signupUrl+"\" class=\"active_A\">进入直播室</a>");
						}else{
							out.println("<a href=\""+detailUrl+"\" class=\"active_A\">进行中</a>");
						}
					}
				}else{         
					out.println("<a href=\""+detailUrl+"\" class=\"active_A\">进行中</a>");
				}
			}else if(5==status){
				if((1==types||3==types)&&1==signupStatus&&StringUtils.isNotBlank(meeting.getWebinarId())){
					out.println("<a href=\""+vedioUrl+"\" class=\"active_A\">视频回放</a>");
				}else{
					out.println("<a href=\""+detailUrl+"\" class=\"active_A\">已结束</a>");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int doEndTag2() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			if(meeting==null){
				return EVAL_PAGE;
			}
			//计算会议状态
			int status = calcStatus();

			String path = ((HttpServletRequest)this.pageContext.getRequest()).getContextPath();
			int signupStatus = meeting.getExtProp("signupStatus",0);//报名状态
			int attentionStatus = meeting.getExtProp("attentionStatus",0);//关注状态
			int upperlimit = meeting.getUpperlimit()==null?0: meeting.getUpperlimit().intValue();
			long signupCount = meeting.getExtProp("signupCount", 0);
			int signupRemindStatus = meeting.getExtProp("signupRemindStatus",0);//提醒状态
			
			StringBuffer sbf = new StringBuffer();
			sbf.append("<div class=\"buttonss\">");
			
			if(0==attentionStatus){
				sbf.append("<div data-id=\""+meeting.getId()+"\" onclick=\"attentionMeeting(this)\">");
				sbf.append("<img src=\""+path+"/resource/img/icons/gz.png\"/>");
				sbf.append("<span>收藏</span>");
				sbf.append("</div>");
			}else{
				sbf.append("<div data-id=\""+meeting.getId()+"\" onclick=\"attentionDelMeeting(this)\">");
				sbf.append("<img src=\""+path+"/resource/img/icons/gzsuccess.png\"/>");
				sbf.append("<span>取消收藏</span>");
				sbf.append("</div>");
			}
			
			if(status==1){//预告中
				if(signupRemindStatus==1){
					sbf.append("<div style=\"background-color:gray;\">已设置提醒</div>");
				}else{
					sbf.append("<div data-id=\""+meeting.getId()+"\" onclick=\"signupRemind(this)\">预报名</div>");
				}
				sbf.append("</div>");
				sbf.append("<br class=\"clear\"/>");
				out.print(sbf.toString());
			}else if(2==status){
				if(signupStatus==1){
					sbf.append("<div style=\"background-color:gray;\">已报名</div>");
				}else{
					if(upperlimit>0&&signupCount>=upperlimit){
						sbf.append("<div style=\"background-color:gray;\">报名人数已达上限</div>");
					}else{
						sbf.append("<div onclick=\"signup()\">立刻报名</div>");
					}
				}
				sbf.append("</div>");
				sbf.append("<br class=\"clear\"/>");
				out.print(sbf.toString());
			}else if(3==status){
				if(signupStatus==1){
					sbf.append("<div style=\"background-color:gray;\">已报名</div>");
				}else{
					sbf.append("<div style=\"background-color:gray;\">报名结束</div>");
				}
				sbf.append("</div>");
				sbf.append("<br class=\"clear\"/>");
				out.print(sbf.toString());
			}else if(4==status){
				Date curDate = new Date();
				if(signupStatus==1){
					sbf.append("<div style=\"background-color:gray;\">已报名</div>");
				}else{
					if(meeting.getStartSignupTime()!=null&&meeting.getEndSignupTime()!=null&&
							curDate.compareTo(meeting.getStartSignupTime())>0
							&&curDate.compareTo(meeting.getEndSignupTime())<=0){
						if(upperlimit>0&&signupCount>=upperlimit){
							sbf.append("<div style=\"background-color:gray;\">报名人数已达上限</div>");
						}else{
							sbf.append("<div onclick=\"signup()\">立刻报名</div>");
						}
					}else{
						sbf.append("<div style=\"background-color:gray;\">报名结束</div>");
					}
				}
				sbf.append("</div>");
				sbf.append("<br class=\"clear\"/>");
				out.print(sbf.toString());
			}else if(5==status){
				sbf.append("<div style=\"background-color:gray;\">已结束</div>");
				sbf.append("</div>");
				sbf.append("<br class=\"clear\"/>");
				out.print(sbf.toString());
			}
			return EVAL_PAGE;
		} catch (IOException e) {
			e.printStackTrace();
			return EVAL_PAGE;
		}
	}
	
	private int calcStatus(){
		if(meeting==null){
			return -1;
		}
		int status = -1;
		Date curDate = new Date();
		if(meeting.getEndTime()!=null&&curDate.compareTo(meeting.getEndTime())>0){
			status= 5;//已结束
			return status;
		}
		if(meeting.getStartTime()!=null&&meeting.getEndTime()!=null
				&&curDate.compareTo(meeting.getStartTime())>=0&&curDate.compareTo(meeting.getEndTime())<=0){
			status= 4;//进行中
			return status;
		}
		if(meeting.getEndSignupTime()!=null&&meeting.getStartTime()!=null
				&&curDate.compareTo(meeting.getEndSignupTime())>0&&curDate.compareTo(meeting.getStartTime())<=0){
			status= 3;//报名结束
			return status;
		}
		if(meeting.getStartSignupTime()!=null&&meeting.getEndSignupTime()!=null&&curDate.compareTo(meeting.getStartSignupTime())>0
				&&curDate.compareTo(meeting.getEndSignupTime())<=0){
			status= 2;//报名中
			return status;
		}
		
		if(meeting.getStartSignupTime()!=null&&curDate.compareTo(meeting.getStartSignupTime())<=0){
			status= 1;//预告中
			return status;
		}
		return status;
	}
}
