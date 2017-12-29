package com.wing.socialcontact.sys.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wing.socialcontact.sys.bean.SyRoleAction;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SyRoleActionDao extends Mapper<SyRoleAction> {

	List findactionbyuserid(String userId);

}
