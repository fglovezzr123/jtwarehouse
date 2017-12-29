//package com.wing.socialcontact.action;
//
//
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.tojoy.common.model.DataGrid;
//import com.tojoy.common.model.PageParam;
//import com.tojoy.service.wx.api.IMeetingService;
//import com.tojoy.service.wx.api.IMeetingSignupService;
//import com.tojoy.service.wx.bean.Meeting;
//import com.tojoy.service.wx.bean.MeetingRelation;
//import com.tojoy.service.wx.bean.MeetingSignup;
//import com.tojoy.service.wx.bean.MeetingSignupRemind;
//import com.tojoy.service.wx.bean.MeetingWhitelist;
//import com.wing.socialcontact.config.MsgConfig;
//
//import com.wing.socialcontact.service.wx.api.IProjectService;
//import com.wing.socialcontact.service.wx.api.ITjyUserService;
//import com.wing.socialcontact.service.wx.api.IWxUserService;
//
//import com.wing.socialcontact.service.wx.bean.Project;
//import com.wing.socialcontact.service.wx.bean.TjyUser;
//import com.wing.socialcontact.sys.action.BaseAction;
//import com.wing.socialcontact.sys.bean.ListValues;
//import com.wing.socialcontact.sys.bean.SyDistrict;
//import com.wing.socialcontact.sys.service.IDistrictService;
//import com.wing.socialcontact.sys.service.IListValuesService;
//import com.wing.socialcontact.util.BeanMapUtils;
//import com.wing.socialcontact.util.DateUtil;
//import com.wing.socialcontact.util.DateUtils;
//import com.wing.socialcontact.util.POIUtil;
//import com.wing.socialcontact.util.ServletUtil;
//import com.wing.socialcontact.util.StringUtil;
//import com.wing.socialcontact.util.UUIDGenerator;
//import com.wing.socialcontact.vhall.api.WebinarAPI;
//import com.wing.socialcontact.vhall.resp.WebinarFetchResp;
//import com.wing.socialcontact.vhall.resp.WebinarListResp;
//
///**
// * 投融保-会议管理
// */
//@Controller
//@RequestMapping("/meetingApp")
//public class MeetingAppAction extends BaseAction {
//    private static Logger logger = LoggerFactory.getLogger(Meeting.class);
//    @Autowired
//    private IMeetingService meetingAppService;
//    @Autowired
//    private IProjectService projectService;
//    @Autowired
//    private IMeetingSignupService meetingAppSignupService;
//    @Autowired
//    private ITjyUserService tjyUserService;
//    @Autowired
//    private IWxUserService wxUserService;
//    @Autowired
//    private IListValuesService listValuesService;
//    @Autowired
//    private IDistrictService districtService;
//
//    /**
//     * 会议首页
//     *
//     * @return
//     */
//    @RequestMapping("meetingindex")
//    public String loadMeeting() {
//        return "meetingApp/meetingindex";
//    }
//
//    /**
//     * 会议列表查询
//     *
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    @RequestMapping("meetingquery")
//    public ModelAndView queryMeeting(PageParam param, Meeting t) {
//        DataGrid grid = this.meetingAppService.selectAllMeeting(param, t);
//        List<Meeting> list = grid.getRows();
//        for (Meeting m : list) {
//            //计算会议状态
//            m.calcStatus();
//        }
//        return ajaxJsonEscape(grid);
//    }
//
//    /**
//     * 会议白名单列表
//     *
//     * @return
//     */
//    @RequestMapping("meetingwhitelist")
//    public String loadMeetingWhitelist(ModelMap map, String meetingId) {
//        map.addAttribute("meetingId", StringUtils.isEmpty(meetingId) ? "-1" : meetingId);
//        return "meetingApp/meetingwhitelist";
//    }
//
//    /**
//     * 会议白名单查询
//     *
//     * @return
//     */
//    @RequestMapping("meetingwhitelistquery")
//    public ModelAndView queryMeetingWhitelist(PageParam param, MeetingWhitelist t) {
//        DataGrid grid = this.meetingAppService.selectAllMeetingWhitelist(param, t);
//        return ajaxJsonEscape(grid);
//    }
//
//    /**
//     * 新增（发布）会议页面
//     *
//     * @return
//     */
//    @RequestMapping("meetingaddpage")
//    public String addPageMeeting(ModelMap map) {
//        // 获取省
//        List provinceList = districtService.selectDistrictByType("1");
//        map.addAttribute("provinceList", provinceList);
//        return "meetingApp/meetingaddorupdate";
//    }
//
//    /**
//     * 修改会议页面
//     *
//     * @return
//     */
//    @RequestMapping("meetingupdatepage")
//    public String updatePageMeeting(ModelMap modelMap, String id) {
//        Meeting m = this.meetingAppService.getMeeting(id);
//        modelMap.addAttribute("obj", m);
//
//        //可视范围
//        modelMap.addAttribute("contentYunYou", (m.getContentVisibleRange() & 1) > 0);
//        modelMap.addAttribute("contentYunQin", (m.getContentVisibleRange() & 2) > 0);
//        modelMap.addAttribute("contentYunShang", (m.getContentVisibleRange() & 4) > 0);
//
//        modelMap.addAttribute("videoYunYou", (m.getVideoVisibleRange() & 1) > 0);
//        modelMap.addAttribute("videoYunQin", (m.getVideoVisibleRange() & 2) > 0);
//        modelMap.addAttribute("videoYunShang", (m.getVideoVisibleRange() & 4) > 0);
//        // 获取省
//        List provinceList = districtService.selectDistrictByType("1");
//        modelMap.addAttribute("provinceList", provinceList);
//        return "meetingApp/meetingaddorupdate";
//    }
//
//    /**
//     * 会议新增或修改保存
//     *
//     * @return
//     */
//    @RequestMapping(value = "meetingsaveorupdate", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> saveOrUpdateMeeting(Meeting t) {
//        try {
//
//            //线下会议
//            if (t.getTypes() != null && t.getTypes() == 2) {
//                t.setWebinarIds("");
//                t.setWebinarId("");
//                t.setWebinarSubject("");
//            }
//
//            //关联视频
//            if (t.getWebinarIds() != null && t.getWebinarIds().trim().length() != 0) {
//                String[] webinarIds = t.getWebinarIds().split(",");
//
//                List<MeetingRelation> meetingRelationList = new ArrayList<MeetingRelation>();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//                for (int i = 0; i < webinarIds.length; i++) {
//                    MeetingRelation relation = new MeetingRelation();
//
//                    WebinarFetchResp webinarFetchResp = WebinarAPI.fetchWebinar(webinarIds[i]);
//                    if (webinarFetchResp.isSuccess()) {
//                        relation.setCreatTime(new Date());
//                        relation.setMeetingId(t.getId());
//                        relation.setWebinarId(webinarIds[i]);
//                        relation.setWebinarStartTime(sdf.parse(webinarFetchResp.getT_start()));
//                        relation.setWebinarSubject(webinarFetchResp.getSubject());
//                        relation.setWebinarThumb(webinarFetchResp.getImg_url());
//                        meetingRelationList.add(relation);
//                    }
//                }
//
//                t.setMeetingRelationList(meetingRelationList);
//            }
//
//            if (t.getId() == null || t.getId().trim().length() == 0) {
//                t.setId(UUIDGenerator.getUUID());
//                t.setCreateTime(new Date());
//                t.setCreateUserId(ServletUtil.getMember().getId());
//                this.meetingAppService.insertMeeting(t);
//                return getAjaxResult("0", "", null);
//            } else {
//                this.meetingAppService.updateMeeting(t);
//                return getAjaxResult("0", "", null);
//            }
//        } catch (Exception e) {
//            logger.error("保存会议信息失败", e);
//            return getAjaxResult("-1", "保存失败", null);
//        }
//    }
//
//    /**
//     * 会议明细
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("meetingdetail")
//    public String detailMeeting(ModelMap modelMap, String id) {
//        Meeting m = this.meetingAppService.getMeeting(id);
//        modelMap.addAttribute("obj", m);
//        modelMap.addAttribute("contentYunYou", (m.getContentVisibleRange() & 1) > 0);
//        modelMap.addAttribute("contentYunQin", (m.getContentVisibleRange() & 2) > 0);
//        modelMap.addAttribute("contentYunShang", (m.getContentVisibleRange() & 4) > 0);
//
//        modelMap.addAttribute("videoYunYou", (m.getContentVisibleRange() & 1) > 0);
//        modelMap.addAttribute("videoYunQin", (m.getContentVisibleRange() & 2) > 0);
//        modelMap.addAttribute("videoYunShang", (m.getContentVisibleRange() & 4) > 0);
//
//        return "meetingApp/meetingdetail";
//    }
//
//    /**
//     * 删除会议（逻辑删除）
//     *
//     * @param ids
//     * @return
//     */
//    @RequestMapping("meetingdel")
//    @ResponseBody
//    public Map<String, Object> deleteMeeting(Meeting t) {
//        try {
//            this.meetingAppService.deleteMeeting(t);
//            return getAjaxResult("0", "", null);
//        } catch (Exception e) {
//            return getAjaxResult("-1", "操作失败", null);
//        }
//    }
//
//    /**
//     * 报名用户页面
//     *
//     * @return
//     */
//    @RequestMapping("signupindex")
//    public String loadSignup(ModelMap modelMap, String meetingId) {
//        modelMap.addAttribute("meetingId", meetingId);
//        return "meetingApp/signupindex";
//    }
//
//    /**
//     * 报名用户列表查询
//     *
//     * @param param
//     * @param t
//     * @return
//     */
//    @RequestMapping("signupquery")
//    public ModelAndView querySignup(PageParam param, String nickname, String mobile, String signupTime,
//                                    String meetingId, Integer attendType, Integer payType) {
//        Map<String, Object> t = new HashMap<String, Object>();
//        t.put("nickname", nickname);
//        t.put("mobile", mobile);
//        t.put("signupTime", signupTime);
//        t.put("attendType", attendType);
//        t.put("meetingId", meetingId);
//        t.put("payType", payType);
//
//        return ajaxJsonEscape(this.meetingAppSignupService.selectAllMeetingSignup(param, t));
//    }
//
//    /**
//     * 报名详细信息
//     *
//     * @return
//     */
//    @RequestMapping("signupdetail")
//    public String detailSignup(ModelMap modelMap, String id) {
//        MeetingSignup ms = this.meetingAppSignupService.getMeetingSignup(id);
//        TjyUser tjyUser = null;
//        Map<String, Object> wxUser = null;
//        if (ms != null && StringUtils.isNotBlank(ms.getUserId())) {
//            tjyUser = getTjyUserById(ms.getUserId());
//            wxUser = getWxUserById(ms.getUserId());
//        }
//        ms = ms == null ? new MeetingSignup() : ms;
//        tjyUser = tjyUser == null ? new TjyUser() : tjyUser;
//        wxUser = wxUser == null ? new HashMap<String, Object>() : wxUser;
//        ms.setMobile(StringUtils.isBlank(ms.getMobile()) ? tjyUser.getMobile() : ms.getMobile());
//
//        modelMap.addAttribute("obj", ms);
//        modelMap.addAttribute("user", tjyUser);
//        modelMap.addAttribute("tjyUser", tjyUser);
//        modelMap.addAttribute("wxUser", wxUser);
//        return "meetingApp/signupdetail";
//    }
//
//    private Map<String, Object> getWxUserById(String id) {
//        Map<String, Object> user = wxUserService.queryUsersByid(id);
//
//        if (!org.springframework.util.StringUtils.isEmpty(user.get("province"))) {
//            SyDistrict sd = districtService.selectByPrimaryKey((String) user.get("province"));
//            user.put("province", sd.getDisName());
//        }
//        if (!org.springframework.util.StringUtils.isEmpty(user.get("city"))) {
//            SyDistrict sd = districtService.selectByPrimaryKey((String) user.get("city"));
//            user.put("city", sd.getDisName());
//        }
//        if (!org.springframework.util.StringUtils.isEmpty(user.get("county"))) {
//            SyDistrict sd = districtService.selectByPrimaryKey((String) user.get("county"));
//            user.put("county", sd.getDisName());
//        }
//        return user == null ? new HashMap<String, Object>() : user;
//    }
//
//    private TjyUser getTjyUserById(String id) {
//        TjyUser tjyUser = tjyUserService.selectByPrimaryKey(id);
//        if (tjyUser == null) return null;
//
//        // 获取职位
//        if (StringUtils.isNotBlank(tjyUser.getJob())) {
//            ListValues job = listValuesService.selectByPrimaryKey(tjyUser.getJob());
//            tjyUser.setJob(job != null ? job.getListValue() : tjyUser.getJob());
//        }
//        // 获取行业
//        if (StringUtils.isNotBlank(tjyUser.getIndustry())) {
//            ListValues industry = listValuesService.selectByPrimaryKey(tjyUser.getIndustry());
//            tjyUser.setIndustry(industry != null ? industry.getListValue() : tjyUser.getIndustry());
//        }
//        // 获取省
//        if (StringUtils.isNotBlank(tjyUser.getProvince())) {
//            SyDistrict province = districtService.selectByPrimaryKey(tjyUser.getProvince());
//            tjyUser.setProvince(province != null ? province.getDisName() : tjyUser.getProvince());
//        }
//        // 获取市
//        if (StringUtils.isNotBlank(tjyUser.getCity())) {
//            SyDistrict city = districtService.selectByPrimaryKey(tjyUser.getCity());
//            tjyUser.setCity(city != null ? city.getDisName() : tjyUser.getCity());
//        }
//        // 获取区
//        if (StringUtils.isNotBlank(tjyUser.getRegion())) {
//            SyDistrict region = districtService.selectByPrimaryKey(tjyUser.getRegion());
//            tjyUser.setRegion(region != null ? region.getDisName() : tjyUser.getRegion());
//        }
//        return tjyUser;
//    }
//
//    /**
//     * 图片上传
//     *
//     * @param request
//     * @param jsonp
//     * @param pic
//     * @return
//     */
//    @SuppressWarnings("rawtypes")
//    @RequestMapping("uploadpic")
//    @ResponseBody
//    public Map uploadPic(HttpServletRequest request, String jsonp, MultipartFile file, String ysStyle) {
//        return super.uploadImage(request, jsonp, file, ysStyle, "meeting");
//    }
//
//    /**
//     * 项目首页
//     *
//     * @return
//     */
//    //@RequiresPermissions("newsclass:read")
//    @RequestMapping("projet/index")
//    public String loadProject() {
//        return "meetingApp/projectindex";
//    }
//
//    /**
//     * 项目列表查询
//     *
//     * @return
//     */
//    @RequestMapping("projet/query")
//    public ModelAndView queryProject(com.wing.socialcontact.common.model.PageParam param, Project t) {
//        return ajaxJsonEscape(this.projectService.selectAllProject(param, BeanMapUtils.toMap(t)));
//    }
//
//    /**
//     * 往届会议首页
//     *
//     * @return
//     */
//    @RequestMapping("meetingSuccessive/index")
//    public String loadMeetingSuccessive() {
//        return "meetingApp/meetingSuccessiveIndex";
//    }
//
//    /**
//     * 微吼首页
//     *
//     * @return
//     */
//    @RequestMapping("vhall/index")
//    public String loadVhall() {
//        return "meetingApp/vhallindex";
//    }
//
//    /**
//     * 微吼首页
//     *
//     * @return
//     */
//    @RequestMapping("vhallapp/index")
//    public String loadVhallApp() {
//        return "meetingApp/vhallpastindex";
//    }
//
//    /**
//     * 会议列表查询
//     *
//     * @return
//     */
//    @RequestMapping("vhall/query")
//    public ModelAndView queryVhall(PageParam param, Project t) {
//        DataGrid data = new DataGrid();
//        PageHelper.startPage(param.getPage(), param.getRows());
//        WebinarListResp resp = WebinarAPI.listWebinar(param.getPage(), param.getRows());
//        List<WebinarListResp.Webinar> list = resp.getList();
////		PageHelper.startPage(1, list.size()==0?20:list.size());
////		PageInfo<WebinarListResp.Webinar> page = new PageInfo<WebinarListResp.Webinar>(list);
//        data.setRows(list);
//        data.setTotal(resp.getTotal());
//        return ajaxJsonEscape(data);
//    }
//
//    /**
//     * 会议报名用户到出
//     */
//    @SuppressWarnings("unchecked")
//    @RequestMapping("/signup/export")
//    public void exportSignup(HttpServletResponse response, HttpServletRequest request
//            , String nickname, String mobile, String signupTime, String meetingId, Integer attendType, Integer payType) throws IOException {
//        Map<String, Object> t = new HashMap<String, Object>();
//        t.put("nickname", nickname);
//        t.put("mobile", mobile);
//        t.put("signupTime", signupTime);
//        t.put("attendType", attendType);
//        t.put("meetingId", meetingId);
//        t.put("payType", payType);
//
//        List<MeetingSignup> list = Lists.newArrayList();
//        int pageNum = 1;
//        int pages = 1;
//        PageParam param = new PageParam(1, 200);
//
//        for (; pageNum <= pages; pageNum++) {
//            param.setPage(pageNum);
//            DataGrid grid = meetingAppSignupService.selectAllMeetingSignup(param, t);
//            if (grid.getRows().size() > 0) {
//                list.addAll(grid.getRows());
//            }
//            pages = grid.getPages();
//        }
//
//
//        HSSFWorkbook wb = new HSSFWorkbook();
//        HSSFSheet sheet0 = wb.createSheet("会议报名用户列表");
//        HSSFRow row0 = sheet0.createRow(0);
//
//        HSSFCellStyle titleStyle = POIUtil.getTitleStyle(wb);
//        HSSFCellStyle cellStyle = POIUtil.getCellStyle(wb);
//
//        POIUtil.setCellValue(row0.createCell(0), titleStyle, "会议主题");
//        POIUtil.setCellValue(row0.createCell(1), titleStyle, "用户姓名");
//        POIUtil.setCellValue(row0.createCell(2), titleStyle, "性别");
//        POIUtil.setCellValue(row0.createCell(3), titleStyle, "出生日期");
//        POIUtil.setCellValue(row0.createCell(4), titleStyle, "手机号");
//        POIUtil.setCellValue(row0.createCell(5), titleStyle, "企业名称");
//        POIUtil.setCellValue(row0.createCell(6), titleStyle, "职位");
//        POIUtil.setCellValue(row0.createCell(7), titleStyle, "所属行业");
//        POIUtil.setCellValue(row0.createCell(8), titleStyle, "所在地区");
////		POIUtil.setCellValue(row0.createCell(9), titleStyle, "主营业务");
////		POIUtil.setCellValue(row0.createCell(10), titleStyle, "注册资金(万元)");
////		POIUtil.setCellValue(row0.createCell(11), titleStyle, "实缴资本(万元)");
////		POIUtil.setCellValue(row0.createCell(12), titleStyle, "总资产(万元)");
////		POIUtil.setCellValue(row0.createCell(13), titleStyle, "年销售额(万元)");
//        POIUtil.setCellValue(row0.createCell(9), titleStyle, "门票价格(元)");
//        POIUtil.setCellValue(row0.createCell(10), titleStyle, "参会方式");
//        POIUtil.setCellValue(row0.createCell(11), titleStyle, "其它需求");
//        POIUtil.setCellValue(row0.createCell(12), titleStyle, "服务秘书电话");
//        POIUtil.setCellValue(row0.createCell(13), titleStyle, "天九联系人");
//        POIUtil.setCellValue(row0.createCell(14), titleStyle, "推荐人");
//        POIUtil.setCellValue(row0.createCell(15), titleStyle, "报名时间");
//        POIUtil.setCellValue(row0.createCell(16), titleStyle, "支付状态");
//        POIUtil.setCellValue(row0.createCell(17), titleStyle, "是否白名单用户");
//
//        int rowIndex = 1;
//
//        for (MeetingSignup ms : list) {
//            TjyUser tjyUser = getTjyUserById(ms.getUserId());
//            Map<String, Object> wxUser = getWxUserById(ms.getUserId());
//            tjyUser = tjyUser == null ? new TjyUser() : tjyUser;
//            wxUser = wxUser == null ? new HashMap<String, Object>() : wxUser;
//            ms.setMobile(StringUtils.isBlank(ms.getMobile()) ? tjyUser.getMobile() : ms.getMobile());
//
//            HSSFRow row = sheet0.createRow(rowIndex++);
//            POIUtil.setCellValue(row.createCell(0), cellStyle, formatValue(ms.getTitles(), ""));//会议主题
//            POIUtil.setCellValue(row.createCell(1), cellStyle, formatValue(ms.getNickname(), ""));//用户姓名
//            POIUtil.setCellValue(row.createCell(2), cellStyle, wxUser.get("sex") == null ? "" : ("2".equals(wxUser.get("sex").toString()) ? "女" : "男"));//性别
//            POIUtil.setCellValue(row.createCell(3), cellStyle, formatValue(wxUser.get("birthday"), ""));//出生日期
//            POIUtil.setCellValue(row.createCell(4), cellStyle, formatValue(ms.getMobile(), ""));//手机号
//            POIUtil.setCellValue(row.createCell(5), cellStyle, formatValue(ms.getComName(), ""));//企业名称
//            POIUtil.setCellValue(row.createCell(6), cellStyle, formatValue(tjyUser.getJob(), ""));//职位
//            POIUtil.setCellValue(row.createCell(7), cellStyle, formatValue(tjyUser.getIndustry(), ""));//所属行业
//            POIUtil.setCellValue(row.createCell(8), cellStyle, formatValue(tjyUser.getProvince(), " ") + formatValue(tjyUser.getCity(), " ") + formatValue(tjyUser.getRegion(), ""));//所在地区
////			POIUtil.setCellValue(row.createCell(9), cellStyle, formatValue(ms.getMainBusiness(),""));//主营业务
////			POIUtil.setCellValue(row.createCell(10), cellStyle, formatValue(ms.getRegCapital(),""));//注册资金
////			POIUtil.setCellValue(row.createCell(11), cellStyle, formatValue(ms.getPayCapital(),""));//实缴资本
////			POIUtil.setCellValue(row.createCell(12), cellStyle, formatValue(ms.getTotalAssets(),""));//总资产
////			POIUtil.setCellValue(row.createCell(13), cellStyle, formatValue(ms.getAnnualSales(),""));//年销售额
//            POIUtil.setCellValue(row.createCell(9), cellStyle, ms.getTicketPrice() == null || ms.getTicketPrice().doubleValue() <= 0 ? "免费" : StringUtil.fixed(ms.getTicketPrice()));
//            POIUtil.setCellValue(row.createCell(10), cellStyle, ms.getAttendType() != null || ms.getAttendType().intValue() == 1 ? "线上参会" : "线下参会");
//            POIUtil.setCellValue(row.createCell(11), cellStyle, formatValue(ms.getOtherReq(), ""));//其它需求
//            POIUtil.setCellValue(row.createCell(12), cellStyle, formatValue(ms.getKfTelephone(), ""));//服务秘书电话
//            POIUtil.setCellValue(row.createCell(13), cellStyle, formatValue(ms.getTjLinkMan(), ""));//天九联系人
//            POIUtil.setCellValue(row.createCell(14), cellStyle, formatValue(ms.getRecLinkMan(), ""));//推荐人
//            POIUtil.setCellValue(row.createCell(15), cellStyle, DateUtil.date2String(ms.getSignupTime(), "yyyy-MM-dd HH:mm:ss"));//报名时间
//            POIUtil.setCellValue(row.createCell(16), cellStyle, (ms.getOrderStatus() != null && ms.getOrderStatus().intValue() > 1) ? "已支付" : "未支付");//支付状态
//            POIUtil.setCellValue(row.createCell(17), cellStyle, (ms.getPayType() != null && ms.getPayType().intValue() == 2) ? "是" : "否");//是否白名单用户
//        }
//        //自动列宽
//        POIUtil.autoSizeColumn(row0);
//        POIUtil.exportForExcle(response, wb, "会议报名用户列表");
//    }
//
//    /**
//     * 系统白名单新增
//     */
//    @RequestMapping("addwhitelistpage")
//    public String addWhitelistPage(ModelMap map, String meetingId) {
//        map.addAttribute("meetingId", meetingId);
//        return "meetingApp/meetingaddwhitelist";
//    }
//
//    @RequestMapping("addwhitelist")
//    public ModelAndView classadd(String meetingId, String ids) {
//        if (StringUtils.isEmpty(meetingId)) {
//            return ajaxDoneError(MsgConfig.MSG_KEY_FAIL);
//        }
//        if (StringUtils.isEmpty(ids)) {
//            return ajaxDone(MsgConfig.MSG_KEY_SUCCESS);
//        }
//        try {
//            return ajaxDone(meetingAppService.insertWhitelists(meetingId, ids));
//        } catch (Exception e) {
//            return ajaxDoneError(MsgConfig.MSG_KEY_FAIL);
//        }
//
//    }
//
//    /**
//     * 系统白名单删除
//     */
//    @RequestMapping("/whitelistpage/del")
//    public ModelAndView del(String ids) {
//        if (StringUtils.isEmpty(ids)) {
//            return ajaxDone(MsgConfig.MSG_KEY_SUCCESS);
//        }
//        try {
//            return ajaxDone(meetingAppService.deleteGlobalWhitelists(ids) + "");
//        } catch (Exception e) {
//            return ajaxDoneError(MsgConfig.MSG_KEY_FAIL);
//        }
//    }
//
//    private String formatValue(Object obj, String defaultValue) {
//        if (obj == null) {
//            return defaultValue;
//        }
//        if (obj instanceof Date) {
//            return DateUtils.dateToString((Date) obj, "yyyy-MM-dd");
//        } else if (obj instanceof String) {
//            return obj.toString();
//        } else if (obj instanceof Double) {
//            DecimalFormat a = new DecimalFormat("#.##");
//            a.applyPattern("0.00");
//            return a.format((Double) obj);
//        } else if (obj instanceof Integer) {
//            return obj.toString();
//        } else {
//            return obj.toString();
//        }
//    }
//
//    /**
//     * 预报名（报名提醒）用户页面
//     *
//     * @return
//     */
//    @RequestMapping("/signupremind/index")
//    public String loadSignupRemind(ModelMap modelMap, String meetingId) {
//        modelMap.addAttribute("meetingId", meetingId);
//        return "meetingApp/signupremindindex";
//    }
//
//    /**
//     * 报名用户列表查询
//     *
//     * @param param
//     * @param t
//     * @return
//     */
//    @RequestMapping("/signupremind/query")
//    public ModelAndView querySignupRemind(PageParam param, MeetingSignupRemind t) {
//        return ajaxJsonEscape(this.meetingAppSignupService.selectAllMeetingSignupRemind(param, t));
//    }
//
//    /**
//     * 报名用户支付状态修改
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("/signup/changestatus")
//    public ModelAndView updateForChangestatus(String id) {
//        Map<String, String> result = Maps.newHashMap();
//        int s = this.meetingAppSignupService.updateForChangestatus(id);
//        if (s == 0) {
//            result.put("code", "1");
//        } else {
//            result.put("code", "0");
//        }
//        return ajaxJsonEscape(result);
//    }
//}
