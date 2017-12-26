package com.wing.socialcontact.service.wx.dao;

import com.wing.socialcontact.service.wx.bean.IntegralGoods;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author devil
 */
@Repository
public interface IIntegralGoodsDao extends Mapper<IntegralGoods> {

	List selectList(Map param);

}
