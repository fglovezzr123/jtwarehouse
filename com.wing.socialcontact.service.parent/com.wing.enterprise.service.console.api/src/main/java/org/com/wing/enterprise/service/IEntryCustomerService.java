package org.com.wing.enterprise.service;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryCustomer;

import com.wing.socialcontact.common.model.PageParam;

/**
 * 企服客服管理
 * 
 * 
 * @ClassName: IEntryCustomerService
 * @Description: TODO
 * @author: sino
 * @date:2017年5月7日
 */
public interface IEntryCustomerService {
    
	/**
	 * 新增客服
	 * @param entryCustomer
	 * @return
	 */
    String addEntryCustomer(EntryCustomer entryCustomer);
    /**
     * 根据企业id查询客服列表
     * @param entryId
     * @return
     */
    List<Map> selCustomerByEntryId(String entryId);

    /**
     * 根据企业id查询客服列表
     * @param param
     * @param entryCustomer
     * @return
     */
	Object selectEntryCustomer(PageParam param, EntryCustomer entryCustomer);

	/**
	 * 根据id获取客服信息
	 * @param id
	 * @return
	 */
	EntryCustomer getById(String id);

	/**
	 * 更新客服
	 * @param dto
	 * @return
	 */
	String updateentrycustomer(EntryCustomer dto);
	/**
	 * 批量删除客服
	 * @param ids
	 * @return
	 */
	String delete(String[] ids);
}
