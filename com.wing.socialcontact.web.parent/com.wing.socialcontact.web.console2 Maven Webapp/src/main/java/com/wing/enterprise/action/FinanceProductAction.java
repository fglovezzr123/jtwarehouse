package com.wing.enterprise.action;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.bean.FinanceProduct;
import com.wing.socialcontact.sys.service.FinanceProductService;
import com.wing.socialcontact.sys.service.OrgConsultantService;
import com.wing.socialcontact.sys.service.OrganizationService;
import com.wing.socialcontact.util.ServletUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.EntryServiceTag;
import org.com.wing.enterprise.bean.QuickDetailBanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 12/25/17.
 *
 * @author fenggang
 * @date 12/25/17
 */
@Controller
@RequestMapping("/financeProduct")
public class FinanceProductAction extends BaseAction {

    @Autowired
    private FinanceProductService financeProductService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrgConsultantService orgConsultantService;

    @RequestMapping("querys")
    @ResponseBody
    public List<FinanceProduct> tagQuery(PageParam param, EntryServiceTag entryServiceTag){

       return financeProductService.findAll();
    }

    /**
     * 列表
     */
//    @RequiresPermissions("")
    @RequestMapping("load")
    public String bannerload(ModelMap map){
//        List finances = financeProductService.selectAllFinanceProduct();
//        map.addAttribute("finances", finances);
        return "finance/load";
    }

    /**
     * 列表查询
     * @param param
     * @param financeProduct
     * @return
     */
    @RequestMapping("query")
    public ModelAndView financeProductQuery(PageParam param,FinanceProduct financeProduct){
        return ajaxJsonEscape(financeProductService.selectFinanceProduct(param,financeProduct));
    }

    /**
     * 新增
     */
    @RequestMapping("addPage")
    public String financeProductAddPage(ModelMap map){
        List org = organizationService.selectAll();
        map.addAttribute("org",org);
        return "finance/add";
    }

    @RequestMapping("add")
    public ModelAndView financeProductAdd(FinanceProduct financeProduct,Errors errors){
        if(errors.hasErrors()) {
            ModelAndView mav=getValidationMessage(errors);
            if(mav!=null){
                return mav;
            }
        }
        financeProduct.setCreateDate(new Date());
        return ajaxDone(financeProductService.addFinanceProduct(financeProduct));

    }

    /**
     * 修改
     */
    @RequestMapping("updatePage")
    public String financeProductUpdatePage(Long id,ModelMap map){
        FinanceProduct financeProduct = financeProductService.selectByPrimaryKey(id);
        map.addAttribute("financeProduct", financeProduct);
        List org = organizationService.selectAll();
        map.addAttribute("org",org);
        List orgc = orgConsultantService.selectAll();
        map.addAttribute("orgc",orgc);

        return "finance/update";
    }

    @RequestMapping("update")
    public ModelAndView financeProductUpdate(FinanceProduct financeProduct,Errors errors){
        if(errors.hasErrors()) {
            ModelAndView mav=getValidationMessage(errors);
            if(mav!=null){
                return mav;
            }
        }
        return ajaxDone(financeProductService.updateFinanceProduct(financeProduct));

    }

    /**
     * 聚合页幻灯片删除
     */
    @RequestMapping("del")
    public ModelAndView del(String[] ids){
        return ajaxDone(financeProductService.deleteFinanceProduct(ids));
    }

}
