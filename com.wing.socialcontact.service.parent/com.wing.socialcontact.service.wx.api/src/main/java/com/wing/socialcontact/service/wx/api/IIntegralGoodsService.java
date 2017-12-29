package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.service.wx.bean.IntegralGoods;

import java.util.List;
import java.util.Map;

/**
 * @author devil
 */
public interface IIntegralGoodsService {

	List<IntegralGoods> queryList(Map<String, Object> searchMap);

}