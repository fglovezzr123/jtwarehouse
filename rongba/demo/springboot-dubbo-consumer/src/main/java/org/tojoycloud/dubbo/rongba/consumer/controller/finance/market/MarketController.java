/**
 * 
 */
package org.tojoycloud.dubbo.rongba.consumer.controller.finance.market;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tojoycloud.dubbo.rongba.api.constant.TjrbConstant;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceProduct;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceTips;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceWill;
import org.tojoycloud.dubbo.rongba.api.entity.InvestPerson;
import org.tojoycloud.dubbo.rongba.api.entity.OrgConsultant;
import org.tojoycloud.dubbo.rongba.api.service.FinanceProductService;
import org.tojoycloud.dubbo.rongba.api.service.FinanceTipsService;
import org.tojoycloud.dubbo.rongba.api.service.FinanceWillService;
import org.tojoycloud.dubbo.rongba.api.service.InvestPersonService;
import org.tojoycloud.dubbo.rongba.api.service.OrgConsultantService;
import org.slf4j.Logger;  
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author ForrestCao
 * 融吧超市Controller
 */
@Api(tags = "融吧超市服务接口")
@Controller
@RequestMapping(value = "/finance/market/*")
public class MarketController {
	
	private  final Logger logger = LoggerFactory.getLogger(MarketController.class);
	
	@Autowired
	private FinanceTipsService financeTipsService;
	@Autowired
	private FinanceProductService financeProductService;
	@Autowired
	private InvestPersonService investPersonService;
	@Autowired
	private OrgConsultantService   orgConsultantService;
	@Autowired
	private FinanceWillService   financeWillService;
	
	
	/**
	* 获取融吧免责书
	* @param phoneNo
	* @param passWord
	* @param customerType
	* @return
	*/
	@ApiOperation(value = "获取融吧超市免责书", notes = "融吧超市免责书", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/relief", method = RequestMethod.POST)

	@ResponseBody
	public Map<String, Object> relief(
			@ApiParam(required = true, name = "userId", value = "用户id") @RequestParam(value = "userId") String userId) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			InvestPerson investPerson =new InvestPerson();
			investPerson.setInvestName(userId);
			InvestPerson investPersonRtn =investPersonService.get(investPerson);
			if(investPersonRtn==null){
				logger.info("用户首次点击该模块---->:"+userId);  
				investPersonService.insert(investPerson);
				FinanceTips object = new FinanceTips();
				object.setTipsType(TjrbConstant.relief);
				List<FinanceTips> fisl =financeTipsService.findList(new FinanceTips());
				if(fisl!=null){
					if(fisl.size()>0){
						object=fisl.get(0);
					}
				}
				map.put("code", "0");
				map.put("message", "获取免责书成功!");
				map.put("content", object);
				return map;
			}
			if(investPersonRtn.getNoRelief()==TjrbConstant.is__relief){
				map.put("code", "0");
				map.put("message", "用户已免责认证!");
				map.put("content", null);
				return map;
			}
			FinanceTips object = new FinanceTips();
			object.setTipsType(TjrbConstant.relief);
			List<FinanceTips> fisl =financeTipsService.findList(new FinanceTips());
			if(fisl!=null){
				object=fisl.get(0);
			}
			map.put("code", "0");
			map.put("message", "获取提示成功!");
			map.put("content", object);
			return map;
	}

	/**
	* 提交融吧免责书
	* @param phoneNo
	* @param passWord
	* @return
	*/
	@ApiOperation(value = "提交融吧免责书", notes = "提交融吧免责书", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/reliefCommit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> tipCommit(
			@ApiParam(required = true, name = "userId", value = "用户id") @RequestParam(value = "userId") String userId) {
		    Map<String, Object> map = new HashMap<String, Object>();
		    InvestPerson investPerson =new InvestPerson();
			investPerson.setId(userId);
			InvestPerson investPersonRtn =investPersonService.get(investPerson);
			if(investPersonRtn==null){
				map.put("code", "0");
				map.put("message", "用户不存在请检查!");
				map.put("content", null);
				return map;
			}
			InvestPerson object = new InvestPerson();
			object.setId(userId);
			object.setUpdateDate(new Date());
			object.setNoRelief(TjrbConstant.is__relief);
			investPersonService.updateAccreditatedInvset(object);
			map.put("code", "0");
			map.put("message", "融吧免责提交成功!");
			map.put("content", object);
			return map;
	}
	
	
	
	/**
	* 融吧超市列表接口
	* @param phoneNo
	* @param passWord
	* @return
	*/
	@ApiOperation(value = "融吧超市列表", notes = "融吧超市列表", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/product", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> productList(
			@ApiParam(required = false, name = "financeType", value = "融资类型") @RequestParam(value = "crowdType") Integer financeType,
			@ApiParam(required = false, name = "financeAmount", value = "融资金额") @RequestParam(value = "crowdType") String financeAmount,
			@ApiParam(required = false, name = "financePeriod", value = "融资期限") @RequestParam(value = "crowdType") String financePeriod) {
		    Map<String, Object> map = new HashMap<String, Object>();
		    FinanceProduct  financeProduct =new FinanceProduct();
		    if(financeType!=null&&!"".equals(financeType)){
		    	   financeProduct.setFinanceType(financeType);
		    }
		    if(financeAmount!=null&&!"".equals(financeAmount)){
		    	   financeProduct.setFinanceAmount(financeAmount);
		    }
		    if(financePeriod!=null&&!"".equals(financePeriod)){
		    	   financeProduct.setFinancePeriod(financePeriod);
		    }
			List<FinanceProduct> object =financeProductService.findList(financeProduct);
			map.put("code", "0");
			map.put("message", "获取融资超市列表成功!");
			map.put("content", object);
			return map;
	}
	
	
	/**
	* 融吧超市产品详情
	* @return
	*/
	@ApiOperation(value = "融吧超市产品详情", notes = "融吧超市产品详情", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/productDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inusreProductDetail(
			@ApiParam(required = true, name = "id", value = "产品id") @RequestParam(value = "id") String id) {
		    Map<String, Object> map = new HashMap<String, Object>();
		    FinanceProduct  financeProduct =new FinanceProduct();
		    financeProduct.setId(id);
		    FinanceProduct object =financeProductService.get(financeProduct);
			if(object!=null){
				if(object.getConsultant()!=null&&!"".equals(object.getConsultant())){
					OrgConsultant orgConsultant =orgConsultantService.get(object.getConsultant());
					object.setOrgConsultant(orgConsultant);
				}
			}
			map.put("code", "0");
			map.put("message", "获取融吧超市商品详情成功!");
			map.put("content", object);
			return map;
	}
	
	
	
	/**
	* 融吧我有意向接口
	* @return
	*/
	@ApiOperation(value = "融吧我有意向提交", notes = "融吧我有意向接口", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/willCommit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> willCommit(
			@ApiParam(required = true, name = "productId", value = "产品id") @RequestParam(value = "productId") Integer productId,
			@ApiParam(required = true, name = "orgNo", value = "机构编号") @RequestParam(value = "orgNo") Integer orgNo,
			@ApiParam(required = true, name = "willAmount", value = "期望金额") @RequestParam(value = "willAmount") String willAmount,
			@ApiParam(required = true, name = "willPeriod", value = "期望期限") @RequestParam(value = "willPeriod") String willPeriod,
			@ApiParam(required = true, name = "person", value = "提交人用户id") @RequestParam(value = "person") String person
			) {
		    Map<String, Object> map = new HashMap<String, Object>();
		    FinanceWill  financeWill =new FinanceWill();
		    financeWill.setOrgNo(orgNo);
		    financeWill.setProductId(productId);
		    financeWill.setWillAmount(willAmount);
		    financeWill.setWillPeriod(willPeriod);
		    financeWill.setCreateDate(new Date());
		    financeWill.setUpdateDate(new Date());
		    financeWillService.insert(financeWill);
			map.put("code", "0");
			map.put("message", "意向提交成功!");
			map.put("content", null);
			return map;
	}
	
	
}
