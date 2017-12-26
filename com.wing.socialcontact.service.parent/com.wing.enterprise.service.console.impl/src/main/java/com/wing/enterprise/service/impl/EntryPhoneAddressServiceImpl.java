package com.wing.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryPhoneAdress;
import org.com.wing.enterprise.service.IEntryPhoneAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.EntryPhoneAdressDao;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;

@Service
public class EntryPhoneAddressServiceImpl extends BaseServiceImpl<EntryPhoneAdress> implements IEntryPhoneAddressService {

    @Autowired
    private EntryPhoneAdressDao entryPhoneAdressDao;
    
    @Override
    public DataGrid selectPhoneAdress(PageParam param, EntryPhoneAdress entryPhoneAdress) {
        DataGrid data=new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());
        Map parm = new HashMap();
        parm.put("ssUserId", entryPhoneAdress.getSsUserId());
        parm.put("ssUserName", entryPhoneAdress.getSsUserName());
        List<Map> lst=entryPhoneAdressDao.selectPhoneAdress(parm);

        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        return data;
    }
    public List selectPhoneAdress(EntryPhoneAdress entryPhoneAdress){
        Map parm = new HashMap();
        parm.put("ssUserId", entryPhoneAdress.getSsUserId());
        parm.put("ssUserName", entryPhoneAdress.getSsUserName());
        List<Map> lst=entryPhoneAdressDao.selectPhoneAdress(parm);
        return lst;
    }
    @Override
    public String addPhoneAdress(EntryPhoneAdress entryPhoneAdress) {
        int res = entryPhoneAdressDao.insert(entryPhoneAdress);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public boolean insertPhoneAdressBatch(List<EntryPhoneAdress> entryPhoneAdressList) {
       return entryPhoneAdressDao.insertPhoneAdressBatch(entryPhoneAdressList);
    }

    @Override
    public String updatePhoneAdress(EntryPhoneAdress entryPhoneAdress) {
        int res = entryPhoneAdressDao.updateByPrimaryKey(entryPhoneAdress);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public EntryPhoneAdress selectByPrimaryKey(String key) {
        return super.selectByPrimaryKeyCache(key, EntryPhoneAdress.class);
    }

    @Override
    public String deletePhoneAdress(String[] ids) {
        for(String id : ids){
            super.deleteByPrimaryKeyCache(id, EntryPhoneAdress.class);
        }
        return MsgConfig.MSG_KEY_SUCCESS;
    }

}
