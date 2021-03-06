package com.wing.socialcontact.sys.dao;

import com.wing.socialcontact.sys.bean.FinanceProductStock;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 12/25/17.
 *
 * @author fenggang
 * @date 12/25/17
 */
@Repository
public interface FinanceProductStockDao extends Mapper<FinanceProductStock> {

    List<Map> selectByParam(Map map);
}
