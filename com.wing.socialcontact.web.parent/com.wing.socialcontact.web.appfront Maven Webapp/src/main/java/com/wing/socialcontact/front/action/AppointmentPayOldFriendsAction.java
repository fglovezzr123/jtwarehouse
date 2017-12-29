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
import com.wing.socialcontact.service.wx.bean.TjyContacts;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.StringUtil;
/**
 * 
 * 老友重逢
 * 
 */
@Controller
@RequestMapping("m/app/oldFriends")
public class AppointmentPayOldFriendsAction extends BaseAppAction
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
	 * 获取通讯录同台好友及通讯录好友
	 * 
	 * */
	@RequestMapping("getOldFriends")
	public @ResponseBody ResponseReport getOldFriends(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		Map relMap = new HashMap();
		//轮播图 老友重逢id
		String oldFriendColumnType = Constants.BANNER_OLDFRIEND_ID;
		List oldFriendListBanner = bannerService.selectBannerByUserId(userId, oldFriendColumnType);
		relMap.put("oldFriendBanner", oldFriendListBanner);
		
		//首选通讯录后台配置个数
		int pageNum = 1;
		int pageSize = 10;// 默认推荐人数10人
		Config config = configService.selectByType("1");
		if (null != config) {
			String config_num = config.getConfig1();
			if (!StringUtils.isEmpty(config_num)) {
				pageSize = Integer.parseInt(config_num);
			}
		}
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
			//获取通讯录平台好友，判断是否够后台配置条数
			int count = pageSize-oldFriendList.size();
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
			return super.getAjaxResult(rr, ResponseCode.OK, "获取成功", relMap);
		}
		catch (Exception e)
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "获取失败", null);
		}
	}
}
