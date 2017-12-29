package com.wing.socialcontact.sys.service.impl;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.OrgConsultant;
import com.wing.socialcontact.sys.dao.OrgConsultantDao;
import com.wing.socialcontact.sys.service.OrgConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fenggang on 12/26/17.
 *
 * @author fenggang
 * @date 12/26/17
 */
@Service
public class OrgConsultantServiceImpl extends BaseServiceImpl<OrgConsultant> implements OrgConsultantService {

    @Autowired
    private OrgConsultantDao orgConsultantDao;

    @Override
    public List selectByOrgNo(String orgNo) {
        OrgConsultant map = new OrgConsultant();
        map.setOrgNo(orgNo);
        return orgConsultantDao.select(map);
    }

    @Override
    public DataGrid select(PageParam param, OrgConsultant orgConsultant) {
        return null;
    }

    @Override
    public List selectAll() {
        return orgConsultantDao.selectAll();
    }

    @Override
    public String add(OrgConsultant orgConsultant) {
        int res = orgConsultantDao.insert(orgConsultant);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String update(OrgConsultant orgConsultant) {
        int res = orgConsultantDao.updateByPrimaryKey(orgConsultant);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public OrgConsultant selectByPrimaryKey(Long key) {
        return super.selectByPrimaryKeyCache(key,OrgConsultant.class);
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;
        for (String id : ids) {
            OrgConsultant orgConsultant=orgConsultantDao.selectByPrimaryKey(id);
            if(orgConsultant!=null){
                orgConsultant.setStatus(1);
                if(super.updateByPrimaryKeyCache(orgConsultant, id)){
                }else{
                    flag = false;
                }
            }
        }
        return flag;
    }
}
