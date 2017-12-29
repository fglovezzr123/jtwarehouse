package com.wing.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryStaticClick;
import org.com.wing.enterprise.service.IEntryStaticClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.EntryStaticClickDao;

@Service
public class EntryStaticClickServiceImpl extends BaseServiceImpl<EntryStaticClick> implements IEntryStaticClickService{

    @Autowired
    private EntryStaticClickDao  entryStaticClickDao;
    @Override
    public boolean addEntryStaticClick(EntryStaticClick entryStaticClick) {
        int res = entryStaticClickDao.insert(entryStaticClick);
        if(res > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateEntryStaticClick(EntryStaticClick entryStaticClick) {
        if(super.updateByPrimaryKeyCache(entryStaticClick,entryStaticClick.getId())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<EntryStaticClick> selStaticClick(String entryId,String userId) {
        Map map = new HashMap();
        
        map.put("entryId", entryId);
        map.put("userId", userId);
        
        return entryStaticClickDao.selStaticClick(map);
    }

        
}
