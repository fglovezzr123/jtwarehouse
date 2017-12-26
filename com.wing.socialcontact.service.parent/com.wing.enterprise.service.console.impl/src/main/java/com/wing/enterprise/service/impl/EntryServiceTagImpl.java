package com.wing.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.com.wing.enterprise.bean.EntryServiceTag;
import org.com.wing.enterprise.service.IEntryServiceTagService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.EntryServiceTagDao;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;

/**
 * 
 * //TODO 企服标签管理接口实现
 * @author sino
 */
@Service
public class EntryServiceTagImpl extends BaseServiceImpl<EntryServiceTag> implements IEntryServiceTagService{
    
    @Resource
    private EntryServiceTagDao entryServiceTagDao;
    
    @Override
    public String addEntryServiceTag(EntryServiceTag entryServiceTag) {
        int res = entryServiceTagDao.insert(entryServiceTag);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String updateEntryServiceTag(EntryServiceTag entryServiceTag) {
        if(super.updateByPrimaryKeyCache(entryServiceTag,entryServiceTag.getId())){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public EntryServiceTag selectByPrimaryKey(String key) {
        return super.selectByPrimaryKeyCache(key, EntryServiceTag.class);
    }


    @Override
    public DataGrid selectEntryServiceTag(PageParam param, EntryServiceTag entryServiceTag) {
        
        DataGrid data= new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());
        
        Map parm = new HashMap();
        parm.put("name", entryServiceTag.getName());
        parm.put("createTime", entryServiceTag.getCreateTime());
        
        List lst = entryServiceTagDao.selectByParam(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        
        return data;
    }

    @Override
    public boolean deleteEntryServiceTag(String[] ids) {
        boolean flag = true;
        for(String id:ids){
            if(StringUtils.isNotBlank(id)){
                EntryServiceTag entryServiceTag=entryServiceTagDao.selectByPrimaryKey(id);
                if(entryServiceTag !=null){
                    entryServiceTag.setStatus(1);
                    if(super.updateByPrimaryKeyCache(entryServiceTag, id)){
                        
                    }else{
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }
    public List selectAllTags(){
        return entryServiceTagDao.selectAllTags();
    }

    /**
     * 热门服务列表查询
     */
	@Override
	public DataGrid selectHotByParam(PageParam param,
			EntryServiceTag entryServiceTag) {
		DataGrid data= new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());
        
        Map parm = new HashMap();
        parm.put("name", entryServiceTag.getName());
        parm.put("createTime", entryServiceTag.getCreateTime());
        
        List lst = entryServiceTagDao.selectHotByParam(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        
        return data;
	}

	@Override
	public List selectHotServiceBySize(Integer size) {
		// TODO Auto-generated method stub
		Map parm = new HashMap();
        parm.put("size", size);
		return entryServiceTagDao.selectHotServiceBySize(parm);
	}
}
