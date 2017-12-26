package com.wing.enterprise.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.EntryCustomer;
import org.com.wing.enterprise.service.IEntryCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.im.IMUtil;
/**
 * 
 * <p>Title:客服管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月8日 下午8:38:39
 */
@Controller
@RequestMapping("qfy/entrycustomer")
public class EntryCustomerAction extends BaseAction {

	@Autowired
	private IEntryCustomerService entryCustomerService;
	@Autowired
    private ITjyUserService tjyUserService;
	
	/**
	 * 客服列表
	 */
	@RequiresPermissions("entrycustomer:read")
	@RequestMapping("load")
	public String classload(ModelMap map,String pid){
		
		map.put("pid", pid);
		return "qfy/entrycustomer/list";
	}
	
	/**
	 * 客服列表数据查询
	 * @param param
	 * @param activitytag
	 * @return
	 */
	@RequiresPermissions("entrycustomer:read")
	@RequestMapping("query")
	public ModelAndView classquery(PageParam param,EntryCustomer entryCustomer,String entryId){
		entryCustomer.setEntryId(entryId);
		return ajaxJsonEscape(entryCustomerService.selectEntryCustomer(param, entryCustomer));
	}
	
	//新增页面
 	@RequiresPermissions("entrycustomer:add")
    @RequestMapping("addPage")
    public String addPage(ModelMap map,String pid){
 		map.addAttribute("pid", pid);
    	return "qfy/entrycustomer/edit";
    }
 	
 	@RequiresPermissions("entrycustomer:add")
 	@RequestMapping("validUserId")
    public @ResponseBody Map validUserId(String ssUserId){
        Map res = new HashMap();
        List<TjyUser> users = tjyUserService.selectbymobile(ssUserId, 2);
        if(CollectionUtils.isEmpty(users)){
            res.put("flag", 0);
        }else{
            res.put("flag", 1);
        }
        
        res.put("code", Constants.AJAX_CODE_SUCCESS);
        return res;
        
    }
	//新增保存
 	 @RequiresPermissions("entrycustomer:add")
     @RequestMapping("add")
     public ModelAndView add(EntryCustomer dto){
 		dto.setCreateTime(new Date());
		dto.setCreateUserId(ServletUtil.getMember().getId());
		
     	return ajaxDone(entryCustomerService.addEntryCustomer(dto));
     }

 	 //修改页面
 	 @RequiresPermissions("entrycustomer:update")
     @RequestMapping("updatePage")
     public String updatePage(String id,ModelMap map){
 		EntryCustomer entrycustomer = entryCustomerService.getById(id);
 		if(entrycustomer==null){
 			return NODATA;
 		}
 		map.addAttribute("entrycustomer", entrycustomer);
     	return "qfy/entrycustomer/edit";
     }

 	 //修改保存
 	@RequiresPermissions("entrycustomer:update")
    @RequestMapping("update")
    public ModelAndView update(EntryCustomer dto){
 		EntryCustomer ec = entryCustomerService.getById(dto.getId());
 		if(!ec.getCustomerName().equals(dto.getCustomerName())){
 			//通知IM
 			String headPortrait = "";
 			String uid = "";
 	 		List<TjyUser> list = tjyUserService.selectbymobile(dto.getCustomerPhoneNum(), 2);
 	 		if(list.size()>0&&list!=null){
 	 			headPortrait = list.get(0).getHeadPortrait();
 	 			uid = list.get(0).getId();
 	 		}
 	 		IMUtil.updateUserOne(uid, dto.getCustomerName(), headPortrait);
 		}
    	return ajaxDone(entryCustomerService.updateentrycustomer(dto));
    }

 	//删除
 	@RequiresPermissions("entrycustomer:delete")
    @RequestMapping("del")
    public ModelAndView del(String [] ids){
    	return ajaxDone(entryCustomerService.delete(ids));
    }
}
