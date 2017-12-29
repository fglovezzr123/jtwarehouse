package com.wing.enterprise.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.wing.enterprise.service.IEntryPriseService;
import org.com.wing.enterprise.service.IEntryServiceClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.service.IDistrictService;
import com.wing.socialcontact.util.Constants;

/**
 * H5首页
 * 
 * 
 * @ClassName: QfyIndexAction
 * @Description: TODO
 * @author: sino
 * @date:2017年5月9日
 */
@Controller
@RequestMapping("/m/qfy/")
public class QyfClassTableAction extends BaseAction {

	@Autowired
    private IEntryPriseService entryPriseService;
	@Autowired
	private IEntryServiceClassService entryServiceClassService;
	@Autowired
    private IDistrictService districtService;
	
	
	@RequestMapping("classTablePage")
    public String homePage(ModelMap map,String classId){
	    map.addAttribute("classService",entryServiceClassService.selectByPrimaryKey(classId));
	    
	    // 获取省
        List provinceList = districtService.selectDistrictByType("1");
        // 获取市
        List cityList = districtService.selectDistrictByType("2");
        map.addAttribute("provinceList", provinceList);
        map.addAttribute("cityList", cityList);
        
        return "classTable";
    }
	@RequestMapping("classTable")
    public @ResponseBody Map classTable(HttpServletRequest request, HttpServletResponse response,
            String timeSort,String serviceCountSort,String classId,String prov,String city,Integer page,Integer size,String searchName) {
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
        
        if(prov != null && !"".equals(prov)){
            if(!prov.startsWith("'")){
                prov = "'"+prov+"'";
            }
        }
        if(city != null && !"".equals(city)){
            if(!city.startsWith("'")){
                city = "'"+city+"'";
            }
        }
        
        DataGrid dg = entryPriseService.selEntryPrise(param,timeSort, serviceCountSort, classId, null,prov,city,searchName);
        
        res.put("lst", dg.getRows());
        res.put("totalSize", dg.getTotal());
        
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
    }

}
