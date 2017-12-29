package com.wing.socialcontact.sys.action;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.bean.TjrbInvestProduct;
import com.wing.socialcontact.sys.bean.TjrbInvestProductInsure;
import com.wing.socialcontact.sys.service.OrgConsultantService;
import com.wing.socialcontact.sys.service.OrganizationService;
import com.wing.socialcontact.sys.service.TjrbInvestProductInsureService;
import com.wing.socialcontact.sys.service.TjrbInvestProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/investProduct")
public class TjrbInvestProductAction extends BaseAction {

	@Resource
	private TjrbInvestProductService tjrbInvestProductService;
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
		return "tjrb/invest/load";
	}

	/**
	 * 列表查询
	 * @param param
	 * @return
	 */
	@RequestMapping("query")
	public ModelAndView financeProductQuery(PageParam param, TjrbInvestProduct tjrbInvestProduct){
		return ajaxJsonEscape(tjrbInvestProductService.select(param,tjrbInvestProduct));
	}

	/**
	 * 新增
	 */
	@RequestMapping("addPage")
	public String financeProductAddPage(ModelMap map){
		List org = organizationService.selectAll();
		map.addAttribute("org",org);
		return "tjrb/invest/add";
	}

	@RequestMapping("add")
	public ModelAndView financeProductAdd(TjrbInvestProduct tjrbInvestProduct, Errors errors){
		if(errors.hasErrors()) {
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null){
				return mav;
			}
		}
		tjrbInvestProduct.setCreateDate(new Date());
		return ajaxDone(tjrbInvestProductService.add(tjrbInvestProduct));

	}

	/**
	 * 修改
	 */
	@RequestMapping("updatePage")
	public String financeProductUpdatePage(Long id,ModelMap map){
		TjrbInvestProduct tjrbInvestProduct = tjrbInvestProductService.selectByPrimaryKey(id);
		map.addAttribute("bean", tjrbInvestProduct);
		List org = organizationService.selectAll();
		map.addAttribute("org",org);
		List orgc = orgConsultantService.selectAll();
		map.addAttribute("orgc",orgc);

		return "tjrb/invest/update";
	}

	@RequestMapping("update")
	public ModelAndView financeProductUpdate(TjrbInvestProduct tjrbInvestProduct,Errors errors){
		if(errors.hasErrors()) {
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null){
				return mav;
			}

		}
		tjrbInvestProduct.setUpdateDate(new Date());
		return ajaxDone(tjrbInvestProductService.update(tjrbInvestProduct));

	}

	/**
	 * 删除
	 */
	@RequestMapping("del")
	public ModelAndView del(String[] ids){
		return ajaxDone(tjrbInvestProductService.delete(ids));
	}

}
