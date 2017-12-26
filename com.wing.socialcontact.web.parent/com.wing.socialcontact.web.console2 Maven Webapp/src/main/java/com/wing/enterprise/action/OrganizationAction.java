package com.wing.enterprise.action;

import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.service.OrgConsultantService;
import com.wing.socialcontact.sys.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by fenggang on 12/26/17.
 *
 * @author fenggang
 * @date 12/26/17
 */
@Controller
@RequestMapping("/organization")
public class OrganizationAction extends BaseAction {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrgConsultantService orgConsultantService;

    @RequestMapping("orgNo")
    public ModelAndView financeProductStockQuery(String orgNo){
        return ajaxJsonEscape(orgConsultantService.selectByOrgNo(orgNo));
    }

}
