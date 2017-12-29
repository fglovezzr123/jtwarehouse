/**
 * 
 */
package org.tojoycloud.dubbo.rongba.consumer.controller.invest;

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
import org.tojoycloud.dubbo.rongba.api.entity.InvestPerson;
import org.tojoycloud.dubbo.rongba.api.entity.InvestProduct;
import org.tojoycloud.dubbo.rongba.api.entity.InvestProductInsure;
import org.tojoycloud.dubbo.rongba.api.entity.InvestTips;
import org.tojoycloud.dubbo.rongba.api.entity.InvestWill;
import org.tojoycloud.dubbo.rongba.api.entity.OrgConsultant;
import org.tojoycloud.dubbo.rongba.api.service.InvestPersonService;
import org.tojoycloud.dubbo.rongba.api.service.InvestProductInsureService;
import org.tojoycloud.dubbo.rongba.api.service.InvestProductService;
import org.tojoycloud.dubbo.rongba.api.service.InvestTipsService;
import org.tojoycloud.dubbo.rongba.api.service.InvestWillService;
import org.tojoycloud.dubbo.rongba.api.service.OrgConsultantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author ForrestCao
 * 投吧接口
 */
@Api(tags = "投吧服务接口")
@Controller
@RequestMapping(value = "/invest/*")
public class InvestController {
	@Autowired
	private InvestPersonService		investPersonService;
	@Autowired
	private InvestTipsService		investTipsService;
	@Autowired
	private InvestWillService       investWillService;
	@Autowired
	private InvestProductInsureService  investProductInsureService;
	@Autowired
	private InvestProductService  investProductService;
	@Autowired
	private OrgConsultantService   orgConsultantService;
	
	


	/**
	* 获取投吧认证书
	* @param phoneNo
	* @param passWord
	* @param customerType
	* @return
	*/
	@ApiOperation(value = "获取投吧认证书", notes = "投吧获取认证书", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/tips", method = RequestMethod.POST)

	@ResponseBody
	public Map<String, Object> tips(
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
			if(investPersonRtn.getAccreditatedInvset()==TjrbConstant.is_accreditatedInvset){
				map.put("code", "0");
				map.put("message", "用户已认证!");
				map.put("content", null);
				return map;
			}
			InvestTips object = new InvestTips();
			List<InvestTips> itsl =investTipsService.findList(new InvestTips());
			if(itsl!=null){
				object=itsl.get(0);
			}
			map.put("code", "0");
			map.put("message", "获取提示成功!");
			map.put("content", object);
			return map;
	}

	/**
	* 提交投吧认证书
	* @param phoneNo
	* @param passWord
	* @return
	*/
	@ApiOperation(value = "提交投吧认证书", notes = "提交投吧认证书", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/tipCommit", method = RequestMethod.POST)
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
			object.setAccreditatedInvset(TjrbConstant.is_accreditatedInvset);
			investPersonService.updateAccreditatedInvset(object);
			map.put("code", "0");
			map.put("message", "投吧认证提交成功!");
			map.put("content", object);
			return map;
	}
	
	
	
	/**
	* 投吧保险列表接口
	* @param phoneNo
	* @param passWord
	* @return
	*/
	@ApiOperation(value = "投吧保险列表", notes = "投吧保险列表", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/inusreProduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inusreProduct(
			@ApiParam(required = false, name = "crowdType", value = "针对人群") @RequestParam(value = "crowdType") String crowdType) {
		    Map<String, Object> map = new HashMap<String, Object>();
		    InvestProductInsure  investProductInsure =new InvestProductInsure();
		    if(crowdType!=null&&!"".equals(crowdType)){
		    	   investProductInsure.setCrowdType(crowdType);
		    }
			List<InvestProductInsure> object =investProductInsureService.findList(investProductInsure);
			map.put("code", "0");
			map.put("message", "获取保险列表成功!");
			map.put("content", object);
			return map;
	}
	
	
	
	/**
	* 投吧保险产品详情
	* @return
	*/
	@ApiOperation(value = "投吧保险产品详情", notes = "投吧保险产品详情", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/inusreProductDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inusreProductDetail(
			@ApiParam(required = true, name = "id", value = "产品id") @RequestParam(value = "id") String id) {
		    Map<String, Object> map = new HashMap<String, Object>();
		    InvestProductInsure  investProductInsure =new InvestProductInsure();
    	    investProductInsure.setId(id);
			InvestProductInsure object =investProductInsureService.get(investProductInsure);
			if(object!=null){
				if(object.getConsultant()!=null&&!"".equals(object.getConsultant())){
					OrgConsultant orgConsultant =orgConsultantService.get(object.getConsultant());
					object.setOrgConsultant(orgConsultant);
				}
			}
			map.put("code", "0");
			map.put("message", "获取保险详情成功!");
			map.put("content", object);
			return map;
	}
	
	
	
	
	/**
	* 投吧非保险产品详情
	* @return
	*/
	@ApiOperation(value = "投吧非保险产品详情", notes = "投吧非保险产品详情", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/productDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> productDetail(
			@ApiParam(required = true, name = "id", value = "产品id") @RequestParam(value = "id") String id) {
		    Map<String, Object> map = new HashMap<String, Object>();
		    InvestProduct  investProduct =new InvestProduct();
		    investProduct.setId(id);
			InvestProduct object =investProductService.get(investProduct);
			if(object!=null){
				if(object.getConsultant()!=null&&!"".equals(object.getConsultant())){
					OrgConsultant orgConsultant =orgConsultantService.get(object.getConsultant());
					object.setOrgConsultant(orgConsultant);
				}
			}
			map.put("code", "0");
			map.put("message", "获取保险详情成功!");
			map.put("content", object);
			return map;
	}
	
	/**
	* 投吧我有意向接口
	* @return
	*/
	@ApiOperation(value = "投吧我有意向提交", notes = "投吧我有意向接口", produces = "application/json", response = Object.class)
	@RequestMapping(value = "/willCommit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> willCommit(
			@ApiParam(required = true, name = "productId", value = "产品id") @RequestParam(value = "productId") Integer productId,
			@ApiParam(required = true, name = "isInsure", value = "是否是保险类") @RequestParam(value = "isInsure") String isInsure,
			@ApiParam(required = true, name = "person", value = "提交人用户id") @RequestParam(value = "person") String person
			
			) {
		    Map<String, Object> map = new HashMap<String, Object>();
		    InvestWill  investWill =new InvestWill();
		    investWill.setIsInsure(isInsure);
		    investWill.setProductId(productId);
		    investWill.setPerson(person);
		    investWill.setCreateDate(new Date());
		    investWill.setUpdateDate(new Date());
		    investWillService.insert(investWill);
			map.put("code", "0");
			map.put("message", "意见提交成功!");
			map.put("content", null);
			return map;
	}
	
	

}
