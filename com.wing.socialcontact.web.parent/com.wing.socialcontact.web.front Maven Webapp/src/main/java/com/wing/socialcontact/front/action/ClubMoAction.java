package com.wing.socialcontact.front.action;

import java.io.IOException;
import java.util.ArrayList;
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
import com.wing.socialcontact.service.wx.api.IClubClassService;
import com.wing.socialcontact.service.wx.api.IClubService;
import com.wing.socialcontact.service.wx.api.IMyCollectionService;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 
 * @author zhangzheng
 *	天九会所接口
 */
@Controller
@RequestMapping("/club/m")
public class ClubMoAction extends BaseAction {
	@Autowired
	private IClubClassService clubclassservice;
	@Autowired
	private IClubService clubService;
	@Autowired
	private IMyCollectionService  myCollectionService;
	
	/**
	 * 查询所有会所列表
	 */
	@RequestMapping(value="/clubList")
	public @ResponseBody Map clubList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		List<Map<String, Object>> res= new ArrayList<Map<String, Object>>();
		 res =  clubService.selectallclubs();
		return super.getSuccessAjaxResult("操作成功！",res);
	}
	/**
	 * 分类列表查询（所有）
	 */

	@RequestMapping(value="/classList")
	public @ResponseBody Map classList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		List<Map> res= new ArrayList<Map>();
		/**
		 * 查一级分类   
		 */
		List<Map> ylist =  clubclassservice.selectonelevelclass();
		if(ylist.size()>0){
			for(Map cls :ylist){
				/**
				 * 根据一级分类查二级分类
				 */
				String  id =(String) cls.get("id");
				List<Map> elist =  clubclassservice.querybyparent(id);
				cls.put("son", elist);
				res.add(cls);
			}
		}
		return super.getSuccessAjaxResult("操作成功！",res);
	}
	
	/**
	 * 根据条件查询会所列表    
	 */
	@RequestMapping(value="/list")
	public  @ResponseBody Map libraryList(HttpServletRequest request,HttpServletResponse response,
			String classid,String  key,Integer page,Integer size) throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		if (StringUtils.isEmpty(page)||page<1) {
			page = 1;
		}
		if (StringUtils.isEmpty(size)||size<1) {
			size = 10;
		}
		if (!StringUtils.isEmpty(key)) {
			/**
			 * post方式不用转码
			 */
			//key = new String(key.getBytes("ISO-8859-1"), "utf-8");
		}
		
		List<Map> res= new ArrayList<Map>();
		res =  clubService.getClubByTerm(classid,page,size,key);
/*		List<Map> res= new ArrayList<Map>();
		res =  clubService.getClubByclassid(classid);
*/		return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS,res);
	}
	
	 /**
     * 跳转到以玩会友页面
     * 
     * @return
     */
    @RequestMapping("/dePage")
    public String playHomePage(HttpServletRequest request,String id,ModelMap map ){
		Member me = ServletUtil.getMember(request);
		if (null == me) {
		return "login";
		}
		map.addAttribute("id", id);
        return "netWork/club-detail";
    }
	
	/**
	 * 查看会所详情
	 */
	@RequestMapping(value="/detail")
	public @ResponseBody Map libraryDetail(HttpServletRequest request,HttpServletResponse response,
			String clubid) throws IOException {
		Member me = (Member) request.getSession().getAttribute("me");
		if (null == me) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}
		String userId = me.getId();
		if (StringUtils.isEmpty(userId)) {
			return super.getAjaxResult("302", "登录超时，请重新登录", null);
		}

		if (StringUtils.isEmpty(clubid)) {
			return super.getAjaxResult("999", "参数错误", null);
		}
		Map res= new HashMap();
		 res =  clubService.selectClubByid(clubid);
		 boolean iscol=myCollectionService.iscollected(userId, clubid, 2);
		 res.put("iscollection", iscol);
		return super.getSuccessAjaxResult("操作成功！",res);
	}
	
	
}
