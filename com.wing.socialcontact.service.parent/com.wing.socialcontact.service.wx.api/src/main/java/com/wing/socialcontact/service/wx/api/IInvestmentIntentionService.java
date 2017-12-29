package com.wing.socialcontact.service.wx.api;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.InvestmentIntention;
/**
 * 
 * @author zhangzheng
 *
 * 投资意向管理
 */
public interface IInvestmentIntentionService {

	/**
	 * 投资意向列表查询
	 * @param param
	 * @param investmentIntention
	 * @param stime
	 * @param etime
	 * @return
	 */
	Object selectinvestmentintention(PageParam param,
			InvestmentIntention investmentIntention,Date stime,Date etime);
	/**
	 * 添加
	 * @param dto
	 * @return
	 */
	boolean addInvestment(InvestmentIntention dto);
	/**
	 * 根据用户id获取投资意向列表
	 * @param userId
	 * @return
	 */
	List<Map> getinvestmentList(String userId,Integer page,Integer size);
	/**
	 * 更新
	 * @param dto
	 * @return
	 */
	String updateInvestmentIntention(InvestmentIntention dto);
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 */
	InvestmentIntention getInvestmentIntentionByid(String id);
	/**
	 * 导出报表数据查询
	 * @param className1
	 * @param status1
	 * @param stime1
	 * @param etime1
	 * @return
	 * @throws IOException
	 */
	List<Map<String,Object>> exportinvestmentintention(String className1, int status1,
			Date stime1, Date etime1) throws IOException ;

}
