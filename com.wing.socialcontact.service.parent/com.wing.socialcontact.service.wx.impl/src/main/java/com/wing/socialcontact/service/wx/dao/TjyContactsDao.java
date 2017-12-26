package com.wing.socialcontact.service.wx.dao;

import com.wing.socialcontact.service.wx.bean.TjyContacts;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface TjyContactsDao extends Mapper<TjyContacts> {

    int ignoreInsertContacts(Map map);

    List<TjyContacts> getContacts(Map map);

}
