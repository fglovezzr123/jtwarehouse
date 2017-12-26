package org.com.wing.enterprise.service;

import java.util.List;

import org.com.wing.enterprise.bean.EntryDescConfig;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

/**
 * 
 * //TODO 图片+链接等统一配置
 * @author sino
 */
public interface IEntryDescConfigService {
    
    /**
     * 条件查询
     * @param param
     * @param role
     * @return
     */
    public DataGrid selectDescConfig(PageParam param, EntryDescConfig entryDescConfig);
    /**
     * 条件查询
     * @param param
     * @param role
     * @return
     */
    public List selectDescConfig(EntryDescConfig entryDescConfig);
    
    /**
     * 
     * //TODO 新增
     * @param QuickDoor
     * @return
     */
    String addDescConfig(EntryDescConfig entryDescConfig);
    
    /**
     * 
     * //TODO 更新
     * @param QuickDoor
     * @return
     */
    String updateDescConfig(EntryDescConfig entryDescConfig);
    
    /**
     * 
     * //TODO 根据主键查询
     * @param key
     * @return
     */
    EntryDescConfig selectByPrimaryKey(String key);
    
    /**
     * 删除
     * //TODO 添加方法功能描述
     * @param id
     * @return
     */
    boolean deleteDescConfig(String[] ids);
}
