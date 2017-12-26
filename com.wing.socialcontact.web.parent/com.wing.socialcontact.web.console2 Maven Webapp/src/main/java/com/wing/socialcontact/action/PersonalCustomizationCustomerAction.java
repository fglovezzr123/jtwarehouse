package com.wing.socialcontact.action;


import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IPersonalCustomizationCustomerService;
import com.wing.socialcontact.service.wx.bean.PersonalCustomizationCustomer;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 个性定制意向客户
 *
 * @author wangyansheng
 * @date 2017/11/01
 */
@Controller
@RequestMapping("/personalCustomizationCustomer")
public class PersonalCustomizationCustomerAction extends BaseAction {

    @Autowired
    private IPersonalCustomizationCustomerService iPersonalCustomizationCustomerService;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("index")
    public String projectload() {
        return "personalCustomizationCustomer/index";
    }

    /**
     * 列表查询
     *
     * @return
     */
    @RequestMapping("query")
    public ModelAndView projectquery(PageParam param, PersonalCustomizationCustomer t) {
        t.setDeleted(0);
        DataGrid dataGrid = this.iPersonalCustomizationCustomerService.
                selectByParam(param, t);
        return ajaxJsonEscape(dataGrid);
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping(value = "handle", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> handle(String ids) {

        try {
            String[] arr = ids.split(",");
            PersonalCustomizationCustomer t = new PersonalCustomizationCustomer();
            for (int i = 0; i < arr.length; i++) {
                t = iPersonalCustomizationCustomerService.
                        selectById(Long.parseLong(arr[i]));
                t.setIsHandle(1);
                t.setUpdateUserId(ServletUtil.getMember().getId());
                t.setUpdateTime(new Date());
                this.iPersonalCustomizationCustomerService.update(t);
            }
            return getAjaxResult("0", "", null);

        } catch (Exception e) {
            return getAjaxResult("-1", "处理失败", null);
        }
    }

    /**
     * 列表导出
     *
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "export")
    public void export(HttpServletResponse response, PageParam param,
                       PersonalCustomizationCustomer t) throws IOException {

        t.setDeleted(0);
        List<PersonalCustomizationCustomer> list = this.iPersonalCustomizationCustomerService.selectByParam(param, t).getRows();

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet0 = wb.createSheet("个人定制意向客户列表");
        HSSFRow row0 = sheet0.createRow(0);

        HSSFCellStyle titleStyle = POIUtil.getTitleStyle(wb);
        HSSFCellStyle cellStyle = POIUtil.getCellStyle(wb);

        POIUtil.setCellValue(row0.createCell(0), titleStyle, "服务名称");
        POIUtil.setCellValue(row0.createCell(1), titleStyle, "姓名");
        POIUtil.setCellValue(row0.createCell(2), titleStyle, "手机号码");
        POIUtil.setCellValue(row0.createCell(3), titleStyle, "所属公司");
        POIUtil.setCellValue(row0.createCell(4), titleStyle, "提交时间");
        POIUtil.setCellValue(row0.createCell(5), titleStyle, "服务秘书电话");
        POIUtil.setCellValue(row0.createCell(6), titleStyle, "是否处理");

        int rowIndex = 1;

        for (PersonalCustomizationCustomer ic : list) {
            HSSFRow row = sheet0.createRow(rowIndex++);
            POIUtil.setCellValue(row.createCell(0), cellStyle, ic.getTitle());
            POIUtil.setCellValue(row.createCell(1), cellStyle, ic.getTrueName());
            POIUtil.setCellValue(row.createCell(2), cellStyle, ic.getMobile());
            POIUtil.setCellValue(row.createCell(3), cellStyle, ic.getComName());
            POIUtil.setCellValue(row.createCell(4),
                    cellStyle, DateUtil.date2String(ic.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            POIUtil.setCellValue(row.createCell(5), cellStyle, ic.getKfTelephone());
            POIUtil.setCellValue(row.createCell(6),
                    cellStyle, ic.getIsHandle() != null && 1 == ic.getIsHandle() ?
                            "是" : "否");

        }
        //自动列宽
        POIUtil.autoSizeColumn(row0);
        POIUtil.exportForExcle(response, wb, "个人定制意向客户列表");
    }

    /**
     * 删除个人定制（逻辑删除）
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        try {
            String[] arr = ids.split(",");
            PersonalCustomizationCustomer t = new PersonalCustomizationCustomer();
            for (int i = 0; i < arr.length; i++) {
                t = iPersonalCustomizationCustomerService.
                        selectById(Long.parseLong(arr[i]));
                t.setDeleted(1);
                t.setUpdateUserId(ServletUtil.getMember().getId());
                t.setUpdateTime(new Date());
                this.iPersonalCustomizationCustomerService.update(t);
            }

            return getAjaxResult("0", "", null);
        } catch (Exception e) {
            return getAjaxResult("-1", "操作失败", null);
        }
    }

}
