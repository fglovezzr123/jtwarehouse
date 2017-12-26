package com.wing.socialcontact.front.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.util.StringUtil;

import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.service.wx.api.IBannerService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * banner
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/m/banner")
public class BannerAction extends BaseAppAction{
	
	@Autowired
	private IBannerService bannerService;
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private ITjyUserService tjyUserService;
	
	/**
	 * 轮播图
	 * 
	 * @return
	 */
	@RequestMapping("bannerPage")
	public String bannerPage(){
		return "banner/bannerPage";
	}
	/*@RequestMapping("selBannerList")
	public @ResponseBody Map selBannerList(HttpServletRequest req,String columnType,String uid){
		RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
		Member m = ServletUtil.getMember(req);
		String userId ="";
		if(null==m){
			userId = uid;
		}else{
			userId = m.getId();
		}
		if(StringUtil.isEmpty(columnType) || StringUtil.isEmpty(userId)){
			return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
		}else{
			WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
			if(wxUser==null){
				return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, userId+"参数无效，请检查！", "");
			}
			TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
			ValueWrapper vw = redisCache.get("selBannerList_"+columnType+"_"+wxUser.getLevel()+"_"+tjyUser.getReconStatus());
			List list = null;
			if(vw != null){
				list = (List)vw.get();
			}
			if(list == null||list.size()==0){
			//	list = bannerService.selectByColumnType(columnType);
				list = bannerService.selectBannerByUserId(userId, columnType);
				//添加缓存
				redisCache.put("selBannerList_"+columnType+"_"+wxUser.getLevel()+"_"+tjyUser.getReconStatus(), list);
			}
			
			return super.getSuccessAjaxResult("获取成功！", list);
		}
	}*/
	/**
	 * 首页轮播图列表
	 * @param rr
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping("selBannerList")
	public @ResponseBody ResponseReport selBannerListApp(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response){
		RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
		String  userId = rr.getUserProperty().getUserId();
		String columnType = "3d1ba858eaab4f93b6d4d5ead09014c8";
		if(StringUtil.isEmpty(userId)){
			return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
		}else{
			WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
			if(wxUser==null){
				return super.getAjaxResult(rr,ResponseCode.NotSupport, "未登录", null);
			}
			TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
			ValueWrapper vw = redisCache.get("selBannerList_"+columnType+"_"+wxUser.getLevel()+"_"+tjyUser.getReconStatus());
			List list = null;
			if(vw != null){
				list = (List)vw.get();
			}
			if(list == null||list.size()==0){
				//	list = bannerService.selectByColumnType(columnType);
				list = bannerService.selectBannerByUserId(userId, columnType);
				//添加缓存
				redisCache.put("selBannerList_"+columnType+"_"+wxUser.getLevel()+"_"+tjyUser.getReconStatus(), list);
			}
			return super.getAjaxResult(rr,ResponseCode.OK, "获取成功！", list);
		}
	}
	@RequestMapping("selGuideList")
	public @ResponseBody ResponseReport selguideBannerList(@RequestBody RequestReport rr,HttpSession session, HttpServletResponse response){
			List list = bannerService.selectByColumnType("guidepage");
			return super.getAjaxResult(rr,ResponseCode.OK, "获取成功！", list);
	}

}
