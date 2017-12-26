/**  
 * @Project: tjy
 * @Title: LoginServiceImpl.java
 * @Package com.wing.socialcontact.sys.service.impl
 * @date 2016-4-1 下午3:23:32
 * @Copyright: 2016 
 */
package com.tojoy.service.console.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.tojoy.service.console.dao.SyLoginLogDao;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.tojoy.common.model.IpInfo;
import com.tojoy.config.BaseConfig;
import com.tojoy.config.MsgConfig;
import com.tojoy.service.console.bean.SyLoginLog;
import com.tojoy.service.console.bean.SyUsers;
import com.tojoy.service.console.dao.UserDao;
import com.tojoy.service.console.service.ILoginService;
import com.tojoy.util.DateUtil;
import com.tojoy.util.IpUtil;
import com.tojoy.util.MD5Util;
import com.tojoy.util.ServletUtil;

/**
 * 
 * 类名：LoginServiceImpl
 * 功能：
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-4-1 下午3:23:32
 *
 */

@Service
public class LoginServiceImpl implements ILoginService{

	@Resource
	private UserDao userDAO;
	@Resource
	private SyLoginLogDao syLoginLogDao;
	
	public Map updateLogin(String vercode,String name,String password,String loginIp,String sessionId){
		Map resultMap = new HashMap();
		
		ModelAndView mav = new ModelAndView("ajaxMessage");
		//HttpSession session=request.getSession();
		//获取登录ip 
		//String loginIp=IpUtil.getIpAddr(request);
		
		//0.验证系统是否过期 验证系统注册信息  判断注册码是否有效
	
		/*boolean iskey = SerialNumberUtil.verificationkey(BaseConfig.serialconfig.getClientname(),
				  BaseConfig.serialconfig.getClientcode(), BaseConfig.serialconfig.getValidstart(),
				  BaseConfig.serialconfig.getValidend(), BaseConfig.serialconfig.getValidday(),
				  BaseConfig.serialconfig.getKey());
		if(!true){
			//系统不可用,已过期
			mav.setViewName("ajaxDone");
			mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);
			mav.addObject(MsgConfig.MESSAGE,"抱歉，您的系统已过期，无法进行操作！请联系管理员！");
			return mav;
		}*/
		
		//1.验证系统防火墙，例：ip,时间 等访问限制，先排除开发人员，超级管理员
		/*if(!name.equals(BaseConfig.getInstance().getDevName())||!name.equals(BaseConfig.getInstance().getSaName())){
			WebConfig wc=BaseConfig.webconfig;
			//先判断系统是否禁止登录
			if(wc.getAppStart()!=1){
				//禁止登录系统
				mav.setViewName("ajaxDone");
				mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);
				mav.addObject(MsgConfig.MESSAGE,"系统已设置禁止访问！请联系管理员！");
				return mav;
				
			}
			//判断是否在可以登录的时间范围内
			if(!AppUtil.checkLoginTime(new Date(), wc.getLoginStartTime(), wc.getLoginEndTime())){
				//不符合使用范围
				mav.setViewName("ajaxDone");
				mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);
				mav.addObject(MsgConfig.MESSAGE,"系统只能在"+ wc.getLoginStartTime()+" 至 "+wc.getLoginEndTime()+"之间才能访问！");
				return mav;
			}
			//进行ip验证
			if(StringUtils.isNotBlank(wc.getAllowIps())&&!AppUtil.checkIp(wc.getAllowIps(), loginIp)){
				
				mav.setViewName("ajaxDone");
				mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);
				mav.addObject(MsgConfig.MESSAGE,"系统已设置ip限制！");
				return mav;
			}else{
				if(StringUtils.isNotBlank(wc.getLimitIps())&&AppUtil.checkIp(wc.getLimitIps(), loginIp)){
					mav.setViewName("ajaxDone");
					mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);
					mav.addObject(MsgConfig.MESSAGE,"系统已设置ip限制！");
					return mav;
				}	
			}
		}*/
		/*
		//2.验证验证码是否正确
		if(!((String)session.getAttribute("imgCode")).equalsIgnoreCase(vercode)){
			mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);
			mav.addObject(MsgConfig.MESSAGE,"msg.validation.code.match");//验证码错误
			return mav;
		}*/
		SyUsers parm = new SyUsers();
		parm.setUserName(name);
		List<SyUsers> lst = userDAO.select(parm);
		if(lst.size()==0){
			System.out.println("不存在此用户");
			mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);
			mav.addObject(MsgConfig.MESSAGE,"msg.login.failure");//用户名或密码错误， 请重新登录
			resultMap.put("mav", mav);
			return resultMap;
		}
		
		SyUsers u=lst.get(0);
		//3. 验证用户是否被限制登录
		if(u.getUserStatus()==(short)0){	
			mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);
			mav.addObject(MsgConfig.MESSAGE,"msg.login.restrict");//用户被限制登录，请联系管理员
			resultMap.put("mav", mav);
			return resultMap;
		}
		
		 IpInfo ipInfo=IpUtil.getIpInfo(loginIp);
		/*IpInfo ipInfo=new IpInfo();//模拟IP信息
		ipInfo.setIp(loginIp);
		ipInfo.setCountry("中国");
		ipInfo.setRegion("山东省");
		ipInfo.setCity("济南市");
		ipInfo.setIsp("联通");*/
		
		Timestamp loginTime=DateUtil.currentTimestamp();
		int num=BaseConfig.webconfig.getPwdErrorNum();
		int time=BaseConfig.webconfig.getPwdErrorTime();
		//4. 验证用户是否因密码多次输入错误，处于限制登录状态。 判断用户密码输入错误的次数是否达到指定次数
		if(u.getErrorCount()>=num){
			//当用户密码输入错误次数大于系统配置的错误次数，获取错误时间 进行判断
			long gotime=loginTime.getTime()-u.getErrorTime().getTime();
			if(gotime<time*60000){
				//如果冷却时间未到
				
				mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);
				mav.addObject(MsgConfig.MESSAGE,"msg.login.restricttime");//您已{0}次密码输入错误，请{1}之后再试！
				mav.addObject(MsgConfig.MESSAGEVALUES, num+","+time+"分钟");//占位符赋值
				resultMap.put("mav", mav);
				return resultMap;
			}else{
				//如果冷却时间已到 将用户输入错误的次数重置
				u.setErrorCount((short)0);
				
			}
			
		}
		//5. 登录认证  验证密码
		if(MD5Util.MD5Validate(password, u.getUserPassword())){
			resultMap.put("loginSuccess", "1");
			resultMap.put("ipInfo", ipInfo);
			resultMap.put("loginUser", u);

			mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_SUCCESS);
			mav.addObject(MsgConfig.MESSAGE,"msg.login.success");//登录成功
			resultMap.put("mav", mav);
			return resultMap;
		}else{
			//认证失败
			System.out.println("密码错误");
			//登录日志
			SyLoginLog log=new SyLoginLog();
			log.setUserId(u.getId());
			log.setLoginType((short)1);
			log.setLoginDesc("密码错误");
			log.setIpInfoCountry(ipInfo.getCountry());
			log.setIpInfoCity(ipInfo.getCity());
			log.setIpInfoIsp(ipInfo.getIsp());
			log.setIpInfoRegion(ipInfo.getRegion());
			log.setLoginIp(loginIp);
			log.setLoginTime(loginTime);
			
			syLoginLogDao.insert(log);//保存登录日志
			
			u.setErrorTime(loginTime);
			u.setErrorCount((short)(u.getErrorCount()+1));
			
			userDAO.updateByPrimaryKey(u);//更新用户
		
			mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);//用户名或密码错误， 请重新登录
			mav.addObject(MsgConfig.MESSAGE,"msg.login.failure");
			resultMap.put("mav", mav);
			return resultMap;
		}
	
	}
	
	public boolean unlock(HttpSession session,String password){
	    SyUsers user=userDAO.selectByPrimaryKey(ServletUtil.getMember().getId());
		if(MD5Util.MD5Validate(password, user.getUserPassword())){
			session.removeAttribute("unlockPwd");
			session.removeAttribute("lock");//解除锁定
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void save(SyLoginLog log) {
		// TODO Auto-generated method stub
		syLoginLogDao.insert(log);
	}
	
}
