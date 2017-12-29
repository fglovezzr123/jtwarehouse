package com.wing.socialcontact.action;


import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.*;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.service.IDistrictService;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 项目超市
 *
 * @author wangyansheng
 * @date 2017/11/01
 */
@Controller
@RequestMapping("/projectSupermarket")
public class ProjectSupermarketAction extends BaseAction {

    @Autowired
    private IProjectSupermarketService projectSupermarketService;

    @Autowired
    private IDistrictService districtService;

    @Autowired
    private IListValuesService listValuesService;

    @Autowired
    private ITjyUserService tjyUserService;

    @Autowired
    private IProjectSupermarketCustomerService projectSupermarketCustomerService;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("index")
    public String loadProject(ModelMap modelMap) {

        // 获取省
        List provinceList = districtService.selectDistrictByType("1");
        modelMap.addAttribute("provinceList", provinceList);

        //获取行业
        List<ListValues> industryList = listValuesService.selectListByType(8001);
        modelMap.addAttribute("industryList", industryList);

        //获取合作模式
        List<ListValues> cooperativeModeList = listValuesService.selectListByType(8008);
        modelMap.addAttribute("cooperativeModeList", cooperativeModeList);
        return "projectSupermarket/index";
    }

    /**
     * 列表查询
     *
     * @return
     */
    @RequestMapping("queryProjectSupermarketList")
    public ModelAndView queryProject(PageParam param, ProjectSupermarket t) {

        return ajaxJsonEscape(this.projectSupermarketService.selectByParam(param, t));
    }

    /**
     * 新增（发布）页面
     *
     * @return
     */
    @RequestMapping("addPage")
    public String addPage(ModelMap modelMap) {

        // 获取省
        List provinceList = districtService.selectDistrictByType("1");
        modelMap.addAttribute("provinceList", provinceList);

        //获取行业
        List<ListValues> industryList = listValuesService.selectListByType(8001);
        modelMap.addAttribute("industryList", industryList);

        //获取合作模式
        List<ListValues> cooperativeModeList = listValuesService.selectListByType(8008);
        modelMap.addAttribute("cooperativeModeList", cooperativeModeList);

        return "projectSupermarket/addorupdate";
    }

    /**
     * 根据省id获取市、区县
     */
    @RequestMapping("getCitys")
    public ModelAndView getCitys(String pid) {

        return ajaxJsonEscape(districtService.selectDistrictBySuperId(pid));
    }

    /**
     * 修改页面
     *
     * @return
     */
    @RequestMapping("updatePage")
    public String updatePage(ModelMap modelMap, String id) {

        // 获取省
        List provinceList = districtService.selectDistrictByType("1");
        modelMap.addAttribute("provinceList", provinceList);

        ProjectSupermarket m = this.projectSupermarketService.selectById(id);
        TjyUser tjyUser = tjyUserService.selectByPrimaryKey(m.getUserId());
        m.setMobile(tjyUser.getMobile());
        modelMap.addAttribute("obj", m);

        //获取行业
        List<ListValues> industryList = listValuesService.selectListByType(8001);
        modelMap.addAttribute("industryList", industryList);

        //获取合作模式
        List<ListValues> cooperativeModeList = listValuesService.selectListByType(8008);
        modelMap.addAttribute("cooperativeModeList", cooperativeModeList);

        return "projectSupermarket/addorupdate";
    }

    /**
     * 新增或修改保存
     *
     * @return
     */
    @RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveOrUpdateProject(ProjectSupermarket t) {

        try {
            if(t.getMobile()==null || "".equals(t.getMobile().trim())){
                return getAjaxResult("-1", "手机号不能为空", null);
            }else{
                // 认证通过的用户
                List<TjyUser> userList = tjyUserService.selectbymobile(t.getMobile(), 2);
                if (null != userList && userList.size() == 1) {
                   t.setUserId(userList.get(0).getId());
                }else{
                    return getAjaxResult("-1", "发布人不存在或为非认证用户", null);
                }
            }

            if (t.getId() == null || "".equals(t.getId())) {
                t.setIsDelete(0);
                t.setCreateTime(new Date());
                t.setCreateUserId(ServletUtil.getMember().getId());
                this.projectSupermarketService.insert(t);
                return getAjaxResult("0", "", null);
            } else {
                t.setIsDelete(0);
                t.setUpdateUserId(ServletUtil.getMember().getId());
                t.setUpdateTime(new Date());
                this.projectSupermarketService.update(t);
                return getAjaxResult("0", "", null);
            }
        } catch (Exception e) {
            return getAjaxResult("-1", "保存失败", null);
        }
    }

    /**
     * 明细
     *
     * @param id
     * @return
     */
    @RequestMapping("detail")
    public String detailProject(ModelMap modelMap, String id) {

        ProjectSupermarket m = this.projectSupermarketService.selectById(id);
        TjyUser tjyUser = tjyUserService.selectByPrimaryKey(m.getUserId());
        m.setMobile(tjyUser.getMobile());
        modelMap.addAttribute("obj", m);
        return "projectSupermarket/detail";
    }

    /**
     * 删除（逻辑删除）
     *
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        try {
            String[] arr = ids.split(",");
            ProjectSupermarket t = new ProjectSupermarket();
            for (int i = 0; i < arr.length; i++) {
                t = projectSupermarketService.
                        selectById(arr[i]);
                t.setIsDelete(1);
                t.setUpdateUserId(ServletUtil.getMember().getId());
                t.setUpdateTime(new Date());
                this.projectSupermarketService.logicalDelete(t);
            }
            return getAjaxResult("0", "", null);
        } catch (Exception e) {
            return getAjaxResult("-1", "操作失败", null);
        }
    }

    /**
     * 图片上传
     *
     * @param request
     * @param jsonp
     * @param
     * @return
     */
    @RequestMapping("uploadPic")
    @ResponseBody
    public Map<String, Object> uploadPic(HttpServletRequest request, String jsonp, MultipartFile file, String ysStyle) {
        return super.uploadImage(request, jsonp, file, ysStyle, "projectSupermarket");
    }

    /**
     * 项目意向客户
     *
     * @return
     */
    @RequestMapping("projectCustomerIndex")
    public String projectCustomerIndex(ModelMap modelMap,String fkId) {
        modelMap.addAttribute("fkId",fkId);
        return "projectSupermarket/projectCustomer";
    }

    /**
     * 项目意向客户
     *
     * @return
     */
    @RequestMapping("projectCustomerQuery")
    public ModelAndView projectCustomerQuery(PageParam param, ProjectSupermarketCustomer t) {
        t.setDeleted(0);
        DataGrid dataGrid = this.projectSupermarketCustomerService.
                selectByParam(param, t);
        return ajaxJsonEscape(dataGrid);
    }

    /**
     * 项目意向客户处理
     *
     * @return
     */
    @RequestMapping(value = "projectCustomerHandle", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> projectCustomerHandle(String ids) {

        try {
            String[] arr = ids.split(",");
            ProjectSupermarketCustomer t = new ProjectSupermarketCustomer();
            for (int i = 0; i < arr.length; i++) {
                t = projectSupermarketCustomerService.
                        selectById(arr[i]);
                t.setIsHandle(1);
                t.setUpdateUserId(ServletUtil.getMember().getId());
                t.setUpdateTime(new Date());
                this.projectSupermarketCustomerService.update(t);
            }
            return getAjaxResult("0", "", null);

        } catch (Exception e) {
            return getAjaxResult("-1", "处理失败", null);
        }
    }

    /**
     * 项目意向客户列表导出
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "export")
    public void export(HttpServletResponse response, PageParam param,
                       ProjectSupermarketCustomer t) throws IOException {

        List<ProjectSupermarketCustomer> list = this.projectSupermarketCustomerService.selectByParam(param, t).getRows();

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet0 = wb.createSheet("项目超市意向客户列表");
        HSSFRow row0 = sheet0.createRow(0);

        HSSFCellStyle titleStyle = POIUtil.getTitleStyle(wb);
        HSSFCellStyle cellStyle = POIUtil.getCellStyle(wb);

        POIUtil.setCellValue(row0.createCell(0), titleStyle, "项目名称");
        POIUtil.setCellValue(row0.createCell(1), titleStyle, "姓名");
        POIUtil.setCellValue(row0.createCell(2), titleStyle, "手机号码");
        POIUtil.setCellValue(row0.createCell(3), titleStyle, "所属公司");
        POIUtil.setCellValue(row0.createCell(4), titleStyle, "职位");
        POIUtil.setCellValue(row0.createCell(5), titleStyle, "服务秘书电话");
        POIUtil.setCellValue(row0.createCell(6), titleStyle, "提交时间");
        POIUtil.setCellValue(row0.createCell(7), titleStyle, "是否处理");

        int rowIndex = 1;

        for (ProjectSupermarketCustomer ic : list) {
            HSSFRow row = sheet0.createRow(rowIndex++);
            POIUtil.setCellValue(row.createCell(0), cellStyle, ic.getName());
            POIUtil.setCellValue(row.createCell(1), cellStyle, ic.getTrueName());
            POIUtil.setCellValue(row.createCell(2), cellStyle, ic.getMobile());
            POIUtil.setCellValue(row.createCell(3), cellStyle, ic.getComName());
            POIUtil.setCellValue(row.createCell(4), cellStyle, ic.getJobName());
            POIUtil.setCellValue(row.createCell(5), cellStyle, ic.getKfTelephone());
            POIUtil.setCellValue(row.createCell(6),
                    cellStyle, DateUtil.date2String(ic.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            POIUtil.setCellValue(row.createCell(7),
                    cellStyle, ic.getIsHandle() != null && 1 == ic.getIsHandle() ?
                            "是" : "否");

        }
        //自动列宽
        POIUtil.autoSizeColumn(row0);
        POIUtil.exportForExcle(response, wb, "项目超市意向客户列表");
    }

}
