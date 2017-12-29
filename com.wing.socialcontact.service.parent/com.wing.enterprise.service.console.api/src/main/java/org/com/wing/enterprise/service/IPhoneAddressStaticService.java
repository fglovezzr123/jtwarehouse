package org.com.wing.enterprise.service;

import org.com.wing.enterprise.bean.PhoneAdressStatic;

import com.wing.socialcontact.common.model.DataGrid;


/**
 * 企服云--通讯录开关统计
 * 
 * 
 * @ClassName: IPhoneAdressStaticService
 * @Description: TODO
 * @author: sino
 * @date:2017年5月7日
 */
public interface IPhoneAddressStaticService {
    
    DataGrid selPAstatic(PhoneAdressStatic pas);
    
    String add(PhoneAdressStatic pas);
}
