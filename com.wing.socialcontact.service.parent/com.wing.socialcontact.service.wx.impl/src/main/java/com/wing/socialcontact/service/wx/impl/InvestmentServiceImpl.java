package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IInvestmentService;
import com.wing.socialcontact.service.wx.bean.Investment;
import com.wing.socialcontact.service.wx.bean.InvestmentClass;
import com.wing.socialcontact.service.wx.bean.TjyLibraryClass;
import com.wing.socialcontact.service.wx.dao.InvestmentClassDao;
import com.wing.socialcontact.service.wx.dao.InvestmentDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
@Service
public class InvestmentServiceImpl extends BaseServiceImpl<Investment> implements IInvestmentService {

	
	private static final String cache_name = "\"DB:Investment:\" + ";
	
	@Resource
	private InvestmentClassDao InvestmentClassDao;
	
	@Resource
	private InvestmentDao InvestmentDao;
	/**
	 * 查询列表
	 */
	@Override
	public Object selectinvestment(PageParam param, Investment investment) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		List<Map<String,Object>> lst=InvestmentDao.getMap(parm);

		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}
	/**
	 * 添加
	 */
	@Override
	public String addinvestment(Investment dto) {
			int res = InvestmentDao.insert(dto);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
	}
	/**
	 * 根据id获取
	 */
	@Override
	public Investment getinvestmentByid(String id) {
		return super.selectByPrimaryKeyCache(id, Investment.class);
	}
	/**
	 * 更新
	 */
	@Override
	public String updateinvestment(Investment dto) {
			if(super.updateByPrimaryKeyCache(dto,dto.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
	}
	/**
	 * 批量删除
	 */
	@Override
	public boolean deleteinvestment(String[] ids) {
		
		for(String id : ids){
			 super.deleteByPrimaryKeyCache(id, Investment.class);
		}
		
		return true;
	}
	/**
	 * 根据类型查询基金投资
	 *  定增投资
	 * 上市孵化
	 * 上市并购
	 */
	@Override
	public List<Map> getinvestmentList(String classId) {
		// TODO Auto-generated method stub
		Map parm = new HashMap<>();
		parm.put("classId", classId);
		return InvestmentDao.getinvestmentList(parm);
	}



}
