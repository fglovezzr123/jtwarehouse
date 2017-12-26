package com.wing.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.com.wing.enterprise.bean.EntryImgs;
import org.com.wing.enterprise.service.IEntryImgsService;
import org.springframework.stereotype.Service;

import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.EntryImgsDao;
import com.wing.socialcontact.config.MsgConfig;
/**
 * 
 * <p>Title:集团图片管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月5日 下午4:01:32
 */
@Service
public class EntryImgsServiceImpl extends BaseServiceImpl<EntryImgs> implements IEntryImgsService {

	@Resource
    private EntryImgsDao entryImgsDao;

    @Override
    public String addEntryImg(EntryImgs entryImg) {
        int res = entryImgsDao.insert(entryImg);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public List<Map> selectByParam(String entryId,int type) {
        Map map = new HashMap();
        map.put("entryId", entryId);
        map.put("type", type);
        return entryImgsDao.selectByParam(map);
    }
}
