package com.wing.socialcontact.front.action;

import java.util.ArrayList;
import java.util.List;
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
import com.wing.socialcontact.service.wx.api.IConfigService;
import com.wing.socialcontact.service.wx.api.ITjyContactsService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.Config;
import com.wing.socialcontact.service.wx.bean.TjyContacts;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.util.StringUtil;

/**
 * 
 * @author 刘涛 商友推荐
 */
@Controller
@RequestMapping("friendsRecommended")
public class FriendsRecommended extends BaseAppAction
{
	@Autowired
	private IImGroupinfoService imGroupinfoService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private ITjyContactsService tjyContactsService;
	@Resource
	private IConfigService configService;
	
	
	/**
	 * 获取同城商友
	 * 
	 * 
	 */
	@RequestMapping("cityFriendsRecommended")
	public @ResponseBody ResponseReport cityFriendsRecommended(@RequestBody RequestReport rr)
	{
		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		// 同城商友（随机推荐）
		List res = imGroupinfoService.selCityElite2(userId);

		return super.getAjaxResult(rr, ResponseCode.OK, "获取成功", res);

	}
	
	/**
	 * 获取同行商友
	 * 
	 * 
	 */
	@RequestMapping("industryFriendsRecommended")
	public @ResponseBody ResponseReport industryFriendsRecommended(@RequestBody RequestReport rr)
	{

		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
		String industry = tjyUser.getIndustry();
		String nickName = tjyUser.getNickname();
		// 同城商友（随机推荐）
		List res = imGroupinfoService.selPeerElite2(userId,industry,nickName);

		return super.getAjaxResult(rr, ResponseCode.OK, "获取成功", res);

	}
	
	
	
	/**
	 * 获取同趣商友
	 * 
	 * 
	 */
	@RequestMapping("interestFriendsRecommended")
	public @ResponseBody ResponseReport interestFriendsRecommended(@RequestBody RequestReport rr)
	{

		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
		String industry = tjyUser.getIndustry();
		String nickName = tjyUser.getNickname();
		// 同行商友（随机推荐）
		List res = imGroupinfoService.selPeerElite2(userId,industry,nickName);

		return super.getAjaxResult(rr, ResponseCode.OK, "获取成功", res);

	}
	
	
	/**
	 * 获取通讯录平台用户
	 * 
	 * 
	 */
	@RequestMapping("contactsList")
	public @ResponseBody ResponseReport contactsList(@RequestBody RequestReport rr)
	{

		String userId = rr.getUserProperty().getUserId();
		if (StringUtil.isEmpty(userId))
		{
			return super.getAjaxResult(rr, ResponseCode.NotSupport, "未登录", null);
		}
		//获取推荐商友个数
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
			List<TjyContacts> contactsList = tjyContactsService.getContacts(Long.parseLong(userId),1,pageSize,"2",true);
			List relList = new ArrayList();
			for(int i=0;i<contactsList.size();i++) {
				List tjyUserList = tjyUserService.selectbymobile(contactsList.get(i).getMobile(), 2);
				if(tjyUserList.size()>0) {
					TjyUser tu = (TjyUser)tjyUserList.get(0);
					relList.add(tu);
				}
			}
			return super.getAjaxResult(rr, ResponseCode.OK, "获取成功", relList);
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
