package com.wing.socialcontact.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.ICouponGenerateService;
import com.wing.socialcontact.service.wx.api.ICouponLogService;
import com.wing.socialcontact.service.wx.api.ICouponService;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.Coupon;
import com.wing.socialcontact.service.wx.bean.CouponGenerate;
import com.wing.socialcontact.service.wx.bean.CouponLog;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.DoubleUtil;
import com.wing.socialcontact.util.UUIDGenerator;
import com.wing.socialcontact.util.WxMsmUtil;

/**
 * 优惠券管理
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/coupon")
public class CouponAction extends BaseAction{
	
	@Autowired
	private ICouponService couponService; 
	@Autowired
	private ICouponGenerateService couponGenerateService; 
	@Autowired
	private ICouponLogService couponLogService;
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private IMessageInfoService messageInfoService;
	@Autowired
	private IWalletLogService walletLogService;
	@Autowired
	private ITjyUserService tjyUserService;
	
	/**
	 * 条件查询优惠券
	 * 
	 * @return
	 */
	@RequiresPermissions("coupon:read")
	@RequestMapping("load")
	public String load(ModelMap map){
		return "coupon/load";
	
	}
	@RequiresPermissions("coupon:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,Coupon coupon,String startTimef,String endTimef){
		
		return ajaxJsonEscape(couponService.selectAllCoupon(param, coupon, startTimef, endTimef));
	}
	/**
	 * 跳转到优惠券添加页面
	 * @return
	 */
	@RequiresPermissions("coupon:add")
	@RequestMapping("addPage")
	public String addPage(ModelMap map){
		return "coupon/add";
	
	}
	/**
	 * 添加优惠券
	 * @param business
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("coupon:add")
	@RequestMapping("add")
	public ModelAndView add(Coupon coupon,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		coupon.setCreateTime(new Date());
		coupon.setCouponAmount(coupon.getCouponAmount()==null?0:coupon.getCouponAmount());
		coupon.setOrderMinBuy(coupon.getOrderMinBuy()==null?0:coupon.getOrderMinBuy());
		return ajaxDone(couponService.addCoupon(coupon));	
	}
	/**
	 * 跳转到优惠券修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("coupon:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		Coupon coupon = couponService.selectById(id);
		if(coupon==null){
			return NODATA;
		}
		map.addAttribute("c",coupon);
		return "coupon/update";
	}
	/**
	 * 修改优惠券
	 * @param ma
	 * @param errors
	 * @return
	 * @throws ParseException 
	 */
	@RequiresPermissions("coupon:update")
	@RequestMapping("update")
	public ModelAndView update(Coupon coupon,Errors errors) throws ParseException{
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		coupon.setCouponAmount(coupon.getCouponAmount()==null?0:coupon.getCouponAmount());
		coupon.setOrderMinBuy(coupon.getOrderMinBuy()==null?0:coupon.getOrderMinBuy());
		return ajaxDone(couponService.updateCoupon(coupon));
		
	}
	
	/**
	 * 删除优惠券
	 * @param id
	 * @return
	 */
	@RequiresPermissions("coupon:delete")
	@RequestMapping("del")
	public ModelAndView del(String id){	
		return ajaxDone(couponService.deleteCoupon(id));
	}
	/**
	 * 店铺首页
	 * @return
	 */
	@RequestMapping("storeindex")
	public String storeindex(){
		return "coupon/storeindex";
	}
	/**
	 * 店铺列表查询
	 * @return
	 */
	@RequestMapping("storequery")
	public ModelAndView storequery(PageParam param,String store_name){
		return ajaxJsonEscape(couponService.selectAllStore(param,store_name));
	}
	/**
	 * 批量生成页面
	 * @return
	 */
	@RequestMapping("batchGeneratePage")
	public String batchGeneratePage(String id,ModelMap map){
		map.addAttribute("fkId", id);
		return "coupon/plsc";
	}
	/**
	 * 批量生成
	 * @return
	 */
	@RequiresPermissions("coupon:add")
	@RequestMapping("batchGenerate")
	public ModelAndView batchGenerate(CouponGenerate cg,Errors errors,String startTime,String endTime)
			throws ParseException{
		//优惠券订单生成规则：下单时间'（年月日时分秒）'
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String code = sdf.format(new Date());
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Coupon coupon = couponService.selectById(cg.getFkId());
		if(coupon.getUseRange().equals("1")||coupon.getUseRange().equals("4")){
			String useStore = coupon.getUseStore();
			if(useStore!=""&&useStore!=null&&!"".equals(useStore)){
				String[] mobiles = useStore.split(",");
				for(int i=0;i<mobiles.length;i++){
					cg.setStartTime(sdf1.parse(startTime));
					cg.setEndTime(sdf1.parse(endTime));
					cg.setCreateTime(new Date());
					cg.setBatchNum(code+mobiles[i]);//优惠券单号
					cg.setReceiveNum(0);
					cg.setRemainNum(cg.getCouponNum());
					cg.setStoreMobile(mobiles[i]);
					couponGenerateService.addCG(cg);
				}
			}else{
				cg.setStartTime(sdf1.parse(startTime));
				cg.setEndTime(sdf1.parse(endTime));
				cg.setCreateTime(new Date());
				cg.setBatchNum(code);//优惠券单号
				cg.setReceiveNum(0);
				cg.setRemainNum(cg.getCouponNum());
				couponGenerateService.addCG(cg);
			}
		}else{
			cg.setStartTime(sdf1.parse(startTime));
			cg.setEndTime(sdf1.parse(endTime));
			cg.setCreateTime(new Date());
			cg.setBatchNum(code);//优惠券单号
			cg.setReceiveNum(0);
			cg.setRemainNum(cg.getCouponNum());
			couponGenerateService.addCG(cg);
		}
		return ajaxDone(true);
	}
	
	/**
	 * 条件查询优惠券生成记录
	 * 
	 * @return
	 */
	@RequiresPermissions("coupon:read")
	@RequestMapping("loadCG")
	public String loadCG(String id,ModelMap map){
		map.addAttribute("fkId", id);
		return "coupon/loadCG";
	
	}
	@RequiresPermissions("coupon:read")
	@RequestMapping("queryCG")
	public ModelAndView queryCG(PageParam param,CouponGenerate cg,String startTimef,String endTimef,String couponName){
		
		return ajaxJsonEscape(couponGenerateService.selectAllCG(param, cg, startTimef, endTimef, couponName));
	}
	/**
	 * 删除优惠券生成记录
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("coupon:delete")
	@RequestMapping("delCG")
	public ModelAndView delCG(String id){	
		return ajaxDone(couponGenerateService.deleteCG(id));
	}
	/**
	 * 条件查询优惠券领取记录
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@RequiresPermissions("coupon:read")
	@RequestMapping("loadCL")
	public String loadCL(String id,ModelMap map) throws ParseException{
		map.addAttribute("fkId", id);
		CouponGenerate cg = couponGenerateService.selectById(id);
		String isff = "1";
		if(cg.getRemainNum()==0){
			isff = "0";
		}
		String isgq = "1";
		//判断有效日期是否过期，如果过期，修改状态
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(cg.getEndTime().getTime()<sdf.parse(sdf.format(new Date())).getTime()){
			isgq = "0";
		}
		map.addAttribute("isff", isff);
		map.addAttribute("isgq", isgq);
		return "coupon/loadCL";
	
	}
	@RequiresPermissions("coupon:read")
	@RequestMapping("queryCL")
	public ModelAndView queryCL(PageParam param,CouponLog cl,String keyword,String couponName){
		return ajaxJsonEscape(couponLogService.selectAllCL(param, cl, keyword, couponName));
	}
	/**
	 * 发放优惠券页面
	 * 
	 * @return
	 */
	@RequiresPermissions("coupon:read")
	@RequestMapping("grantCouponPage")
	public String grantCouponPage(String id,ModelMap map){
		map.addAttribute("fkId", id);
		CouponGenerate cg = couponGenerateService.selectById(id);
		map.addAttribute("remainNum", cg.getRemainNum());
		return "coupon/grantCouponPage";
	
	}
	@RequiresPermissions("coupon:read")
	@RequestMapping("grantCoupon")
	public ModelAndView grantCoupon(String mobeils,String fkId){
		String[] arry = mobeils.split(",");
		for(int i=0;i<arry.length;i++){
			WxUser user = wxUserService.selectByMobile(arry[i]);
			if(null == user){
				return ajaxDoneTextError("该手机"+arry[i]+"用户未在本系统中注册！");
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String code = sdf.format(new Date());
		CouponGenerate cg = couponGenerateService.selectById(fkId);
		Coupon c = couponService.selectById(cg.getFkId());
		Random r = new Random();
		for(int i=0;i<arry.length;i++){
			WxUser user = wxUserService.selectByMobile(arry[i]);
			CouponLog cl = new CouponLog();
			cl.setUserId(String.valueOf(user.getId()));
			cl.setFkId(fkId);
			cl.setReceiveTime(new Date());
			cl.setCreateTime(new Date());
			cl.setCouponCode(code+r.nextInt(10000));
			//判断是否是兑换券
			if(c.getCouponType()==3){
				cl.setUseStatus(1);
				cl.setUseTime(new Date());
				//给该用户加上J币
				if(c.getCouponCoinType()==1){
					user.setJbAmount(DoubleUtil.add(user.getJbAmount(), c.getCouponAmount()));
				}else if(c.getCouponCoinType()==2){//RMB
					user.setAvailablebalance(DoubleUtil.add(user.getAvailablebalance(), c.getCouponAmount()));
				}
				wxUserService.updateWxUser(user);
				WalletLog logf = new WalletLog();
				logf.setCreateTime(new Date());
				logf.setPdType("1");
				logf.setUserId(String.valueOf(user.getId()));
				logf.setAmount(c.getCouponAmount());
				logf.setRemark("兑换券");
				logf.setPayStatus("1");
				logf.setBusinessType(12);
				String cointype = "";
				if(c.getCouponCoinType()==1){
					logf.setYeAmount(user.getJbAmount());
					logf.setType("2");
					cointype = "J币";
				}else if(c.getCouponCoinType()==2){
					logf.setYeAmount(user.getAvailablebalance());
					logf.setType("1");
					cointype = "元";
				}
			//	logf.setSourceUser();
				walletLogService.addWalletLog(logf);
				//采纳之后，发了一条信息，包含微信、系统消息
				// 发送微信
				TjyUser tjyUser = tjyUserService.selectByPrimaryKey(user.getId() + "");
				String name = tjyUser.getNickname();
				String touser = user.getId().toString();
				MessageInfo messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(2);// 微信
				messageInfo.setToUserId(touser);
				messageInfo.setCreateTime(new Date());
				// 组装内容
				String content = AldyMessageUtil.receiveCoupon(name,c.getCouponAmount().intValue(),cointype);
				String con = WxMsmUtil.getTextMessageContent(content);
				messageInfo.setContent(con);
				messageInfo.setTemplateId("RECEIVE_COUPON");
				messageInfo.setStatus(0);// 未发送
				messageInfo.setWxMsgType(1);///** 微信消息类型（1：文本消息2：图文消息） */
				messageInfoService.addMessageInfo(messageInfo);
				/**
				 * 发送系统消息
				 */
				messageInfo = new MessageInfo();
				messageInfo.setId(UUIDGenerator.getUUID());
				messageInfo.setDeleted(0);
				messageInfo.setType(3);// 系统消息
				messageInfo.setToUserId(touser);
				messageInfo.setCreateTime(new Date());
				messageInfo.setContent(content);
				messageInfo.setStatus(0);// 不需要发送
				messageInfoService.addMessageInfo(messageInfo);
			}else{
				cl.setUseStatus(2);
			}
			couponLogService.addCL(cl);
		}
		cg.setReceiveNum(cg.getReceiveNum()+arry.length);
		cg.setRemainNum(cg.getRemainNum()-arry.length);
		couponGenerateService.updateCG(cg);
		return ajaxDone(true);
		
	}
	/**
	 * 删除优惠券领取记录
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("coupon:delete")
	@RequestMapping("delCL")
	public ModelAndView delCL(String[] ids){	
		return ajaxDone(couponLogService.deleteCLs(ids));
	}
}
