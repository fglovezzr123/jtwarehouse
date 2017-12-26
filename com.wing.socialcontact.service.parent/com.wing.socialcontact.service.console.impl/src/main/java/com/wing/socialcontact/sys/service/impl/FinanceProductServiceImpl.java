package com.wing.socialcontact.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.FinanceProduct;
import com.wing.socialcontact.sys.dao.FinanceProductDao;
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
        int res = financeProductDao.insert(financeProduct);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String updateFinanceProduct(FinanceProduct financeProduct) {
        int res = financeProductDao.updateByPrimaryKey(financeProduct);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public FinanceProduct selectByPrimaryKey(Long key) {
        return super.selectByPrimaryKeyCache(key,FinanceProduct.class);
    }

    @Override
    public boolean deleteFinanceProduct(String[] ids) {
        boolean flag = true;
        for (String id : ids) {
            FinanceProduct financeProduct=financeProductDao.selectByPrimaryKey(id);
            if(financeProduct!=null){
                financeProduct.setStatus(1);
                if(super.updateByPrimaryKeyCache(financeProduct, id)){
                }else{
                    flag = false;
                }
            }
        }
        return flag;
    }
}
