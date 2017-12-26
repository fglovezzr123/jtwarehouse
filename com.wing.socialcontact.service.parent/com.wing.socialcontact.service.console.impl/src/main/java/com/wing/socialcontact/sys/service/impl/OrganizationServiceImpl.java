package com.wing.socialcontact.sys.service.impl;

import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.Organization;
import com.wing.socialcontact.sys.dao.OrganizationDao;
import com.wing.socialcontact.sys.service.OrganizationService;
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
public class OrganizationServiceImpl extends BaseServiceImpl<Organization> implements OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;

    @Override
    public List<Organization> findAll() {
        return organizationDao.selectAll();
    }

    @Override
    public DataGrid select(PageParam param, Organization organization) {
        return null;
    }

    @Override
    public List selectAll() {
        return organizationDao.selectAll();
    }

    @Override
    public String add(Organization organization) {
        int res = organizationDao.insert(organization);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public String update(Organization organization) {
        int res = organizationDao.updateByPrimaryKey(organization);
        if(res > 0){
            return MsgConfig.MSG_KEY_SUCCESS;
        }else{
            return MsgConfig.MSG_KEY_FAIL;
        }
    }

    @Override
    public Organization selectByPrimaryKey(Long key) {
        return super.selectByPrimaryKeyCache(key,Organization.class);
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag = true;
        for (String id : ids) {
            Organization organization=organizationDao.selectByPrimaryKey(id);
            if(organization!=null){
                organization.setStatus(1);
                if(super.updateByPrimaryKeyCache(organization, id)){
                }else{
                    flag = false;
                }
            }
        }
        return flag;
    }
}
