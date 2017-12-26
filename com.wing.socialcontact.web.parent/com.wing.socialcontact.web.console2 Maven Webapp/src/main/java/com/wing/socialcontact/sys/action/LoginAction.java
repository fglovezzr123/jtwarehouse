/**  
 * @Project: jxoa
 * @Title: LoginAction.java
 * @Package com.oa.manager.system.action
 * @date 2013-4-1 下午3:16:19
 * @Copyright: 2013 
 */
package com.wing.socialcontact.sys.action;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wing.socialcontact.common.model.LoginInfo;
import com.wing.socialcontact.common.model.Member;
import com.wing.socialcontact.common.model.OnLineUser;
import com.wing.socialcontact.common.model.RSAPublicKeyModel;
import com.wing.socialcontact.config.BaseConfig;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.exception.MyRuntimeException;
import com.wing.socialcontact.sys.bean.SyLoginLog;
import com.wing.socialcontact.sys.bean.SyUsers;
import com.wing.socialcontact.sys.service.ILoginService;
import com.wing.socialcontact.sys.service.IUserService;
import com.wing.socialcontact.util.DateUtil;
import com.wing.socialcontact.util.FileUtils;
import com.wing.socialcontact.util.IpUtil;
import com.wing.socialcontact.util.MD5Util;
import com.wing.socialcontact.util.RSAUtils;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 
 * 类名：LoginAction
 * 功能：登录模块
 * 详细：用户登录
 * 作者：LiuJincheng
 * 版本：1.0
 * 日期：2013-4-1 下午3:16:19
 *
 */
@Controller
@RequestMapping("/sy_login")
public class LoginAction extends BaseAction{
	
	@Autowired
	private ILoginService service; 
	@Autowired
	private IUserService userService; 
	
	/**
	 * 跳转到登录页面
	 * @param map
	 * @return
	 */
	@RequestMapping("")
	public String tologin(HttpSession session){
		
		Member me=ServletUtil.getMember();
		//判断当前用户是否已经登录
		if(me!=null){
			Map<String,OnLineUser> usersMap =ServletUtil.getOnLineUsers();
			OnLineUser om=usersMap.get(me.getId());
			if(om!=null){
				Map<String,LoginInfo> loginInfos=om.getLoginInfos();
				if(loginInfos.containsKey(session.getId())){
					//用户已经登录
					return "has_login";
				}
			}
		}
		return "login";
	}
	/**
	 * 跳转到登录页面
	 * @param map
	 * @return
	 */
	@RequestMapping("getEncryption")
	public ModelAndView getEncryption(HttpSession session){
		Map<String,Object> map=new HashMap<String,Object>();
		//生成加密密钥
		String pwd=UUID.randomUUID().toString();
		session.setAttribute("jmpw", pwd);
		RSAPublicKeyModel publicKey = RSAUtils.getPublicKeyModel(pwd); 
		
		map.put(MsgConfig.STATUSCODE, MsgConfig.CODE_SUCCESS);
		map.put("modulus",publicKey.getHexModulus());
		map.put("exponent",publicKey.getHexPublicExponent());
		return ajaxJsonEscape(map);
	}
	/**
	 * 用户登录
	 * @param vercode 验证码
	 * @param name
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("go")
	public ModelAndView login(String vercode,String name,String password,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		String ps=(String)request.getSession().getAttribute("jmpw");
		 
		if(StringUtils.isNotBlank(ps)){
			//解密
			//String pwd=RSAUtils.decryptStringByJs(ps,password);

			return updateLogin(vercode,name,password,request,response);
		}else{
			return ajaxDoneError("msg.login.nojmcode");//加密信息获取失败，请重新
		}
	}
	

	/**
	 * 注销登录
	 * @return
	 */
	@RequestMapping("out")
	public String out(){
		
		System.out.println("********注销登录******");
		Subject currentUser =SecurityUtils.getSubject();
		currentUser.getSession().setAttribute("isOut",true);
		currentUser.logout();
		return "redirect:/sy_login.do";
	}
	/**
	 * 锁定账号 返回解锁界面
	 * @param session
	 * @return
	 */
	@RequestMapping("lock")
	public String lock(HttpSession session){
		
		session.setAttribute("lock", true);//session中标识锁定，只有解锁才能进行其他操作
		
		return "locking";
	}
	/**
	 * 获取解锁 时 密码传输加密密钥
	 * @param session
	 * @return
	 */
	@RequestMapping("unlockEncryptionInfo")
	public ModelAndView unlockEncryptionInfo(HttpSession session){
		Map<String,Object> map=new HashMap<String,Object>();
		//生成加密密钥
		String pwd=UUID.randomUUID().toString();
		session.setAttribute("unlockPwd", pwd);
		RSAPublicKeyModel publicKey = RSAUtils.getPublicKeyModel(pwd); 
		
		map.put(MsgConfig.STATUSCODE, MsgConfig.CODE_SUCCESS);
		map.put("modulus",publicKey.getHexModulus());
		map.put("exponent",publicKey.getHexPublicExponent());
		
		return ajaxJsonEscape(map);
	}
	/**
	 * 解锁
	 * @param session
	 * @return
	 */
	@RequestMapping("unlock")
	public ModelAndView unlock(HttpSession session,String password){
		String ps=(String)session.getAttribute("unlockPwd");
		if(StringUtils.isNotBlank(ps)){
			//解密
			String pwd=RSAUtils.decryptStringByJs(ps,password);
			return ajaxDone(service.unlock(session,pwd));
		}else{
			throw new MyRuntimeException("解锁失败!请刷新页面重试！");
		}
	}
	
	/**
	 * 获取验证码
	 * @param session
	 * @param response
	 */
	@RequestMapping("imgNum")
	public void getImg(HttpSession session,HttpServletResponse response){
		
		ServletOutputStream out=null;
		try{ 
			 DefaultKaptcha captchaProducer=(DefaultKaptcha)ServletUtil.getApplicationContext().getBean("captchaProducer");
			
			 response.setDateHeader("Expires", 0);     
	         response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");    
	         response.addHeader("Cache-Control", "post-check=0, pre-check=0");    
	         response.setHeader("Pragma", "no-cache");    
	         response.setContentType("image/jpeg");    
	         String capText = captchaProducer.createText();    
	         session.setAttribute("imgCode", capText);    
	         BufferedImage bi = captchaProducer.createImage(capText);
	         out = response.getOutputStream();
	         ImageIO.write(bi, "jpg", out);
            
             out.flush(); 
             
         }catch (Exception e) {
			
		 }
		 finally{    
             if(out!=null){
            	 try {
					out.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
             }
        
         }    
		
		
		
		
	}
	
	
public ModelAndView updateLogin(String vercode,String name,String password,HttpServletRequest request,HttpServletResponse response){
		
		ModelAndView mav = new ModelAndView("ajaxMessage");
		HttpSession session=request.getSession();
		//获取登录ip 
		String loginIp=IpUtil.getIpAddr(request);
		/*
		
		//0.验证系统是否过期 验证系统注册信息  判断注册码是否有效
	
		boolean iskey = SerialNumberUtil.verificationkey(BaseConfig.serialconfig.getClientname(),
				  BaseConfig.serialconfig.getClientcode(), BaseConfig.serialconfig.getValidstart(),
				  BaseConfig.serialconfig.getValidend(), BaseConfig.serialconfig.getValidday(),
				  BaseConfig.serialconfig.getKey());
		

		 if(!true){
			//系统不可用,已过期
			mav.setViewName("ajaxDone");
			mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);
			mav.addObject(MsgConfig.MESSAGE,"抱歉，您的系统已过期，无法进行操作！请联系管理员！");
			return mav;
		}
		//1.验证系统防火墙，例：ip,时间 等访问限制，先排除开发人员，超级管理员
		if(!name.equals(BaseConfig.getInstance().getDevName())||!name.equals(BaseConfig.getInstance().getSaName())){
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
		}
		*/
		/*
		//2.验证验证码是否正确
		if(!((String)session.getAttribute("imgCode")).equalsIgnoreCase(vercode)){
			mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);
			mav.addObject(MsgConfig.MESSAGE,"msg.validation.code.match");//验证码错误
			return mav;
		}*/
		
		SyUsers u=userService.findUserByLoginName(name);
		if(u==null){
			System.out.println("不存在此用户");
			mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);
			mav.addObject(MsgConfig.MESSAGE,"msg.login.failure");//用户名或密码错误， 请重新登录

			return mav;
		}
		
		//3. 验证用户是否被限制登录
		if(u.getUserStatus()==(short)0){	
			mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);
			mav.addObject(MsgConfig.MESSAGE,"msg.login.restrict");//用户被限制登录，请联系管理员
			return mav;
		}
		
		//IpInfo ipInfo=IpUtil.getIpInfo(loginIp);
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
		if(u.getErrorCount()>=num && u.getErrorTime() != null){
			//当用户密码输入错误次数大于系统配置的错误次数，获取错误时间 进行判断
			long gotime=loginTime.getTime()-u.getErrorTime().getTime();
			if(gotime<time*60000){
				//如果冷却时间未到
				
				mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);
				mav.addObject(MsgConfig.MESSAGE,"msg.login.restricttime");//您已{0}次密码输入错误，请{1}之后再试！
				mav.addObject(MsgConfig.MESSAGEVALUES, num+","+time+"分钟");//占位符赋值
				return mav;
			}else{
				//如果冷却时间已到 将用户输入错误的次数重置
				u.setErrorCount((short)0);
				
			}
			
		}
		//5. 登录认证  验证密码
		if(MD5Util.MD5Validate(password, u.getUserPassword())){
			Subject currentUser =SecurityUtils.getSubject();
			UsernamePasswordToken token =new UsernamePasswordToken(u.getId(), password);
			currentUser.login(token);//登录认证 记录登录信息
			System.out.println("****登录成功*****");
			//6.1 登录成功 保持一些用户信息到session中
			Member me=new Member();//需要放入当前session 的用户信息
			me.setId(u.getId());
			//me.setIpInfo(ipInfo);
			me.setLoginTime(loginTime);
			me.setDeptId(u.getDeptId());
			session.setAttribute("minfo",me); //将当前用户信息存入session中
			ServletUtil.getSession().setAttribute("minfo", me);
			
			if(name.equals(BaseConfig.getInstance().getDevName())){
				session.setAttribute("dev", true);//当前登录用户是开发者，拥有所有权限
			}else{
				session.setAttribute("dev", false);
			}
			if(name.equals(BaseConfig.getInstance().getSaName())){
				session.setAttribute("sa", true);//当前登录用户是系统超级管理员
			}else{
				session.setAttribute("sa", false);
			}
			//6.2 保持一些用户登录信息 到全局在线用户列表
			//获取全局 用户列表 将此次登录用户添加到用户在线列表中
			Map<String,OnLineUser> usersMap=ServletUtil.getOnLineUsers();
			
			OnLineUser onmy=usersMap.get(u.getId());
			if(onmy==null){
				onmy=new OnLineUser();
			}
			onmy.setId(u.getId());
			onmy.setTrueName(u.getTrueName());
			onmy.setDeptId(u.getDeptId());
			onmy.setSex(u.getUserSex());
			
			Map<String,LoginInfo> loginInfos=onmy.getLoginInfos();
			if(loginInfos==null){
				loginInfos=new HashMap<String,LoginInfo>();
			}
			LoginInfo loginInfo=new LoginInfo();
			loginInfo.setId(FileUtils.getUUID());
			loginInfo.setLoginType(1);
			//loginInfo.setIpInfo(ipInfo);
			loginInfo.setLoginTime(loginTime);
			loginInfos.put(session.getId(),loginInfo );
			onmy.setLoginInfos(loginInfos);
			usersMap.put(u.getId(), onmy);//将当前用户信息放入在线用户列表
			session.setAttribute("loginType", 1);//标记此session登录方式 用于退出时使用
			//6.3 记录登录日志
			SyLoginLog log=new SyLoginLog();
			log.setUserId(u.getId());
			log.setLoginType((short)1);
			log.setLoginDesc("登录成功");
			//log.setIpInfoCountry(ipInfo.getCountry());
			//log.setIpInfoRegion(ipInfo.getRegion());
			//log.setIpInfoCity(ipInfo.getCity());
			//log.setIpInfoIsp(ipInfo.getIsp());
			log.setLoginIp(loginIp);
			log.setLoginTime(loginTime);
			
			//dao.save(log);//保存登录日志
			//6.4 保持用户本此登录时间 ip 等信息保持到数据库
			u.setLastLoginIp(loginIp);//登录ip
			u.setLastLoginTime(loginTime);//登录时间
			u.setErrorCount((short)0);//将密码错误次数重置为0
			userService.updateUser(u);//更新用户
			
			mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_SUCCESS);
			mav.addObject(MsgConfig.MESSAGE,"msg.login.success");//登录成功
			
			session.removeAttribute("jmpw");//清除加密密码
			session.setAttribute("fromLogin",true);//标记刚执行登录操作
			return mav;
		}else{
			//认证失败
			System.out.println("密码错误");
			//登录日志
			SyLoginLog log=new SyLoginLog();
			log.setUserId(u.getId());
			log.setLoginType((short)1);
			log.setLoginDesc("密码错误");
			/*log.setIpInfoCountry(ipInfo.getCountry());
			log.setIpInfoCity(ipInfo.getCity());
			log.setIpInfoIsp(ipInfo.getIsp());
			log.setIpInfoRegion(ipInfo.getRegion());*/
			log.setLoginIp(loginIp);
			log.setLoginTime(loginTime);
			
			//dao.save(log);//保存登录日志
			
			u.setErrorTime(loginTime);
			u.setErrorCount((short)(u.getErrorCount()+1));
			
			userService.updateUser(u);//更新用户
		
			mav.addObject(MsgConfig.STATUSCODE, MsgConfig.CODE_FAIL);//用户名或密码错误， 请重新登录
			mav.addObject(MsgConfig.MESSAGE,"msg.login.failure");
			return mav;
		}
	
	}
	
	
	
}
