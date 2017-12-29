package com.wing.socialcontact.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.FinanceProduct;
import com.wing.socialcontact.sys.bean.Organization;
import com.wing.socialcontact.sys.dao.FinanceProductDao;
import com.wing.socialcontact.sys.dao.OrganizationDao;
import com.wing.socialcontact.sys.service.FinanceProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 12/25/17.
 *
 * @author fenggang
 * @date 12/25/17
 */
@Service
public class FinanceProductServiceImpl  extends BaseServiceImpl<FinanceProduct> implements FinanceProductService{

    @Autowired
    private FinanceProductDao financeProductDao;
    @Autowired
    private OrganizationDao organizationDao;

    @Override
    public List<FinanceProduct> findAll() {
        return financeProductDao.selectAll();
    }

    @Override
    public DataGrid selectFinanceProduct(PageParam param, FinanceProduct financeProduct) {
        DataGrid data= new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());

        Map parm = new HashMap();

        List lst = financeProductDao.selectByParam(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());

        return data;
    }

    @Override
    public List selectAllFinanceProduct() {
        return financeProductDao.selectAll();
    }

    @Override
    public String addFinanceProduct(FinanceProduct financeProduct) {
        financeProduct.setStatus(1);
        Organization org = organizationDao.selectByPrimaryKey(financeProduct.getOrgNo());
        if(org!=null){
            financeProduct.setOrgName(org.getOrgName());
        }
        financeProduct.setFinanceAmount(financeProduct.getFinanceAmount1()+"-"+financeProduct.getFinanceAmount2());
        financeProduct.setFinancePeriod(financeProduct.getFinancePeriod1()+"-"+financeProduct.getFinancePeriod2());
        int res = financeProductDao.insert(financeProduct);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String updateFinanceProduct(FinanceProduct financeProduct) {
        financeProduct.setStatus(1);
        Organization org = organizationDao.selectByPrimaryKey(financeProduct.getOrgNo());
        if(org!=null){
            financeProduct.setOrgName(org.getOrgName());
        }
        financeProduct.setFinanceAmount(financeProduct.getFinanceAmount1()+"-"+financeProduct.getFinanceAmount2());
        financeProduct.setFinancePeriod(financeProduct.getFinancePeriod1()+"-"+financeProduct.getFinancePeriod2());
        int res = financeProductDao.updateByPrimaryKey(financeProduct);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public FinanceProduct selectByPrimaryKey(Long key) {
        FinanceProduct financeProduct =  financeProductDao.selectByPrimaryKey(key);
        return financeProduct;
    }

    @Override
    public boolean deleteFinanceProduct(String[] ids) {
        boolean flag = true;
        for (String id : ids) {
            Long lID = Long.valueOf(id);
            FinanceProduct financeProduct=financeProductDao.selectByPrimaryKey(lID);
            if(financeProduct!=null){
                financeProduct.setStatus(0);
                if(super.updateByPrimaryKeyCache(financeProduct, lID)){
                }else{
                    flag = false;
                }
            }
        }
        return flag;
    }
}
