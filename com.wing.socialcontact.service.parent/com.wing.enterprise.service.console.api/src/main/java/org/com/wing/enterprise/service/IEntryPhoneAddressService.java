package org.com.wing.enterprise.service;

import java.util.List;

import org.com.wing.enterprise.bean.EntryPhoneAdress;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;

/**
 * 通讯录上传
 * 
 * 
 * @ClassName: IEntryClassService
 * @Description: TODO
 * @author: sino
 * @date:2017年5月7日
 */
public interface IEntryPhoneAddressService {

    
    /**
     * 条件查询
     * @param param
     * @param role
     * @return
     */
    public DataGrid selectPhoneAdress(PageParam param, EntryPhoneAdress entryPhoneAdress);
    /**
     * 条件查询
     * @param param
     * @param role
     * @return
     */
    public List selectPhoneAdress(EntryPhoneAdress entryPhoneAdress);
    /**
     * 
     * //TODO 新增
     * @param QuickDoor
     * @return
     */
    String addPhoneAdress(EntryPhoneAdress entryPhoneAdress);
    
    /**
     * 批量保存
     * //TODO 添加方法功能描述
     * @param entryPhoneAdressList
     * @return
     */
    boolean insertPhoneAdressBatch(List<EntryPhoneAdress > entryPhoneAdressList);
    
    /**
     * 
     * //TODO 更新
     * @param QuickDoor
     * @return
     */
    String updatePhoneAdress(EntryPhoneAdress entryPhoneAdress);
    
    /**
     * 
     * //TODO 根据主键查询
     * @param key
     * @return
     */
    EntryPhoneAdress selectByPrimaryKey(String key);
    
    /**
     * 删除
     * //TODO 添加方法功能描述
     * @param id
     * @return
     */
    String deletePhoneAdress(String[] ids);
}
