/**  
 * @Title: DataPermissionsAction.java
 * @date 2016-10-18 下午4:05:33
 * @Copyright: 2016 
 */
package com.wing.socialcontact.im.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wing.socialcontact.common.action.BaseAction;

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
