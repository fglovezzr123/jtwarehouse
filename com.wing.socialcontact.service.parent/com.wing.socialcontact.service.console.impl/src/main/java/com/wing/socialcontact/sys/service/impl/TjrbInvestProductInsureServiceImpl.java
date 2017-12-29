package com.wing.socialcontact.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.FinanceProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.sys.bean.TjrbInvestProductInsure;
import com.wing.socialcontact.sys.dao.TjrbInvestProductInsureDao;
import com.wing.socialcontact.sys.service.TjrbInvestProductInsureService;



@Service
public class TjrbInvestProductInsureServiceImpl extends BaseServiceImpl<TjrbInvestProductInsure> implements TjrbInvestProductInsureService{

	@Autowired
	private TjrbInvestProductInsureDao tjrbInvestProductInsureDao;

	@Override
	public DataGrid select(PageParam param, TjrbInvestProductInsure tjrbInvestProductInsure) {
		DataGrid data= new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());

		Map parm = new HashMap();

		List lst = tjrbInvestProductInsureDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());

		return data;
	}

	@Override
	public List selectAll() {
		return tjrbInvestProductInsureDao.selectAll();
	}

	@Override
	public String add(TjrbInvestProductInsure tjrbInvestProductInsure) {
		int res = tjrbInvestProductInsureDao.insert(tjrbInvestProductInsure);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String update(TjrbInvestProductInsure tjrbInvestProductInsure) {
		int res = tjrbInvestProductInsureDao.updateByPrimaryKey(tjrbInvestProductInsure);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public TjrbInvestProductInsure selectByPrimaryKey(Long key) {
		return tjrbInvestProductInsureDao.selectByPrimaryKey(key);
	}

	@Override
	public boolean delete(String[] ids) {
		boolean flag = true;
		for (String id : ids) {
			Long lID = Long.valueOf(id);
			TjrbInvestProductInsure tjrbInvestProductInsure=tjrbInvestProductInsureDao.selectByPrimaryKey(lID);
			if(tjrbInvestProductInsure!=null){
				tjrbInvestProductInsure.setStatus(0);
				if(super.updateByPrimaryKeyCache(tjrbInvestProductInsure, lID)){
				}else{
					flag = false;
				}
			}
		}
		return flag;
	}
}
