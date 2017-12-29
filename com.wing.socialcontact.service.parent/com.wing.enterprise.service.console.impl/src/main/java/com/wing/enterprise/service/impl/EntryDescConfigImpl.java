package com.wing.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.com.wing.enterprise.bean.EntryDescConfig;
import org.com.wing.enterprise.service.IEntryDescConfigService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.EntryDescConfigDao;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;

/**
 * 
 * //TODO 图片+链接等统一配置
 * @author sino
 */
@Service
public class EntryDescConfigImpl extends BaseServiceImpl<EntryDescConfig> implements IEntryDescConfigService{
    
    @Resource
    private EntryDescConfigDao entryDescConfigDao;
    
    @Override
    public String addDescConfig(EntryDescConfig entryDescConfig) {
        int res = entryDescConfigDao.insert(entryDescConfig);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String updateDescConfig(EntryDescConfig entryDescConfig) {
        if(super.updateByPrimaryKeyCache(entryDescConfig,entryDescConfig.getId())){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public EntryDescConfig selectByPrimaryKey(String key) {
        return super.selectByPrimaryKeyCache(key, EntryDescConfig.class);
    }


    @Override
    public DataGrid selectDescConfig(PageParam param, EntryDescConfig entryDescConfig) {
        
        DataGrid data= new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());
        
        Map parm = new HashMap();
//        parm.put("BannerName", BannerName);
        /*parm.put("BannerName", DescConfig.getBannerName());
        parm.put("createTime", DescConfig.getCreateTime());*/
        
        List lst = entryDescConfigDao.selectByParam(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        
        return data;
    }

    @Override
    public boolean deleteDescConfig(String[] ids) {
        boolean flag = true;
        for (String id : ids) {
            EntryDescConfig entryDescConfig=entryDescConfigDao.selectByPrimaryKey(id);
            if(entryDescConfig!=null){
                entryDescConfig.setStatus(1);
                 if(super.updateByPrimaryKeyCache(entryDescConfig, id)){
                     
                 }else{
                    flag=false;
                 };
            }
        }
        return flag;
    }
    public List selectDescConfig(EntryDescConfig entryDescConfig){
        
        Map parm = new HashMap();
        parm.put("type", entryDescConfig.getType());
        
        List lst = entryDescConfigDao.selectByParam(parm);
        
        return lst;
    }
}
