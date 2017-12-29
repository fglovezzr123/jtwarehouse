package org.com.wing.enterprise.service;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.QuickDetailClass;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

/**
 * 
 * //TODO 聚合页分类管理接口
 * @author sino
 */
public interface IEntryQuickDetailClassService {
    
    /**
     * 条件查询
     * @param param
     * @param role
     * @return
     */
    public DataGrid selectQuickDetailClass(PageParam param,QuickDetailClass quickDetailClass);
    /**
     * 
     * //TODO 获取所有的幻灯片所属的快捷入口
     * @return
     */
    public List selectAllQucikDoor();
    
    /**
     * 
     * //TODO 获取所有幻灯片所属的分类
     * @return
     */
    public List selectAllClasses();
    /**
     * 
     * //TODO 根据分类ID和所属位置ID查询是否存在
     * @return
     */
    public List isExist(QuickDetailClass quickDetailClass);
    /**
     * 
     * //TODO 新增
     * @param QuickDoor
     * @return
     */
    String addQuickDetailClass(QuickDetailClass quickDetailClass);
    
    /**
     * 
     * //TODO 更新
     * @param QuickDoor
     * @return
     */
    String updateQuickDetailClass(QuickDetailClass quickDetailClass);
    
    /**
     * 
     * //TODO 根据主键查询
     * @param key
     * @return
     */
    QuickDetailClass selectByPrimaryKey(String key);
    
    /**
     * 删除
     * //TODO 添加方法功能描述
     * @param id
     * @return
     */
    boolean deleteQuickDetailClass(String[] ids);
    
    /**
     * 
     * //TODO 查询快捷入口第一个分类
     * @param key
     * @return
     */
    List<Map> selectFristByQdId(String quickDoorId);
    /**
     * 根绝快捷入口查询分类
     * //TODO 添加方法功能描述
     * @param quickDoorId
     * @return
     */
    List<Map> selectClassByQdId(String quickDoorId);
}
