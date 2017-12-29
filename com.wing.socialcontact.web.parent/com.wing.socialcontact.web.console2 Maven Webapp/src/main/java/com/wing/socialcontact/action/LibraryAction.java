package com.wing.socialcontact.action;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.service.wx.api.*;
import com.wing.socialcontact.service.wx.bean.*;
import com.wing.socialcontact.util.*;
import com.wing.socialcontact.vhall.api.BaseAPI;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.sys.service.IListValuesService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangzheng
 *         文库控制器
 */
@Controller
@RequestMapping("/library")
public class LibraryAction extends BaseAction {


    @Autowired
    private ILibraryService libraryService;
    @Autowired
    private ILibraryClassService libraryClassService;
    @Autowired
    private IListValuesService listValuesservice;
    @Autowired
    private IAccessoryService accessoryService;
    @Autowired
    private IWxUserService wxUserService;

    @Autowired
    private ITjyUserService tjyUserService;
    @Autowired
    private IMyCollectionService myCollectionService;
    @Autowired
    private IWalletLogService walletLogService;
    @Autowired
    private ILibraryOpLogService libraryOpLogService;

    @Autowired
    private IMeetingService meetingService;

    /**
     * 文库列表
     */
    @RequiresPermissions("library:read")
    @RequestMapping("load")
    public String load(ModelMap map) {
        /**
         * 查询所有一级分类
         */
        TjyLibraryClass parm = new TjyLibraryClass();
        parm.setLevel(1);
        List classes = libraryClassService.selectLevelOne(parm);
        map.addAttribute("classes", classes);
        /**
         * 查询所有二级分类
         */
        parm.setLevel(2);
        List tag = libraryClassService.selectLevelOne(parm);
        map.addAttribute("tag", tag);
        return "system/library/load";
    }

    @RequiresPermissions("library:read")
    @RequestMapping("query")
    public ModelAndView query(PageParam param, TjyLibrary libraryc, Date stime, Date etime) {
        return ajaxJsonEscape(libraryService.selectLibrary(param, libraryc, stime, etime));
    }

    /**
     * 文库新增
     */
    @RequiresPermissions("library:add")
    @RequestMapping("addPage")
    public String addPage(ModelMap map) {
        //获取所有分类
        List<TjyLibraryClass> cla = libraryClassService.getAllClass();
        map.addAttribute("cla", cla);
        List tags = listValuesservice.selectListByType(9000);
        map.addAttribute("tags", tags);
        //打赏默认账户
        WxUser wxUser = wxUserService.selectByPrimaryKey("1");
        map.addAttribute("wxUser", wxUser);
        return "system/library/add";
    }

    @RequiresPermissions("library:add")
    @RequestMapping("add")
    public ModelAndView add(Date puTime, TjyLibrary dto, Errors errors, @RequestParam(value = "file1") MultipartFile file1, @RequestParam(value = "file2") MultipartFile file2) {
        if (errors.hasErrors()) {
            ModelAndView mav = getValidationMessage(errors);
            if (mav != null) return mav;
        }
        Date now = new Date();
        dto.setUpdateTime(now);//文章添加时间
        if (StringUtil.isEmpty(puTime)) {
            dto.setCreateTime(now);//发布
        } else {
            dto.setCreateTime(puTime);
        }
        dto.setCreateUserId(ServletUtil.getMember().getId());
//		dto.setReadtimes(0);
        //TjyLibraryClass scla= libraryClassService.
        TjyLibraryClass liclass = libraryClassService.getTjyLibraryClassByid(dto.getClassId());
        if (liclass == null || liclass.getName() == null) {
            return ajaxDone(MsgConfig.MSG_KEY_FAIL);
        }
        if (2 != liclass.getLevel()) {
            return ajaxDone(MsgConfig.MSG_KEY_FAIL);
        }
        dto.setOneclass(liclass.getParentId());
        if (null != file1 && !file1.isEmpty() && file1.getSize() > 0) {
            if (file1.getSize() > 2000000) {
                return ajaxDone(MsgConfig.MSG_KEY_FAIL);
            }
            //上传附件，获取路径
            try {
                String libid = UUID.randomUUID().toString().replace("-", "");
                String acid = UUID.randomUUID().toString().replace("-", "");
                InputStream io = file1.getInputStream();
                String ext = "." + FilenameUtils.getExtension(file1.getOriginalFilename());
                String path = FileUploadUtil.uploadFileInputStream(io, ext, "library");
                String filename = file1.getOriginalFilename();
                Accessory accessory = new Accessory();
                accessory.setCreateTime(new Date());
                accessory.setDeleted(0);
                accessory.setCreateUserId(ServletUtil.getMember().getId());
                accessory.setUserId(ServletUtil.getMember().getId());
                accessory.setCreateUserName(ServletUtil.getMember().getUserName());
                accessory.setExt(ext);
                accessory.setOriginalName(filename);
                accessory.setNewName(filename);
                accessory.setPath(path);
                accessory.setInfo("文章附件");
                accessory.setSize((int) file1.getSize());
                accessory.setStatus(1);
                accessory.setSuperId(libid);
                accessory.setId(acid);
                accessoryService.addAccessory(accessory);
                dto.setId(libid);
                dto.setFileId(acid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != file2 && !file2.isEmpty() && file2.getSize() > 0) {
            try {
                //上传音频
                InputStream io2 = file2.getInputStream();
                String ext2 = "." + FilenameUtils.getExtension(file2.getOriginalFilename());
                String adpath = FileUploadUtil.uploadFileInputStream(io2, ext2, "library", 1);
                dto.setAudioFile(adpath);

                //音频时长计算
                OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
                String ossUrl = ossConfig.getOss_getUrl();
                URL urlFile = new URL(ossUrl + adpath);
                URLConnection con = null;
                con = urlFile.openConnection();
                int b = con.getContentLength();//
                BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
                Bitstream bt = new Bitstream(bis);
                Header h = bt.readFrame();
                int time = (int) h.total_ms(b);
                dto.setAudioTime(String.valueOf(Math.round(time / 1000)));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (BitstreamException e) {
                e.printStackTrace();
            }
        }
        return ajaxDone(libraryService.addTjyLibrary(dto));

    }

    /**
     * 文库修改
     */
    @RequiresPermissions("library:update")
    @RequestMapping("updatePage")
    public String updatePage(String id, ModelMap map) {
        OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
        String ossurl = ossConfig.getOss_getUrl();
        map.addAttribute("ossurl", ossurl);
        TjyLibrary dto = libraryService.getTjyLibraryByid(id);
        map.addAttribute("dto", dto);
        List tags = listValuesservice.selectListByType(9000);
        map.addAttribute("tags", tags);
        if (null != dto.getFileId() && !"".equals(dto.getFileId())) {
            Accessory parm = new Accessory();
            parm.setSuperId(dto.getId());
            List<Accessory> list = accessoryService.selectByTerm(parm);
            if (list.size() > 0) {
                map.addAttribute("accessory", list.get(0));
            }
        }
        //获取附件
        //判断是否已经发布
        if (dto.getCreateTime().compareTo(new Date()) > 0) {
            map.addAttribute("published", 0);
        } else {
            map.addAttribute("published", 1);
        }
        //打赏默认账户
        WxUser wxUser = null;
        if (dto.getRewardUser() != null && !dto.getRewardUser().equals("")) {
            wxUser = wxUserService.selectByPrimaryKey(dto.getRewardUser());
        } else {
            wxUser = wxUserService.selectByPrimaryKey("1");
        }
        map.addAttribute("wxUser", wxUser);

        //可视范围
        map.addAttribute("contentYunYou", (dto.getContentVisibleRange() & 1) > 0);
        map.addAttribute("contentYunQin", (dto.getContentVisibleRange() & 2) > 0);
        map.addAttribute("contentYunShang", (dto.getContentVisibleRange() & 4) > 0);
        map.addAttribute("videoYunYou", (dto.getVideoVisibleRange() & 1) > 0);
        map.addAttribute("videoYunQin", (dto.getVideoVisibleRange() & 2) > 0);
        map.addAttribute("videoYunShang", (dto.getVideoVisibleRange() & 4) > 0);

        return "system/library/update";
    }

    @RequiresPermissions("library:update")
    @RequestMapping("update")
    public ModelAndView update(TjyLibrary dto, Errors errors, @RequestParam(value = "file1") MultipartFile file1, @RequestParam(value = "file2") MultipartFile file2) {
        if (errors.hasErrors()) {
            ModelAndView mav = getValidationMessage(errors);
            if (mav != null) return mav;
        }
        //dto.setUpdateTime(new Date());
        dto.setUpdateUserId(ServletUtil.getMember().getId());
        TjyLibraryClass liclass = libraryClassService.getTjyLibraryClassByid(dto.getClassId());
        if (liclass == null || liclass.getName() == null) {
            return ajaxDone(MsgConfig.MSG_KEY_FAIL);
        }
        if (2 != liclass.getLevel()) {
            return ajaxDone(MsgConfig.MSG_KEY_FAIL);
        }
        dto.setOneclass(liclass.getParentId());
        if (null != file1 && !file1.isEmpty() && file1.getSize() > 0) {
            if (file1.getSize() > 5120000) {
                return ajaxDone(MsgConfig.MSG_KEY_FAIL);
            }
            //上传附件，获取路径
            try {

                Accessory parm = new Accessory();
                parm.setSuperId(dto.getFileId());
                List<Accessory> list = accessoryService.selectByTerm(parm);
                if (list.size() > 0) {
                    //删除之前附件
                    accessoryService.deleteAccessoryByTerm(parm);
                }
                String acid = UUID.randomUUID().toString().replace("-", "");
                InputStream io = file1.getInputStream();
                String ext = "." + FilenameUtils.getExtension(file1.getOriginalFilename());
                String path = FileUploadUtil.uploadFileInputStream(io, ext, "library");
                String filename = file1.getOriginalFilename();
                Accessory accessory = new Accessory();
                accessory.setCreateTime(new Date());
                accessory.setDeleted(0);
                accessory.setCreateUserId(ServletUtil.getMember().getId());
                accessory.setUserId(ServletUtil.getMember().getId());
                accessory.setCreateUserName(ServletUtil.getMember().getUserName());
                accessory.setExt(ext);
                accessory.setOriginalName(filename);
                accessory.setNewName(filename);
                accessory.setPath(path);
                accessory.setInfo("文章附件");
                accessory.setSize((int) file1.getSize());
                accessory.setStatus(1);
                accessory.setSuperId(dto.getId());
                accessory.setId(acid);
                accessoryService.addAccessory(accessory);

                dto.setFileId(acid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != file2 && !file2.isEmpty() && file2.getSize() > 0) {
            try {
                //上传音频
                InputStream io2 = file2.getInputStream();
                String ext2 = "." + FilenameUtils.getExtension(file2.getOriginalFilename());
                String adpath = FileUploadUtil.uploadFileInputStream(io2, ext2, "library", 1);
                dto.setAudioFile(adpath);

                //音频时长计算
                OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
                String ossUrl = ossConfig.getOss_getUrl();
                URL urlFile = new URL(ossUrl + adpath);
                URLConnection con = null;
                con = urlFile.openConnection();
                int b = con.getContentLength();//
                BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
                Bitstream bt = new Bitstream(bis);
                Header h = bt.readFrame();
                int time = (int) h.total_ms(b);
                dto.setAudioTime(String.valueOf(Math.round(time / 1000)));

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (BitstreamException e) {
                e.printStackTrace();
            }
        }
        return ajaxDone(libraryService.updateTjyLibrary(dto));

    }

    /**
     * 查看文章详情
     */
    @RequestMapping(value = "/detail")
    public Map<String, Object> libraryDetail(HttpServletRequest request, HttpServletResponse response, String libraryid)
            throws IOException {

        Map res = new HashMap();
        res = libraryService.getLibraryByid(libraryid);

        Map<String, Object> signMap = null;
        String roomId = "";
        if (res != null) {
            roomId = res.get("webinarId") == null ? "" : res.get("webinarId").toString();
            if (roomId != null && !"".equals(roomId)) {
                signMap = BaseAPI.createVedioSign(ServletUtil.getMember().getId(), ServletUtil.getMember().getUserName(), roomId);
            }
        }
        int rewardCount = 0;//打赏总数
        int tpCount = 0;//点赞总数

        rewardCount = walletLogService.selectRewardSum("17", libraryid);
        tpCount = libraryOpLogService.getCountByFkIdAndType(libraryid, "1", "1");

        res.put("signObj", signMap);
        res.put("rewardCount", rewardCount);
        res.put("tpCount", tpCount);

        return getAjaxResult("0", "", res);

    }

    /**
     * 文库详情
     */
    @RequiresPermissions("library:read")
    @RequestMapping("viewPage")
    public String viewPage(String id, ModelMap map) {
        OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
        String ossurl = ossConfig.getOss_getUrl();
        map.addAttribute("ossurl", ossurl);
        TjyLibrary dto = libraryService.getTjyLibraryByid(id);
        map.addAttribute("dto", dto);
        List tags = listValuesservice.selectListByType(9000);
        map.addAttribute("tags", tags);
        if (null != dto.getFileId() && !"".equals(dto.getFileId())) {
            Accessory parm = new Accessory();
            parm.setSuperId(dto.getId());
            List<Accessory> list = accessoryService.selectByTerm(parm);
            if (list.size() > 0) {
                map.addAttribute("accessory", list.get(0));
            }
        }

        //可视范围
        map.addAttribute("contentYunYou", (dto.getContentVisibleRange() & 1) > 0);
        map.addAttribute("contentYunQin", (dto.getContentVisibleRange() & 2) > 0);
        map.addAttribute("contentYunShang", (dto.getContentVisibleRange() & 4) > 0);
        map.addAttribute("videoYunYou", (dto.getVideoVisibleRange() & 1) > 0);
        map.addAttribute("videoYunQin", (dto.getVideoVisibleRange() & 2) > 0);
        map.addAttribute("videoYunShang", (dto.getVideoVisibleRange() & 4) > 0);

        return "system/library/view";
    }

    /**
     * 文库预览
     */
    @RequiresPermissions("library:read")
    @RequestMapping("viewyulan")
    public String viewyulan(String id, ModelMap map) {
        OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
        String ossurl = ossConfig.getOss_getUrl();
        map.addAttribute("ossurl", ossurl);
        Map dto = new HashMap();
        dto = libraryService.getLibraryByid(id);
        map.addAttribute("dto", dto);

        return "system/library/yulan";
    }

    /**
     * 文库删除
     */
    @RequiresPermissions("library:delete")
    @RequestMapping("del")
    public ModelAndView del(String[] ids) {

        return ajaxDone(libraryService.deleteTjyLibrary(ids));
    }

    /**
     * 附件删除
     */
    @RequestMapping("delaccessory")
    public ModelAndView delaccessory(String id) {
        Accessory parm = accessoryService.selectById(id);
        accessoryService.deleteAccessoryByTerm(parm);
        return ajaxDone(true);
    }

    //附件上传方法
}
