package com.wing.socialcontact.action;

import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.action.BaseAction;

/**
 * 操作类型（1：金额2：J币3：互助宝）钱包管理
 * 
 * @author gaojun
 *
 */
@Controller
@RequestMapping("/walletLog")
public class WalletLogAction extends BaseAction {

	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private IWalletLogService walletLogService;

	/**
	 * 互助宝列表
	 * 
	 * @return
	 */
	@RequiresPermissions("walletLog:read")
	@RequestMapping("hzb_list")
	public String hzbList(ModelMap map) {
		return "walletLog/hzb_list";
	}
	/**
	 * J币列表
	 * 
	 * @return
	 */
	@RequiresPermissions("walletLog:read")
	@RequestMapping("jb_list")
	public String jbList(ModelMap map) {
		return "walletLog/jb_list";
	}
	/**
	 * 人民币余额列表
	 * 
	 * @return
	 */
	@RequiresPermissions("walletLog:read")
	@RequestMapping("rmb_list")
	public String rmbList(ModelMap map) {
		return "walletLog/rmb_list";
	}
	

	@RequiresPermissions("walletLog:read")
	@RequestMapping("hzb_query")
	public ModelAndView hzbQuery(PageParam param, String nickname, String type, String remark,String mobile,String userId) {
		return ajaxJsonEscape(walletLogService.selectAllWalletLog(param, nickname, "3", remark,mobile,userId));
	}
	@RequiresPermissions("walletLog:read")
	@RequestMapping("jb_query")
	public ModelAndView jbQuery(PageParam param, String nickname, String type, String remark,String mobile,String userId) {
		return ajaxJsonEscape(walletLogService.selectAllWalletLog(param, nickname, "2", remark,mobile,userId));
	}
	@RequiresPermissions("walletLog:read")
	@RequestMapping("rmb_query")
	public ModelAndView rmbQuery(PageParam param, String nickname, String type, String remark,String mobile,String userId) {
		return ajaxJsonEscape(walletLogService.selectAllWalletLog(param, nickname, "1", remark,mobile,userId));
	}
	
	
	@RequestMapping("addHzbPage")
	public String addHzbPage(ModelMap map) {
		return "walletLog/hzb_add";
	}
	@RequestMapping("addJbPage")
	public String addJbPage(ModelMap map) {
		return "walletLog/jb_add";
	}
	@RequestMapping("addRmbPage")
	public String addRmbPage(ModelMap map) {
		return "walletLog/rmb_add";
	}
	
	
	
	@RequiresPermissions("walletLog:add")
	@RequestMapping("add")
	public ModelAndView add(WalletLog walletLog,String mobile,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		WxUser user = wxUserService.selectByMobile(mobile);
		if(null == user){
			return ajaxDoneTextError("该手机用户未在本系统中注册！");
		}
		//TjyUser tjyuser = tjyUserService.selectByPrimaryKey(user.getId().toString());
		walletLog.setUserId(user.getId().toString());
		walletLog.setDeleted("0");
		walletLog.setCreateTime(new Date());
		///1：金额2：J币3：互助宝
		double amount=0.0;
		if("1".equals(walletLog.getType())){
			if(null != user.getAvailablebalance()){
				//1:+ 2:-
				if("1".equals(walletLog.getPdType())){
					amount= user.getAvailablebalance()+walletLog.getAmount();
				}else if("2".equals(walletLog.getPdType())){
					amount= user.getAvailablebalance()-walletLog.getAmount();
				}
			}else{
				if("1".equals(walletLog.getPdType())){
					amount= amount+walletLog.getAmount();
				}else if("2".equals(walletLog.getPdType())){
					amount= amount-walletLog.getAmount();
				}
			}
			if(amount<0.0){
				return ajaxDoneTextError("该手机用户人民币余额不足！");
			}else{
				walletLog.setYeAmount(amount);
				walletLogService.addWalletLog(walletLog);
				user.setAvailablebalance(amount);
				wxUserService.updateWxUser(user);
			}
		}else if("2".equals(walletLog.getType())){
			if(null != user.getJbAmount()){
				//1:+ 2:-
				if("1".equals(walletLog.getPdType())){
					amount= user.getJbAmount()+walletLog.getAmount();
				}else if("2".equals(walletLog.getPdType())){
					amount= user.getJbAmount()-walletLog.getAmount();
				}
			}else{
				if("1".equals(walletLog.getPdType())){
					amount= amount+walletLog.getAmount();
				}else if("2".equals(walletLog.getPdType())){
					amount= amount-walletLog.getAmount();
				}
			}
			if(amount<0.0){
				return ajaxDoneTextError("该手机用户J币余额不足！");
			}else{
				walletLog.setYeAmount(amount);
				walletLogService.addWalletLog(walletLog);
				user.setJbAmount(amount);
				wxUserService.updateWxUser(user);
			}
		}else if("3".equals(walletLog.getType())){
			if(null != user.getHzbAmount()){
				//1:+ 2:-
				if("1".equals(walletLog.getPdType())){
					amount= user.getHzbAmount()+walletLog.getAmount();
				}else if("2".equals(walletLog.getPdType())){
					amount= user.getHzbAmount()-walletLog.getAmount();
				}
			}else{
				if("1".equals(walletLog.getPdType())){
					amount= amount+walletLog.getAmount();
				}else if("2".equals(walletLog.getPdType())){
					amount= amount-walletLog.getAmount();
				}
			}
			if(amount<0.0){
				return ajaxDoneTextError("该手机用户互助宝余额不足！");
			}else{
				walletLog.setYeAmount(amount);
				walletLogService.addWalletLog(walletLog);
				user.setHzbAmount(amount);
				wxUserService.updateWxUser(user);
			}
		}
		
		return  ajaxDone(MsgConfig.MSG_KEY_SUCCESS);	
	}
	

    @RequiresPermissions("walletLog:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){	
		return ajaxDone(walletLogService.deletewalletLog(ids));
	}


}
