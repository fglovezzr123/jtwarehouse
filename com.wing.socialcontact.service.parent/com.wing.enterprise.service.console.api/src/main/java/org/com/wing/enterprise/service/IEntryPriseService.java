package org.com.wing.enterprise.service;

import java.util.List;
import java.util.Map;

import org.com.wing.enterprise.bean.EntryPrise;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

/**
 * 
 * <p>Title:集团管理 </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author zhangzheng
 * @Date  2017年5月5日 下午4:59:24
 */
public interface IEntryPriseService {
    
    /**
     * 
     * //TODO 查询企服列表
     * @param param
     * @param entryPrise
     * @return
     */
    public DataGrid selEntryPrises(PageParam param, EntryPrise entryPrise,String searchName);
    /**
     * 
     * //TODO 查询企服列表
     * @param param
     * @param entryPrise
     * @return
     */
     List selEntryPrises(EntryPrise entryPrise);

     DataGrid selEntryPrise(PageParam param,String timeSort,String serviceCountSort,String classId,EntryPrise entryPrise,String prov,String city,String searchName);
    
     DataGrid selQuickEntrys(PageParam param,String quickDoorId,String classId,String searchName);

    /**
     * 
     * @param dto
     * @return
     * * 新增
     */
     
    String addEntryPrise(EntryPrise dto,String reconImg,String bannerImg,String entryClass,String entryTags);
    
    public String updateEntryPriseByDto(EntryPrise dto);
    /**
     * 根据id获取
     * @param id
     * @return
     */
    EntryPrise getEntryPriseByid(String id);
    /**
     * 
     * @param dto
     * @return
     * * 修改
     */
    String updateEntryPrise(EntryPrise dto,String reconImg,String bannerImg,String entryClass,String entryTags);


    /**
     * 删除
     * @param ids
     * @return
     */
    public boolean deleteEntryPrise(String[] ids);
    /**
     * 查询客服id
     * //TODO 添加方法功能描述
     * @param id
     * @return
     */
    public String[] selCustomer(String id);
    /**
     * 企服统计
     * //TODO 添加方法功能描述
     * @param map
     * @return
     */
    DataGrid selStaticEntry(PageParam param,EntryPrise dto);
    
    List<Map> selStaticEntryExport(EntryPrise entryPrise);
    
    List<EntryPrise> selAll();
}
