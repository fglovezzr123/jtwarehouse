package com.wing.enterprise.action;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.action.BaseAction;
import org.com.wing.enterprise.bean.EntryServiceTag;
import org.com.wing.enterprise.bean.FinanceProduct;
import org.com.wing.enterprise.bean.FinanceProductStock;
import org.com.wing.enterprise.service.FinanceProductService;
import org.com.wing.enterprise.service.FinanceProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * Created by fenggang on 12/25/17.
 *
 * @author fenggang
 * @date 12/25/17
 */
@Controller
@RequestMapping("/financeProductStock")
public class FinanceProductStockAction extends BaseAction {

    @Autowired
    private FinanceProductStockService financeProductStockService;

    @RequestMapping("querys")
    @ResponseBody
    public List<FinanceProductStock> tagQuery(PageParam param){

       return financeProductStockService.findAll();
    }

    /**
     * 列表
     */
//    @RequiresPermissions("")
    @RequestMapping("load")
    public String bannerload(ModelMap map){
//        List finances = financeProductService.selectAllFinanceProduct();
//        map.addAttribute("finances", finances);
        return "finance/stock/load";
    }

    /**
     * 列表查询
     * @param param
     * @param financeProductStock
     * @return
     */
    @RequestMapping("query")
    public ModelAndView financeProductStockQuery(PageParam param,FinanceProductStock financeProductStock){
        return ajaxJsonEscape(financeProductStockService.selectFinanceProductStock(param,financeProductStock));
    }

    /**
     * 新增
     */
    @RequestMapping("addPage")
    public String financeProductStockAddPage(ModelMap map){
        return "finance/stock/add";
    }

    @RequestMapping("add")
    public ModelAndView financeProductStockAdd(FinanceProductStock financeProductStock,Errors errors){
        if(errors.hasErrors()) {
            ModelAndView mav=getValidationMessage(errors);
            if(mav!=null){
                return mav;
            }
        }
        financeProductStock.setCreateDate(new Date());
        return ajaxDone(financeProductStockService.addFinanceProductStock(financeProductStock));

    }

    /**
     * 聚合页幻灯片修改
     */
    @RequestMapping("updatePage")
    public String financeProductStockUpdatePage(Long id,ModelMap map){
        FinanceProductStock financeProductStock = financeProductStockService.selectByPrimaryKey(id);
        map.addAttribute("financeProduct", financeProductStock);
        return "finance/stock/update";
    }

    @RequestMapping("update")
    public ModelAndView financeProductStockUpdate(FinanceProductStock financeProductStock,Errors errors){
        if(errors.hasErrors()) {
            ModelAndView mav=getValidationMessage(errors);
            if(mav!=null){
                return mav;
            }
        }
        return ajaxDone(financeProductStockService.updateFinanceProductStock(financeProductStock));

    }

    /**
     * 删除
     */
    @RequestMapping("del")
    public ModelAndView del(String[] ids){
        return ajaxDone(financeProductStockService.deleteFinanceProductStock(ids));
    }

}
