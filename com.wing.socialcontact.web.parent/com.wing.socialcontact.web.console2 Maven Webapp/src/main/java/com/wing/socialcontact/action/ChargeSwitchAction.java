package com.wing.socialcontact.action;

import java.util.Date;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.IChargeSwitchService;
import com.wing.socialcontact.service.wx.bean.ChargeSwitch;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 平台收费比例配置及开关
 *
 * @ClassName: ChargeSwitchAction
 * @Description: app端配置平台收费比例配置及开关
 * @author 刘涛
 */
@Controller
@RequestMapping("/chargeswitch")
public class ChargeSwitchAction extends BaseAction
{
	@Autowired
	private IChargeSwitchService chargeSwitchService;
	/**
	 * 列表
	 *
	 * @Title: load
	 * @Description: 加载页面
	 * @param map
	 * @return: String
	 * @author 刘涛
	 */
	@RequiresPermissions("chargeswitch:read")
	@RequestMapping("load")
	public String load(ModelMap map) {
		return "chargeswitch/query";
	}
	/**
	 * ajax分页查询
	 *
	 */
	@RequiresPermissions("chargeswitch:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param, ChargeSwitch chargeSwitch) {
		return ajaxJsonEscape(chargeSwitchService.selectAllChargeSwitch(param, chargeSwitch));
	}



	/**
	 * 跳转到chargeSwitch添加页面
	 *
	 * @return
	 */
	@RequiresPermissions("chargeswitch:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map) {
		//List<UserEmpirical> userLevelList = userEmpiricalService.selectAllUserEmpirical();
		//map.addAttribute("userLevelList", userLevelList);
		return "chargeswitch/add";
	}

	/**
	 * 添加chargeswitch
	 *
	 * @param chargeswitch
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("chargeswitch:add")
	@RequestMapping("add")
	public ModelAndView add(ChargeSwitch chargeSwitch, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		chargeSwitch.setCreateTime(new Date());
		chargeSwitch.setCreateUserId(ServletUtil.getMember().getId());
		return ajaxDone(chargeSwitchService.addChargeSwitch(chargeSwitch));
	}

	/**
	 * 跳转到chargeSwitch修改页面
	 *
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("chargeswitch:update")
	@RequestMapping("updatePage")
	public String updatePage(String id, ModelMap map) {
		ChargeSwitch chargeSwitch = chargeSwitchService.selectByPrimaryKey(id);
		if (chargeSwitch == null) {
			return NODATA;
		}
		map.addAttribute("b", chargeSwitch);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
//		String ossurl = ossConfig.getOss_getUrl();
//		map.addAttribute("ossurl", ossurl);
		//List<UserEmpirical> userLevelList = userEmpiricalService.selectAllUserEmpirical();
		//map.addAttribute("userLevelList", userLevelList);
		return "chargeswitch/update";
	}


	/**
	 * 修改chargeSwitch
	 *
	 * @param chargeSwitch
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("chargeswitch:update")
	@RequestMapping("update")
	public ModelAndView update(ChargeSwitch chargeSwitch, Errors errors) {
		if (errors.hasErrors()) {
			ModelAndView mav = getValidationMessage(errors);
			if (mav != null)
				return mav;
		}
		return ajaxDone(chargeSwitchService.updateChargeSwitch(chargeSwitch));

	}

	/**
	 * 删除chargeSwitch
	 *
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("chargeswitch:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids) {
		return ajaxDone(chargeSwitchService.deleteChargeSwitch(ids[0]));
	}


	/**
	 * 修改状态
	 *
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("chargeswitch:add")
	@RequestMapping("updateStatus")
	@ResponseBody
	public Map<String, Object> updateStatus(String id) {
		ChargeSwitch chargeSwitch = chargeSwitchService.selectByPrimaryKey(id);
		chargeSwitch.setStatus(chargeSwitch.getStatus() == 1 ? 0 : 1);
		String result = chargeSwitchService.updateChargeSwitch(chargeSwitch);
		if (result.equals(MsgConfig.MSG_KEY_SUCCESS)) {
			return super.getAjaxResult("0", "修改状态成功", null);
		} else {
			return super.getAjaxResult("999", "修改状态失败", null);
		}
	}
}
