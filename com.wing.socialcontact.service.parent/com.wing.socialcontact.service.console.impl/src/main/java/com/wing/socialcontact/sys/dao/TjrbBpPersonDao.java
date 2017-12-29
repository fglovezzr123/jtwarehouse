package com.wing.socialcontact.sys.dao;

import com.wing.socialcontact.sys.bean.TjrbBpPersonEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 12/28/17.
 *
 * @author fenggang
 * @date 12/28/17
 */
@Repository
public interface TjrbBpPersonDao extends Mapper<TjrbBpPersonEntity> {

    List<Map> selectByParam(Map map);
}
