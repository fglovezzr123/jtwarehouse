package org.com.wing.enterprise.service;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.QuickDetailBanner;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

/**
 * 
 * //TODO 聚合页幻灯片管理接口
 * @author sino
 */
public interface IEntryQuickDetailBannerService {
    
    /**
     * 条件查询
     * @param param
     * @param role
     * @return
     */
    public DataGrid selectQuickDetailBanner(PageParam param,QuickDetailBanner quickDetailBanner);
    
    /**
     * 
     * //TODO 获取所有的幻灯片所属的快捷入口
     * @return
     */
    public List selectAllQucikDoor();
    /**
     * 
     * //TODO 新增
     * @param QuickDoor
     * @return
     */
    String addQuickDetailBanner(QuickDetailBanner quickDetailBanner);
    
    /**
     * 
     * //TODO 更新
     * @param QuickDoor
     * @return
     */
    String updateQuickDetailBanner(QuickDetailBanner quickDetailBanner);
    
    /**
     * 
     * //TODO 根据主键查询
     * @param key
     * @return
     */
    QuickDetailBanner selectByPrimaryKey(String key);
    
    /**
     * 删除
     * //TODO 添加方法功能描述
     * @param id
     * @return
     */
    boolean deleteQuickDetailBanner(String[] ids);
    
    List<Map> selectBannerByQdId(String quickDoorId);
}
