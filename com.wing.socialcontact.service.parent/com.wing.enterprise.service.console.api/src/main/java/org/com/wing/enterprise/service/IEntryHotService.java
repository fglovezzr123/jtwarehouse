package org.com.wing.enterprise.service;

import org.com.wing.enterprise.bean.EntryHotService;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

/**
 * 
 * //TODO 热门服务
 * @author sino
 */
public interface IEntryHotService {
    
    /**
     * 条件查询
     * @param param
     * @param role
     * @return
     */
    public DataGrid selectHotService(PageParam param, EntryHotService entryHotService);
    
    /**
     * 
     * //TODO 新增
     * @param QuickDoor
     * @return
     */
    String addHotService(EntryHotService entryHotService);
    
    /**
     * 
     * //TODO 更新
     * @param QuickDoor
     * @return
     */
    String updateHotService(EntryHotService entryHotService);
    
    /**
     * 
     * //TODO 根据主键查询
     * @param key
     * @return
     */
    EntryHotService selectByPrimaryKey(String key);
    
    /**
     * 删除
     * //TODO 添加方法功能描述
     * @param id
     * @return
     */
    boolean deleteDHotService(String[] ids);
}
