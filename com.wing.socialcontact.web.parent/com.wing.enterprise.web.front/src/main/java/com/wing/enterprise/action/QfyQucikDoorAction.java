package com.wing.enterprise.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.wing.enterprise.service.IEntryPriseService;
import org.com.wing.enterprise.service.IEntryQuickDetailBannerService;
import org.com.wing.enterprise.service.IEntryQuickDetailClassService;
import org.com.wing.enterprise.service.IEntryQuickDoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.util.Constants;

/**
 * 聚合页
 * 
 * 
 * @ClassName: QfyQucikDoorAction
 * @Description: TODO
 * @author: sino
 * @date:2017年5月10日
 */
@Controller
@RequestMapping("/m/qfy/")
public class QfyQucikDoorAction extends BaseAction {

	@Autowired
	private IEntryQuickDoorService entryQuickDoorService;
	@Autowired
	private IEntryQuickDetailClassService entryQuickDetailClassService;
	@Autowired
	private IEntryQuickDetailBannerService entryQuickDetailBannerService;
	@Autowired
	private IEntryPriseService entryPriseService;
	
	
	@RequestMapping("quickIndexPage")
    public String homePage(String id,ModelMap map,String quickName){
	    map.addAttribute("quick",entryQuickDoorService.selectByPrimaryKey(id));
	    
        return "quickDoor";
    }
	 
	@RequestMapping("selQucik")
	public @ResponseBody Map selQucik(HttpServletRequest request, HttpServletResponse response,
	        Integer page,Integer size,String quickDoorId,String detailClassId,String searchName) {
	    
	    String userId = checkLogin(request);
        if(userId == null){
            return super.getAjaxResult("302", "登录超时，请重新登录", null); 
        }
        
        if (page==null||page<1) {
            page = 1;
        }
        if (size==null||size<1) {
            size = 10;
        }
        
        PageParam param = new PageParam();
        param.setRows(size);
        param.setPage(page);
        
        Map res = new HashMap();
        
        //聚合页分类
        List<Map> classes = entryQuickDetailClassService.selectClassByQdId(quickDoorId);
        
        //企服列表
        DataGrid dg = entryPriseService.selQuickEntrys(param,quickDoorId, detailClassId,searchName);
        
        res.put("classes", classes);
        res.put("entrys", dg.getRows());
        res.put("totolSize", dg.getTotal());
        
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
	}
	@RequestMapping("quickBanner")
    public @ResponseBody Map selBannerList(String quickDoorId){
        List list = entryQuickDetailBannerService.selectBannerByQdId(quickDoorId);
        return super.getSuccessAjaxResult("获取成功！", list);
    }
}
