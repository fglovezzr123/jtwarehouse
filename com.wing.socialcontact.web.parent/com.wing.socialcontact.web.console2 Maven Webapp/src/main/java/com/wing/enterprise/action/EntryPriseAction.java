package com.wing.enterprise.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.com.wing.enterprise.bean.EntryPrise;
import org.com.wing.enterprise.service.IEntryClassService;
import org.com.wing.enterprise.service.IEntryCustomerService;
import org.com.wing.enterprise.service.IEntryImgsService;
import org.com.wing.enterprise.service.IEntryPriseService;
import org.com.wing.enterprise.service.IEntryServiceClassService;
import org.com.wing.enterprise.service.IEntryServiceTagService;
import org.com.wing.enterprise.service.IEntryTagService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.FileUploadUtil;
import com.wing.socialcontact.util.QfyConstants;
import com.wing.socialcontact.util.SpringContextUtil;
/**
 * 
 * <p>Title:企业管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月5日 下午4:51:24
 */
@Controller
@RequestMapping("qfy/entry")
public class EntryPriseAction extends BaseAction {

	@Autowired
	private IEntryImgsService entryImgsService;
	@Autowired
	private IEntryCustomerService entryCustomerService;
	@Autowired
	private IEntryClassService entryClassService;
	@Autowired
	private IEntryServiceTagService entryServiceTagService;
	@Autowired
	private IEntryPriseService entryPriseService;
	@Autowired
    private IEntryServiceClassService entryServiceClassService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IEntryTagService entryTagService;
	@Autowired
    private OssConfig ossConfig;
	/**
     * 企服列表
     */
    @RequiresPermissions("entryPrise:read")
    @RequestMapping("load")
    public String entryload(){
        return "qfy/entry/load";
    }
    
    /**
     * 企服列表查询
     * @param param
     * @param EntryPrise
     * @return
     */
    @RequiresPermissions("entryPrise:read")
    @RequestMapping("query")
    public ModelAndView entryquery(PageParam param,EntryPrise entryPrise){
        entryPrise.setIsGood(2);
        
        
        
        
        DataGrid dg = entryPriseService.selEntryPrises(param, entryPrise,null);
        List lst = dg.getRows();
        for (Object object : lst) {
            String clNames = "";
            Map tagMap = (Map) object;
            List<Map> cls = entryClassService.selClassesByEntryId((String) tagMap.get("id"));
            if(cls != null && cls.size() > 0){
                for (Map map : cls) {
                    clNames += map.get("className")+",";
                }
                clNames = clNames.substring(0,clNames.length() -1);
            }
            tagMap.put("className", clNames);
            tagMap.put("logoImgPath", ossConfig.getOss_getUrl()+tagMap.get("logoImgPath"));
        }
        
        return ajaxJsonEscape(dg);
//        return ajaxJsonEscape(entryPriseService.selEntryPrises(param, entryPrise,null));
    }
    /**
     * 企服新增
     */
    @RequiresPermissions("entryPrise:add")
    @RequestMapping("addPage")
    public String entryaddPage(ModelMap map){
        return "qfy/entry/add";
    }
    
    @RequiresPermissions("entryPrise:add")
    @RequestMapping("add")
    public ModelAndView entryadd(EntryPrise dto,Errors errors,String reconImg,String bannerImg,String entryClass,String entryTags){
        if(errors.hasErrors()) {  
            ModelAndView mav=getValidationMessage(errors);
            if(mav!=null)return mav;
        }
        return ajaxDone(entryPriseService.addEntryPrise(dto,reconImg,bannerImg,entryClass,entryTags));
        
    }
    
    @RequiresPermissions("entryPrise:read")
    @RequestMapping("validUserId")
    public @ResponseBody Map validUserId(String ssUserId){
        Map res = new HashMap();
        List<TjyUser> users = tjyUserService.selectbymobile(ssUserId, 2);
        if(CollectionUtils.isEmpty(users)){
            res.put("flag", 0);
        }else{
            res.put("flag", 1);
        }
        
        res.put("code", Constants.AJAX_CODE_SUCCESS);
        return res;
        
    }
    
    /**
     * 企服修改
     */
    @RequiresPermissions("entryPrise:update")
    @RequestMapping("updatePage")
    public String entryupdatePage(String id,ModelMap map){
        EntryPrise dto = entryPriseService.getEntryPriseByid(id);
        map.addAttribute("dto", dto);

        List<Map> banners = entryImgsService.selectByParam(id, QfyConstants.QFY_ENTRY_IMG_BANNER);
        net.sf.json.JSONArray bannerJson = net.sf.json.JSONArray.fromObject(banners);
        map.addAttribute("banners", bannerJson.toString());
        
        List<Map> recons = entryImgsService.selectByParam(id, QfyConstants.QFY_ENTRY_IMG_RECON);
        net.sf.json.JSONArray reconsJson = net.sf.json.JSONArray.fromObject(recons);
        map.addAttribute("recons", reconsJson.toString());
        
        List<Map> entryClasses = entryClassService.selClassesByEntryId(id);
        net.sf.json.JSONArray classJson = net.sf.json.JSONArray.fromObject(entryClasses);
        map.addAttribute("entryClasses", classJson.toString());
        
        List<Map> entryTags = entryTagService.selTagsByEntryId(id);
        net.sf.json.JSONArray tagJson = net.sf.json.JSONArray.fromObject(entryTags);
        map.addAttribute("entryTags", tagJson.toString());
        
        return "qfy/entry/update";
    }
    
    @RequiresPermissions("entryPrise:update")
    @RequestMapping("update")
    public ModelAndView entryupdate(EntryPrise dto,Errors errors,String reconImg,String bannerImg,String entryClass,String entryTags){
        if(errors.hasErrors()) {  
            ModelAndView mav=getValidationMessage(errors);
            if(mav!=null)return mav;
        }
        return ajaxDone(entryPriseService.updateEntryPrise(dto,reconImg,bannerImg,entryClass,entryTags));
        
    }
    
    /**
     * 企服删除
     */
    @RequiresPermissions("entryPrise:delete")
    @RequestMapping("del")
    public ModelAndView del(String[] ids){
        
        return ajaxDone(entryPriseService.deleteEntryPrise(ids));
    }
    
    /**
     * 弹出选择页面
     */
    @RequiresPermissions("entryPrise:read")
    @RequestMapping("queryClass")
    public String queryClassPage(ModelMap map){
        List classes = entryServiceClassService.selectSecond();
        map.addAttribute("classes", classes);
        return "qfy/serviceClass/query_class";
    }
    /**
     * 弹出选择页面
     */
    @RequiresPermissions("entryPrise:read")
    @RequestMapping("queryTags")
    public String queryTagsPage(ModelMap map){
        List tags = entryServiceTagService.selectAllTags();
        map.addAttribute("tags", tags);
        return "qfy/serviceTag/query_tag";
    }
    
    /**
     * 统计列表
     */
    @RequiresPermissions("entryPrise:read")
    @RequestMapping("staticLoad")
    public String staticLoad(){
        return "qfy/entry/staticLoad";
    }
    
    /**
     * 企服统计列表查询
     * @param param
     * @param EntryPrise
     * @return
     */
    @RequiresPermissions("entryPrise:read")
    @RequestMapping("staticQuery")
    public ModelAndView staticQuery(PageParam param,EntryPrise entryPrise){
        return ajaxJsonEscape(entryPriseService.selStaticEntry(param, entryPrise));
    }
    
    /**
     * 
     * //TODO 富文本编辑器上传图片
     * @param request
     * @param response
     * @param ysStyle
     * @throws ClassNotFoundException
     */
    @RequestMapping("upload")
    public void upload(HttpServletRequest request, HttpServletResponse response,String ysStyle,String moduleName) throws ClassNotFoundException {
        String msg = "";
        JSONObject obj = new JSONObject();
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("imgFile");
            file = file==null||file.isEmpty()?(CommonsMultipartFile) multipartRequest.getFile("file"):file;
            InputStream io = file.getInputStream();
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            String picPath = FileUploadUtil.uploadFileInputStream(io, ext, moduleName);
            OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
            String ossurl = ossConfig.getOss_getUrl();
            obj.put("error", Integer.valueOf(0));
            obj.put("url", ossurl+picPath+"?x-oss-process=style/"+ysStyle);
        //  obj.put("fileName",picPath);
        } catch (IOException e) {
            obj.put("error", Integer.valueOf(1));
            obj.put("message", e.getMessage());
            e.printStackTrace();
        }
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.print(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping("staticExport")
    public void staticExport(HttpServletResponse response, HttpServletRequest request,String ssUserId,String name,String title){
        OutputStream  os=null;
        try{
            EntryPrise ep = new EntryPrise();
            ep.setSsUserId(ssUserId);
            ep.setName(name);
            ep.setTitle(title);
            List<Map> list = entryPriseService.selStaticEntryExport(ep);
              // 创建新的Excel 工作簿
              HSSFWorkbook workbook = new HSSFWorkbook();
              HSSFSheet sheet = workbook.createSheet();
              //第二行添加类型名称
              HSSFRow secondRow = sheet.createRow(0);
              
              for(int i=0;i<7;i++){
                  HSSFCell cell = secondRow.createCell(i);
                  cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                  switch (i) {
                    case 0:
                        cell.setCellValue("企业名称");
                        break;
                    case 1:
                        cell.setCellValue("联盟标题");
                        break;
                    case 2:
                        cell.setCellValue("所属ID");
                        break;
                    case 3:
                        cell.setCellValue("电话咨询");
                        break;
                    case 4:
                        cell.setCellValue("在线咨询");
                        break;
                    case 5:
                        cell.setCellValue("合计");
                        break;
                    case 6:
                        cell.setCellValue("服务数量");
                        break;
                }
              }
              
              for(int i =0;i<list.size();i++){
                  Map map = list.get(i);
              
                  HSSFRow row = sheet.createRow(i+1);
                  for(int j=0;j<7;j++){
                      HSSFCell cell = row.createCell(j);
                      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//                      if(j<=2){
//                          cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//                      }else{
//                          cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//                      }
                      switch (j) {
                          case 0:
                              cell.setCellValue((String)map.get("name"));
                              break;
                          case 1:
                              cell.setCellValue((String)map.get("title"));
                              break;
                          case 2:
                              cell.setCellValue((String)map.get("ssUserId"));
                              break;
                          case 3:
                              //cell.setCellValue(String.valueOf(map.get("phoneCount")).equals("") ? "0" : String.valueOf(map.get("phoneCount")));
                              cell.setCellValue(map.get("phoneCount") == null ? "0" : String.valueOf(map.get("phoneCount")));
                              break;
                          case 4:
                              cell.setCellValue(map.get("msgCount") == null ? "0" : String.valueOf(map.get("msgCount")));
                              break;
                          case 5:
                              cell.setCellValue(map.get("totalCount") == null ? "0" : String.valueOf(map.get("totalCount")));
                              break;
                          case 6:
                              cell.setCellValue(map.get("serviceCount") == null ? "0" : String.valueOf(map.get("serviceCount")));
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
                  response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("企服联盟统计.xls","UTF8")); 
                  
              }else{
                  response.addHeader("Content-Disposition", "attachment;filename=" + new String("企服联盟统计.xls".getBytes("UTF-8"),"ISO-8859-1"));  
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
