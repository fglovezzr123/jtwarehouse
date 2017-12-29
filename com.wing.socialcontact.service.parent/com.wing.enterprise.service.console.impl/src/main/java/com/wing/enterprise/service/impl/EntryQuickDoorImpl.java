package com.wing.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.com.wing.enterprise.bean.QuickDoor;
import org.com.wing.enterprise.service.IEntryQuickDoorService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.EntryQuickDoorDao;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;

/**
 * 
 * //TODO 企服快捷入口接口实现
 * @author sino
 */
@Service
public class EntryQuickDoorImpl extends BaseServiceImpl<QuickDoor> implements IEntryQuickDoorService{
    
    @Resource
    private EntryQuickDoorDao entryQuickDoorDao;
    
    @Override
    public String addQuickDoor(QuickDoor quickDoor) {
        int res = entryQuickDoorDao.insert(quickDoor);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String updateQuickDoor(QuickDoor quickDoor) {
        if(super.updateByPrimaryKeyCache(quickDoor,quickDoor.getId())){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public QuickDoor selectByPrimaryKey(String key) {
        return super.selectByPrimaryKeyCache(key, QuickDoor.class);
    }


    @Override
    public DataGrid selectQuickDoor(PageParam param, QuickDoor quickDoor) {
        
        DataGrid data= new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());
        
        Map parm = new HashMap();
        parm.put("quickName", quickDoor.getQuickName());
        /*parm.put("className", QuickDoor.getClassName());
        parm.put("createTime", QuickDoor.getCreateTime());*/
        
        List lst = entryQuickDoorDao.selectByParam(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        
        return data;
    }

    public List selectAllQuickDoor(){
        return entryQuickDoorDao.selectByParam(null);
    }
    
    @Override
    public boolean deleteQuickDoor(String[] ids) {
        boolean flag = true;
        for (String id : ids) {
            QuickDoor quickDoor=entryQuickDoorDao.selectByPrimaryKey(id);
            if(quickDoor!=null){
                quickDoor.setQuickStatus(1);
                 if(super.updateByPrimaryKeyCache(quickDoor, id)){
                     
                 }else{
                    flag=false;
                 };
            }
        }
        return flag;
    }
    public List selectH5IndexQuickDoors(int num){
        Map parm = new HashMap();
        parm.put("num",num);
        return entryQuickDoorDao.selectH5IndexQuickDoors(parm);
    }
}
