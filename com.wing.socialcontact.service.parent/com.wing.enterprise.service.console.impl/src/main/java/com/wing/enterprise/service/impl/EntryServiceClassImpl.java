package com.wing.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.com.wing.enterprise.bean.EntryServiceClass;
import org.com.wing.enterprise.service.IEntryServiceClassService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.EntryServiceClassDao;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;

/**
 * 
 * //TODO 企服类别管理接口实现
 * @author sino
 */
@Service
public class EntryServiceClassImpl extends BaseServiceImpl<EntryServiceClass> implements IEntryServiceClassService{
    
    @Resource
    private EntryServiceClassDao entryServiceClassDao;
    
    @Override
    public String addEntryServiceClass(EntryServiceClass entryServiceClass) {
        int res = entryServiceClassDao.insert(entryServiceClass);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String updateEntryServiceClass(EntryServiceClass entryServiceClass) {
        if(super.updateByPrimaryKeyCache(entryServiceClass,entryServiceClass.getId())){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public EntryServiceClass selectByPrimaryKey(String key) {
        return super.selectByPrimaryKeyCache(key, EntryServiceClass.class);
    }

    @Override
    public List<Map> selectByParentKey(String parentKey,String searchName,String isShow) {
        Map parm = new HashMap();
        parm.put("parentId", parentKey);
        parm.put("searchName", searchName);
        parm.put("isShow", isShow);
       return entryServiceClassDao.selectByParam(parm);
    }

    @Override
    public DataGrid selectEntryServiceClass(PageParam param, EntryServiceClass entryServiceClass) {
        
        DataGrid data= new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());
        
        Map parm = new HashMap();
        parm.put("parentId", entryServiceClass.getParentId());
        parm.put("className", entryServiceClass.getClassName());
        parm.put("createTime", entryServiceClass.getCreateTime());
        
        List lst = entryServiceClassDao.selectByParam(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        
        return data;
    }

    @Override
    public boolean deleteEntryServiceClass(String id) {
        boolean flag = true;
        EntryServiceClass entryServiceClass=entryServiceClassDao.selectByPrimaryKey(id);
        if(entryServiceClass!=null){
            entryServiceClass.setStatus(1);
             if(super.updateByPrimaryKeyCache(entryServiceClass, id)){
                 
             }else{
                flag=false;
             };
        }
        return flag;
    }
    public List selectSecond(){
        return entryServiceClassDao.selectSecond();
    }
}
