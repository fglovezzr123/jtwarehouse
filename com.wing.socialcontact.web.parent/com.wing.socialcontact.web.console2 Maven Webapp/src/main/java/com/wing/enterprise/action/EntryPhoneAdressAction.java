package com.wing.enterprise.action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.EntryPhoneAdress;
import org.com.wing.enterprise.service.IEntryPhoneAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.DateUtil;

/**
 * 上传通讯录
 * 
 * 
 * @ClassName: EntryPhoneAdressAction
 * @Description: TODO
 * @author: sino
 * @date:2017年5月12日
 */
@Controller
@RequestMapping("/qfy/phoneAdress")
public class EntryPhoneAdressAction extends BaseAction {

	@Autowired
	private IEntryPhoneAddressService entryPhoneAddressService;
	
	/**
     * 通讯录列表
     */
    @RequiresPermissions("phoneAdress:read")
    @RequestMapping("load")
    public String entryPhoneAdressload(){
        return "qfy/phoneAdress/load";
    }
    
    /**
     * 通讯录列表查询
     * @param param
     * @param 
     * @return
     */
    @RequiresPermissions("phoneAdress:read")
    @RequestMapping("query")
    public ModelAndView phoneAdressQuery(PageParam param,EntryPhoneAdress entryPhoneAdress){
        return ajaxJsonEscape(entryPhoneAddressService.selectPhoneAdress(param, entryPhoneAdress));
    }
    
  //巡视信息分组统计
    @RequestMapping("export")
    public void patrollookGroup(HttpServletResponse response, HttpServletRequest request,String ssUserId,String ssUserName){
        OutputStream  os=null;
        try{
            EntryPhoneAdress entryPhoneAdress = new EntryPhoneAdress();
            entryPhoneAdress.setSsUserId(ssUserId);
            entryPhoneAdress.setSsUserName(ssUserName);
            List<Map> list = entryPhoneAddressService.selectPhoneAdress(entryPhoneAdress);
              // 创建新的Excel 工作簿
              HSSFWorkbook workbook = new HSSFWorkbook();
              HSSFSheet sheet = workbook.createSheet();
              //第二行添加类型名称
              HSSFRow secondRow = sheet.createRow(0);
              
              for(int i=0;i<6;i++){
                  HSSFCell cell = secondRow.createCell(i);
                  cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                  switch (i) {
                    case 0:
                        cell.setCellValue("通讯录姓名");
                        break;
                    case 1:
                        cell.setCellValue("手机号");
                        break;
                    case 2:
                        cell.setCellValue("所属手机号");
                        break;
                    case 3:
                        cell.setCellValue("用户ID");
                        break;
                    case 4:
                        cell.setCellValue("账号人姓名");
                        break;
                    case 5:
                        cell.setCellValue("创建时间");
                        break;
                }
              }
              
              for(int i =0;i<list.size();i++){
                  Map map = list.get(i);
              
                  HSSFRow row = sheet.createRow(i+1);
                  for(int j=0;j<6;j++){
                      HSSFCell cell = row.createCell(j);
                      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                      switch (j) {
                          case 0:
                              cell.setCellValue((String)map.get("userName"));
                              break;
                          case 1:
                              cell.setCellValue((String)map.get("userPhone"));
                              break;
                          case 2:
                              cell.setCellValue((String)map.get("ssUserPhone"));
                              break;
                          case 3:
                              cell.setCellValue((String)map.get("ssUserId"));
                              break;
                          case 4:
                              cell.setCellValue((String)map.get("ssUserName"));
                              break;
                          case 5:
                              cell.setCellValue(DateUtil.date2StringHHMM((Date)map.get("createTime")));
                              break;
                      }
                  }
              }
              
              //以下载的形式
              response.setContentType("application/octet-stream"); 
              
              // 根据不同浏览器 设置response的Header
              String userAgent = request.getHeader("User-Agent").toLowerCase();
              
              //response.setContentType("application/vnd.ms-excel");
              
              if(userAgent.indexOf("msie")!=-1){
                  //ie浏览器
                  response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户通讯录.xls","UTF8")); 
                  
              }else{
                  response.addHeader("Content-Disposition", "attachment;filename=" + new String("用户通讯录.xls".getBytes("UTF-8"),"ISO-8859-1"));  
              }
              
              os=response.getOutputStream();
              workbook.write(os);
              os.flush();
          }catch(Exception e) {
               e.printStackTrace();
          }finally{
              if(os!=null){
                   try {
                      os.close();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
                }
          }
    }
}
