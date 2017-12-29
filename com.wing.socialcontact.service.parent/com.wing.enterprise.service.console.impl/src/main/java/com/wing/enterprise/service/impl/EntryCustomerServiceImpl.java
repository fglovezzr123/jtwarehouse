package com.wing.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.com.wing.enterprise.bean.EntryCustomer;
import org.com.wing.enterprise.service.IEntryCustomerService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.EntryCustomerDao;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;

@Service
public class EntryCustomerServiceImpl extends BaseServiceImpl<EntryCustomer> implements IEntryCustomerService{

    @Resource
    private EntryCustomerDao entryCustomerDao;
    
    /**
     * 添加客服
     */
    @Override
    public String addEntryCustomer(EntryCustomer entryCustomer) {
        int res = entryCustomerDao.insert(entryCustomer);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    /**
     * 根据企业id查询客服列表
     */
    @Override
    public List<Map> selCustomerByEntryId(String entryId) {
        return entryCustomerDao.selCustomerByEntryId(entryId);
    }

    /**
     * 条件分页查询客服
     */
	@Override
	public Object selectEntryCustomer(PageParam param,
			EntryCustomer entryCustomer) {
		
		
		DataGrid data=new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("entryId", entryCustomer.getEntryId());
		List<Map<String,Object>> lst=entryCustomerDao.getclassMap(parm);

		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	/**
	 * 根据id查询客服
	 */
	@Override
	public EntryCustomer getById(String id) {
		// TODO Auto-generated method stub
		return super.selectByPrimaryKeyCache(id, EntryCustomer.class);
	}

	/**
	 * 更新客服
	 */
	@Override
	public String updateentrycustomer(EntryCustomer dto) {
		int res = entryCustomerDao.updateByPrimaryKey(dto);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
	}

	/**
	 * 批量删除客服
	 */
	@Override
	public String delete(String[] ids) {
		for(String id : ids){
			super.deleteByPrimaryKeyCache(id, EntryCustomer.class);
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}
    
}
