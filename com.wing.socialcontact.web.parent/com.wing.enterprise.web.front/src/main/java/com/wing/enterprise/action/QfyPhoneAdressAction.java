package com.wing.enterprise.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.com.wing.enterprise.bean.EntryPhoneAdress;
import org.com.wing.enterprise.service.IEntryPhoneAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.gexin.rp.sdk.base.uitls.MD5Util;
import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.QfyConstants;

/**
 * 通讯录
 * 
 * 
 * @ClassName: QfyPhoneAdressAction
 * @Description: TODO
 * @author: sino
 * @date:2017年5月12日
 */
@Controller
@RequestMapping("/m/qfy/")
public class QfyPhoneAdressAction extends BaseAction {

	@Autowired
	private IEntryPhoneAddressService entryPhoneAddressService;
	
	
	@RequestMapping("upPhoneAdress")
    public @ResponseBody Map selBannerList(HttpServletRequest request,String phones){
	    List<EntryPhoneAdress> ps = null;
        if(phones != null && !"".equals(phones)){
            ps = JSONArray.parseArray(phones,EntryPhoneAdress.class);
        }
	    String ssUserId = "";
        if(!CollectionUtils.isEmpty(ps)){
            for (EntryPhoneAdress entryPhoneAdress : ps) {
                ssUserId = entryPhoneAdress.getSsUserId();
                entryPhoneAdress.setId(UUID.randomUUID().toString().replace("-", ""));
                entryPhoneAdress.setCreateTime(new Date());
            }
            EntryPhoneAdress entryPhoneAdress = new EntryPhoneAdress();
            entryPhoneAdress.setSsUserId(ssUserId);
            List lst = entryPhoneAddressService.selectPhoneAdress(entryPhoneAdress);
            if(lst != null && lst.size() >0){
            }else{
                boolean bo = entryPhoneAddressService.insertPhoneAdressBatch(ps);
                if (!bo) {
                    return super.getAjaxResult("999", "上传失败", null);
                }
            }
        }
        return super.getSuccessAjaxResult();
    }
	@RequestMapping("selPaUpStatus")
    public @ResponseBody Map selPaUpStatus(HttpSession session,HttpServletRequest request, HttpServletResponse response,String key,String m) {

        String userId = checkLogin(request);
        if(userId == null){
            if(StringUtils.isEmpty(m) && StringUtils.isEmpty(key)){
                return super.getAjaxResult("302", "登录超时，请重新登录", null);
            }
            if(!MD5Util.getMD5Format(QfyConstants.LOGIN_KEY+m).equals(key)){
                return super.getAjaxResult("302", "登录超时，请重新登录", null); 
            }else{
               userId = m; 
            }
        }
	    EntryPhoneAdress entryPhoneAdress = new EntryPhoneAdress();
	    entryPhoneAdress.setSsUserId(userId);
	    List lst = entryPhoneAddressService.selectPhoneAdress(entryPhoneAdress);
	    Map res = new HashMap();
	    if(lst != null && lst.size() >0){
	        res.put("status", "T");
	    }else{
	        res.put("status", "F");
	    }
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
    }
}
