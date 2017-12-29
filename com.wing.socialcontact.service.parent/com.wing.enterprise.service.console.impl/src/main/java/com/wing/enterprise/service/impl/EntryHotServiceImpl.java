package com.wing.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.com.wing.enterprise.bean.EntryHotService;
import org.com.wing.enterprise.service.IEntryHotService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.EntryHotServiceDao;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;

/**
 * 
 * //TODO 热门服务
 * @author sino
 */
@Service
public class EntryHotServiceImpl extends BaseServiceImpl<EntryHotService> implements IEntryHotService{
    
    @Resource
    private EntryHotServiceDao entryHotServiceDao;
    
    @Override
    public String addHotService(EntryHotService entryHotService) {
        int res = entryHotServiceDao.insert(entryHotService);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String updateHotService(EntryHotService entryHotService) {
        if(super.updateByPrimaryKeyCache(entryHotService,entryHotService.getId())){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public EntryHotService selectByPrimaryKey(String key) {
        return super.selectByPrimaryKeyCache(key, EntryHotService.class);
    }


    @Override
    public DataGrid selectHotService(PageParam param, EntryHotService entryHotService) {
        
        DataGrid data= new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());
        
        Map parm = new HashMap();
//        parm.put("BannerName", BannerName);
        /*parm.put("BannerName", DescConfig.getBannerName());
        parm.put("createTime", DescConfig.getCreateTime());*/
        
        List lst = entryHotServiceDao.selectByParam(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        
        return data;
    }

    @Override
    public boolean deleteDHotService(String[] ids) {
        boolean flag = true;
        for (String id : ids) {
            if(super.deleteByPrimaryKeyCache(id, EntryHotService.class)){
                
            }else{
                flag = false;
            }
        }
        return flag;
    }

}
