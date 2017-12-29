package com.wing.socialcontact.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ILibraryLiveService;
import com.wing.socialcontact.service.wx.api.IliveSignupService;
import com.wing.socialcontact.service.wx.bean.TjyLibraryLive;
import com.wing.socialcontact.service.wx.bean.TjyLiveSignup;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.UUIDGenerator;
@Controller
@RequestMapping("librarylive")
public class LibraryLiveAction extends BaseAction {

	private static Logger logger = LoggerFactory.getLogger(TjyLibraryLive.class);
	@Autowired
	private ILibraryLiveService libraryLiveService;
	@Autowired
	private IliveSignupService liveSignupService;
	
	/**
	 * 直播列表
	 */
	@RequestMapping("load")
	public String load(ModelMap map){
		return "system/librarylive/load";
	}
	
	/**
	 * 列表查询
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,TjyLibraryLive t,Date stime ,Date etime){
		DataGrid grid = libraryLiveService.selectAllLibraryLive(param,t,stime,etime);
		List<TjyLibraryLive> list = grid.getRows();
		return ajaxJsonEscape(grid);
	}
	
	/**
	 * 新增直播页面
	 * @return
	 */
	@RequestMapping("addPage")
	public String addPage(){
		return "system/librarylive/addorupdate";
	}
	/**
	 * 修改直播页面
	 * @return
	 */
	@RequestMapping("updatePage")
	public String updatePage(ModelMap modelMap,String id){
		//直播详情
		TjyLibraryLive l = libraryLiveService.getLibraryLive(id);
		modelMap.addAttribute("obj", l);
		
		return "system/librarylive/addorupdate";
	}
	/**
	 * 新增或修改保存
	 * @return
	 */
	@RequestMapping(value="addupdate",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveOrUpdateMeeting(TjyLibraryLive t){
		try {
			t.setDeleted(0);
			t.setStatus(1);
			if(t.getId()==null||t.getId().trim().length()==0){
				t.setId(UUIDGenerator.getUUID());
				t.setCreateTime(new Date());
				t.setCreateUserId(ServletUtil.getMember().getId());
				libraryLiveService.insertTjyLibraryLive(t);
				return getAjaxResult("0", "", "");
			}else{
				t.setUpdateTime(new Date());
				t.setUpdateUserId(ServletUtil.getMember().getId());
				libraryLiveService.updateTjyLibraryLive(t);
				return getAjaxResult("0", "", "");
			}
		} catch (Exception e) {
			logger.error("保存直播信息失败", e);
			return getAjaxResult("-1", "保存失败", "");
		}
	}
	
	/**
	 * 详情直播页面
	 * @return
	 */
	@RequestMapping("viewPage")
	public String viewPage(ModelMap modelMap,String id){
		//直播详情
		TjyLibraryLive l = libraryLiveService.getLibraryLive(id);
		modelMap.addAttribute("obj", l);
		
		return "system/librarylive/view";
	}
	
	
	@RequestMapping("del")
	public ModelAndView del(String id) {
		TjyLibraryLive t = libraryLiveService.getLibraryLive(id);
		t.setDeleted(1);
		libraryLiveService.updateTjyLibraryLive(t);
		return ajaxDone(MsgConfig.MSG_KEY_SUCCESS);
	}
	
	
	/**
	 * 直播报名列表
	 */
	@RequestMapping("userload")
	public String userload(ModelMap map,String id){
		map.put("id", id);
		return "system/librarylive/loaduser";
	}
	
	/**
	 * 列表查询
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("userquery")
	public ModelAndView userquery(PageParam param,String id,TjyLiveSignup t){
		DataGrid grid = liveSignupService.selectAllLiveuser(param, id,t);
		List<TjyLibraryLive> list = grid.getRows();
		return ajaxJsonEscape(grid);
	}
}
