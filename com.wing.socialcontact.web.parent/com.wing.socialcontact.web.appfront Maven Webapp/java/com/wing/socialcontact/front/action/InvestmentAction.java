package com.wing.socialcontact.front.action;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.front.util.ApplicationPath;
import com.wing.socialcontact.service.wx.api.IMeetingService;
import com.wing.socialcontact.service.wx.api.IMeetingSignupService;
import com.wing.socialcontact.service.wx.api.IProjectService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.Meeting;
import com.wing.socialcontact.service.wx.bean.MeetingSignup;
import com.wing.socialcontact.service.wx.bean.MeetingSignupRemind;
import com.wing.socialcontact.service.wx.bean.Project;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.util.BeanMapUtils;
import com.wing.socialcontact.util.ConfigUtil;
import com.wing.socialcontact.util.PayCommonUtil;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.utils.CtxHolder;
import com.wing.socialcontact.wechat.api.PayMchAPI;
import com.wing.socialcontact.wechat.entity.MchOrderquery;
import com.wing.socialcontact.wechat.resp.MchOrderInfoResult;

/**
 * 投融保
 */
@Controller
@RequestMapping("/m/investment")
public class InvestmentAction extends BaseAction{
	@Autowired
	private IMeetingService meetingService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IMeetingSignupService meetingSignupService;
	@Autowired
	private ITjyUserService tjyUserService;
	/**
	 * 投融保首页
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		Meeting param = new Meeting();
		param.setShowEnable(1);
		param.setRecommendEnable(1);
		//param.setOrderBy("order by m.create_time desc");
		
		DataGrid grid = meetingService.selectAllMeeting(new PageParam(1,20) , param);
		List<Meeting> list = grid.getRows();
		
		for(Meeting m : list){
			m.calcStatus();
			//查询当前用户的报名状态
			m.setExtProp("signupStatus", checkMeetingSignupStatus(m)?1:0);
		}
		//获取报名中的会议信息
		modelMap.addAttribute("list0", list);

		//获取推荐项目
		PageParam param2 = new PageParam();
		param2.setRows(10);
		param2.setPage(1);
		
		Project p = new Project();
		p.setIsRecommend(1);
		p.setShowEnable(1);
		DataGrid grid2 = projectService.selectAllProject(param2 ,BeanMapUtils.toMap(p));
		modelMap.addAttribute("list", grid2.getRows());
		
		//精选项目
		return "investment/index";
	}
	/**
	 * 金服联盟
	 * @return
	 */
	@RequestMapping("goldservices/index")
	public String goldservices(ModelMap modelMap){
		return "investment/goldservices";
	}
	/**
	 * 基金投资介绍
	 * @return
	 */
	@RequestMapping("invention/introduce")
	public String inventionintroduce(ModelMap modelMap){
		return "investment/invention/introduce";
	}
	/**
	 * 基金投资添加
	 * @return
	 */
	@RequestMapping("invention/add")
	public String inventionadd(ModelMap modelMap,HttpServletRequest request){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		TjyUser tjyUser=tjyUserService.selectByPrimaryKey(userId);
		if(!"1".equals(tjyUser.getIsRealname()+"")){
			return "commons/to_recon";
		}
		return "investment/invention/add";
	}
	/**
	 * 基金投资列表
	 * @return
	 */
	@RequestMapping("invention/list")
	public String inventionlist(ModelMap modelMap,HttpServletRequest request){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			return "login";
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return "login";
		}
		TjyUser tjyUser=tjyUserService.selectByPrimaryKey(userId);
		if(!"1".equals(tjyUser.getIsRealname()+"")){
			return "commons/to_recon";
		}
		return "investment/invention/list";
	}
	/**
	 * 获取当前用户的报名状态
	 * @param m
	 * @return
	 */
	private boolean checkMeetingSignupStatus(Meeting m){
		if(m==null) return false;
		
		MeetingSignup ms = meetingSignupService.selectByMeetingIdAndUserId(CtxHolder.getUserId(),m.getId());
		if(ms==null) return false;
		//门票价格
		if(m.getTicketPrice()==null||m.getTicketPrice().doubleValue()<=0){//免费
			return true;
		}
		//已支付
		if(ms.getOrderStatus()!=null&&(ms.getOrderStatus().intValue()==2||ms.getOrderStatus().intValue()==3)){
			m.setExtProp("signupStatus", 1);
			return true;
		}
		if(ms.getOrderStatus()!=null&&ms.getOrderStatus().intValue()==1){
			//从微信查询支付状态
			MchOrderquery mchOrderquery = new MchOrderquery();
			mchOrderquery.setAppid(ApplicationPath.getParameter("wx_appid"));
			mchOrderquery.setMch_id(ConfigUtil.MCH_ID);
			mchOrderquery.setTransaction_id(ms.getOrderId());
			mchOrderquery.setNonce_str(PayCommonUtil.CreateNoncestr());
			
			MchOrderInfoResult o = PayMchAPI.payOrderquery(mchOrderquery);
			if(o.isSuccess()&&"SUCCESS".equals(o.getTrade_state())){
				ms.setOrderStatus(2);
				ms.setPayTime(new Date(Long.valueOf(o.getTime_end())));
				meetingSignupService.updateMeetingSignup(ms);
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}