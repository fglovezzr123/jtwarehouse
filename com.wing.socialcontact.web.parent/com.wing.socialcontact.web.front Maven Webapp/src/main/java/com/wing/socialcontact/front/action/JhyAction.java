package com.wing.socialcontact.front.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IPageAggregateService;
import com.wing.socialcontact.service.wx.bean.PageAggregate;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 聚合页面控制器
 * 
 * @ClassName: JhyAction
 * @Description: TODO
 * @author: zengmin
 * @date:2017年4月2日 下午5:57:56
 */
@Controller
@RequestMapping("/m/jhy")
public class JhyAction extends BaseAction {

	@Autowired
	private IPageAggregateService pageAggregateService;

	/**
	 * 跳转至聚合页面
	 * 
	 * @Title: view
	 * @Description: TODO
	 * @param map
	 * @param id
	 * @param viewFlag
	 * @param request
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月18日 上午9:59:48
	 */
	@RequestMapping(value = "view")
	public String view(ModelMap map, String id, String viewFlag, HttpServletRequest request) {
		PageAggregate page = pageAggregateService.selectPageById(id);
		if(null == page || page.getStatus() == 0){
			map.addAttribute("url", page.getTyUrl());
			return "netWork/qhye_ty";
		}
		map.addAttribute("page", page);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		map.addAttribute("viewFlag", viewFlag);
		return "netWork/qhye";
	}
	





	/**
	 * 获取聚合页面json
	 * 
	 * @Description: TODO
	 * @param map
	 * @param id
	 * @param viewFlag
	 * @param request
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年7月18日 上午9:59:48
	 */
	@RequestMapping(value = "gjson")
	public @ResponseBody Map gjson( String id, String viewFlag, HttpServletRequest request) {
		PageAggregate page = pageAggregateService.selectPageById(id);
		if(null == page || page.getStatus() == 0){
	        return super.getAjaxResult("401", "参数错误", "");
		}
		
		Map data= new HashMap();
		data.put("page", page);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		data.put("ossurl", ossurl);
		data.put("viewFlag", viewFlag);

        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, data);
	}

}
