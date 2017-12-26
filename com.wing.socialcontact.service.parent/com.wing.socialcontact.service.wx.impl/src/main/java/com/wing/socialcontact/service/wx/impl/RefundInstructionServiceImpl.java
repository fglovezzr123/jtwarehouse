package com.wing.socialcontact.service.wx.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IRefundInstructionService;
import com.wing.socialcontact.service.wx.bean.RefundInstruction;
import com.wing.socialcontact.service.wx.dao.RefundInstructionDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
@Service
public class RefundInstructionServiceImpl extends BaseServiceImpl<RefundInstruction> implements
		IRefundInstructionService {

	@Resource
	private RefundInstructionDao refundInstructionDao;

	@Override
	public Object selectrefundinstruction(PageParam param) {
		DataGrid data=new DataGrid();
		List lst = null;
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		
		lst = refundInstructionDao.getclassMap();
		
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	@Override
	public String addrefundinstruction(RefundInstruction dto) {
		int res = refundInstructionDao.insert(dto);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}

	@Override
	public RefundInstruction getrefundinstructionByid(Integer id) {
		// TODO Auto-generated method stub
		return super.selectByPrimaryKeyCache(id, RefundInstruction.class);
	}

	@Override
	public String updaterefundinstruction(RefundInstruction dto) {
		if(super.updateByPrimaryKeyCache(dto,dto.getId())){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
	}
}
