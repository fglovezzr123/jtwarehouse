package com.wing.socialcontact.front.action;

import com.alibaba.fastjson.JSON;
import com.tojoy.service.wx.bean.*;
import com.tojoycloud.common.report.ResponseCode;
import com.tojoycloud.common.report.base.BaseAppAction;
import com.tojoycloud.common.report.base.CommandInfo;
import com.tojoycloud.common.report.base.UserProperty;
import com.tojoycloud.report.RequestReport;
import com.tojoycloud.report.ResponseReport;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.*;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.service.wx.bean.MeetingSignup;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IDistrictService;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.CommUtil;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP项目超市  接口controller
 * @author wangyansheng
 * @date 2017/11/21
 */
@Controller
@RequestMapping("/m/projectSupermarket")
public class ProjectSupermarketAction extends BaseAppAction {

    @Autowired
    private IBannerService bannerService;
    @Autowired
    private IWxUserService wxUserService;
    @Autowired
    private ITjyUserService tjyUserService;
    @Autowired
    private IDistrictService districtService;
    @Autowired
    private IListValuesService listValuesService;
    @Autowired
    private IProjectSupermarketService projectSupermarketService;
    @Autowired
    private IAttentionService attentionService;
    @Autowired
    private IProjectSupermarketCustomerService projectSupermarketCustomerService;

    /**
     * 轮播图列表
     *
     * @param requestReport
     * @return
     */
    @RequestMapping("queryBannerList")
    public
    @ResponseBody
    ResponseReport queryBannerList(@RequestBody RequestReport requestReport) {

        RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
        String userId = requestReport.getUserProperty().getUserId();
        String columnType = "b89cd4fd8c0349018b71202d057e31b0";
        if (StringUtil.isEmpty(userId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "用户id不能为空", null);
        } else {
            WxUser wxUser = wxUserService.selectByPrimaryKey(userId);
            if (wxUser == null) {
                return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "用户信息为空", null);
            }
            TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
            Cache.ValueWrapper vw = redisCache.get("selBannerList_" + columnType + "_" + wxUser.getLevel() + "_" + tjyUser.getReconStatus());
            List list = null;
            if (vw != null) {
                list = (List) vw.get();
            }
            if (list == null || list.size() == 0) {
                list = bannerService.selectBannerByUserId(userId, columnType);
                //添加缓存
                redisCache.put("selBannerList_" + columnType + "_" + wxUser.getLevel() + "_" + tjyUser.getReconStatus(), list);
            }
            return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功！", list);
        }
    }

    /**
     * 获取省列表
     *
     * @param requestReport
     * @return
     */
    @RequestMapping("/queryProvinceList")
    public
    @ResponseBody
    ResponseReport queryProvinceList(@RequestBody RequestReport requestReport) {
        // 获取省
        List provinceList = districtService.selectDistrictByType("1");
        Map res = new HashMap();
        res.put("provinceList", provinceList);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", res);
    }

    /**
     * 获取行业列表
     *
     * @param requestReport
     * @return
     */
    @RequestMapping("/queryIndustryList")
    public
    @ResponseBody
    ResponseReport queryIndustryList(@RequestBody RequestReport requestReport) {

        List<ListValues> industryList = listValuesService.selectListByType(8001);
        Map res = new HashMap();
        res.put("industryList", industryList);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", res);
    }

    /**
     * 获取合作模式
     *
     * @param requestReport
     * @return
     */
    @RequestMapping("/queryCooperativeModeList")
    public
    @ResponseBody
    ResponseReport queryCooperativeModeList(@RequestBody RequestReport requestReport) {

        List<ListValues> cooperativeModeList = listValuesService.selectListByType(8008);
        Map res = new HashMap();
        res.put("cooperativeModeList", cooperativeModeList);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", res);
    }

    /**
     * 获取市或区县
     *
     * @param requestReport
     * @return
     */
    @RequestMapping("/getCityOrCounty")
    public
    @ResponseBody
    ResponseReport getCityOrCounty(@RequestBody RequestReport requestReport) {
        CommandInfo command = requestReport.getCommandInfo();
        String pId = command.getData().get("pId") != null ? command.getData().get("pId").toString() : "";
        if (StringUtil.isEmpty(pId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "用户id不能为空", null);
        }

        List cityOrCounty = districtService.selectDistrictBySuperId(pId);
        Map res = new HashMap();
        res.put("cityOrCounty", cityOrCounty);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", res);
    }


    /**
     * 获取项目超市列表
     *
     * @param requestReport
     * @return
     */
    @RequestMapping("/queryProjectSupermarketList")
    public
    @ResponseBody
    ResponseReport queryProjectSupermarketList(@RequestBody RequestReport requestReport) {

        CommandInfo command = requestReport.getCommandInfo();
        Integer page = command.getData().get("page") != null ? Integer.valueOf(command.getData().get("page").toString()) : 1;
        Integer size = command.getData().get("size") != null ? Integer.valueOf(command.getData().get("size").toString()) : 10;
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();
        if (com.wing.socialcontact.util.StringUtil.isEmpty(userId)) {
            return super.getAjaxResult(requestReport, ResponseCode.NotSupport, "用户id不能为空", null);
        }

        //省市县、行业
        String province = command.getData().get("province") != null ? command.getData().get("province").toString() : "";
        String city = command.getData().get("city") != null ? command.getData().get("city").toString() : "";
        String county = command.getData().get("county") != null ? command.getData().get("county").toString() : "";
        String industry = command.getData().get("industry") != null ? command.getData().get("industry").toString() : "";

        ProjectSupermarket projectSupermarket = new ProjectSupermarket();
        PageParam pageParam = new PageParam();
        pageParam.setPage(page);
        pageParam.setRows(size);
        projectSupermarket.setIsShow(0);
        projectSupermarket.setIsDelete(0);
        projectSupermarket.setProvince(province);
        projectSupermarket.setCity(city);
        projectSupermarket.setCounty(county);
        projectSupermarket.setIndustry(industry);

        DataGrid dataGrid = this.projectSupermarketService.selectByParam(pageParam, projectSupermarket);
        for (int i = 0; i < dataGrid.getRows().size(); i++) {

            //收藏
            ProjectSupermarket supermarket = (ProjectSupermarket) dataGrid.getRows().get(i);
            Attention att = new Attention();
            att.setFkId(supermarket.getId());
            Integer attentionCount = attentionService.selectCount(att);
            ((ProjectSupermarket) dataGrid.getRows().get(i)).setAttentionCount(attentionCount);

            att.setUserId(userId);
            List<Attention> attlist = attentionService.queryAttention(att);
            int isAttention = 0;
            if (attlist.size() > 0 && attlist != null) {
                isAttention = 1;
            }
            ((ProjectSupermarket) dataGrid.getRows().get(i)).setIsAttention(isAttention);
        }

        Map res = new HashMap();
        res.put("dataGrid", dataGrid);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", res);
    }

    /**
     * 项目超市详情
     *
     * @param requestReport
     * @return
     */
    @RequestMapping("queryProjectSupermarketDetail")
    public
    @ResponseBody
    ResponseReport queryProjectSupermarketDetail(@RequestBody RequestReport requestReport) {
        CommandInfo command = requestReport.getCommandInfo();
        String id = command.getData().get("id").toString();
        ProjectSupermarket projectSupermarket = projectSupermarketService.selectById(id);
        UserProperty userProperty = requestReport.getUserProperty();
        String userId = userProperty.getUserId();

        Attention att = new Attention();
        att.setFkId(id);
        att.setUserId(userId);
        List<Attention> attlist = attentionService.queryAttention(att);
        int isAttention = 0;
        if (attlist.size() > 0 && attlist != null) {
            isAttention = 1;
        }
        projectSupermarket.setIsAttention(isAttention);
        Map res = new HashMap();
        res.put("projectSupermarket", projectSupermarket);
        return super.getAjaxResult(requestReport, ResponseCode.OK, "获取成功", res);
    }

    /**
     * 发布项目
     *
     * @param requestReport
     * @return
     */
    @RequestMapping("/addProjectSupermarket")
    public
    @ResponseBody
    ResponseReport addProjectSupermarket(@RequestBody RequestReport requestReport) {
        CommandInfo command = requestReport.getCommandInfo();
        String projectSupermarketJson = command.getData().get("projectSupermarket").toString();
        ProjectSupermarket projectSupermarket = JSON.parseObject(projectSupermarketJson, ProjectSupermarket.class);
        Integer iCount = projectSupermarketService.insert(projectSupermarket);
        if (iCount > 0) {
            return super.getAjaxResult(requestReport, ResponseCode.OK, "添加成功", null);
        } else {
            return super.getAjaxResult(requestReport, ResponseCode.Error, "添加失败", null);
        }
    }

    /**
     * 商洽（意向用户）
     *
     * @param requestReport
     * @return
     */
    @RequestMapping("/addProjectCustomer")
    public
    @ResponseBody
    ResponseReport addProjectCustomer(@RequestBody RequestReport requestReport) {
        CommandInfo command = requestReport.getCommandInfo();
        String projectCustomerJson = command.getData().get("projectCustomer").toString();
        ProjectSupermarketCustomer projectCustomer = JSON.parseObject(projectCustomerJson, ProjectSupermarketCustomer.class);
        Integer iCount = projectSupermarketCustomerService.insert(projectCustomer);
        if (iCount > 0) {
            return super.getAjaxResult(requestReport, ResponseCode.OK, "添加成功", null);
        } else {
            return super.getAjaxResult(requestReport, ResponseCode.Error, "添加失败", null);
        }
    }
}
