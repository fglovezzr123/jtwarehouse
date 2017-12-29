package com.wing.socialcontact.front.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tojoy.service.wx.api.IAppointmentPayService;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.service.im.api.IImGroupinfoService;
import com.wing.socialcontact.service.wx.api.IBannerService;
import com.wing.socialcontact.service.wx.api.IConfigService;
import com.wing.socialcontact.service.wx.api.ITjyContactsService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.Config;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.StringUtil;
/**
 * 
 * 新友结缘
 * 
 */
@Controller
@RequestMapping("m/app/newFriends")
public class AppointmentPayNewFriendsAction extends BaseAppAction
{
	@Autowired
	private IImGroupinfoService imGroupinfoService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private ITjyContactsService tjyContactsService;
	@Resource
	private IConfigService configService;
	@Autowired
	private IBannerService bannerService;
	@Autowired
	private IAppointmentPayService appointmentPayService;
	
	
	/**
	 * 获取同城、同行、同趣好友接口(新友)
	 */
	@RequestMapping("getNewFriends")
	public @ResponseBody ResponseReport getNewFriends(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		String key = rr.getDataValue("key");
		if (StringUtil.isEmpty(key))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		Map relMap = new HashMap();
		//轮播图 老友重逢id
		String newFriendColumnType = Constants.BANNER_NEWFRIEND_ID;
		List newFriendListBanner = bannerService.selectBannerByUserId(userId, newFriendColumnType);
		relMap.put("oldFriendBanner", newFriendListBanner);
		int pageNum = 1;
		int pageSize = 10;// 默认推荐人数10人
		//获取后台配置推荐好友个数
		Config config = configService.selectByType("1");
		if (null != config) {
			String config_num = config.getConfig1();
			if (!StringUtils.isEmpty(config_num)) {
				pageSize = Integer.parseInt(config_num);
			}
		}
		List userList = tjyUserService.getUserListById(userId);
		String cityName = "";
		String industryName = "";
		String hobbys = "";
		if(userList.size()>0) {
			Map ulMap = (HashMap)userList.get(0);
			cityName = ulMap.get("city_name").toString();
			industryName = ulMap.get("industry_name").toString();
			hobbys = appointmentPayService.getUserHobby(userId);
		}
		List newFriendList = new ArrayList();
		
		if("cityFriend".equals(key)) {
			//同城商友
			newFriendList= imGroupinfoService.selCityElite(userId, pageNum, pageSize);
			relMap.put("newFriendList", newFriendList);
			relMap.put("obj", cityName);
			return super.getAjaxResult(rr,ResponseCode.OK, "获取成功！", relMap);
		}else if("peerElite".equals(key)){
			//同行商友：   peerElite
			newFriendList= imGroupinfoService.selPeerElite(userId, null, null,pageNum, pageSize);
			relMap.put("newFriendList", newFriendList);
			relMap.put("obj", industryName);
			return super.getAjaxResult(rr,ResponseCode.OK, "获取成功！", relMap);
		}else if("sameHobby".equals(key)){
			//同趣商友
			String hobby =rr.getDataValue("hobbyId");
			String groupName = rr.getDataValue("nickName");
			newFriendList= imGroupinfoService.selIdenticalHobbyFriends(userId, hobby,groupName,pageNum, pageSize);
			relMap.put("newFriendList", newFriendList);
			relMap.put("obj", hobbys);
			return super.getAjaxResult(rr,ResponseCode.OK, "获取成功！", relMap);
		}else {
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "参数key错误", null);
		}
	}
}
