package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.bean.RefundInstruction;

public interface IRefundInstructionService {

	Object selectrefundinstruction(PageParam param);

	String addrefundinstruction(RefundInstruction dto);

	RefundInstruction getrefundinstructionByid(Integer id);

	String updaterefundinstruction(RefundInstruction dto);

}
