package com.wing.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.com.wing.enterprise.bean.EntryClass;
import org.com.wing.enterprise.service.IEntryClassService;
import org.springframework.stereotype.Service;

import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.EntryClassDao;
import com.wing.socialcontact.config.MsgConfig;

@Service
public class EntryClassesServiceImpl extends BaseServiceImpl<EntryClass> implements IEntryClassService{

    @Resource
    private EntryClassDao entryClassDao;
    
    @Override
    public String addEntryClass(EntryClass entryClass) {
        int res = entryClassDao.insert(entryClass);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public List<Map> selClassesByEntryId(String entryId) {
        Map classMap = new HashMap();
        classMap.put("entryId",entryId);
        return entryClassDao.selClassesByEntryId(classMap);
    }
    
}
