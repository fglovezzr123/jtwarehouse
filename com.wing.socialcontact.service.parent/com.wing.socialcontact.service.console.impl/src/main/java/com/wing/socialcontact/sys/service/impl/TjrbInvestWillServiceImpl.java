package com.wing.socialcontact.sys.service.impl;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.TjrbInvestWill;
import com.wing.socialcontact.sys.dao.TjrbInvestWillDao;
import com.wing.socialcontact.sys.service.TjrbInvestWillService;
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
public class TjrbInvestWillServiceImpl extends BaseServiceImpl<TjrbInvestWill> implements TjrbInvestWillService {

    @Autowired
   private TjrbInvestWillDao tjrbInvestWillDao;

    @Override
    public DataGrid select(PageParam param, TjrbInvestWill tjrbInvestWill) {
        return null;
    }

    @Override
    public List selectAll() {
        return tjrbInvestWillDao.selectAll();
    }

    @Override
    public String add(TjrbInvestWill tjrbInvestWill) {
        int res = tjrbInvestWillDao.insert(tjrbInvestWill);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String update(TjrbInvestWill tjrbInvestWill) {
        int res = tjrbInvestWillDao.updateByPrimaryKey(tjrbInvestWill);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public TjrbInvestWill selectByPrimaryKey(Long key) {
        return tjrbInvestWillDao.selectByPrimaryKey(key);
    }

    @Override
    public boolean delete(String[] ids) {
        return false;
    }
}
