package com.tojoy.service.console.dao;

import java.util.List;

import com.tojoy.service.console.bean.SyRoleAction;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SyRoleActionDao extends Mapper<SyRoleAction> {

	List findactionbyuserid(String userId);

}
