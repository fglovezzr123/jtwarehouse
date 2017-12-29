/**
 * 
 */
package org.tojoycloud.dubbo.rongba.consumer.controller.initPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceProduct;
import org.tojoycloud.dubbo.rongba.api.entity.FinanceProductStock;
import org.tojoycloud.dubbo.rongba.api.entity.InvestProduct;
import org.tojoycloud.dubbo.rongba.api.entity.InvestType;
import org.tojoycloud.dubbo.rongba.api.entity.initPage.InvestPage;
import org.tojoycloud.dubbo.rongba.api.service.FinanceProductService;
import org.tojoycloud.dubbo.rongba.api.service.FinanceProductStockService;
import org.tojoycloud.dubbo.rongba.api.service.InvestProductService;
import org.tojoycloud.dubbo.rongba.api.service.InvestTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author zhangpengzhi
 *
 */
@Api(tags = "首页初始化接口")
@Controller
@RequestMapping(value = "/initPage/")
public class InitPageController {

	@Autowired
	private InvestTypeService investTypeService;
	@Autowired
	private InvestProductService investProductService;
	@Autowired
	private FinanceProductService financeProductService;
	@Autowired
	private FinanceProductStockService financeProductStockService;

	/**
	* 投吧首页
	* @return
	*/
	@ApiOperation(value = "投吧首页展现", notes = "投吧首页展现", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/invest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> invest() {
			Map<String, Object> map = new HashMap<String, Object>();
			InvestPage object = new InvestPage();
			List<InvestType> investTypeList =investTypeService.findList(new InvestType());
			object.setInvestTypeList(investTypeList);
			InvestType investType =new InvestType();
			if(investTypeList!=null){
				investType=investTypeList.get(0);
			}
			InvestProduct investProduct =new InvestProduct();
			investProduct.setInvestType(investType.getId());
			List<InvestProduct> InvestProductList =investProductService.findList(investProduct);
			object.setInvestProductList(InvestProductList);
			map.put("code", "0");
			map.put("message", "初始化投吧首页成功");
			map.put("content", object);
			return map;
	}
	
	
	/**
	* 融吧超市首页
	* @return
	*/
	@ApiOperation(value = "融吧超市首页展现", notes = "融吧超市首页展现", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/finance/market", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> financeMarket() {
			Map<String, Object> map = new HashMap<String, Object>();
			FinanceProduct financeProduct =new FinanceProduct();
			List<FinanceProduct> object =financeProductService.findList(financeProduct);
			map.put("code", "0");
			map.put("message", "初始化融吧超市首页成功");
			map.put("content", object);
			return map;
	}
	
	
	/**
	* 股权融资首页
	* @return
	*/
	@ApiOperation(value = "股权融资首页展现", notes = "融吧超市首页展现", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/finance/stock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> financeStock() {
			Map<String, Object> map = new HashMap<String, Object>();
			FinanceProductStock financeStockProduct =new FinanceProductStock();
			List<FinanceProductStock> object =financeProductStockService.findList(financeStockProduct);
			map.put("code", "0");
			map.put("message", "初始化股权投资首页成功");
			map.put("content", object);
			return map;
	}
}
