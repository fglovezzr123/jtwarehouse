package com.wing.socialcontact.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.FinanceProductStock;
import com.wing.socialcontact.sys.dao.FinanceProductStockDao;
import com.wing.socialcontact.sys.service.FinanceProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 12/26/17.
 *
 * @author fenggang
 * @date 12/26/17
 */
@Service
public class FinanceProductStockServiceImpl  extends BaseServiceImpl<FinanceProductStock> implements FinanceProductStockService {

    @Autowired
    private FinanceProductStockDao financeProductStockDao;

    @Override
    public List<FinanceProductStock> findAll() {
        return financeProductStockDao.selectAll();
    }

    @Override
    public DataGrid selectFinanceProductStock(PageParam param, FinanceProductStock financeProductStock) {
        DataGrid data= new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());

        Map parm = new HashMap();

        List lst = financeProductStockDao.selectByParam(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());

        return data;
    }

    @Override
    public List selectAllFinanceProductStock() {
        return financeProductStockDao.selectAll();
    }

    @Override
    public String addFinanceProductStock(FinanceProductStock financeProductStock) {
        int res = financeProductStockDao.insert(financeProductStock);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String updateFinanceProductStock(FinanceProductStock financeProductStock) {
        int res = financeProductStockDao.updateByPrimaryKey(financeProductStock);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public FinanceProductStock selectByPrimaryKey(Long key) {
        return super.selectByPrimaryKeyCache(key,FinanceProductStock.class);
    }

    @Override
    public boolean deleteFinanceProductStock(String[] ids) {
        boolean flag = true;
        for (String id : ids) {
            FinanceProductStock financeProductStock=financeProductStockDao.selectByPrimaryKey(id);
            if(financeProductStock!=null){
                financeProductStock.setStatus(1);
                if(super.updateByPrimaryKeyCache(financeProductStock, id)){
                }else{
                    flag = false;
                }
            }
        }
        return flag;
    }
}
