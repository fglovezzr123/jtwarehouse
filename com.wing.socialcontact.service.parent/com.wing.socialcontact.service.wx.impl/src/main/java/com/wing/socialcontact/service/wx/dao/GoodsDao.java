package com.wing.socialcontact.service.wx.dao;

import com.wing.socialcontact.service.wx.bean.Goods;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author devil
 */
@Repository
public interface GoodsDao extends Mapper<Goods> {

	List selectList(Map param);

}
