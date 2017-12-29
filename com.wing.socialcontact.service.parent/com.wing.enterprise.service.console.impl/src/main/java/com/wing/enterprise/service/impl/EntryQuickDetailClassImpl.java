package com.wing.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.com.wing.enterprise.bean.QuickDetailClass;
import org.com.wing.enterprise.service.IEntryQuickDetailClassService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.EntryQuickDetailClassDao;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;

/**
 * 
 * //TODO 聚合页分类接口实现
 * @author sino
 */
@Service
public class EntryQuickDetailClassImpl extends BaseServiceImpl<QuickDetailClass> implements IEntryQuickDetailClassService{
    
    @Resource
    private EntryQuickDetailClassDao entryQuickDetailClassDao;
    
    @Override
    public String addQuickDetailClass(QuickDetailClass quickDetailClass) {
        int res = entryQuickDetailClassDao.insert(quickDetailClass);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String updateQuickDetailClass(QuickDetailClass quickDetailClass) {
        if(super.updateByPrimaryKeyCache(quickDetailClass,quickDetailClass.getId())){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public QuickDetailClass selectByPrimaryKey(String key) {
        return super.selectByPrimaryKeyCache(key, QuickDetailClass.class);
    }


    @Override
    public DataGrid selectQuickDetailClass(PageParam param, QuickDetailClass quickDetailClass) {
        
        DataGrid data= new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());
        
        Map parm = new HashMap();
        parm.put("classId", quickDetailClass.getClassId());
        parm.put("quickDoorId", quickDetailClass.getQuickDoorId());
        parm.put("createTime", quickDetailClass.getCreateTime());
        
        List lst = entryQuickDetailClassDao.selectByParam(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        
        return data;
    }

    @Override
    public boolean deleteQuickDetailClass(String[] ids) {
        boolean flag = true;
        for (String id : ids) {
            QuickDetailClass quickDetailClass=entryQuickDetailClassDao.selectByPrimaryKey(id);
            if(quickDetailClass!=null){
                quickDetailClass.setStatus(1);
                 if(super.updateByPrimaryKeyCache(quickDetailClass, id)){
                     
                 }else{
                    flag=false;
                 };
            }
        }
        return flag;
    }
    @Override
    public List selectAllQucikDoor() {
        // TODO Auto-generated method stub
        return entryQuickDetailClassDao.selectAllQucikDoor();
    }
    @Override
    public List selectAllClasses() {
        // TODO Auto-generated method stub
        return entryQuickDetailClassDao.selectAllClasses();
    }

    @Override
    public List isExist(QuickDetailClass quickDetailClass) {
        // TODO Auto-generated method stub
        Map parm = new HashMap();
        parm.put("classId", quickDetailClass.getClassId());
        parm.put("quickDoorId", quickDetailClass.getQuickDoorId());
        parm.put("id", quickDetailClass.getId());
        
        List lst = entryQuickDetailClassDao.isExist(parm);
        return lst;
    }

    @Override
    public List<Map> selectFristByQdId(String quickDoorId) {
        return entryQuickDetailClassDao.selectFristByQdId(quickDoorId);
    }
    
    public List<Map> selectClassByQdId(String quickDoorId){
        Map parm = new HashMap();
        parm.put("quickDoorId", quickDoorId);
        return entryQuickDetailClassDao.selectClassByQdId(parm);
    }
}
