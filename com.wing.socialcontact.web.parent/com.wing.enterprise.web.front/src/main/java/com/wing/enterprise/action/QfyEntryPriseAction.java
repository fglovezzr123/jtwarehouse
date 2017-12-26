package com.wing.enterprise.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.wing.enterprise.bean.EntryPrise;
import org.com.wing.enterprise.service.IEntryImgsService;
import org.com.wing.enterprise.service.IEntryPriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.QfyConstants;
import com.wing.socialcontact.util.RedisCache;

/**
 * 企服管理
 * 
 * 
 * @ClassName: QfyEntryPriseAction
 * @Description: TODO
 * @author: sino
 * @date:2017年5月9日
 */
@Controller
@RequestMapping("/m/qfy/")
public class QfyEntryPriseAction extends BaseAction {

	@Autowired
	private IEntryPriseService entryPriseService;
	@Autowired
	private IEntryImgsService entryImgsService;
	@Autowired
	private RedisCache redisCache;
	
	@RequestMapping("entryDetailPage")
    public String homePage(String entryId,ModelMap map,String page){
	    map.addAttribute("entryId",entryId);
	    map.addAttribute("page",page);
        return "entryDetail";
    }
	
	@RequestMapping("entryDetailBanner")
    public @ResponseBody Map selBannerList(String entryId){
        List list = entryImgsService.selectByParam(entryId, QfyConstants.QFY_ENTRY_IMG_BANNER);
        return super.getSuccessAjaxResult("获取成功！", list);
    }
	
	@RequestMapping("entryDetail")
	public @ResponseBody Map entryDetail(HttpServletRequest request, HttpServletResponse response,String entryId) {
	    String userId = checkLogin(request);
        if(userId == null){
             return super.getAjaxResult("302", "登录超时，请重新登录", null); 
        }
	    
	    Map res = new HashMap();
	    
	    //企服详情
	    EntryPrise entryPrise = entryPriseService.getEntryPriseByid(entryId);
	    
	    //认证图片
	    List<Map> reconImgs = entryImgsService.selectByParam(entryId,QfyConstants.QFY_ENTRY_IMG_RECON);
	    
	    //客服电话
	    String cusPhone = "";
	    if(entryPrise != null){
	        String phone = entryPrise.getPhone();
	        String[] phones = phone.split(",");
	        
	        ValueWrapper rv = redisCache.get(QfyConstants.ENTRY_CUSTOMER_CACHE_PHONE_FOR_H5+entryId);
	        int vNum = 0;
	        if(phones != null && phones.length > 0){
	            if(rv != null){
	                int rvalue =  (int) rv.get();
	                if(rvalue < phones.length){
	                    vNum = rvalue;
	                }
	                cusPhone = phones[vNum];
	                vNum++;
	                redisCache.put(QfyConstants.ENTRY_CUSTOMER_CACHE_PHONE_FOR_H5+entryId,vNum);
	            }else{
	                if(phones.length > 0){
	                    cusPhone = phones[0];
	                }
	                redisCache.put(QfyConstants.ENTRY_CUSTOMER_CACHE_PHONE_FOR_H5+entryId,1);
	            }
	        }
	        
	    }
	    res.put("phone", cusPhone);
	    //客服ID，用于聊天
	    
	    String[] uids = entryPriseService.selCustomer(entryId);
	    String toUid = "";
	    ValueWrapper rv = redisCache.get(QfyConstants.ENTRY_CUSTOMER_CACHE_FOR_H5+entryId);
	    int vNum = 0;
	    if(uids != null && uids.length > 0){
    	    if(rv != null){
    	        int rvalue =  (int) rv.get();
                if(rvalue < uids.length){
                    vNum = rvalue;
                }
                toUid = uids[vNum];
                vNum++;
                redisCache.put(QfyConstants.ENTRY_CUSTOMER_CACHE_FOR_H5+entryId,vNum);
    	    }else{
    	        if(uids.length > 0){
    	            toUid = uids[0];
    	        }
    	        redisCache.put(QfyConstants.ENTRY_CUSTOMER_CACHE_FOR_H5+entryId,1);
    	    }
	    }
	    
        res.put("toUid", toUid);
	    
	    res.put("entryPrise", entryPrise);
	    res.put("reconImgs", reconImgs);
	    
	    return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
	}
	

}
