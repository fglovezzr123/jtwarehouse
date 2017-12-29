package com.wing.socialcontact.sys.service.impl;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.TjrbFinanceWill;
import com.wing.socialcontact.sys.dao.TjrbFinanceWillDao;
import com.wing.socialcontact.sys.service.TjrbFinanceWillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fenggang on 12/28/17.
 *
 * @author fenggang
 * @date 12/28/17
 */
@Service
public class TjrbFinanceWillServiceImpl extends BaseServiceImpl<TjrbFinanceWill> implements TjrbFinanceWillService {

    @Autowired
    private TjrbFinanceWillDao tjrbFinanceWillDao;

    @Override
    public DataGrid select(PageParam param, TjrbFinanceWill tjrbFinanceWill) {
        return null;
    }

    @Override
    public List selectAll() {
        return tjrbFinanceWillDao.selectAll();
    }

    @Override
    public String add(TjrbFinanceWill tjrbFinanceWill) {
        int res = tjrbFinanceWillDao.insert(tjrbFinanceWill);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String update(TjrbFinanceWill tjrbFinanceWill) {
       int res = tjrbFinanceWillDao.updateByPrimaryKey(tjrbFinanceWill);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public TjrbFinanceWill selectByPrimaryKey(Long key) {
        return tjrbFinanceWillDao.selectByPrimaryKey(key);
    }

    @Override
    public boolean delete(String[] ids) {
        return false;
    }
}
