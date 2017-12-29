package org.com.wing.enterprise.service;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryServiceClass;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

/**
 * 
 * //TODO 企服类别管理接口
 * @author sino
 */
public interface IEntryServiceClassService {
    
    /**
     * 条件查询
     * @param param
     * @param role
     * @return
     */
    public DataGrid selectEntryServiceClass(PageParam param,EntryServiceClass entryServiceClass);
    /**
     * 查询所有二级分类
     * //TODO 添加方法功能描述
     * @return
     */
    public List selectSecond();
    /**
     * 
     * //TODO 新增
     * @param entryServiceClass
     * @return
     */
    String addEntryServiceClass(EntryServiceClass entryServiceClass);
    
    /**
     * 
     * //TODO 更新
     * @param entryServiceClass
     * @return
     */
    String updateEntryServiceClass(EntryServiceClass entryServiceClass);
    
    /**
     * 
     * //TODO 根据主键查询
     * @param key
     * @return
     */
    EntryServiceClass selectByPrimaryKey(String key);
    
    /**
     * 
     * //TODO 根据父节点查询
     * @param parentKey
     * @return
     */
    List<Map> selectByParentKey(String parentKey,String searchName,String isShow);
    
    boolean deleteEntryServiceClass(String id);
}
