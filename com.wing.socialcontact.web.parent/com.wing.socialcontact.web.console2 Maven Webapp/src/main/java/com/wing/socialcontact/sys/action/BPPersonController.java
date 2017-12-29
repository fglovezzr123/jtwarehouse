package com.wing.socialcontact.sys.action;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.bean.FinanceProduct;
import com.wing.socialcontact.sys.bean.TjrbBpPersonEntity;
import com.wing.socialcontact.sys.bean.TjrbBpPersonProjectEntity;
import com.wing.socialcontact.sys.bean.TjrbInvestProductInsure;
import com.wing.socialcontact.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by fenggang on 12/28/17.
 *
 * @author fenggang
 * @date 12/28/17
 */
@Controller
@RequestMapping(value = "/bpPerson")
public class BPPersonController extends BaseAction {


    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrgConsultantService orgConsultantService;
    @Autowired
    private TjrbBpPersonService tjrbBpPersonService;
    @Autowired
    private TjrbBpPersonProjectService tjrbBpPersonProjectService;
    @Autowired
    private TjrbInvestProductInsureService tjrbInvestProductInsureService;

    /**
     * 列表
     */
//    @RequiresPermissions("")
    @RequestMapping("load")
    public String bannerload(ModelMap map){
        return "tjrb/vc/load";
    }

    /**
     * 列表查询
     * @param param
     * @param
     * @return
     */
    @RequestMapping("query")
    public ModelAndView Query(PageParam param, TjrbBpPersonEntity tjrbBpPersonEntity){
        return ajaxJsonEscape(tjrbBpPersonService.select(param,tjrbBpPersonEntity));
    }

    /**
     * 列表
     */
//    @RequiresPermissions("")
    @RequestMapping("bpload")
    public String bannerloadBP(ModelMap map){
        return "tjrb/bp/load";
    }

    /**
     * 列表查询
     * @param param
     * @param
     * @return
     */
    @RequestMapping("bpquery")
    public ModelAndView QueryBP(PageParam param, TjrbBpPersonProjectEntity tjrbBpPersonProjectEntity){
        DataGrid dataGrid = tjrbBpPersonProjectService.select(param,tjrbBpPersonProjectEntity);
        List<Map<String,Object>> list = dataGrid.getRows();
        List dataList = new ArrayList();
        if(list!=null && !list.isEmpty()){
            for(Map<String,Object> map:list){
                Map m = new HashMap();
                String dpId = map.get("dpId")+"";
                String productId = map.get("productId")+"";
                TjrbInvestProductInsure bean = tjrbInvestProductInsureService.selectByPrimaryKey(Long.valueOf(productId));
                TjrbBpPersonEntity entity = tjrbBpPersonService.selectByPrimaryKey(Long.valueOf(dpId));
                m.put("productName",bean.getProductName());
                m.put("dpName",entity.getBpName());
                m.put("mobile",entity.getMobile());
                m.put("area",entity.getArea());
                m.put("focusOn",entity.getFocusOn());
                dataList.add(m);
            }
        }
        dataGrid.setRows(dataList);
        return ajaxJsonEscape(dataGrid);
    }


    /**
     * 列表
     */
//    @RequiresPermissions("")
    @RequestMapping("inload")
    public String intention(ModelMap map){
        return "tjrb/intention/load";
    }

    /**
     * 列表查询
     * @param param
     * @param
     * @return
     */
    @RequestMapping("inquery")
    public ModelAndView Queryintention(PageParam param, TjrbBpPersonProjectEntity tjrbBpPersonProjectEntity){
        return ajaxJsonEscape(tjrbBpPersonProjectService.select(param,tjrbBpPersonProjectEntity));
    }
    /**
     * 新增
     */
    @RequestMapping("addPage")
    public String AddPage(ModelMap map){
        List org = organizationService.selectAll();
        map.addAttribute("org",org);
        return "tjrb/vc/add";
    }

    @RequestMapping("add")
    public ModelAndView Add(TjrbBpPersonEntity tjrbBpPersonEntity,Errors errors){
        if(errors.hasErrors()) {
            ModelAndView mav=getValidationMessage(errors);
            if(mav!=null){
                return mav;
            }
        }
        tjrbBpPersonEntity.setCreateDate(new Date());
        return ajaxDone(tjrbBpPersonService.add(tjrbBpPersonEntity));

    }

    /**
     * 修改
     */
    @RequestMapping("updatePage")
    public String UpdatePage(Long id,ModelMap map){
        TjrbBpPersonEntity tjrbBpPersonEntity = tjrbBpPersonService.selectByPrimaryKey(id);
        map.addAttribute("bean", tjrbBpPersonEntity);
        List org = organizationService.selectAll();
        map.addAttribute("org",org);
        List orgc = orgConsultantService.selectAll();
        map.addAttribute("orgc",orgc);

        return "tjrb/vc/update";
    }

    @RequestMapping("update")
    public ModelAndView Update(TjrbBpPersonEntity tjrbBpPersonEntity,Errors errors){
        if(errors.hasErrors()) {
            ModelAndView mav=getValidationMessage(errors);
            if(mav!=null){
                return mav;
            }
        }
        return ajaxDone(tjrbBpPersonService.update(tjrbBpPersonEntity));

    }

    /**
     * 删除
     */
    @RequestMapping("del")
    public ModelAndView del(String[] ids){
        return ajaxDone(tjrbBpPersonService.delete(ids));
    }

}
