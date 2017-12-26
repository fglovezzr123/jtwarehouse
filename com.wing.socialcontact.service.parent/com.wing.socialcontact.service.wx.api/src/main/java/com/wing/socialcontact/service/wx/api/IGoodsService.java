package com.wing.socialcontact.service.wx.api;

import com.wing.socialcontact.service.wx.bean.Goods;

import java.util.List;
import java.util.Map;

public interface IGoodsService {

	List<Goods> queryList(Map<String, Object> searchMap);

}
