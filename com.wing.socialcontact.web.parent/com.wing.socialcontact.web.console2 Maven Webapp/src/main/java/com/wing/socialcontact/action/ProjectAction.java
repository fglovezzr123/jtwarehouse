package com.wing.socialcontact.action;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IMessageInfoService;
import com.wing.socialcontact.service.wx.api.IProjectRecommendService;
import com.wing.socialcontact.service.wx.api.IProjectService;
import com.wing.socialcontact.service.wx.api.IProjectWillService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.bean.MessageInfo;
import com.wing.socialcontact.service.wx.bean.Project;
import com.wing.socialcontact.service.wx.bean.ProjectRecommend;
import com.wing.socialcontact.service.wx.bean.ProjectWill;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.util.AldyMessageUtil;
import com.wing.socialcontact.util.BeanMapUtils;
import com.wing.socialcontact.util.DateUtil;
import com.wing.socialcontact.util.DateUtils;
import com.wing.socialcontact.util.POIUtil;
import com.wing.socialcontact.util.UUIDGenerator;
/**
 * 项目管理
 *
 */
@Controller
@RequestMapping("/project")
public class ProjectAction extends BaseAction{
	private static final Logger logger = LoggerFactory.getLogger(ProjectAction.class);
	@Autowired
	private IProjectService projectService;

	@Autowired
	private IProjectWillService projectWillService;

	@Autowired
	private IProjectRecommendService projectRecommendService;

	@Autowired
	private IMessageInfoService messageInfoService;

	@Autowired
	private ITjyUserService tjyUserService;

	@Autowired
	private IListValuesService listValuesService;

	/**
	 * 项目首页
	 * @return
	 */
	@RequestMapping("projectindex")
	public String projectload(){
		return "project/projectindex";
	}
	/**
	 * 项目列表查询
	 * @return
	 */
	@RequestMapping("projectquery")
	public ModelAndView projectquery(PageParam param,Project t){
		return ajaxJsonEscape(this.projectService.selectAllProject2(param, t));
	}
	/**
	 * 新增（发布）项目页面
	 * @return
	 */
	@RequestMapping("projectaddpage")
	public String addPageProject(){
		return "project/projectaddorupdate";
	}
	/**
	 * 修改项目页面
	 * @return
	 */
	@RequestMapping("projectupdatepage")
	public String updatePageProject(ModelMap modelMap,String id){
		Project m = this.projectService.getProject(id,"-1");
		modelMap.addAttribute("obj", m);
		//可视范围
		modelMap.addAttribute("contentYunYou", (m.getContentVisibleRange() & 1) > 0);
		modelMap.addAttribute("contentYunQin", (m.getContentVisibleRange() & 2) > 0);
		modelMap.addAttribute("contentYunShang", (m.getContentVisibleRange() & 4) > 0);
		modelMap.addAttribute("videoYunYou", (m.getVideoVisibleRange() & 1) > 0);
		modelMap.addAttribute("videoYunQin", (m.getVideoVisibleRange() & 2) > 0);
		modelMap.addAttribute("videoYunShang", (m.getVideoVisibleRange() & 4) > 0);

		return "project/projectaddorupdate";
	}
	/**
	 * 项目新增或修改保存
	 * @return
	 */
	@RequestMapping(value="projectsaveorupdate",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveOrUpdateProject(Project t){
		try {
			if(t.getId()==null||t.getId().trim().length()==0){
				t.setId(UUIDGenerator.getUUID());
				t.setCreateTime(new Date());
				this.projectService.insertProject(t);
				return getAjaxResult("0", "", null);
			}else{
				this.projectService.updateProject(t);
				return getAjaxResult("0", "", null);
			}
		} catch (Exception e) {
			return getAjaxResult("-1", "保存失败", null);
		}
	}
	/**
	 * 项目明细
	 * @param id
	 * @return
	 */
	@RequestMapping("projectdetail")
	public String detailProject(ModelMap modelMap,String id){
		Project m = this.projectService.getProject(id,"-1");
		modelMap.addAttribute("obj", m);

		//可视范围
		modelMap.addAttribute("contentYunYou", (m.getContentVisibleRange() & 1) > 0);
		modelMap.addAttribute("contentYunQin", (m.getContentVisibleRange() & 2) > 0);
		modelMap.addAttribute("contentYunShang", (m.getContentVisibleRange() & 4) > 0);
		modelMap.addAttribute("videoYunYou", (m.getVideoVisibleRange() & 1) > 0);
		modelMap.addAttribute("videoYunQin", (m.getVideoVisibleRange() & 2) > 0);
		modelMap.addAttribute("videoYunShang", (m.getVideoVisibleRange() & 4) > 0);

		return "project/projectdetail";
	}
	/**
	 * 删除项目（逻辑删除）
	 * @param ids
	 * @return
	 */
	@RequestMapping("projectdel")
	@ResponseBody
	public Map<String,Object> deleteProject(Project t){
		try {
			this.projectService.deleteProject(t);
			return getAjaxResult("0", "", null);
		} catch (Exception e) {
			return getAjaxResult("-1", "操作失败", null);
		}
	}
	/**
	 * 报名用户页面
	 * @return
	 */
	@RequestMapping("signupindex")
	public String loadSignup(ModelMap modelMap,String prjId,String type){
		modelMap.addAttribute("prjId", prjId);
		if("2".equals(type)){
			return "project/willindex";
		}else{
			return "project/signupindex";
		}
	}
	/**
	 *  报名用户列表查询
	 * @param param
	 * @param t
	 * @return
	 */
	@RequestMapping("signupquery")
	public ModelAndView querySignup(PageParam param, String userName,String mobile,
					String createTime,String prjId){
		Map<String, Object> t = new HashMap<String, Object>();
		t.put("userName", userName);
		t.put("mobile", mobile);
		t.put("createTime", createTime);
		t.put("prjId", prjId);

		return ajaxJsonEscape(this.projectWillService.selectAllProjectWill(param, t));
	}
	/**
	 * 项目报名详细页面
	 * @param param
	 * @param t
	 * @return
	 */
	@RequestMapping("signup/detail")
	public String detailSignup(ModelMap modelMap,String id){
		ProjectWill t = projectWillService.getProjectWill(id);
		modelMap.addAttribute("obj", t);
		return "project/willdetail";
	}
	/**
	 *  报名处理
	 * @param param
	 * @param t
	 * @return
	 */
	@RequestMapping("signup/update")
	@ResponseBody
	public Map<String,Object> updateSignup(String id,Integer status){
		try {
			projectWillService.updateProjectWillStatus(id,status);
			return getAjaxResult("0", "", null);
		} catch (Exception e) {
			return getAjaxResult("-1", "保存失败", null);
		}
	}
	/**
	 * 项目征集页面
	 * @return
	 */
	@RequestMapping("collect/index")
	public String loadCollect(ModelMap modelMap,String prjId,String type){

		List list = listValuesService.selectListByType(8006003);
		modelMap.addAttribute("list", list);
		return "project/collectindex";
	}
	/**
	 * 项目征集修改
	 * @return
	 */
	@RequestMapping("collect/saveorupdate")
	@ResponseBody
	public Map<String,Object> saveOrUpdateCollect(ModelMap modelMap,ProjectRecommend t){
		try {
			int newStatus = t.getStatus()==null?1:t.getStatus().intValue();

			ProjectRecommend old = null;
			if(StringUtils.isNotBlank(t.getId())){
				old = projectRecommendService.getProjectRecommend(t.getId());
			}

			int oldStatus = old==null||old.getStatus()==null?-1:old.getStatus().intValue();

			projectRecommendService.updateProjectRecommendByMap(BeanMapUtils.toMap(t));
			try {
				if(oldStatus!=newStatus){
					TjyUser tjyUser = tjyUserService.selectById(old.getUserId());
					//需要判断审核状态是否发生了变化
					if(newStatus==1){
						//发送活动消息
						String message =AldyMessageUtil.projectrecommendaccept(tjyUser.getNickname(), old.getPrjName());
//						String message ="【"+AldyMessageUtil.SMSPRE+"】尊敬的"+tjyUser.getNickname()+"，恭喜！您所推荐的"+old.getPrjName()
//								+"项目于"+DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm")+"已通过审核。";
						//String con = WxMsmUtil.getTextMessageContent(message);
						MessageInfo messageInfo = new MessageInfo();
						messageInfo.setId(UUIDGenerator.getUUID());
						messageInfo.setDeleted(0);
						messageInfo.setType(4);
						messageInfo.setToUserId(old.getUserId());
						messageInfo.setCreateTime(new Date());
						messageInfo.setContent(message);
						messageInfo.setTemplateId("ACTIVITY_BM_TX");
						messageInfo.setStatus(0);
						messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息） */
						messageInfoService.addMessageInfo(messageInfo);
					}else if(newStatus==0){
						//发送活动消息
						String message =AldyMessageUtil.projectrecommendrefuse(tjyUser.getNickname(), old.getPrjName());
//						String message ="【"+AldyMessageUtil.SMSPRE+"】尊敬的"+tjyUser.getNickname()+"，很抱歉！您所推荐的"+old.getPrjName()
//								+"项目于"+DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm")+"未通过审核。";
						//String con = WxMsmUtil.getTextMessageContent(message);
						MessageInfo messageInfo = new MessageInfo();
						messageInfo.setId(UUIDGenerator.getUUID());
						messageInfo.setDeleted(0);
						messageInfo.setType(4);
						messageInfo.setToUserId(old.getUserId());
						messageInfo.setCreateTime(new Date());
						messageInfo.setContent(message);
						messageInfo.setTemplateId("ACTIVITY_BM_TX");
						messageInfo.setStatus(0);
						messageInfo.setWxMsgType(1);/// ** 微信消息类型（1：文本消息2：图文消息） */
						messageInfoService.addMessageInfo(messageInfo);
					}
				}
			} catch (Throwable e) {
				logger.error("自荐项目审核通过发送微信失败", e);
			}

			return getAjaxResult("0", "", null);
		} catch (Exception e) {
			return getAjaxResult("-1", "保存失败", null);
		}
	}
	/**
	 * 项目征集详细页面
	 * @return
	 */
	@RequestMapping("collect/detail")
	public String detailCollect(ModelMap modelMap,String id){
		ProjectRecommend t = projectRecommendService.getProjectRecommend(id);
		modelMap.addAttribute("obj", t);
		return "project/collectdetail";
	}
	/**
	 * 项目征集列表查询
	 * @param param
	 * @param t
	 * @return
	 */
	@RequestMapping("collectquery")
	public ModelAndView queryCollect(PageParam param, String userName,String prjName,
					Integer status,String createTime,String prjType){
		Map<String, Object> t = new HashMap<String, Object>();
		t.put("userName", userName);
		t.put("prjName", prjName);
		t.put("prjType", prjType);
		t.put("status", status);
		t.put("createTime", createTime);
		return ajaxJsonEscape(this.projectRecommendService.selectAllProjectRecommend(param, t));
	}
	/**
	 * 图片上传
	 * @param request
	 * @param jsonp
	 * @param pic
	 * @return
	 */
	@RequestMapping("uploadpic")
	@ResponseBody
	public Map<String,Object> uploadPic(HttpServletRequest request,String jsonp,MultipartFile file,String ysStyle) {
		return super.uploadImage(request, jsonp, file,ysStyle,"project");
	}

	/**
	 * 项目征集列表导出
	 * @param param
	 * @param t
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/collect/export")
	public void exportCollect(HttpServletResponse response, HttpServletRequest request,
			String userName,String prjName,Integer status,String createTime) throws IOException{
		Map<String, Object> t = new HashMap<String, Object>();
		t.put("userName", userName);
		t.put("prjName", prjName);
		t.put("status", status);
		t.put("createTime", createTime);

		List<ProjectRecommend> list = Lists.newArrayList();
		int pageNum = 1;
		int pages = 1;
		PageParam param = new PageParam(1, 200);

		for(;pageNum<=pages;pageNum++){
			param.setPage(pageNum);
			DataGrid grid = projectRecommendService.selectAllProjectRecommend(param, t);
			if(grid.getRows().size()>0){
				list.addAll(grid.getRows());
			}
			pages = grid.getPages();
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet0 = wb.createSheet("项目征集列表");
		HSSFRow row0 = sheet0.createRow(0);

		HSSFCellStyle titleStyle = POIUtil.getTitleStyle(wb);
		HSSFCellStyle cellStyle = POIUtil.getCellStyle(wb);

		POIUtil.setCellValue(row0.createCell(0), titleStyle, "项目名称");
		POIUtil.setCellValue(row0.createCell(1), titleStyle, "项目类型");
		POIUtil.setCellValue(row0.createCell(2), titleStyle, "提交人");
		POIUtil.setCellValue(row0.createCell(3), titleStyle, "联系方式");
		POIUtil.setCellValue(row0.createCell(4), titleStyle, "认证企业");
		POIUtil.setCellValue(row0.createCell(5), titleStyle, "处理状态");
		POIUtil.setCellValue(row0.createCell(6), titleStyle, "提交时间");

		int rowIndex = 1;

		for(ProjectRecommend ms :list){
			HSSFRow row = sheet0.createRow(rowIndex++);
			POIUtil.setCellValue(row.createCell(0), cellStyle,ms.getPrjName());
			POIUtil.setCellValue(row.createCell(1), cellStyle, ms.getPrjTypeName());
			POIUtil.setCellValue(row.createCell(2), cellStyle, ms.getUserName());
			POIUtil.setCellValue(row.createCell(3), cellStyle, ms.getMobile());
			POIUtil.setCellValue(row.createCell(4), cellStyle, ms.getComName());
			String statusstr = "未审核";
			if(ms.getStatus()!=null && ms.getStatus()==0){
				statusstr="不通过";
			}else if(ms.getStatus()!=null && ms.getStatus()==1){
				statusstr="通过";
			}
			POIUtil.setCellValue(row.createCell(5), cellStyle, statusstr);
			POIUtil.setCellValue(row.createCell(6), cellStyle, DateUtil.date2String(ms.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		}
		//自动列宽
		POIUtil.autoSizeColumn(row0);
		POIUtil.export(response, wb, "项目征集列表");
	}
	/**
	 * 项目报名用户列表导出
	 * @param param
	 * @param t
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/will/export")
	public void exportSignup(HttpServletResponse response, HttpServletRequest request,
			String userName,String mobile,String prjId,String createTime) throws IOException{

		Map<String, Object> t = new HashMap<String, Object>();
		t.put("userName", userName);
		t.put("mobile", mobile);
		t.put("createTime", createTime);
		t.put("prjId", prjId);

		List<ProjectWill> list = Lists.newArrayList();
		int pageNum = 1;
		int pages = 1;
		PageParam param = new PageParam(1, 200);

		for(;pageNum<=pages;pageNum++){
			param.setPage(pageNum);
			DataGrid grid = projectWillService.selectAllProjectWill(param, t);
			if(grid.getRows().size()>0){
				list.addAll(grid.getRows());
			}
			pages = grid.getPages();
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet0 = wb.createSheet("项目咨询用户列表");
		HSSFRow row0 = sheet0.createRow(0);

		HSSFCellStyle titleStyle = POIUtil.getTitleStyle(wb);
		HSSFCellStyle cellStyle = POIUtil.getCellStyle(wb);

		POIUtil.setCellValue(row0.createCell(0), titleStyle, "项目名称");
		POIUtil.setCellValue(row0.createCell(1), titleStyle, "咨询类型");
		POIUtil.setCellValue(row0.createCell(2), titleStyle, "姓名");
		POIUtil.setCellValue(row0.createCell(3), titleStyle, "手机号");
		POIUtil.setCellValue(row0.createCell(4), titleStyle, "所属公司");
		POIUtil.setCellValue(row0.createCell(5), titleStyle, "咨询时间");
		POIUtil.setCellValue(row0.createCell(6), titleStyle, "是否处理");
		POIUtil.setCellValue(row0.createCell(7), titleStyle, "意向说明");

		int rowIndex = 1;

		for(ProjectWill pw :list){
			HSSFRow row = sheet0.createRow(rowIndex++);
			POIUtil.setCellValue(row.createCell(0), cellStyle,pw.getTitles2());
			POIUtil.setCellValue(row.createCell(1), cellStyle, pw.getWillTypeName());
			POIUtil.setCellValue(row.createCell(2), cellStyle, pw.getUserName());
			POIUtil.setCellValue(row.createCell(3), cellStyle, pw.getMobile());
			POIUtil.setCellValue(row.createCell(4), cellStyle, pw.getComName());
			POIUtil.setCellValue(row.createCell(5), cellStyle, DateUtil.date2String(pw.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			POIUtil.setCellValue(row.createCell(6), cellStyle, pw.getStatus()!=null&&1==pw.getStatus()?"是":"否");
			POIUtil.setCellValue(row.createCell(7), cellStyle, pw.getWillDesc());
		}
		//自动列宽
		POIUtil.autoSizeColumn(row0);
		POIUtil.export(response, wb, "项目咨询用户列表");
	}
}
