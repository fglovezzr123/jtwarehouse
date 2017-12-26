package com.wing.socialcontact.action;


import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.ITbLogService;
import com.wing.socialcontact.service.wx.bean.TbLog;
import com.wing.socialcontact.service.wx.bean.TbLogContent;
import com.wing.socialcontact.sys.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 表修改日志
 *
 * @author wangyansheng
 * @date 2017/11/28
 */
@Controller
@RequestMapping("/tbLog")
public class TbLogAction extends BaseAction {

    @Autowired
    private ITbLogService tbLogService;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "system/tbLog/index";
    }

    /**
     * 列表查询
     *
     * @return
     */
    @RequestMapping("queryTbLogList")
    public ModelAndView queryTbLogList(PageParam param, TbLog t) {
        return ajaxJsonEscape(this.tbLogService.selectLogByParam(param, t));
    }

    /**
     * 明细
     *
     * @param id
     * @return
     */
    @RequestMapping("detail")
    public String detailProject(ModelMap modelMap, String id) {
        TbLog m = this.tbLogService.selectById(id);
        modelMap.addAttribute("obj", m);
        return "system/tbLog/detail";
    }

    /**
     * 详细变更数据查询
     *
     * @return
     */
    @RequestMapping("queryTbLogContentList")
    public ModelAndView queryTbLogContentList(PageParam param, TbLogContent t) {
        return ajaxJsonEscape(this.tbLogService.selectLogContentByParam(param, t));
    }

}
