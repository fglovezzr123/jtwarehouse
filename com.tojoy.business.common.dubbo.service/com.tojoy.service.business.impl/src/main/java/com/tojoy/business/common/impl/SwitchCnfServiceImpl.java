package com.tojoy.business.common.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tojoy.business.common.api.ITableCommonService;
import com.tojoy.business.common.dao.SwitchCnfDao;
import com.tojoy.business.common.model.PageParam;
import com.tojoy.business.common.model.DataGrid;

import com.tojoy.business.common.api.ISwitchCnfService;
import com.tojoy.business.common.bean.SwitchCnf;
import com.tojoy.business.common.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author wangyansheng
 * @version 1.0
 * @date 2017-11-27
 */
@Service
public class SwitchCnfServiceImpl implements ISwitchCnfService {

    @Resource
    private SwitchCnfDao switchCnfDao;
    @Resource
    private ITableCommonService tableCommonService;

    @Override
    public DataGrid queryByParam(PageParam param, SwitchCnf switchCnf) {
        PageHelper.startPage(param.getPage(), param.getRows());
        List<SwitchCnf> list = switchCnfDao.queryByParam(switchCnf);
        PageInfo<SwitchCnf> page = new PageInfo<SwitchCnf>(list);
        DataGrid data = new DataGrid(page);
        return data;
    }

    @Override
    public int insert(SwitchCnf switchCnf) {

        /* DDL直接触发隐式提交，任何事务回滚的前提是没有commit，
           隐式提交直接给你commit了，后续的rollback一点意义都没有
         所以create不支持回滚*/
        tableCommonService.createTable(switchCnf.getKeyWord());
        switchCnf.setId(UUIDGenerator.getUUID());
        switchCnf.setDeleted(0);
        int n = switchCnfDao.insert(switchCnf);
        return n;
    }

    /**
     * 修改
     *
     * @param switchCnf
     * @return
     * @author wangyansheng
     * @date 2017-11-27
     */
    @Override
    public int update(SwitchCnf switchCnf) {
        int n = switchCnfDao.updateByPrimaryKey(switchCnf);
        return n;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     * @author wangyansheng
     * @date 2017-11-27
     */
    @Override
    public SwitchCnf queryById(String id) {
        return switchCnfDao.selectByPrimaryKey(id);
    }

    @Override
    public SwitchCnf queryByKeyWord(SwitchCnf switchCnf) {
        return switchCnfDao.queryByKeyWord(switchCnf);
    }
}