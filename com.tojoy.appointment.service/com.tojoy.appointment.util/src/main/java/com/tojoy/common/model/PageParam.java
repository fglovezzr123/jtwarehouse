/**  
 * @Title: PageParam.java
 * @date 2016-8-28 下午10:36:05
 * @Copyright: 2016 
 */
package com.tojoy.common.model;


/**
 * easyui 版分页查询参数
 * @author	dijuli
 * @version	 1.0
 *
 */
public class PageParam implements java.io.Serializable{
	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 起始页数
	 */
	private int page = 1;
	/**
	 * 每页显示的条数
	 */
	private int rows = 20;
	/**
	 * 排序字段名称
	 */
	private String sort;
	/**
	 * 排序方式，按顺序对应排序字段名称
	 */
	private String order;
	/**
	 * 条件查询规则： json格式
	 * groups:
	 *		rules: field,op,value,type
	 *		groups:
	 *		op:
	 */
	private String searchRules;
	
	public PageParam(){
		super();
	}
	
	public PageParam(int page,int rows){
		this();
		this.page = page<=0?1:page;
		this.rows = rows<=0?1:rows;
	}
	
	/**
	 * hql语句拼接排序语句
	 * @param hql
	 */
	public void appendOrderBy(StringBuffer hql){
		
		if(sort!=null&&!"".equals(sort)){
			String[] sorts=sort.split(",");
			String[] orders=order.split(",");
			hql.append(" order by ");
			for(int i=0,c=sorts.length;i<c;i++){
				if(i==0){
					hql.append(sorts[i]+" "+orders[i]);
				}else{
					hql.append(","+sorts[i]+" "+orders[i]);
				}
				
			}
		}
		
	}
	
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrderByString(){
		String orderstr = "";
		if(sort!=null&&!"".equals(sort)){
			String[] sorts=sort.split(",");
			String[] orders=order.split(",");
			orderstr+=" order by ";
			for(int i=0,c=sorts.length;i<c;i++){
				if(i==0){
					orderstr+= sorts[i]+" "+orders[i];
				}else{
					orderstr+= ","+sorts[i]+" "+orders[i];
				}
				
			}
		}
		return orderstr;
		
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}

	public String getSearchRules() {
		return searchRules;
	}

	public void setSearchRules(String searchRules) {
		this.searchRules = searchRules;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
