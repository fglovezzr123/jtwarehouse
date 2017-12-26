package com.wing.socialcontact.sys.dao;

import com.wing.socialcontact.sys.bean.OrgConsultant;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 12/26/17.
 *
 * @author fenggang
 * @date 12/26/17
 */
@Repository
public interface OrgConsultantDao extends Mapper<OrgConsultant> {

    List<Map> selectByParam(Map map);
}
