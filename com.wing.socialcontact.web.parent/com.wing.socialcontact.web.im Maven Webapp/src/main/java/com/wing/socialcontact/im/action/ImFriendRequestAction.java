/**  
 * @Title: DataPermissionsAction.java
 * @date 2016-10-18 下午4:05:33
 * @Copyright: 2016 
 */
package com.wing.socialcontact.im.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.util.StringUtil;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.im.api.IImFriendrequestService;

/**
 * 数据权限管理
 * @author	dijuli
 * @version	 1.0
 *
 */
@Controller
@RequestMapping("/imFriendRequest")
public class ImFriendRequestAction extends BaseAction{

	@Autowired
	private IImFriendrequestService imFriendrequestService; 
	
	/**
	 * 测试
	 * @return
	 */
	@RequestMapping("test")
	public @ResponseBody Map findMyFriendListByUserId(HttpServletRequest request,HttpServletResponse response) throws IOException{
		return super.getSuccessAjaxResult("seesionid:"+request.getSession().getId());
	}
	
	/**
	 * 获取新好友列表
	 * @return
	 */
	@RequestMapping("findMyNewFriendListByUserId")
	public void findMyNewFriendListByUserId(HttpServletResponse response,String userId,String pageNum, String pageSize) throws IOException{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(StringUtil.isEmpty(userId) || StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(pageSize) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageNum) || !com.wing.socialcontact.util.StringUtil.isNumeric(pageSize)){
			Map resultMap = new HashMap();
			resultMap.put("code", MsgConfig.CODE_FAIL);
			resultMap.put("msg", "参数无效，请检查！");
			resultMap.put("dataobj", "");
			JSONObject json = new JSONObject();
			json.putAll(resultMap);
			out.write(json.toString());
		}else{
			
			List performs = imFriendrequestService.findMyNewFriendListByUserId(userId, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
			Map resultMap = new HashMap();
			resultMap.put("code", MsgConfig.CODE_SUCCESS);
			resultMap.put("msg", "获取成功！");
			resultMap.put("dataobj", performs);
			JSONObject json = new JSONObject();
			json.putAll(resultMap);
			out.write(json.toString());
		}
		out.flush();
		out.close();
	}
	
	/**
	 * 添加好友请求
	 * @return
	 */
	@RequestMapping("addFriendRequest")
	public void addFriendRequest(HttpServletResponse response,String userId,String friendUserId,String askmessage) throws IOException{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(StringUtil.isEmpty(userId) || StringUtil.isEmpty(friendUserId)){
			Map resultMap = new HashMap();
			resultMap.put("code", MsgConfig.CODE_FAIL);
			resultMap.put("msg", "参数无效，请检查！");
			resultMap.put("dataobj", "");
			JSONObject json = new JSONObject();
			json.putAll(resultMap);
			out.write(json.toString());
		}else{
			try{
				String requestStr = imFriendrequestService.saveFriendRequest(userId, friendUserId, askmessage);
				Map resultMap = new HashMap();
				resultMap.put("code", MsgConfig.CODE_SUCCESS);
				resultMap.put("msg", "发送请求成功！");
				resultMap.put("dataobj", requestStr);
				JSONObject json = new JSONObject();
				json.putAll(resultMap);
				out.write(json.toString());
			}catch(RuntimeException e){
				Map resultMap = new HashMap();
				resultMap.put("code", MsgConfig.CODE_FAIL);
				resultMap.put("msg", e.getMessage());
				resultMap.put("dataobj", "");
				JSONObject json = new JSONObject();
				json.putAll(resultMap);
				out.write(json.toString());
			}
		}
		out.flush();
		out.close();
	}
	
	/**
	 *  处理好友请求 
	 * @param response
	 * @param friendRequestId 请求id
	 * @param status 处理结果 2、同意 3、拒绝
	 * @throws IOException
	 */
	@RequestMapping("dualFriendRequest")
	public void dualFriendRequest(HttpServletResponse response,String friendRequestId,String status) throws IOException{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(StringUtil.isEmpty(friendRequestId) || StringUtil.isEmpty(status) || !com.wing.socialcontact.util.StringUtil.isNumeric(status) || (!status.equals("2") && !status.equals("3"))){
			Map resultMap = new HashMap();
			resultMap.put("code", MsgConfig.CODE_FAIL);
			resultMap.put("msg", "参数无效，请检查！");
			resultMap.put("dataobj", "");
			JSONObject json = new JSONObject();
			json.putAll(resultMap);
			out.write(json.toString());
		}else{
			try{
				String requestStr = imFriendrequestService.updateFriendRequestStatus(friendRequestId, Integer.parseInt(status));
				Map resultMap = new HashMap();
				resultMap.put("code", MsgConfig.CODE_SUCCESS);
				resultMap.put("msg", "操作成功！！");
				resultMap.put("dataobj", requestStr);
				JSONObject json = new JSONObject();
				json.putAll(resultMap);
				out.write(json.toString());
			}catch(RuntimeException e){
				Map resultMap = new HashMap();
				resultMap.put("code", MsgConfig.CODE_FAIL);
				resultMap.put("msg", e.getMessage());
				resultMap.put("dataobj", "");
				JSONObject json = new JSONObject();
				json.putAll(resultMap);
				out.write(json.toString());
			}
		}
		out.flush();
		out.close();
	}
	
	
	
}
