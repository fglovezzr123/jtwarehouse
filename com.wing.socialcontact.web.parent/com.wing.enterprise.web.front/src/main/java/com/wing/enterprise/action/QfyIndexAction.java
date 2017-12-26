package com.wing.enterprise.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.wing.enterprise.bean.EntryDescConfig;
import org.com.wing.enterprise.bean.EntryPrise;
import org.com.wing.enterprise.service.IEntryDescConfigService;
import org.com.wing.enterprise.service.IEntryPriseService;
import org.com.wing.enterprise.service.IEntryQuickDoorService;
import org.com.wing.enterprise.service.IEntryServiceTagService;
import org.com.wing.enterprise.service.IEntryTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.util.StringUtil;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IBannerService;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.QfyConstants;

/**
 * 分类首页
 * 
 * 
 * @ClassName: QfyIndexAction
 * @Description: TODO
 * @author: sino
 * @date:2017年5月9日
 */
@Controller
@RequestMapping("/m/qfy/")
public class QfyIndexAction extends BaseAction {

	@Autowired
	private IEntryQuickDoorService entryQuickDoorService;
	@Autowired
	private IEntryDescConfigService entryDescConfigService;
	@Autowired
	private IEntryServiceTagService entryServiceTagService;
	@Autowired
	private IEntryPriseService entryPriseService;
	@Autowired
	private IBannerService bannerService;
	@Autowired
	private IEntryTagService entryTagService;
	
	@RequestMapping("indexPage")
    public String homePage(HttpServletRequest request){
        return "index";
    }
	 
	@RequestMapping("index")
	public @ResponseBody Map index(HttpServletRequest request, HttpServletResponse response,Integer page,Integer size,String searchName) {
	    
	    
	    String userId = checkLogin(request);
        if(userId == null){
            return super.getAjaxResult("302", "登录超时，请重新登录", null); 
        }
        
        Map res = new HashMap();
        
        //快捷入口
        List qds = entryQuickDoorService.selectH5IndexQuickDoors(3);
        //首页联盟介绍图片
        EntryDescConfig edc = new EntryDescConfig();
        edc.setType(QfyConstants.QFY_INDEX_DESC_TYPE);
        List descConfig = entryDescConfigService.selectDescConfig(edc);
        Map indexDescConfig = null;
        if(null != descConfig){
            indexDescConfig = (Map) descConfig.get(0);
        }
        //TODO 热门服务
        List hots = entryServiceTagService.selectHotServiceBySize(12);
        //TODO 精选企服
        if (page==null||page<1) {
            page = 1;
        }
        if (size==null||size<1) {
            size = 10;
        }
        EntryPrise ep = new EntryPrise();
        ep.setIsGood(1);
        
        PageParam param = new PageParam();
        param.setRows(size);
        param.setPage(page);
        
        DataGrid dg = entryPriseService.selEntryPrises(param, ep,searchName);
        
        List<Map> entrys = dg.getRows();
        List<Map> entryTags = null;
        List<Map> goodEntrys = new ArrayList<>();
        String entryId = "";
        if(!CollectionUtils.isEmpty(entrys)){
            for (Map map : entrys) {
                map.put("titleDesc", "");
                map.put("entryDesc", "");
                map.put("detailDesc", "");
                map.put("serviceCase", "");
                entryId = (String) map.get("id");
                entryTags = entryTagService.selTagsByEntryId(entryId);
                map.put("entryTags", entryTags);
                goodEntrys.add(map);
            }
        }
        res.put("qds", qds);
        res.put("indexDescConfig", indexDescConfig);
        res.put("hots", hots);
        res.put("entrys", goodEntrys);
        res.put("totalSize", dg.getTotal());
        
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
	}
	@RequestMapping("selBannerList")
    public @ResponseBody Map selBannerList(String columnType){
        if(StringUtil.isEmpty(columnType)){
            return super.getAjaxResult(Constants.AJAX_CODE_ERROR_PARAM, "参数无效，请检查！", "");
        }else{
            List list = bannerService.selectByColumnType(columnType);
            return super.getSuccessAjaxResult("获取成功！", list);
        }
    }
}
