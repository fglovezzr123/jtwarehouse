package org.com.wing.enterprise.service;

import java.util.List;

import org.com.wing.enterprise.bean.EntryServiceTag;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

/**
 * 
 * //TODO 企服标签管理接口
 * @author sino
 */
public interface IEntryServiceTagService {
    
    /**
     * 条件查询
     * @param param
     * @param role
     * @return
     */
    public DataGrid selectEntryServiceTag(PageParam param,EntryServiceTag entryServiceTag);
    
    /**
     * 
     * //TODO 查询所有标签
     * @return
     */
    public List selectAllTags();
    /**
     * 
     * //TODO 新增
     * @param EntryServiceTag
     * @return
     */
    String addEntryServiceTag(EntryServiceTag entryServiceTag);
    
    /**
     * 
     * //TODO 更新
     * @param EntryServiceTag
     * @return
     */
    String updateEntryServiceTag(EntryServiceTag entryServiceTag);
    
    /**
     * 
     * //TODO 根据主键查询
     * @param key
     * @return
     */
    EntryServiceTag selectByPrimaryKey(String key);
    
    /**
     * 删除
     * //TODO 添加方法功能描述
     * @param id
     * @return
     */
    boolean deleteEntryServiceTag(String[] ids);

    /**
     * 热门服务列表查询
     * @param param
     * @param entryServiceTag
     * @return
     */
	public DataGrid selectHotByParam(PageParam param,
			EntryServiceTag entryServiceTag);
	/**
	 * 获取前size条未删除的热门服务
	 * @param size
	 * @return
	 */
	public List selectHotServiceBySize(Integer size);
}
