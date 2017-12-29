package com.wing.socialcontact.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IListValuesService;

/**
 * 共用文章控制器
 * 
 * @ClassName: ConfigAction
 * @Description: TODO
 * @author: zengmin
 * @date: 2017年7月7日 下午3:56:05
 */
@Controller
@RequestMapping("/config")
public class ConfigAction extends BaseAction {

	@Autowired
	private IListValuesService listValuesService;

	/**
	 * 列表
	 * 
	 * @Title: load
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年6月29日 上午8:58:49
	 */
	@RequiresPermissions("inviteRecord:manager")
	@RequestMapping("load")
	public String load(ModelMap map,String type) {
		if(StringUtils.isEmpty(type)){
			type = "888";
		}
		List<Map<String, Object>> listValues = listValuesService.selectListByType(Integer.valueOf(type));
		if (null != listValues && !listValues.isEmpty()) {
			map.put("yqConfig", listValues.get(0));
		}else{
			ListValues lv = new ListValues();
			lv.setListType(Integer.valueOf(type));
			map.put("yqConfig", lv);
		}
		return "config/manager";
	}
	
	/**
	 * 列表
	 * 
	 * @Title: load
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年6月29日 上午8:58:49
	 */
	@RequiresPermissions("inviteRecord:manager")
	@RequestMapping("load_url")
	public String load_url(ModelMap map,String type) {
		if(StringUtils.isEmpty(type)){
			type = "888";
		}
		List<Map<String, Object>> listValues = listValuesService.selectListByType(Integer.valueOf(type));
		if (null != listValues && !listValues.isEmpty()) {
			map.put("yqConfig", listValues.get(0));
		}else{
			ListValues lv = new ListValues();
			lv.setListType(Integer.valueOf(type));
			map.put("yqConfig", lv);
		}
		return "config/manager_url";
	}

	/**
	 * 添加indexAd
	 * 
	 * @param indexAd
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("inviteRecord:manager")
	@RequestMapping("manager")
	public ModelAndView add(ListValues listValues, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		String result = "";
		if (StringUtils.isEmpty(listValues.getId())) {
			listValues.setId(null);
			if(null == listValues.getListType()){
				listValues.setListType(888);
			}
			listValues.setSortno(1);
			listValues.setDeleted(0);
			listValues.setListValue("b");
			result = listValuesService.addListValues(listValues);
		} else {
			result = listValuesService.updateListValues(listValues);
		}
		return ajaxDone(result);
	}
	
	
}
