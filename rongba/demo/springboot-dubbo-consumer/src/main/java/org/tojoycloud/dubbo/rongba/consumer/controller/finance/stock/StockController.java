package org.tojoycloud.dubbo.rongba.consumer.controller.finance.stock;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tojoycloud.dubbo.rongba.api.constant.TjrbConstant;
import org.tojoycloud.dubbo.rongba.api.entity.BpPerson;
import org.tojoycloud.dubbo.rongba.api.entity.BpPersonProject;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceProductStock;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceTips;
import org.tojoycloud.dubbo.rongba.api.entity.InvestPerson;
import org.tojoycloud.dubbo.rongba.api.service.BpPersonProjectService;
import org.tojoycloud.dubbo.rongba.api.service.BpPersonService;
import org.tojoycloud.dubbo.rongba.api.service.FinanceProductStockService;
import org.tojoycloud.dubbo.rongba.api.service.FinanceTipsService;
import org.tojoycloud.dubbo.rongba.api.service.InvestPersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author ForrestCao
 * 股权融资Controller
 */
@Api(tags = "股权融资服务接口")
@Controller
@RequestMapping(value = "/finance/stock/*")
public class StockController {

	@Autowired
	private FinanceTipsService financeTipsService;
	@Autowired
	private FinanceProductStockService financeProductStockService;
	@Autowired
	private InvestPersonService investPersonService;
	@Autowired
	private BpPersonService  bpPersonService;
	@Autowired
	private BpPersonProjectService bpPersonProjectService;
	
	/**
	* 获取股权融资提示
	* @param phoneNo
	* @param passWord
	* @param customerType
	* @return
	*/
	@ApiOperation(value = "获取融吧超市免责书", notes = "融吧超市免责书", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/tips", method = RequestMethod.POST)

	@ResponseBody
	public Map<String, Object> relief(
			@ApiParam(required = true, name = "userId", value = "用户id") @RequestParam(value = "userId") String userId) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			InvestPerson investPerson =new InvestPerson();
			investPerson.setId(userId);
			InvestPerson investPersonRtn =investPersonService.get(investPerson);
			if(investPersonRtn==null){
				investPersonService.insert(investPerson);
				FinanceTips object = new FinanceTips();
				object.setTipsType(TjrbConstant.accreditated);
				List<FinanceTips> fisl =financeTipsService.findList(new FinanceTips());
				if(fisl!=null){
					object=fisl.get(0);
				}
				map.put("code", "0");
				map.put("message", "获取免责书成功!");
				map.put("content", object);
				return map;
			}
			if(investPersonRtn.getNoRelief()==TjrbConstant.is_accreditatedFinance){
				map.put("code", "0");
				map.put("message", "用户已提示认证!");
				map.put("content", null);
				return map;
			}
			FinanceTips object = new FinanceTips();
			object.setTipsType(TjrbConstant.accreditated);
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
	* 提交股权融资提示
	* @param phoneNo
	* @param passWord
	* @return
	*/
	@ApiOperation(value = "提交融吧股权融资提示", notes = "提交融吧股权融资提示", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/tipsCommit", method = RequestMethod.POST)
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
			object.setNoRelief(TjrbConstant.is_accreditatedFinance);
			investPersonService.updateAccreditatedInvset(object);
			map.put("code", "0");
			map.put("message", "融吧股权提示提交成功!");
			map.put("content", object);
			return map;
	}
	
	
	
	/**
	* 风头对接列表接口
	* @param phoneNo
	* @param passWord
	* @return
	*/
	@ApiOperation(value = "风头对接列表", notes = "风头对接列表", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/bpPersonList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> productList(
			@ApiParam(required = false, name = "tendency", value = "倾向轮次") @RequestParam(value = "tendency") String tendency,
			@ApiParam(required = false, name = "focusOn", value = "关注行业") @RequestParam(value = "focusOn") String focusOn
			) {
		    Map<String, Object> map = new HashMap<String, Object>();
		    BpPerson  bpPerson =new BpPerson();
		    if(focusOn!=null&&!"".equals(focusOn)){
		    	bpPerson.setFocusOn(focusOn);
		    }
		    if(tendency!=null&&!"".equals(tendency)){
		    	bpPerson.setTendency(tendency);
		    }
			List<BpPerson> object =bpPersonService.findList(bpPerson);
			map.put("code", "0");
			map.put("message", "获取风头列表成功!");
			map.put("content", object);
			return map;
	}
	
	
	
	/**
	* 股权融资列表接口
	* @param phoneNo
	* @param passWord
	* @return
	*/
	@ApiOperation(value = "股权融资列表接口", notes = "股权融资列表接口", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/stockList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> stockList() {
		    Map<String, Object> map = new HashMap<String, Object>();
		    FinanceProductStock  financeProductStock =new FinanceProductStock();
			List<FinanceProductStock> object =financeProductStockService.findList(financeProductStock);
			map.put("code", "0");
			map.put("message", "获取股权融资列表成功!");
			map.put("content", object);
			return map;
	}
	
	
	
	/**
	* 融吧股权产品详情
	* @return
	*/
	@ApiOperation(value = " 融吧股权产品详情", notes = " 融吧股权产品详情", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/productDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> productDetail(
			@ApiParam(required = true, name = "id", value = "产品id") @RequestParam(value = "id") String id) {
		    Map<String, Object> map = new HashMap<String, Object>();
		    FinanceProductStock  financeProductStock =new FinanceProductStock();
		    financeProductStock.setId(id);
		    FinanceProductStock object =financeProductStockService.get(financeProductStock);
			map.put("code", "0");
			map.put("message", "获取融吧股权商品详情成功!");
			map.put("content", object);
			return map;
	}
	
	
	
	/**
	* 融吧BP项目接口
	* @return
	*/
	@ApiOperation(value = "融吧BP项目接口", notes = "融吧BP项目接口", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/bpProject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> bpProject(
			@ApiParam(required = true, name = "productId", value = "产品id") @RequestParam(value = "productId") String productId,
			@ApiParam(required = true, name = "bpId", value = "风头人Id") @RequestParam(value = "bpId") String bpId,
			@ApiParam(required = true, name = "actionType", value = "操作方式 0-投的项目 1-收到的项目 2-收藏的项目")@RequestParam(value = "actionType") String actionType
			) {
		    Map<String, Object> map = new HashMap<String, Object>();
		    BpPersonProject  bpPersonProject =new BpPersonProject();
		    bpPersonProject.setProductId(productId);
		    bpPersonProject.setBpId(bpId);
		    bpPersonProject.setCreateDate(new Date());
		    bpPersonProject.setUpdateDate(new Date());
		    bpPersonProjectService.insert(bpPersonProject);
			map.put("code", "0");
			map.put("message", "投bp或者收藏操作成功!");
			map.put("content", null);
			return map;
	}
	
	
}
