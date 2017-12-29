/**  
 * @Title: DataGrid.java
 * @date 2016-8-28 下午10:54:14
 * @Copyright: 2016 
 */
package com.tojoy.business.common.model;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * easyui 分页查询数据表格返回的结果
 * @author	dijuli
 * @version	 1.0
 *
 */
public class DataGrid implements java.io.Serializable{
	/**
	 * @Fields serialVersionUID : 
	 */
	
	private static final long serialVersionUID = 1L;
	/**
	 * 总条数
	 */
	private long  total = 0;// 总记录数
	/**
	 * 查询数据集合
	 */
	@SuppressWarnings("rawtypes")
	private List rows = new ArrayList();
	
	private int pageNum = 1;//当前页码
	
	private int pages = 0;//总页数
	
	
	public DataGrid(){
		super();
	}
	

	public DataGrid(PageInfo<?> page){
		this();
		if(page!=null){
			this.pageNum = page.getPageNum();
			this.rows = page.getList()==null?this.rows:page.getList();
			this.total = page.getTotal();
			this.pages = page.getPages();
		}
	}
	
	/**
	 * 表格底部显示的信息,固定在底部
	 * 列名和数据列名相同
	 */
	@SuppressWarnings("rawtypes")
	private List footer;
	
	public long  getTotal() {
		return total;
	}
	public void setTotal(long  total) {
		this.total = total;
	}
	@SuppressWarnings("rawtypes")
	public List getRows() {
		return rows==null?new ArrayList():rows;
	}
	@SuppressWarnings("rawtypes")
	public void setRows(List rows) {
		this.rows = rows==null?new ArrayList():rows;
	}
	@SuppressWarnings("rawtypes")
	public List getFooter() {
		return footer;
	}
	@SuppressWarnings("rawtypes")
	public void setFooter(List footer) {
		this.footer = footer;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}


	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
}
