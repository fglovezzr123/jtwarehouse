package com.wing.socialcontact.front.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IUserGreetingsService;
import com.wing.socialcontact.service.wx.api.IUserLatestvistorService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.UserGreetings;
import com.wing.socialcontact.service.wx.bean.UserLatestvistor;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 话题pk管理
 * 
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/m/message")
public class MessageAction extends BaseAction {

	@Autowired
	private IWxUserService wxUserService;

	@Autowired
	private IMessageInfoService messageInfoService;

	@Autowired
	private IUserGreetingsService userGreetingsService;

	@Autowired
	private ITjyUserService tjyUserService;

	@Autowired
	private IUserLatestvistorService userLatestvistorService;

	/**
	 * 消息主页
	 * 
	 * @return
	 */
	@RequestMapping("messagePage")
	public String messagePage(ModelMap map) {
		return "message/message_index";
	}

	/**
	 * 最近访客
	 * 
	 * @return
	 */
	@RequestMapping("lasetVistor")
	public String lasetVistorPage(ModelMap map) {
		return "message/lasetVistor_page";
	}

	/**
	 * 系统消息
	 * 
	 * @return
	 */
	@RequestMapping("system_mes")
	public String systemMes(HttpServletRequest request, ModelMap map) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			// return "login";
		}
		String userId = me.getId();
		MessageInfo m = new MessageInfo();
		m.setStatus(0);// 未读
		m.setType(3);
		m.setToUserId(userId);
		List smc = messageInfoService.selectMessageInfos(m);
		map.addAttribute("smc", smc);
		return "message/systemmes";
	}

	/**
	 * 消息列表
	 * 
	 * @Title: message_list
	 * @Description: TODO
	 * @param type
	 * @param request
	 * @param map
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年5月27日 下午2:14:15
	 */
	@RequestMapping("message_list")
	public String message_list(String type, HttpServletRequest request, ModelMap map) {
		if (StringUtils.isEmpty(type)) {
			return "error";
		}
		if (!ServletUtil.isLogin(request)) {
			return "login";
		}
		map.addAttribute("type", type);
		return "message/message_list";
	}

	/**
	 * ajax分页获取消息列表
	 * 
	 * @Title: messageListAjaxPage
	 * @Description: TODO
	 * @param userId
	 * @param type
	 * @param page
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月27日 下午2:27:20
	 */
	@RequestMapping("message_list_ajax_page")
	public @ResponseBody Map messageListAjaxPage(String userId, String type, int page, int pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(userId)) {
			Member me = ServletUtil.getMember(request);
			userId = me.getId();
		}
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		if (StringUtils.isEmpty(type)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		List list = messageInfoService.selectMessageInfoPageByUserIdAndType(userId, Integer.valueOf(type), page,
				pageSize);
		return super.getSuccessAjaxResult("success", list);
	}

	/**
	 * 查询未读消息条数
	 * 
	 * @Title: countMessage
	 * @Description: TODO
	 * @param userId
	 * @param type
	 * @param request
	 * @param response
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月27日 下午3:19:47
	 */
	@RequestMapping("count_message")
	public @ResponseBody Map countMessage(String userId, String type, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(userId)) {
			Member me = ServletUtil.getMember(request);
			userId = me.getId();
		}
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		if (StringUtils.isEmpty(type)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		Integer c = messageInfoService.countMessage(userId, Integer.valueOf(type), 0);
		return super.getSuccessAjaxResult("success", c);
	}

	/**
	 * 修改用户消息状态-设已读
	 * 
	 * @Title: updateMessageStatus
	 * @Description: TODO
	 * @param userId
	 * @param type
	 * @param request
	 * @param response
	 * @return
	 * @return: Map
	 * @author: zengmin
	 * @date: 2017年5月27日 下午2:45:59
	 */
	@RequestMapping("update_message_status")
	public @ResponseBody Map updateMessageStatus(String userId, String type, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(userId)) {
			Member me = ServletUtil.getMember(request);
			userId = me.getId();
		}
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		if (StringUtils.isEmpty(type)) {
			return super.getAjaxResult("401", "参数错误", null);
		}
		messageInfoService.updateStatusByUserIdAndType(userId, Integer.valueOf(type));
		return super.getSuccessAjaxResult();
	}

	/**
	 * 消息主页
	 * 
	 * @return
	 */
	@RequestMapping("activity_mes")
	public String activityMes(HttpServletRequest request, ModelMap map) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			// return "login";
		}
		String userId = me.getId();
		MessageInfo m = new MessageInfo();
		m.setStatus(0);// 未读
		m.setType(4);
		m.setToUserId(userId);
		List smc = messageInfoService.selectMessageInfos(m);
		map.addAttribute("smc", smc);
		return "message/activitymes";
	}

	/**
	 * 系统消息未读数目
	 * 
	 * @return
	 */
	@RequestMapping("systemMessageCount")
	public @ResponseBody Map systemMessageCount(HttpServletRequest request, HttpServletResponse response) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			// return "login";
		}
		String userId = me.getId();
		MessageInfo m = new MessageInfo();
		m.setStatus(0);// 未读
		m.setType(3);
		m.setToUserId(userId);
		List smc = messageInfoService.selectMessageInfos(m);
		int o = 0;
		if (null != smc) {
			o = smc.size();
		}
		return super.getSuccessAjaxResult("success", o);
	}

	/**
	 * 系统消息列表
	 * 
	 * @return
	 */
	@RequestMapping("systemMessageList")
	public @ResponseBody Map systemMessageList(HttpServletRequest request, HttpServletResponse response) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			// return "login";
		}
		String userId = me.getId();
		MessageInfo m = new MessageInfo();
		m.setStatus(0);// 未读
		m.setType(3);
		m.setToUserId(userId);
		List smc = messageInfoService.selectMessageInfos(m);

		return super.getSuccessAjaxResult("success", smc);
	}

	/**
	 * 修改为已读系统消息
	 * 
	 * @return
	 */
	@RequestMapping("systemMessageReaded")
	public @ResponseBody Map systemMessageReaded(HttpServletRequest request, HttpServletResponse response) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			// return "login";
		}
		String userId = me.getId();
		MessageInfo m = new MessageInfo();
		m.setStatus(0);// 未读
		m.setType(3);
		m.setToUserId(userId);
		List<MessageInfo> smc = messageInfoService.selectMessageInfos(m);
		for (MessageInfo mes : smc) {
			MessageInfo ms = messageInfoService.selectByPrimaryKey(mes.getId());
			ms.setStatus(1);
			messageInfoService.updateMessageInfo(ms);
		}

		return super.getSuccessAjaxResult();
	}

	/**
	 * 活动消息数目
	 * 
	 * @return
	 */
	@RequestMapping("activitMessageCount")
	public @ResponseBody Map activitMessageCount(HttpServletRequest request, HttpServletResponse response) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			// return "login";
		}
		String userId = me.getId();
		MessageInfo m = new MessageInfo();
		m.setStatus(0);// 未读
		m.setType(4);
		m.setToUserId(userId);
		List smc = messageInfoService.selectMessageInfos(m);
		int o = 0;
		if (null != smc) {
			o = smc.size();
		}
		return super.getSuccessAjaxResult("success", o);
	}

	/**
	 * 获取我的最近访客信息数目
	 * 
	 * @return
	 */
	@RequestMapping("latestVistorsCount")
	public @ResponseBody Map latestVistorsCount(HttpServletRequest request, HttpServletResponse response) {
		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		UserLatestvistor ul = new UserLatestvistor();
		ul.setUserId(userId);
		try {
			List latestVistorList = userLatestvistorService.selectAllUserLatestvistor(ul);
			int o = 0;
			if (null != latestVistorList) {
				o = latestVistorList.size();
			}

			return super.getSuccessAjaxResult("获取成功！", o);
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
	}
	
	/**
	 * 获取我的最近访客信息数目
	 * 
	 * @return
	 */
	@RequestMapping("deleteLatestVistors")
	public @ResponseBody Map deleteLatestVistors(HttpServletRequest request, HttpServletResponse response,String ids) {
		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}
		if (ids == null || "".equals(ids)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数错误", "");
		}
		
		try {
			boolean lv = userLatestvistorService.deleteUserLatestvistors(ids);
			if(lv){
				return super.getSuccessAjaxResult();
			}else{
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, "删除失败","");
			}
			
			
		} catch (Exception e) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
		}
	}

	/**
	 * 获取我的最近访客信息
	 * 
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("selectLatestVistors")
	public @ResponseBody Map selectLatestVistors(HttpServletRequest request, String pageNum, String pageSize) {
		String userId = super.checkLogin(request);
		// 被访问人id
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}

		if (StringUtils.isEmpty(pageNum) || StringUtils.isEmpty(pageSize)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum)
				|| !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		} else {
			try {
				List latestVistorList = userLatestvistorService.selectLatestVistors(userId, null,
						Integer.parseInt(pageNum), Integer.parseInt(pageSize));
				OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
				String ossurl = ossConfig.getOss_getUrl();
				Map valueMap = new HashMap();
				valueMap.put("ossurl", ossurl);
				valueMap.put("latestVistorList", latestVistorList);
				return super.getSuccessAjaxResult("获取成功！", valueMap);
			} catch (Exception e) {
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_INFO, e.getMessage(), "");
			}
		}
	}
	
	/**
	 * 修改为已读最近访客
	 * 
	 * @return
	 */
	@RequestMapping("latestVistorsReaded")
	public @ResponseBody Map latestVistorsReaded(HttpServletRequest request, HttpServletResponse response) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			// return "login";
		}
		String userId = me.getId();
		UserLatestvistor u = new UserLatestvistor();
		u.setStatus(0);// 未读
		u.setUserId(userId);
		List<Map<String, Object>> smc = userLatestvistorService.selectAllUserLatestvistor(u);
		for (Map<String, Object> mes : smc) {
			UserLatestvistor ms = userLatestvistorService.selectByPrimaryKey((String)mes.get("id"));
			ms.setStatus(1);
			userLatestvistorService.updateUserLatestvistor(ms);
		}

		return super.getSuccessAjaxResult();
	}

	/**
	 * 活动消息列表
	 * 
	 * @return
	 */
	@RequestMapping("activitMessageList")
	public @ResponseBody Map activitMessageList(HttpServletRequest request, HttpServletResponse response) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			// return "login";
		}
		String userId = me.getId();
		MessageInfo m = new MessageInfo();
		m.setStatus(0);// 未读
		m.setType(4);
		m.setToUserId(userId);
		List smc = messageInfoService.selectMessageInfos(m);
		return super.getSuccessAjaxResult("success", smc);
	}

	/**
	 * 修改为已读活动消息
	 * 
	 * @return
	 */
	@RequestMapping("activitMessageReaded")
	public @ResponseBody Map activitMessageReaded(HttpServletRequest request, HttpServletResponse response) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			// return "login";
		}
		String userId = me.getId();
		MessageInfo m = new MessageInfo();
		m.setStatus(0);// 未读
		m.setType(4);
		m.setToUserId(userId);
		List<MessageInfo> smc = messageInfoService.selectMessageInfos(m);
		for (MessageInfo mes : smc) {
			MessageInfo ms = messageInfoService.selectByPrimaryKey(mes.getId());
			ms.setStatus(1);
			messageInfoService.updateMessageInfo(ms);
		}

		return super.getSuccessAjaxResult();
	}

	/**
	 * 收到的打招呼的数量
	 * 
	 * @return
	 */
	@RequestMapping("toGreetingsCount")
	public @ResponseBody Map toGreetingsCount(HttpServletRequest request, HttpServletResponse response) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			// return "login";
		}
		String userId = me.getId();
		UserGreetings ug = new UserGreetings();
		ug.setFriendUser(userId);
		ug.setStatus(0);
		List smc = userGreetingsService.selectAllUserGreetings(ug);
		int o = 0;
		if (null != smc) {
			o = smc.size();
		}
		return super.getSuccessAjaxResult("success", o);
	}
	
	
	/**
	 * 修改为已读活动消息
	 * 
	 * @return
	 */
	@RequestMapping("toGreetingsReaded")
	public @ResponseBody Map toGreetingsReaded(HttpServletRequest request, HttpServletResponse response) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			// return "login";
		}
		String userId = me.getId();
		UserGreetings ug = new UserGreetings();
		ug.setFriendUser(userId);
		ug.setStatus(0);
		List<Map<String, Object>> smc = userGreetingsService.selectAllUserGreetings(ug);
		
		for (Map<String, Object> mes : smc) {
			UserGreetings ms = userGreetingsService.selectByPrimaryKey((String)mes.get("id"));
			ms.setStatus(1);
			userGreetingsService.updateUserGreetings(ms);
		}

		return super.getSuccessAjaxResult();
	}


	/**
	 * 收到的打招呼的列表
	 * 
	 * @return
	 */
	@RequestMapping("toGreetingsList")
	public @ResponseBody Map toGreetingsList(HttpServletRequest request, HttpServletResponse response) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			// return "login";
		}
		String userId = me.getId();
		UserGreetings ug = new UserGreetings();
		ug.setFriendUser(userId);
		List smc = userGreetingsService.selectAllUserGreetings(ug);

		return super.getSuccessAjaxResult("success", smc);
	}

	/**
	 * 自己发出的打招呼的列表
	 * 
	 * @return
	 */
	@RequestMapping("fromGreetingsList")
	public @ResponseBody Map fromGreetingsList(HttpServletRequest request, HttpServletResponse response) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			// return "login";
		}
		String userId = me.getId();
		UserGreetings ug = new UserGreetings();
		ug.setUserId(userId);
		List smc = userGreetingsService.selectAllUserGreetings(ug);

		return super.getSuccessAjaxResult("success", smc);
	}

	/**
	 * 自己发出的打招呼和收到的招呼的列表
	 * 
	 * @Title: user_greets
	 * @Description: TODO
	 * @param request
	 * @param modelMap
	 * @return
	 * @return: String
	 * @author: gaojun
	 * @date: 2017年4月24日 上午11:23:11
	 */
	@RequestMapping("user_greets")
	public String user_greets(HttpServletRequest request, ModelMap modelMap) {
		Member me = ServletUtil.getMember(request);
		if (null == me) {
			// return "login";
		}
		String userId = me.getId();
		// 收到的招呼
		UserGreetings ug1 = new UserGreetings();
		ug1.setFriendUser(userId);
		List<Map<String, Object>> smc1 = userGreetingsService.selectAllUserGreetings(ug1);
		for (Map<String, Object> m : smc1) {
			TjyUser tjyUser = tjyUserService.selectByPrimaryKey((String) m.get("userId"));
			m.put("tjyUser", tjyUser);
		}
		modelMap.addAttribute("smc1", smc1);

		// 发出的招呼
		UserGreetings ug2 = new UserGreetings();
		ug2.setUserId(userId);
		List<Map<String, Object>> smc2 = userGreetingsService.selectAllUserGreetings(ug2);
		for (Map<String, Object> m : smc2) {
			TjyUser tjyUser = tjyUserService.selectByPrimaryKey((String) m.get("friendUser"));
			m.put("tjyUser", tjyUser);
		}
		modelMap.addAttribute("smc2", smc2);

		return "message/usergreets";
	}

	/**
	 * 消息数目
	 * 
	 * @return
	 */
	@RequestMapping("messageCount")
	public @ResponseBody Map messageCount(HttpServletRequest request, HttpServletResponse response) {
		String userId = super.checkLogin(request);
		if (userId == null || "".equals(userId)) {
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_NOTLOGIN, "未登录，请重新登录！", "");
		}

		Map o = new HashMap();
		// 活动消息个数
		o.put("activitMessageCount", messageInfoService.countMessage(userId, 4, 0));

		// 系统消息个数
		o.put("systemMessageCount", messageInfoService.countMessage(userId, 3, 0));

		// 打招呼个数
		UserGreetings ug = new UserGreetings();
		ug.setFriendUser(userId);
		ug.setStatus(0);
		//ug.setAnswerstatus("0");
		List smc = userGreetingsService.selectAllUserGreetings(ug);
		o.put("toGreetingsCount", smc.size());

		// 最近访客个数
		UserLatestvistor ul = new UserLatestvistor();
		ul.setUserId(userId);
		ul.setStatus(0);
		List latestVistorList = userLatestvistorService.selectAllUserLatestvistor(ul);
		o.put("latestVistorsCount", latestVistorList.size());

		return super.getSuccessAjaxResult("success", o);
	}

}
