package com.wing.enterprise.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.wing.enterprise.bean.EntryDescConfig;
import org.com.wing.enterprise.bean.ShareConfig;
import org.com.wing.enterprise.service.IEntryDescConfigService;
import org.com.wing.enterprise.service.IShareConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.ILeaveMsgService;
import com.wing.socialcontact.service.wx.api.ITjyUserService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.LeaveMsg;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.bean.SyDistrict;
import com.wing.socialcontact.sys.service.IDistrictService;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.QfyConstants;
import com.wing.socialcontact.util.SpringContextUtil;
/**
 * 
 * <p>Title:我的管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月10日 下午7:48:12
 */
@Controller
@RequestMapping("/m/qfy/")
public class MineAction extends BaseAction {
	
	
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private ITjyUserService tjyUserService;
	@Autowired
	private IDistrictService districtService; 
	@Autowired
	private IEntryDescConfigService entryDescConfigService; 
	@Autowired
	private IShareConfigService shareConfigservice;
	@Autowired
	private ILeaveMsgService leaveMsgService;
	@Autowired
    private OssConfig ossConfig;
	
	
	/**
	 * 跳转到我的页面
	 */
	
	@RequestMapping("mineIndex")
	public String personCentre(HttpServletRequest request, ModelMap modelMap) {
	    String userId = checkLogin(request);
	    if(userId != null){
        
    		WxUser user = wxUserService.selectByPrimaryKey(userId);
    		modelMap.addAttribute("user", user);
    		TjyUser tjyuser = tjyUserService.selectById(userId);
    		if (StringUtils.isEmpty(tjyuser.getVisitQuantity())) {
    			tjyuser.setVisitQuantity(0.0);
    		}
    
    		//OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
    		String ossurl = ossConfig.getOss_getUrl();
    		modelMap.addAttribute("ossurl", ossurl);
    
    		modelMap.addAttribute("user", user);
    		modelMap.addAttribute("tjyuser", tjyuser);
    		//添加市县
    		String prov = "";
    		String city = "";
    		String county = "";
    		if(!StringUtils.isEmpty(tjyuser.getProvince())){
    		    prov = districtService.selectByPrimaryKey(tjyuser.getProvince()).getDisName();
    		}
    		if(!StringUtils.isEmpty(tjyuser.getCity())){
    		    city = districtService.selectByPrimaryKey(tjyuser.getCity()).getDisName();
    		}
    		/*if(!StringUtils.isEmpty(tjyuser.getCounty())){
    		    county = districtService.selectByPrimaryKey(tjyuser.getCounty()).getDisName();
    		}*/
    		modelMap.addAttribute("place", prov+city+county);
	    }
		return "mine";
	}
	
	/**
	 * 关于企服联盟页面
	 */
	@RequestMapping("aboutqfy")
	public String aboutqfy(ModelMap remap){
		//type  4
		
		EntryDescConfig edc = new EntryDescConfig();
        edc.setType(QfyConstants.QFY_ABOUT_TYPE);
        List res = entryDescConfigService.selectDescConfig(edc);
        if(null != res){
            Map map = (Map) res.get(0);
            String imgPath = (String) map.get("imgPath");
            //OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
            String ossurl = ossConfig.getOss_getUrl();
            map.put("imgPath",ossurl+imgPath);
            String link = (String) map.get("link");
            map.put("link",link);
            remap.addAttribute("dto", map);
        }
		return "aboutqfy";
	}
	/**
	 * 常见问题页面
	 */
	@RequestMapping("trouble")
	public @ResponseBody Map trouble(){
		//type  3
		EntryDescConfig edc = new EntryDescConfig();
        edc.setType(QfyConstants.QFY_COM_PROBLEM_TYPE);
        List res = entryDescConfigService.selectDescConfig(edc);
        if(null != res){
            Map map = (Map) res.get(0);
            String imgPath = (String) map.get("imgPath");
            //OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
            String ossurl = ossConfig.getOss_getUrl();
            map.put("imgPath",ossurl+imgPath);
            String link = (String) map.get("link");
            map.put("link",link);
        }
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, res);
	}
	
	/**
	 * 留言
	 */
	
	@RequestMapping("leavemsg")
	public String leavemsg(){
		return "leaveMsg";
	}
	@RequestMapping("messagesave")
	public @ResponseBody Map messagesave(HttpServletRequest request,HttpServletResponse response,LeaveMsg dto){
        
	    String userId = checkLogin(request);
	    
        if(userId == null){
            return super.getAjaxResult("302", "登录超时，请重新登录", null); 
        }
        
		dto.setSource("2");
		dto.setType("3");
		dto.setCreateTime(new Date());
		dto.setUserId(userId);
		dto.setDeleted(0);
		boolean bo = leaveMsgService.addLeaveMsg(dto);
		
        if (bo) {
            return super.getSuccessAjaxResult();
        } else {
            return super.getAjaxResult("999", "留言失败", null);
        }
	}
	
	/**
	 * 验证手机号页面
	 */
	@RequestMapping("changenext")
	public String changenext(){
		return "changePhone_next";
	}
	/**
	 * 
	 */
	@RequestMapping("changePhone")
	public String changePhone(HttpServletRequest request,ModelMap map){
	    String userId = checkLogin(request);
        
        if(userId != null){
            
            TjyUser tjyuser = tjyUserService.selectByPrimaryKey(userId);
            
            map.addAttribute("user", tjyuser);
        }
        
	    return "changePhone";
	}

	/**
	 * 个人设置
	 * 
	 * @return
	 */
	@RequestMapping("person_setting")
	public String personSetting(HttpServletRequest request, ModelMap modelMap) {
        
	    String userId = checkLogin(request);
	    
        if(userId != null){
            Map<String, Object> user = wxUserService.queryUsersByid(userId);
            TjyUser tjyUser = tjyUserService.selectByPrimaryKey(userId);
            if (!StringUtils.isEmpty(user.get("province"))) {
                SyDistrict sd = districtService.selectByPrimaryKey((String) user.get("province"));
                user.put("province", sd.getDisName());
            }
            if (!StringUtils.isEmpty(user.get("city"))) {
                SyDistrict sd = districtService.selectByPrimaryKey((String) user.get("city"));
                user.put("city", sd.getDisName());
            }
            if (!StringUtils.isEmpty(user.get("county"))) {
                SyDistrict sd = districtService.selectByPrimaryKey((String) user.get("county"));
                user.put("county", sd.getDisName());
            }
            
            modelMap.addAttribute("user", user);
            modelMap.addAttribute("isRecon", tjyUser.getIsRealname());
            
            // 获取省
            List provinceList = districtService.selectDistrictByType("1");
            // 获取市
            List cityList = districtService.selectDistrictByType("2");
            // 获取区
            List areaList = districtService.selectDistrictByType("3");
            modelMap.addAttribute("provinceList", provinceList);
            modelMap.addAttribute("cityList", cityList);
            modelMap.addAttribute("areaList", areaList);
        }
        

		return "user_setting";
	}
	
	/**
	 * 分享页跳转
	 */
	
	@RequestMapping("sharepage")
	public String sharepage(ModelMap map){
		ShareConfig dto = shareConfigservice.getById(QfyConstants.QFY_SHARE_CONFIG);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
        String ossurl = ossConfig.getOss_getUrl();
        dto.setImagePath(ossurl+dto.getImagePath());
    	map.addAttribute("dto", dto);
		return "sharepage";
	}
	/**
	 * 分享页内容
	 */
	
	@RequestMapping("share")
	public @ResponseBody Map share(){
	    ShareConfig dto = shareConfigservice.getById(QfyConstants.QFY_SHARE_CONFIG);
        Map map = new HashMap();
        OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
        String ossurl = ossConfig.getOss_getUrl();
        map.put("title", dto.getTitle());
        map.put("content", dto.getContent());
        map.put("link", dto.getLink());
        map.put("imagePath", ossurl+dto.getImagePath());
        
        return super.getSuccessAjaxResult(Constants.AJAX_MSG_SUCCESS, map);
	}
	
	/**
	 * 添加到user
	 * 
	 */
	@RequestMapping(value = "addusers")
	public @ResponseBody Map addusers(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    String userId = checkLogin(request);
        if(userId == null){
                return super.getAjaxResult("302", "登录超时，请重新登录", null); 
        }

		TjyUser tjyuser = tjyUserService.selectByPrimaryKey(userId);
		//WxUser user = wxUserService.selectByPrimaryKey(userId);
		String nickname = request.getParameter("nickname");
		if (!StringUtils.isEmpty(nickname)) {
			//user.setTruename(nickname);
			tjyuser.setNickname(nickname);
			//user.setNickName(nickname);
			//tjyuser.setTrueName(nickname);
		}
		String province = request.getParameter("province");
		if (!StringUtils.isEmpty(province)) {
			tjyuser.setProvince(province);
		}
		String city = request.getParameter("city");
		if (!StringUtils.isEmpty(city)) {
			tjyuser.setCity(city);
		}
		String county = request.getParameter("county");
		if (!StringUtils.isEmpty(county)) {
			tjyuser.setCounty(county);
		}
		if (tjyUserService.updateTjyUser(tjyuser)) {
			try {
				tjyUserService.remotingUpdateTjyUser(tjyuser, tjyuser.getMobile());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return super.getSuccessAjaxResult();
		}
//		if (wxUserService.updateWxUser(user) && tjyUserService.updateTjyUser(tjyuser)) {
//		    return super.getSuccessAjaxResult();
//		}

		return super.getSuccessAjaxResult(Constants.AJAX_MSG_ERROR, null);
	}
	/**
     * 账号安全
     */
    
    @RequestMapping("/mine/security")
    public String security(HttpServletRequest request, ModelMap map){
        
        String userId = checkLogin(request);
        
        if(userId != null){
            
            TjyUser tjyuser = tjyUserService.selectByPrimaryKey(userId);
            
            map.addAttribute("user", tjyuser);
        }
        
        return "security";
    }
}
