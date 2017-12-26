package org.com.wing.enterprise.service;

import java.util.List;

import org.com.wing.enterprise.bean.QuickDoor;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

/**
 * 
 * //TODO 企服快捷入口管理接口
 * @author sino
 */
public interface IEntryQuickDoorService {
    
    /**
     * 条件查询
     * @param param
     * @param role
     * @return
     */
    public DataGrid selectQuickDoor(PageParam param,QuickDoor quickDoor);
    
    /**
     * 
     * //TODO 获取所有快捷入口
     * @return
     */
    public List selectAllQuickDoor();
    /**
     * 
     * //TODO 获取快捷入口
     * @return
     */
    public List selectH5IndexQuickDoors(int num);
    
    /**
     * 
     * //TODO 新增
     * @param QuickDoor
     * @return
     */
    String addQuickDoor(QuickDoor quickDoor);
    
    /**
     * 
     * //TODO 更新
     * @param QuickDoor
     * @return
     */
    String updateQuickDoor(QuickDoor quickDoor);
    
    /**
     * 
     * //TODO 根据主键查询
     * @param key
     * @return
     */
    QuickDoor selectByPrimaryKey(String key);
    
    /**
     * 删除
     * //TODO 添加方法功能描述
     * @param id
     * @return
     */
    boolean deleteQuickDoor(String[] ids);
}
