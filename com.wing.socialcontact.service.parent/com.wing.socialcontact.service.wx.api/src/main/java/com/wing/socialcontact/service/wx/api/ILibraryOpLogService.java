package com.wing.socialcontact.service.wx.api;

import java.util.List;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.LibraryOpLog;

/**
* @author zhangfan
 * @date 2017-10-12 
 * @version 1.0
 */
public interface ILibraryOpLogService{

	/**
	 * 查询出所有
	 * @return
	 */
	public List selectAllOpLog(LibraryOpLog opLog);
	/**
	 * 条件查询
	 * @param param
	 * @param role
	 * @return
	 */
	public DataGrid selectOpLog(PageParam param,LibraryOpLog opLog);
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	public String addOpLog(LibraryOpLog opLog);
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public String updateOpLog(LibraryOpLog opLog);


	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public LibraryOpLog selectById(String id);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteOpLogById(String id);
	
    public int getCountByFkIdAndType(String fkId,String opType,String type);
}