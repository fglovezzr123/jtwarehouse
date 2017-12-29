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

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import tk.mybatis.mapper.util.StringUtil;

import com.wing.socialcontact.common.action.BaseAction;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.im.api.IImFriendService;

/**
 * 数据权限管理
 * @author	dijuli
 * @version	 1.0
 *
 */
@Controller
@RequestMapping("/imFriend")
public class ImFriendAction extends BaseAction{

	@Autowired
	private IImFriendService imFriendService; 
	
	/**
	 * 获取好友列表
	 * @return
	 */
	@RequestMapping("findMyFriendListByUserId")
	public void findMyFriendListByUserId(HttpServletResponse response,String userId,String pageNum, String pageSize) throws IOException{
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
			
			List performs = imFriendService.findMyFriendListByUserId(userId, Integer.parseInt(pageNum), Integer.parseInt(pageSize));
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
	 * 获取好友列表
	 * @return
	 */
	@RequestMapping("delMyFriendByUserIdAndFriendUserid")
	public void delMyFriendByUserIdAndFriendUserid(HttpServletResponse response,String userId,String friendUserId) throws IOException{
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
				String requestStr = imFriendService.deleteEachOtherFriendByUserAndFriend(userId, friendUserId);
				Map resultMap = new HashMap();
				resultMap.put("code", MsgConfig.CODE_SUCCESS);
				resultMap.put("msg", "删除好友成功！");
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
