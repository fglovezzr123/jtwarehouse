package com.wing.socialcontact.service.wx.api;

import java.util.List;
import java.util.Map;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.Investment;
import com.wing.socialcontact.service.wx.bean.InvestmentClass;
/**
 * 
 * @author zhangzheng
 * 投资列表
 */
public interface IInvestmentService {

	/**
	 * 列表查询
	 * @param param
	 * @param investment
	 * @return
	 */
	Object selectinvestment(PageParam param,
			Investment investment);
	/**
	 * 添加
	 * @param dto
	 * @return
	 */
	public String addinvestment(Investment dto);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Investment getinvestmentByid(String id);
	/**
	 * 更新
	 * @param dto
	 * @return
	 */
	public String updateinvestment(Investment dto);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteinvestment(String[] ids);
	/**
	 * 根据类型查询基金投资
	 *  定增投资
	 * 上市孵化
	 * 上市并购
	 */
	List<Map> getinvestmentList(String classId);
}
