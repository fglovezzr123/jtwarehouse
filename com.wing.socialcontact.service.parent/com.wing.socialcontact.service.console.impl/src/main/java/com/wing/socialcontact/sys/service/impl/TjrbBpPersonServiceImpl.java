package com.wing.socialcontact.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.TjrbBpPersonEntity;
import com.wing.socialcontact.sys.dao.TjrbBpPersonDao;
import com.wing.socialcontact.sys.service.TjrbBpPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 12/28/17.
 *
 * @author fenggang
 * @date 12/28/17
 */
@Service
public class TjrbBpPersonServiceImpl extends BaseServiceImpl<TjrbBpPersonEntity> implements TjrbBpPersonService {

    @Autowired
    private TjrbBpPersonDao tjrbBpPersonDao;

    @Override
    public DataGrid select(PageParam param, TjrbBpPersonEntity tjrbBpPersonEntity) {
        DataGrid data= new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());

        Map parm = new HashMap();

        List lst = tjrbBpPersonDao.selectByParam(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());

        return data;
    }

    @Override
    public List selectAll() {
        return tjrbBpPersonDao.selectAll();
    }

    @Override
    public String add(TjrbBpPersonEntity tjrbBpPersonEntity) {
       int res = tjrbBpPersonDao.insert(tjrbBpPersonEntity);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String update(TjrbBpPersonEntity tjrbBpPersonEntity) {
        int res = tjrbBpPersonDao.updateByPrimaryKey(tjrbBpPersonEntity);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public TjrbBpPersonEntity selectByPrimaryKey(Long key) {
        return tjrbBpPersonDao.selectByPrimaryKey(key);
    }

    @Override
    public boolean delete(String[] ids) {
        return false;
    }
}
