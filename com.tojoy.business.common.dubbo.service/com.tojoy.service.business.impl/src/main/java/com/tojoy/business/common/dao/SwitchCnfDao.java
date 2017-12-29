package com.tojoy.business.common.dao;

import com.tojoy.business.common.bean.SwitchCnf;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *
 * @author wangyansheng
 * @date 2017-11-27
 * @version 1.0
 */
@Repository
public interface SwitchCnfDao  extends Mapper<SwitchCnf> {

	List<SwitchCnf> queryByParam(SwitchCnf switchCnf);

	SwitchCnf queryByKeyWord(SwitchCnf switchCnf);
}
