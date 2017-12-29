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
import com.wing.socialcontact.service.im.api.IImFollowService;
import com.wing.socialcontact.service.im.api.IImFriendService;
import com.wing.socialcontact.util.Constants;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 关注接口
 * @author	xuxinyuan
 * @version	 1.0
 *
 */
@Controller
@RequestMapping("/im/friend")
public class ImBussinessFriendAction extends BaseAction{

	/**
	 * 好友页面
	 * @return
	 */
	@RequestMapping("businessFriend")
	public String reconPage() {
		return "businessFriend/businessFriend";
	}
	
	
}
