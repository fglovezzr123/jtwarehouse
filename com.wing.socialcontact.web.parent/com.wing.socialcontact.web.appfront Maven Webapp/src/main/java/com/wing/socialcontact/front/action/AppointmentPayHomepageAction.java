package com.wing.socialcontact.front.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.service.im.api.IImGroupinfoService;
import com.wing.socialcontact.service.wx.api.IBannerService;
import com.wing.socialcontact.service.wx.api.IConfigService;
import com.wing.socialcontact.service.wx.api.ITjyContactsService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.TjyContacts;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.StringUtil;

/**
 * 
 * 约见首页
 * 
 */
@Controller
@RequestMapping("m/app/appointmentPayHomepage")
public class AppointmentPayHomepageAction extends BaseAppAction
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
	
	
	
	/**
	 * 约见首页
	 * @author 刘涛
	 * */
	@RequestMapping("getAppointmentHomepage")
	public @ResponseBody ResponseReport getAppointmentHomepage(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		Map relMap = new HashMap();
		//轮播图 老友重逢id
		String oldFriendColumnType = Constants.BANNER_OLDFRIEND_ID;
		//轮播图 新友结缘
		String newFriendColumnType = Constants.BANNER_NEWFRIEND_ID;
		//广告页
		String advertisement = Constants.BANNER_ADVERTISEMENT_ID;
		List oldFriendListBanner = bannerService.selectBannerByUserId(userId, oldFriendColumnType);
		List newFriendListBanner = bannerService.selectBannerByUserId(userId, newFriendColumnType);
		List advertisementBanner = bannerService.selectBannerByUserId(userId, advertisement);
		relMap.put("oldFriendBanner", oldFriendListBanner);
		relMap.put("newFriendBanner", newFriendListBanner);
		relMap.put("advertisement", advertisementBanner);
		
		//老友重逢默认取5条，首选通讯录，通讯录不足5条获取二度人脉
		int pageNum = 1;
		int pageSize = 5;
		try
		{
			List<TjyContacts> contactsList = tjyContactsService.getContacts(Long.parseLong(userId),pageNum,pageSize,"2",true);
			List contactsFriendList = new ArrayList();
			List oldFriendList = new ArrayList();
			for(int i=0;i<contactsList.size();i++) {
				contactsFriendList = tjyUserService.selectByMobile(contactsList.get(i).getMobile());
				for(int x=0;x<contactsFriendList.size();x++) {
					Map oldFriendMap = (HashMap)contactsFriendList.get(x);
					oldFriendMap.put("from", "商友来自通讯录");
					oldFriendList.add(oldFriendMap);
				}
				
			}
			//获取通讯录平台好友，判断是否够5条
			int count = 5-oldFriendList.size();
			if(count>0) {//通讯录好友不足5条，用二度人脉补全
				//获取二度人脉
				List res = imGroupinfoService.selTwoDegreeConnections(userId,  1, Math.abs(count));
				for(int i=0;i<res.size();i++) {
					Map twoDegreeMap = (HashMap)res.get(i);
					twoDegreeMap.put("from", "商友来自二度人脉");
					oldFriendList.add(twoDegreeMap);
				}
			}
			relMap.put("oldFriendList", oldFriendList);
			
			//新友结缘
			List newFriendList = new ArrayList();
			String key = rr.getDataValue("key");
			Map newfriendMap = new HashMap();
			if("cityFriend".equals(key)) {
				//同城商友
				List cityFriendList= imGroupinfoService.selCityElite(userId, pageNum, pageSize);
				for(int i=0;i<cityFriendList.size();i++) {
					Map cityFriendmap = (HashMap)cityFriendList.get(i);
					newFriendList.add(cityFriendmap);
				}
			}else if("peerElite".equals(key)){
				//同行商友：   peerElite
				List peerEliteList= imGroupinfoService.selPeerElite(userId, null, null,pageNum, pageSize);
				for(int i=0;i<peerEliteList.size();i++) {
					Map peerElitemap = (HashMap)peerEliteList.get(i);
					newFriendList.add(peerElitemap);
				}
			}else if("sameHobby".equals(key)){
				//同趣商友
				String hobby =rr.getDataValue("hobbyId");
				String groupName = rr.getDataValue("nickName");
				List sameHobbyList= imGroupinfoService.selIdenticalHobbyFriends(userId, hobby,groupName,pageNum, pageSize);
				for(int i=0;i<sameHobbyList.size();i++) {
					Map sameHobbymap = (HashMap)sameHobbyList.get(i);
					newFriendList.add(sameHobbymap);
				}
			}else {
				List cityFriendList = imGroupinfoService.selCityElite(userId, 1, 2);
				List peerEliteList = imGroupinfoService.selPeerElite(userId, null, null,1, 1);
				String hobby =rr.getDataValue("hobbyId");
				String groupName = rr.getDataValue("nickName");
				List sameHobbyList = imGroupinfoService.selIdenticalHobbyFriends(userId, hobby,groupName,1, 2);
				if(cityFriendList.size()>0) {
					for(int i=0;i<cityFriendList.size();i++) {
						Map cityFriendmap = (HashMap)cityFriendList.get(i);
						newFriendList.add(cityFriendmap);
					}
				}
				if(peerEliteList.size()>0) {
					for(int i=0;i<peerEliteList.size();i++) {
						Map peerElitemap = (HashMap)peerEliteList.get(i);
						newFriendList.add(peerElitemap);
					}
				}
				if(sameHobbyList.size()>0) {
					for(int i=0;i<sameHobbyList.size();i++) {
						Map sameHobbymap = (HashMap)sameHobbyList.get(i);
						newFriendList.add(sameHobbymap);
					}
				}
//				newFriendList.add(cityFriendList);
//				newFriendList.add(peerEliteList);
//				newFriendList.add(sameHobbyList);
			}
			relMap.put("newFriendList", newFriendList);
			return super.getAjaxResult(rr, ResponseCode.OK, "获取成功", relMap);
		}
		catch (NumberFormatException e)
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "获取失败", null);
		}
		catch (Exception e)
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "获取失败", null);
		}
	}
}
