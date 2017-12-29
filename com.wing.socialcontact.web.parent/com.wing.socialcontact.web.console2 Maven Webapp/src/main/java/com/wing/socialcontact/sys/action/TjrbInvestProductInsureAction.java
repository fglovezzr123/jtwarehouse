package com.wing.socialcontact.sys.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.bean.FinanceProduct;
import com.wing.socialcontact.sys.service.OrgConsultantService;
import com.wing.socialcontact.sys.service.OrganizationService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.wing.socialcontact.sys.bean.TjrbInvestProductInsure;
import com.wing.socialcontact.sys.service.TjrbInvestProductInsureService;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/investProductInsure")
public class TjrbInvestProductInsureAction extends BaseAction {

	@Resource
	private TjrbInvestProductInsureService tjrbInvestProductInsureService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private OrgConsultantService orgConsultantService;


	/**
	 * 列表
	 */
//    @RequiresPermissions("")
	@RequestMapping("load")
	public String bannerload(ModelMap map){
//        List finances = financeProductService.selectAllFinanceProduct();
//        map.addAttribute("finances", finances);
		return "tjrb/invest/insure/load";
	}

	/**
	 * 列表查询
	 * @param param
	 * @return
	 */
	@RequestMapping("query")
	public ModelAndView financeProductQuery(PageParam param, TjrbInvestProductInsure tjrbInvestProductInsure){
		return ajaxJsonEscape(tjrbInvestProductInsureService.select(param,tjrbInvestProductInsure));
	}

	/**
	 * 新增
	 */
	@RequestMapping("addPage")
	public String financeProductAddPage(ModelMap map){
		List org = organizationService.selectAll();
		map.addAttribute("org",org);
		return "tjrb/invest/insure/add";
	}

	@RequestMapping("add")
	public ModelAndView financeProductAdd(TjrbInvestProductInsure tjrbInvestProductInsure, Errors errors){
		if(errors.hasErrors()) {
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null){
				return mav;
			}
		}
		tjrbInvestProductInsure.setCreateDate(new Date());
		return ajaxDone(tjrbInvestProductInsureService.add(tjrbInvestProductInsure));

	}

	/**
	 * 修改
	 */
	@RequestMapping("updatePage")
	public String financeProductUpdatePage(Long id,ModelMap map){
		TjrbInvestProductInsure tjrbInvestProductInsure = tjrbInvestProductInsureService.selectByPrimaryKey(id);
		map.addAttribute("bean", tjrbInvestProductInsure);
		List org = organizationService.selectAll();
		map.addAttribute("org",org);
		List orgc = orgConsultantService.selectAll();
		map.addAttribute("orgc",orgc);

		return "tjrb/invest/insure/update";
	}

	@RequestMapping("update")
	public ModelAndView financeProductUpdate(TjrbInvestProductInsure tjrbInvestProductInsure,Errors errors){
		if(errors.hasErrors()) {
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null){
				return mav;
			}
		}
		return ajaxDone(tjrbInvestProductInsureService.update(tjrbInvestProductInsure));

	}

	/**
	 * 删除
	 */
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		return ajaxDone(tjrbInvestProductInsureService.delete(ids));
	}

}
