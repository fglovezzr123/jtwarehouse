package com.wing.socialcontact.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.TjrbInvestProduct;
import com.wing.socialcontact.sys.bean.TjrbInvestProductInsure;
import com.wing.socialcontact.sys.dao.TjrbInvestProductDao;
import com.wing.socialcontact.sys.dao.TjrbInvestProductInsureDao;
import com.wing.socialcontact.sys.service.TjrbInvestProductInsureService;
import com.wing.socialcontact.sys.service.TjrbInvestProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TjrbInvestProductServiceImpl extends BaseServiceImpl<TjrbInvestProduct> implements TjrbInvestProductService{

	@Autowired
	private TjrbInvestProductDao tjrbInvestProductDao;

	@Override
	public DataGrid select(PageParam param, TjrbInvestProduct tjrbInvestProduct) {
		DataGrid data= new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());

		Map parm = new HashMap();

		List lst = tjrbInvestProductDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());

		return data;
	}

	@Override
	public List selectAll() {
		return tjrbInvestProductDao.selectAll();
	}

	@Override
	public String add(TjrbInvestProduct tjrbInvestProduct) {
		int res = tjrbInvestProductDao.insert(tjrbInvestProduct);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public String update(TjrbInvestProduct tjrbInvestProduct) {
		int res = tjrbInvestProductDao.updateByPrimaryKey(tjrbInvestProduct);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public TjrbInvestProduct selectByPrimaryKey(Long key) {
		return tjrbInvestProductDao.selectByPrimaryKey(key);
	}

	@Override
	public boolean delete(String[] ids) {
		boolean flag = true;
		for (String id : ids) {
			Long lID = Long.valueOf(id);
			TjrbInvestProduct tjrbInvestProduct=tjrbInvestProductDao.selectByPrimaryKey(lID);
			if(tjrbInvestProduct!=null){
				tjrbInvestProduct.setStatus(0);
				if(super.updateByPrimaryKeyCache(tjrbInvestProduct, lID)){
				}else{
					flag = false;
				}
			}
		}
		return flag;
	}
}
