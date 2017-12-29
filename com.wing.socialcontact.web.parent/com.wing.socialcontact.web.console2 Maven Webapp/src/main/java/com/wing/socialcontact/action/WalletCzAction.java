package com.wing.socialcontact.action;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IListValuesService;

/**
 * 钱包充值提醒
 * 
 * @ClassName: WalletCzAction
 * @Description: TODO
 * @author: zengmin
 * @date: 2017年5月22日 下午3:06:56
 */
@Controller
@RequestMapping("/walletCz")
public class WalletCzAction extends BaseAction {
	@Autowired
	private IWalletLogService walletLogService;
	@Autowired
	private IListValuesService listValuesService;

	/**
	 * 列表页
	 * 
	 * @return
	 */
	// @RequiresPermissions("walletTx:read")
	@RequestMapping("load")
	public String load(ModelMap map) {
		ListValues listValue = listValuesService.selectByPrimaryKey("fbc8733aafed4fd9ac4f341b155b5311");
		map.addAttribute("listValue", listValue);
		return "walletCz/walletCz_list";
	}

	// @RequiresPermissions("walletTx:read")
	/**
	 * 充值记录查询
	 * 
	 * @Title: query
	 * @Description: TODO
	 * @param param
	 * @param nickname
	 *            用户名称
	 * @param type
	 *            充值类型
	 * @param date
	 *            充值时间
	 * @param mobile
	 *            用户手机
	 * @param status
	 *            充值状态
	 * @return
	 * @return: ModelAndView
	 * @author: zengmin
	 * @date: 2017年5月22日 下午3:11:29
	 */
	@RequestMapping("query")
	public ModelAndView query(PageParam param, String nickname, String type, String date, String mobile,
			String status) {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(nickname)) {
			mapParam.put("nickname", nickname);
		}
		if (!StringUtils.isEmpty(type)) {
			mapParam.put("type", type);
		}
		if (!StringUtils.isEmpty(date)) {
			mapParam.put("createTime", date);
		}
		if (!StringUtils.isEmpty(mobile)) {
			mapParam.put("mobile", mobile);
		}
		if (!StringUtils.isEmpty(status)) {
			mapParam.put("payStatus", status);
		}
		return ajaxJsonEscape(walletLogService.selectCzWalletLogByParam(param, mapParam));
	}

	/**
	 * 调整比例
	 * 
	 * @Title: tzblPage
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年5月22日 下午4:12:42
	 */
	@RequestMapping("tzblPage")
	public String tzblPage(ModelMap map) {
		ListValues listValue = listValuesService.selectByPrimaryKey("fbc8733aafed4fd9ac4f341b155b5311");
		if (null != listValue) {
			map.addAttribute("listValue", listValue);
		}
		return "walletCz/tzbl";
	}

	/**
	 * 设置人民币J币比例
	 * 
	 * @Title: tzblSave
	 * @Description: TODO
	 * @param tzbl
	 * @return
	 * @return: ModelAndView
	 * @author: zengmin
	 * @date: 2017年5月22日 下午5:29:47
	 */
	@RequestMapping("tzblSave")
	public ModelAndView tzblSave(String tzbl) {
		// fbc8733aafed4fd9ac4f341b155b5311--固定id,人民币J币比例
		ListValues listValue = listValuesService.selectByPrimaryKey("fbc8733aafed4fd9ac4f341b155b5311");
		listValue.setListValue(tzbl);
		listValue.setListDesc("1RMB=" + tzbl + "J币");
		return ajaxDone(listValuesService.updateListValues(listValue));
	}

}
