//package com.wing.socialcontact.action;
//
//
//import com.tojoy.business.common.api.ISwitchCnfService;
//import com.tojoy.business.common.bean.SwitchCnf;
//import com.tojoy.business.common.model.PageParam;
//import com.tojoy.business.common.util.SwitchSetting;
//import com.wing.socialcontact.service.wx.api.IPageContentTypeService;
//import com.wing.socialcontact.service.wx.bean.PageContentType;
//import com.wing.socialcontact.service.wx.bean.PersonalCustomization;
//import com.wing.socialcontact.sys.action.BaseAction;
//import com.wing.socialcontact.util.ServletUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author wangyansheng
// * @date 2017/11/01
// */
//@Controller
//@RequestMapping("/switchCnf")
//public class SwitchCnfAction extends BaseAction {
//
//    @Autowired
//    private ISwitchCnfService switchCnfService;
//
//    @Autowired
//    private IPageContentTypeService pageContentTypeService;
//
//    /**
//     * 首页
//     *
//     * @return
//     */
//    @RequestMapping("index")
//    public String index() {
//        return "switchCnf/index";
//    }
//
//    /**
//     * 列表查询
//     *
//     * @return
//     */
//    @RequestMapping("query")
//    public ModelAndView query(PageParam param, SwitchCnf t) {
//        t.setDeleted(0);
//        return ajaxJsonEscape(this.switchCnfService.queryByParam(param, t));
//    }
//
//    /**
//     * 新增页面
//     *
//     * @return
//     */
//    @RequestMapping("addPage")
//    public String addPage(ModelMap modelMap) {
//        List<PageContentType> pageContentTypeList =  pageContentTypeService.selectAllPageContentType();
//        modelMap.addAttribute("pageContentTypeList", pageContentTypeList);
//        return "switchCnf/addorupdate";
//    }
//
//    /**
//     * 修改页面
//     *
//     * @return
//     */
//    @RequestMapping("updatePage")
//    public String updatePage(ModelMap modelMap, String id) {
//
//        List<PageContentType> pageContentTypeList =  pageContentTypeService.selectAllPageContentType();
//        modelMap.addAttribute("pageContentTypeList", pageContentTypeList);
//
//        SwitchCnf m = this.switchCnfService.queryById(id);
//        SwitchSetting setting = new SwitchSetting(m.getCnfValue());
//        if (setting.getStatus(SwitchSetting.collection)) {
//            m.setCollection(SwitchSetting.collection);
//        }
//        if (setting.getStatus(SwitchSetting.thumbUp)) {
//            m.setThumbUp(SwitchSetting.thumbUp);
//        }
//        if (setting.getStatus(SwitchSetting.comment)) {
//            m.setComment(SwitchSetting.comment);
//        }
//        if (setting.getStatus(SwitchSetting.reward)) {
//            m.setReward(SwitchSetting.reward);
//        }
//        if (setting.getStatus(SwitchSetting.share)) {
//            m.setShare(SwitchSetting.share);
//        }
//        modelMap.addAttribute("obj", m);
//        return "switchCnf/addorupdate";
//    }
//
//    /**
//     * 保存
//     *
//     * @return
//     */
//    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> saveOrUpdate(SwitchCnf t) {
//
//        try {
//
//            SwitchSetting setting = new SwitchSetting(0L);
//            if (t.getCollection() != 0) {
//                setting.switchChange(SwitchSetting.collection, true);
//            }
//            if (t.getThumbUp() != 0) {
//                setting.switchChange(SwitchSetting.thumbUp, true);
//            }
//            if (t.getComment() != 0) {
//                setting.switchChange(SwitchSetting.comment, true);
//            }
//            if (t.getReward() != 0) {
//                setting.switchChange(SwitchSetting.reward, true);
//            }
//            if (t.getShare() != 0) {
//                setting.switchChange(SwitchSetting.share, true);
//            }
//            t.setCnfValue(setting.getValue());
//
//            if (t.getId() == null || "".equals(t.getId())) {
//                t.setDeleted(0);
//                t.setCreateTime(new Date());
//                t.setCreateUserId(ServletUtil.getMember().getId());
//                this.switchCnfService.insert(t);
//                return getAjaxResult("0", "", null);
//            } else {
//                t.setUpdateUserId(ServletUtil.getMember().getId());
//                t.setUpdateTime(new Date());
//                this.switchCnfService.update(t);
//                return getAjaxResult("0", "", null);
//            }
//        } catch (Exception e) {
//            return getAjaxResult("-1", "保存失败", null);
//        }
//    }
//
//    /**
//     * 明细
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("detail")
//    public String detail(ModelMap modelMap, String id) {
//        SwitchCnf m = this.switchCnfService.queryById(id);
//        SwitchSetting setting = new SwitchSetting(m.getCnfValue());
//        if (setting.getStatus(SwitchSetting.collection)) {
//            m.setCollection(SwitchSetting.collection);
//        }
//        if (setting.getStatus(SwitchSetting.thumbUp)) {
//            m.setThumbUp(SwitchSetting.thumbUp);
//        }
//        if (setting.getStatus(SwitchSetting.comment)) {
//            m.setComment(SwitchSetting.comment);
//        }
//        if (setting.getStatus(SwitchSetting.reward)) {
//            m.setReward(SwitchSetting.reward);
//        }
//        if (setting.getStatus(SwitchSetting.share)) {
//            m.setShare(SwitchSetting.share);
//        }
//        modelMap.addAttribute("obj", m);
//        return "switchCnf/detail";
//    }
//
//    /**
//     * 删除
//     *
//     * @param t
//     * @return
//     */
//    @RequestMapping("delete")
//    @ResponseBody
//    public Map<String, Object> delete(SwitchCnf t) {
//        try {
//            t = switchCnfService.queryById(t.getId());
//            t.setDeleted(1);
//            t.setUpdateUserId(ServletUtil.getMember().getId());
//            t.setUpdateTime(new Date());
//            this.switchCnfService.update(t);
//            return getAjaxResult("0", "", null);
//        } catch (Exception e) {
//            return getAjaxResult("-1", "操作失败", null);
//        }
//    }
//}
