package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Template;

/**
 * 
 * @author liangwj
 * @date 2017-03-27 11:22:42
 * @version 1.0
 */
public interface ITemplateService{

	/**
	 * 查询出所有分类
	 * @return
	 */
	public List selectAllTemplate();
	/**
	 * 条件查询
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectTemplates(PageParam param,Template template);
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public String addTemplate(Template template);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public String updateTemplate(Template template);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteTemplates(String[] ids);
	/**
	 * 批量修改isopen
	 * @param ids
	 * @return
	 */
	public boolean updateTemplateByIsopen(String[] ids,int isopen);

	public Template selectByPrimaryKey(String key);
	
	public Template selectById(String id);
	
	


}