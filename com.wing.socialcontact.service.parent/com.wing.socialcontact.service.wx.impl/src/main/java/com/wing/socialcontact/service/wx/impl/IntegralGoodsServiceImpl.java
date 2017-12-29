package com.wing.socialcontact.service.wx.impl;

import com.github.pagehelper.PageHelper;
import com.wing.socialcontact.service.wx.api.IIntegralGoodsService;
import com.wing.socialcontact.service.wx.bean.IntegralGoods;
import com.wing.socialcontact.service.wx.dao.IIntegralGoodsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IntegralGoodsService实现
 *
 * @author Devil
 * @date 2017/10/31 15:04
 */
@Service(value = "integralGoodsService")
public class IntegralGoodsServiceImpl implements IIntegralGoodsService {

    @Autowired
    private IIntegralGoodsDao integralGoodsDao;

    @Override
    public List<IntegralGoods> queryList(Map<String, Object> searchMap) {
        PageHelper.startPage(Integer.valueOf(searchMap.get("pageStart").toString()),
                Integer.valueOf(searchMap.get("pageSize").toString()));
        Map<String, Object> paraMap = new HashMap<>();
        List<IntegralGoods> goodsList = integralGoodsDao.selectList(paraMap);
        return goodsList;
    }

}
