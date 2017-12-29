package com.wing.socialcontact.sys.dao;

import com.wing.socialcontact.sys.bean.TjrbInvestProduct;
import com.wing.socialcontact.sys.bean.TjrbInvestProductInsure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author fenggang
 */
@Repository
public interface TjrbInvestProductDao extends tk.mybatis.mapper.common.Mapper<TjrbInvestProduct> {

	List selectByParam(Map parm);
	
}
