package com.wing.socialcontact.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.TjrbBpPersonProjectEntity;
import com.wing.socialcontact.sys.dao.TjrbBpPersonProjectDao;
import com.wing.socialcontact.sys.service.TjrbBpPersonProjectService;
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
public class TjrbBpPersonProjectServiceImpl extends BaseServiceImpl<TjrbBpPersonProjectEntity> implements TjrbBpPersonProjectService {

    @Autowired
    private TjrbBpPersonProjectDao tjrbBpPersonProjectDao;

    @Override
    public DataGrid select(PageParam param, TjrbBpPersonProjectEntity tjrbBpPersonProjectEntity) {
        DataGrid data= new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());

        Map parm = new HashMap();

        List lst = tjrbBpPersonProjectDao.selectByParam(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());

        return data;
    }

    @Override
    public List selectAll() {
        return tjrbBpPersonProjectDao.selectAll();
    }

    @Override
    public String add(TjrbBpPersonProjectEntity tjrbBpPersonProjectEntity) {
        int res = tjrbBpPersonProjectDao.insert(tjrbBpPersonProjectEntity);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String update(TjrbBpPersonProjectEntity tjrbBpPersonProjectEntity) {
       int res = tjrbBpPersonProjectDao.updateByPrimaryKey(tjrbBpPersonProjectEntity);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public TjrbBpPersonProjectEntity selectByPrimaryKey(Long key) {
        return tjrbBpPersonProjectDao.selectByPrimaryKey(key);
    }

    @Override
    public boolean delete(String[] ids) {
        return false;
    }
}
