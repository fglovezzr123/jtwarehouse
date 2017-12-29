package com.wing.socialcontact.front.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.util.StringUtil;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.service.wx.api.IBannerService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * banner
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/m/banner")
public class BannerAction extends BaseAction{
	
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
	@RequestMapping("selBannerList")
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
	}
	@RequestMapping("selguideBannerList")
	public @ResponseBody Map selguideBannerList(HttpServletRequest req){
			List list = bannerService.selectByColumnType("guidepage");
			return super.getSuccessAjaxResult("获取成功！", list);
	}

}
