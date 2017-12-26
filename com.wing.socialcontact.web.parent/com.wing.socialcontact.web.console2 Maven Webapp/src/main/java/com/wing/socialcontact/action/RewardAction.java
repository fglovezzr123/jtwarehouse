package com.wing.socialcontact.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.OssConfig;
import com.wing.socialcontact.service.wx.api.ICommentService;
import com.wing.socialcontact.service.wx.api.ICommentThumbupService;
import com.wing.socialcontact.service.wx.api.IRewardAnswerService;
import com.wing.socialcontact.service.wx.api.IRewardClassService;
import com.wing.socialcontact.service.wx.api.IRewardService;
import com.wing.socialcontact.service.wx.api.IRewardSetService;
import com.wing.socialcontact.service.wx.api.IWalletLogService;
import com.wing.socialcontact.service.wx.api.IWxUserService;
import com.wing.socialcontact.service.wx.bean.BusinessDisscuss;
import com.wing.socialcontact.service.wx.bean.Comment;
import com.wing.socialcontact.service.wx.bean.CommentThumbup;
import com.wing.socialcontact.service.wx.bean.Reward;
import com.wing.socialcontact.service.wx.bean.RewardAnswer;
import com.wing.socialcontact.service.wx.bean.RewardClass;
import com.wing.socialcontact.service.wx.bean.RewardSet;
import com.wing.socialcontact.service.wx.bean.WalletLog;
import com.wing.socialcontact.service.wx.bean.WxUser;
import com.wing.socialcontact.sys.action.BaseAction;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.DoubleUtil;
import com.wing.socialcontact.util.ServletUtil;
import com.wing.socialcontact.util.SpringContextUtil;

/**
 * 悬赏管理
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/reward")
public class RewardAction extends BaseAction{
	
	@Autowired
	private IRewardClassService rewardClassService; 
	@Autowired
	private IRewardService rewardService; 
	@Autowired
	private IRewardAnswerService rewardAnswerService;
	@Autowired
	private IRewardSetService rewardSetService;
	@Autowired
	private ICommentThumbupService commentThumbupService;
	@Autowired
	private ICommentService commentService;
	@Autowired
	private IWxUserService wxUserService;
	@Autowired
	private IWalletLogService walletLogService;
	/**
	 * 条件查询行业类别
	 * 
	 * @return
	 */
	@RequiresPermissions("rclass:read")
	@RequestMapping("loadClass")
	public String loadClass(ModelMap map){
		return "reward/loadclass";
	
	}
	@RequiresPermissions("rclass:read")
	@RequestMapping("queryClass")
	public ModelAndView queryClass(PageParam param,RewardClass rClass){
		
		return ajaxJsonEscape(rewardClassService.selectClass(param, rClass));
	}
	/**
	 * 跳转到行业类别添加页面
	 * @return
	 */
	@RequiresPermissions("rclass:add")
	@RequestMapping("addPageClass")
	public String addPageClass(ModelMap map){
		return "reward/addClass";
	
	}
	/**
	 * 添加行业类别
	 * @param rClass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("rclass:add")
	@RequestMapping("addClass")
	public ModelAndView addClass(RewardClass rClass,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		rClass.setCreateTime(new Date());
		rClass.setCreateUserId(ServletUtil.getMember().getId());
		rClass.setCreateUserName(ServletUtil.getMember().getUserName());
		String str = rewardClassService.addClass(rClass);
		if(str.equals("msg.rewardClass.unique")){
			return ajaxDoneTextError("该行业名称已存在！");
		}
		return ajaxDone(str);	
	}
	/**
	 * 跳转到行业类别修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("rclass:update")
	@RequestMapping("updatePageClass")
	public String updatePageClass(String id,ModelMap map){
		RewardClass rclass = rewardClassService.selectById(id);
		if(rclass==null){
			return NODATA;
		}
		map.addAttribute("r",rclass);
		OssConfig ossConfig = (OssConfig) SpringContextUtil.getBean("ossConfig");
		String ossurl = ossConfig.getOss_getUrl();
		map.addAttribute("ossurl", ossurl);
		return "reward/updateclass";
	}
	/**
	 * 修改行业类别
	 * @param rClass
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("rclass:update")
	@RequestMapping("updateClass")
	public ModelAndView updateClass(RewardClass rClass,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		String str = rewardClassService.updateClass(rClass);
		if(str.equals("msg.rewardClass.unique")){
			return ajaxDoneTextError("该行业名称已存在！");
		}
		return ajaxDone(str);
	}
	/**
	 * 删除行业类别
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("rclass:delete")
	@RequestMapping("delClass")
	public ModelAndView delClass(String id){
		return ajaxDone(rewardClassService.deleteClass(id));
	}
	/**
	 * 更新推荐状态
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("rclass:update")
	@RequestMapping("updateRecommend")
	public ModelAndView updateRecommend(String id,String isRecommend){
		RewardClass rclass = rewardClassService.selectById(id);
		rclass.setIsRecommend(Integer.valueOf(isRecommend));
		return ajaxDone(rewardClassService.updateClass(rclass));
	}
	
	/**
	 * 条件查询悬赏
	 * 
	 * @return
	 */
	@RequiresPermissions("reward:read")
	@RequestMapping("load")
	public String load(ModelMap map){
		List list = rewardClassService.selectAllClass(null,null);
		map.addAttribute("list", list);
		return "reward/load";
	}
	@RequiresPermissions("reward:read")
	@RequestMapping("query")
	public ModelAndView query(PageParam param,Reward reward,String startTimef,String endTimef,
			String createUserId,String startTimeyx,String endTimeyx,String userId){
		
		return ajaxJsonEscape(rewardService.selectAllReward(param, reward, startTimef, endTimef,
				createUserId,startTimeyx,endTimeyx,userId));
	}
	@RequiresPermissions("reward:read")
	@RequestMapping("query2")
	public ModelAndView query2(PageParam param,Reward reward,String startTimef,String endTimef,
			String createUserId,String startTimeyx,String endTimeyx,String userId){
		
		return ajaxJsonEscape(rewardService.selectAllReward2(param, reward, startTimef, endTimef,
				createUserId,startTimeyx,endTimeyx,userId));
	}
	/**
	 * 跳转到悬赏修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("reward:update")
	@RequestMapping("updatePage")
	public String updatePage(String id,ModelMap map){
		Reward reward = rewardService.selectById(id);
		if(reward==null){
			return NODATA;
		}
		map.addAttribute("r",reward);
		List list = rewardClassService.selectAllClass(null,null);
		map.addAttribute("list", list);
		return "reward/update";
	}
	/**
	 * 修改合作
	 * @param reward
	 * @param errors
	 * @return
	 * @throws ParseException 
	 */
	@RequiresPermissions("reward:update")
	@RequestMapping("update")
	public ModelAndView update(Reward reward,Errors errors) throws ParseException{
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		//判断有效日期是否过期，如果过期，修改状态
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(reward.getEndTime().getTime()<sdf.parse(sdf.format(new Date())).getTime()){
			reward.setStatus(5);
		}
		//如果状态为取消，取消就退J币
		if(reward.getStatus()==3){
			WxUser user = wxUserService.selectById(reward.getCreateUserId());
			Double doub = DoubleUtil.add(user.getJbAmount()==null?0:user.getJbAmount(), Double.valueOf(reward.getReward()));
			user.setJbAmount(doub);
			wxUserService.updateWxUser(user);
			WalletLog log = new WalletLog();
			log.setCreateTime(new Date());
			log.setType("2");
			log.setPdType("1");
			log.setUserId(reward.getCreateUserId());
			log.setAmount(Double.valueOf(reward.getReward()));
			log.setRemark("诸葛解惑悬赏退回");
			log.setPayStatus("1");
			log.setBusinessType(6);
			log.setYeAmount(doub);
			walletLogService.addWalletLog(log);
		}
		return ajaxDone(rewardService.updateReward(reward));
		
	}
	/**
	 * 删除悬赏
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("reward:delete")
	@RequestMapping("del")
	public ModelAndView del(String[] ids){	
		return ajaxDone(rewardService.deleteRewards(ids));
	}
	
	/**
	 * 跳转到悬赏详情页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("reward:update")
	@RequestMapping("showPage")
	public String showPage(String id,ModelMap map){
		Map<String, Object> reward = rewardService.selectRewardById(id);
		if(reward==null){
			return NODATA;
		}
		map.addAttribute("r",reward);
		List<Map<String, Object>> list = rewardAnswerService.selectRAByFkId(id, null);
		//回复数
		int subcount = 0;
		for (Map<String, Object> m : list) {
			// 获取子评论
			Comment subcomment = new Comment();
			subcomment.setParentId((String) (m.get("id")));
			List<Map<String, Object>> subCommentList = commentService
					.queryCommentbyPid((String) (m.get("id")));
			if (null != subCommentList) {
				subcount = subCommentList.size();
			}
			for(Map<String, Object> s : subCommentList){
				// 获取用户
				Map<String, Object> user = wxUserService.queryUsersByid((String) (s
						.get("userId")));
				if (null != user) {
					s.put("imgurl", (String) (user.get("head_portrait")));
					s.put("nickname", (String) (user.get("nickname")));
					if(null!=user.get("jobName")){
						s.put("job", (String) (user.get("jobName")));
					}else{
						s.put("job", "");
					}
					if(null!=user.get("industryName")){
						s.put("industry", (String) (user.get("industryName")));
					}else{
						s.put("industry", "");
					}
				} else {
					s.put("imgurl", "");
					s.put("nickname", "匿名");
					s.put("job", "无工作");
					s.put("industry", "无职位");
				}
			}
			m.put("subcount", subcount);
			m.put("subCommentList", subCommentList);
		}
		map.addAttribute("list",list);
		return "reward/show";
	}
	
	/**
	 * 跳转到悬赏设置修改页面
	 */
	@RequiresPermissions("rewardSet:update")
	@RequestMapping("updatePageSet")
	public String updatePageSet(ModelMap map){
		RewardSet rs = null;
		List list = rewardSetService.selectRewardSet();
		if(list!=null&&list.size()>0){
			rs = (RewardSet)list.get(0);
		}
		map.addAttribute("s",rs);
		return "reward/setupdate";
	}
	/**
	 * 修改悬赏设置
	 */
	@RequiresPermissions("rewardSet:update")
	@RequestMapping("updateRewardSet")
	public ModelAndView updateRewardSet(RewardSet set,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		if(set.getId().equals("")||set.getId()==null){
			set.setCreateTime(new Date());
			set.setId(null);
			set.setCreateUserId(ServletUtil.getMember().getId());
			return ajaxDone(rewardSetService.addRewardSet(set));	
		}else{
			return ajaxDone(rewardSetService.updateRewardSet(set));
		}
	}
	
	/**
	 * 条件查询应答
	 * 
	 * @return
	 */
	@RequiresPermissions("rewardAnswer:read")
	@RequestMapping("loadRA")
	public String loadRA(){
		return "reward/loadRA";
	
	}
	@RequiresPermissions("rewardAnswer:read")
	@RequestMapping("queryRA")
	public ModelAndView queryRA(PageParam param,RewardAnswer ra,String question,String startTimef,String endTimef,String fbUserName,String ydUserName){
		
		return ajaxJsonEscape(rewardAnswerService.selectAllRA(param, ra, question, fbUserName, ydUserName, startTimef, endTimef));
	}
	/**
	 * 跳转到应答修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequiresPermissions("rewardAnswer:update")
	@RequestMapping("updatePageRA")
	public String updatePageRA(String id,ModelMap map){
		Map<String, Object> ra = rewardAnswerService.selectRAById(id);
		if(ra==null){
			return NODATA;
		}
		map.addAttribute("a",ra);
		return "reward/updateRA";
	}
	/**
	 * 修改应答
	 * @param business
	 * @param errors
	 * @return
	 */
	@RequiresPermissions("rewardAnswer:update")
	@RequestMapping("updateRA")
	public ModelAndView updateRA(RewardAnswer ra,Errors errors){
		if(errors.hasErrors()) {  
			ModelAndView mav=getValidationMessage(errors);
			if(mav!=null)return mav;
        }
		RewardAnswer ras = rewardAnswerService.selectById(ra.getId());
		ras.setContent(ra.getContent());
		return ajaxDone(rewardAnswerService.updateRA(ras));
		
	}
	/**
	 * 删除应答
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("rewardAnswer:delete")
	@RequestMapping("delRA")
	public ModelAndView delRA(String[] ids){	
		return ajaxDone(rewardAnswerService.deleteRAs(ids));
	}
	
	/**
	 * 更新显示状态
	 * @return
	 */
	@RequiresPermissions("rewardAnswer:update")
	@RequestMapping("updateIsShow")
	public ModelAndView updateIsShow(String id,String isShow){
		RewardAnswer ra = rewardAnswerService.selectById(id);
		ra.setIsShow(Integer.valueOf(isShow));
		return ajaxDone(rewardAnswerService.updateRA(ra));
	}
}
