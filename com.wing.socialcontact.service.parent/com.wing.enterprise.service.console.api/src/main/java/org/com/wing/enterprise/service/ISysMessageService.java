package org.com.wing.enterprise.service;

import java.io.IOException;

import org.com.wing.enterprise.bean.SysMessage;

import com.wing.socialcontact.common.model.PageParam;

/**
 * 
 * <p>Title:企服云系统消息管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月10日 上午11:53:50
 */

public interface ISysMessageService {

	/**
	 * 列表查询
	 * @param param
	 * @param sysMessage
	 * @return
	 */
	Object selsysMessage(PageParam param, SysMessage sysMessage);

	/**
	 * 新增
	 * @param dto
	 * @return
	 */
	String addsysMessage(SysMessage dto) throws IOException;
	boolean deleteSysMsg(String[] ids);
}
