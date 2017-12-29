package com.wing.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.com.wing.enterprise.bean.QuickDetailBanner;
import org.com.wing.enterprise.service.IEntryQuickDetailBannerService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.enterprise.service.base.BaseServiceImpl;
import com.wing.enterprise.service.dao.EntryQuickDetailBannerDao;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;

/**
 * 
 * //TODO 聚合页幻灯片接口实现
 * @author sino
 */
@Service
public class EntryQuickDetailBannerImpl extends BaseServiceImpl<QuickDetailBanner> implements IEntryQuickDetailBannerService{
    
    @Resource
    private EntryQuickDetailBannerDao entryQuickDetailBannerDao;
    
    @Override
    public String addQuickDetailBanner(QuickDetailBanner quickDetailBanner) {
        int res = entryQuickDetailBannerDao.insert(quickDetailBanner);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String updateQuickDetailBanner(QuickDetailBanner quickDetailBanner) {
        if(super.updateByPrimaryKeyCache(quickDetailBanner,quickDetailBanner.getId())){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public QuickDetailBanner selectByPrimaryKey(String key) {
        return super.selectByPrimaryKeyCache(key, QuickDetailBanner.class);
    }


    @Override
    public DataGrid selectQuickDetailBanner(PageParam param, QuickDetailBanner quickDetailBanner) {
        
        DataGrid data= new DataGrid();
        PageHelper.startPage(param.getPage(), param.getRows());
        
        Map parm = new HashMap();
        parm.put("name", quickDetailBanner.getName());
        parm.put("quickDoorId", quickDetailBanner.getQuickDoorId());
        parm.put("createTime", quickDetailBanner.getCreateTime());
        
        List lst = entryQuickDetailBannerDao.selectByParam(parm);
        PageInfo page = new PageInfo(lst);
        data.setRows(lst);
        data.setTotal(page.getTotal());
        
        return data;
    }

    @Override
    public boolean deleteQuickDetailBanner(String[] ids) {
        boolean flag = true;
        for (String id : ids) {
            QuickDetailBanner quickDetailBanner=entryQuickDetailBannerDao.selectByPrimaryKey(id);
            if(quickDetailBanner!=null){
                quickDetailBanner.setStatus(1);
                 if(super.updateByPrimaryKeyCache(quickDetailBanner, id)){
                 }else{
                     flag = false;
                 }
            }
        }
        return flag;
    }

    @Override
    public List selectAllQucikDoor() {
        // TODO Auto-generated method stub
        return entryQuickDetailBannerDao.selectAllQucikDoor();
    }
    public List<Map> selectBannerByQdId(String quickDoorId){
        Map parm = new HashMap();
        parm.put("quickDoorId", quickDoorId);
        return entryQuickDetailBannerDao.selectBannerByQdId(parm);
    }
}
