//
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.alibaba.fastjson.JSON;
//import com.google.common.collect.Maps;
//import com.wing.socialcontact.common.model.DataGrid;
//import com.wing.socialcontact.common.model.PageParam;
//import com.wing.socialcontact.service.wx.api.IMeetingService;
//import com.wing.socialcontact.service.wx.api.IProjectRecommendService;
//import com.wing.socialcontact.service.wx.api.ITjyUserService;
//import com.wing.socialcontact.service.wx.bean.Meeting;
//import com.wing.socialcontact.service.wx.bean.ProjectRecommend;
//import com.wing.socialcontact.service.wx.bean.TjyUser;
//import com.wing.socialcontact.task.MeetingTask;
//import com.wing.socialcontact.util.AldyMessageUtil;
//import com.wing.socialcontact.util.DateUtils;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:config/dubbo-consumer.xml", "classpath*:config/applicationContext.xml" })  
//public class TaskTest {
//	
//	@Resource
//	private ITjyUserService tjyUserService;
//	
//	@Resource
//	private IMeetingService meetingService;
//	
//	@Resource
//	private IProjectRecommendService projectRecommendService;
//	
//	@Resource
//	private MeetingTask meetingTask;
//	/**
//	 * 会议报名提醒【活动消息】
//	 */
//	@Test
//	public void sendMeetingSignupActiveMsgTest() {
//		TjyUser tjyUser = tjyUserService.selectById("6300");
//		DataGrid grid = meetingService.selectAllMeeting(new PageParam(1, 1), new Meeting());
//		if(grid.getRows().size()>0){
//			Meeting ms = (Meeting) grid.getRows().get(0);
//			String message ="【"+AldyMessageUtil.SMSPRE+"】尊敬的"+tjyUser.getTrueName()+"，"
//					+ "您所关注的"+ms.getTitles()+"会议于"
//				    +DateUtils.dateToString(ms.getStartSignupTime(), "yyyy-MM-dd")+"开始报名。";
//			meetingTask.doSendActiveMsg("6300",message);
//		}
//		
//		try {
//			Thread.sleep(2000);
//		} catch (Exception e) {
//		}
//	}
//	/**
//	 * 会议开始【活动消息】+【短信】
//	 */
//	@Test
//	public void sendMeetingStartActiveMsgTest() {
//		TjyUser tjyUser = tjyUserService.selectById("6300");
//		DataGrid grid = meetingService.selectAllMeeting(new PageParam(1, 1), new Meeting());
//		if(grid.getRows().size()>0){
//			Meeting ms = (Meeting) grid.getRows().get(0);
//			String message ="【"+AldyMessageUtil.SMSPRE+"】尊敬的"+tjyUser.getTrueName()+"，您所报名的"+ms.getTitles()
//				+"会议于"+DateUtils.dateToString(ms.getStartTime(), "yyyy-MM-dd")+"正式开始，"
//				+ "请您提前持入场凭证参加会议，并将手机调至震动模式。";
//			meetingTask.doSendActiveMsg("6300",message);
//			
//			Map<String,String> params = Maps.newHashMap();
//			params.put("name", tjyUser.getTrueName());
//			params.put("time", DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm"));
//			params.put("hyname", ms.getTitles());
//			
//			meetingTask.doSendSmsMsg(tjyUser.getMobile(),
//					AldyMessageUtil.MsmTemplateId.MEETING_START,JSON.toJSONString(params));
//		}
//		try {
//			Thread.sleep(2000);
//		} catch (Exception e) {
//		}
//	}
//	/**
//	 * 会议报名成功【活动消息】+【短信】
//	 */
//	@Test
//	public void sendMeetingSignupOkActiveMsgTest() {
//		TjyUser tjyUser = tjyUserService.selectById("6300");
//		DataGrid grid = meetingService.selectAllMeeting(new PageParam(1, 1), new Meeting());
//		if(grid.getRows().size()>0){
//			Meeting ms = (Meeting) grid.getRows().get(0);
//			String message ="【"+AldyMessageUtil.SMSPRE+"】尊敬的"+tjyUser.getTrueName()+"，"
//					  + "您于"+DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm")
//					  +"成功报名"+ms.getTitles()+"会议，会议凭证为：姓名："+tjyUser.getTrueName()+"，电话："+tjyUser.getMobile()+"。";
//			meetingTask.doSendActiveMsg("6300",message);
//			
//			Map<String,String> params = Maps.newHashMap();
//			params.put("name", tjyUser.getTrueName());
//			params.put("time", DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm"));
//			params.put("hyname", ms.getTitles());
//			params.put("uname", tjyUser.getTrueName());
//			params.put("mobilem",tjyUser.getMobile());
//			
//			meetingTask.doSendSmsMsg(tjyUser.getMobile(),
//					AldyMessageUtil.MsmTemplateId.MEETING_SIGNUP_SUCCESS,JSON.toJSONString(params));
//		}
//		try {
//			Thread.sleep(2000);
//		} catch (Exception e) {
//		}
//	}
//	/**
//	 * 项目自荐审核通过【活动消息】
//	 */
//	@Test
//	public void sendProjectRecommondOkActiveMsgTest() {
//		TjyUser tjyUser = tjyUserService.selectById("6300");
//		DataGrid grid = projectRecommendService.selectAllProjectRecommend(new PageParam(1, 1), new HashMap<String, Object>());
//		if(grid.getRows().size()>0){
//			ProjectRecommend ms = (ProjectRecommend) grid.getRows().get(0);
//			String message ="【"+AldyMessageUtil.SMSPRE+"】尊敬的"+tjyUser.getTrueName()+"，恭喜！您所推荐的"+ms.getPrjName()
//							+"项目于"+DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm")
//							+"已通过审核。";
//			meetingTask.doSendActiveMsg("6300",message);
//		}
//		try {
//			Thread.sleep(2000);
//		} catch (Exception e) {
//		}
//	}
//}
