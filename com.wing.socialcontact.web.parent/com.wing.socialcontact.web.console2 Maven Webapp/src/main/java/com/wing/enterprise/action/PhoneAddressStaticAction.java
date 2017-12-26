package com.wing.enterprise.action;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.PhoneAdressStatic;
import org.com.wing.enterprise.service.IPhoneAddressStaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.action.BaseAction;
/**
 * 通讯录开关统计
 * 
 * 
 * @ClassName: PhoneAddressStaticAction
 * @Description: TODO
 * @author: sino
 * @date:2017年6月13日
 */
@Controller
@RequestMapping("pastatic")
public class PhoneAddressStaticAction extends BaseAction {

	@Autowired
	private IPhoneAddressStaticService phoneAddressStaticService;
	
	/**
     * 企服列表
     */
    @RequiresPermissions("pastatic:read")
    @RequestMapping("load")
    public String pastaticload(){
        return "qfy/pastatic/load";
    }
    @RequiresPermissions("pastatic:read")
    @RequestMapping("query")
    public ModelAndView pastaticquery(PageParam param,PhoneAdressStatic pas){
        return ajaxJsonEscape(phoneAddressStaticService.selPAstatic(pas));
    }
}
