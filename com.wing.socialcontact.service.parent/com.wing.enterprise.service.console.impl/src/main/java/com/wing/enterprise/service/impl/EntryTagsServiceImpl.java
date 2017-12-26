package com.wing.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.com.wing.enterprise.bean.EntryTags;
import org.com.wing.enterprise.service.IEntryTagService;
import org.springframework.stereotype.Service;

import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.EntryTagsDao;
import com.wing.socialcontact.config.MsgConfig;

@Service
public class EntryTagsServiceImpl extends BaseServiceImpl<EntryTags> implements IEntryTagService{

    @Resource
    private EntryTagsDao entryTagsDao;
    
    @Override
    public String addEntryTag(EntryTags entryTag) {
        int res = entryTagsDao.insert(entryTag);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public List<Map> selTagsByEntryId(String entryId) {
        Map map = new HashMap();
        map.put("entryId",entryId);
        return entryTagsDao.selTagsByEntryId(map);
    }
    
}
