package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.ProjectSupermarket;

import java.util.List;
import java.util.Map;

/**
 * 项目超市
 * @author wangyansheng
 * @date 2017-11-01
 * @version 1.0
 */
public interface IProjectSupermarketService {

	/**
	 * 新增
	 * @param t
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public int insert(ProjectSupermarket t);

	/**
	 * 修改
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public int update(ProjectSupermarket t);

	/**
	 * 删除
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public int logicalDelete(ProjectSupermarket t);

	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public DataGrid selectByParam(PageParam param, ProjectSupermarket t);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @author wangyansheng
	 * @date 2017-11-01
	 */
	public ProjectSupermarket selectById(String id);

	/**
	 * @author devil
	 * @desicription: 获取项目超市图片
	 * @date Created in 2017/11/14 16:09
	 */
	public List<Map> selectImagesByParam(Map paraMap);

}