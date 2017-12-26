package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.InvestmentClass;

/**
 * 
 * @author zhangzheng
 * 投资分类管理
 */
public interface IInvestmentClassService {

	/**
	 * 列表查询
	 * @param param
	 * @param investmentClassc
	 * @return
	 */
	Object selectinvestmentClass(PageParam param,
			InvestmentClass investmentClassc);
	/**
	 * 添加
	 * @param dto
	 * @return
	 */
	public String addinvestmentClass(InvestmentClass dto);

	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	InvestmentClass getinvestmentClassByid(String id);
	/**
	 * 更新
	 * @param dto
	 * @return
	 */
	public String updateinvestmentClass(InvestmentClass dto);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteinvestmentClass(String[] ids);
	/**
	 * 获取所有分类
	 * @return
	 */
	List<InvestmentClass> getAllClass();
	/**
	 * 获取所有分类
	 * @return
	 */
	List<Map> getClassList();

}
