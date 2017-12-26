package com.wing.socialcontact.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.commons.util.ApplicationPath;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.WeixinUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 同步微信账号控制器
 * 
 * @author zengmin
 *
 */
@Controller
@RequestMapping("/wx")
public class SynWxUserAction extends BaseAction {

	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IWxUserService wxUserService;

	/**
	 * 同步微信企业号用户状态，根据用户手机号来检测
	 * 
	 * @param param
	 * @param userId
	 * @return
	 */
	@RequestMapping("syn_user")
	public ModelAndView synUser() {
		String corpid = ApplicationPath.getParameter("wx_corpid_qyh");
		String secret = ApplicationPath.getParameter("wx_secret_qyh");
		JSONObject result = WeixinUtil.getUserDetailByOrgId(corpid, secret, "1", "1", "1");
		System.out.println(result);
		if (null != result) {
			try {
				int errcode = result.getInt("errcode");
				if (errcode == 0) {
					JSONArray ja = result.getJSONArray("userlist");
					JSONObject jo = null;
					for (int i = 0; i < ja.size(); i++) {
						jo = ja.getJSONObject(i);
						String mobile = jo.getString("mobile");
						String userid = jo.getString("userid");
						WxUser wxUser = wxUserService.selectByWxUserId(userid);
						// 先根据userid去获取wxUserId=userid的用户，如果获取不到再通过认证手机号去查找
						if (null != wxUser) {
							TjyUser tjyUser = tjyUserService.selectByPrimaryKey(wxUser.getId() + "");
							if (null != tjyUser.getReconStatus() && tjyUser.getReconStatus() == 2) {
								continue;
							}
							tjyUser.setStatus(2);
							tjyUserService.updateTjyUser(tjyUser);
						} else {
							// 认证通过的用户
							List<TjyUser> userList = tjyUserService.selectbymobile(mobile, 2);
							if (null != userList && userList.size() == 1) {
								TjyUser tjyUser = userList.get(0);
								tjyUser.setStatus(2);
								wxUser = wxUserService.selectByPrimaryKey(tjyUser.getId());
								boolean bo = tjyUserService.updateTjyUser(tjyUser);
								if (bo) {
									if (!userid.equals(wxUser.getWxUserId())) {
										wxUser.setWxUserId(userid);
										wxUserService.updateWxUser(wxUser);
									}
								}
							}
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return ajaxJsonEscape(false);
			}
		}
		return ajaxJsonEscape(true);
	}
}
