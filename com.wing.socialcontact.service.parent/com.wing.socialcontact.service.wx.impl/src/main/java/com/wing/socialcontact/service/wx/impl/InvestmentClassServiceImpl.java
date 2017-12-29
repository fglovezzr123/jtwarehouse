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
import com.wing.socialcontact.service.wx.api.IInvestmentClassService;
import com.wing.socialcontact.service.wx.bean.InvestmentClass;
import com.wing.socialcontact.service.wx.bean.TjyLibraryClass;
import com.wing.socialcontact.service.wx.dao.InvestmentClassDao;
import com.wing.socialcontact.service.wx.dao.InvestmentDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
@Service
public class InvestmentClassServiceImpl  extends BaseServiceImpl<InvestmentClass> implements IInvestmentClassService {

	
	private static final String cache_name = "\"DB:InvestmentClass:\" + ";
	
	@Resource
	private InvestmentClassDao InvestmentClassDao;
	
	@Resource
	private InvestmentDao InvestmentDao;
	/**
	 * 分类列表查询
	 */
	@Override
	public Object selectinvestmentClass(PageParam param,
			InvestmentClass investmentClass) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("name", investmentClass.getName());
		List<Map<String,Object>> lst=InvestmentClassDao.getclassMap(parm);

		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}
	/**
	 * 添加
	 */
	@Override
	public String addinvestmentClass(InvestmentClass dto) {
		InvestmentClass parm = new InvestmentClass();
		parm.setName(dto.getName());
		List lst = InvestmentClassDao.select(parm);
		if(lst.size()==0){
			int res = InvestmentClassDao.insert(dto);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.newsclass.unique";//
		}
	}
	/**
	 * 根据id获取
	 */
	@Override
	public InvestmentClass getinvestmentClassByid(String id) {
		return super.selectByPrimaryKeyCache(id, InvestmentClass.class);
	}
	/**
	 * 更新
	 */
	@Override
	public String updateinvestmentClass(InvestmentClass dto) {
		InvestmentClass parm = new InvestmentClass();
		parm.setName(dto.getName());
		InvestmentClass obj = InvestmentClassDao.selectOne(parm);
		if(obj==null || obj.getId().equals(dto.getId())){
			if(super.updateByPrimaryKeyCache(dto,dto.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.newsclass.unique";//
		}
	}
	/**
	 * 批量删除
	 */
	@Override
	public boolean deleteinvestmentClass(String[] ids) {
		for(String id :ids ){
			List list =InvestmentDao.getInvestmentByClassId(id);
			if(list.size()>0){
			}else{
				return super.deleteByPrimaryKeyCache(id, InvestmentClass.class);
			}
		}
		return true;
	}
	/**
	 * 获取所有分类
	 */
	@Override
	public List<InvestmentClass> getAllClass() {
		// TODO Auto-generated method stub
		return InvestmentClassDao.selectAll();
	}
	/**
	 * 获取所有分类
	 */
	@Override
	public List<Map> getClassList() {
		// TODO Auto-generated method stub
		return InvestmentClassDao.getClassList();
	}

}
