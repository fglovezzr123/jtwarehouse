package com.wing.enterprise.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.com.wing.enterprise.bean.PhoneAdressStatic;
import org.com.wing.enterprise.service.IPhoneAddressStaticService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.PhoneAddressStaticDao;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.config.MsgConfig;

@Service
public class PhoneAddressStaticServiceImpl extends BaseServiceImpl<PhoneAdressStatic> implements IPhoneAddressStaticService{

    @Resource
    private PhoneAddressStaticDao phoneAddressStaticDao;

    @Override
    public DataGrid selPAstatic(PhoneAdressStatic pas) {
        
        DataGrid data= new DataGrid();
        
        List<Map> lst = phoneAddressStaticDao.selectByParam(null);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        
        return data;
        
    }

    @Override
    public String add(PhoneAdressStatic pas) {
        int res = phoneAddressStaticDao.insert(pas);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }
}
