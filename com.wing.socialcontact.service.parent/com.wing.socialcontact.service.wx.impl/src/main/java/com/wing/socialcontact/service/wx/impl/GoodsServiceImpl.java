package com.wing.socialcontact.service.wx.impl;

import com.github.pagehelper.PageHelper;
import com.wing.socialcontact.service.wx.api.IGoodsService;
import com.wing.socialcontact.service.wx.bean.Goods;
import com.wing.socialcontact.service.wx.dao.GoodsDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品service实现
 *
 * @author Devil
 * @date 2017/10/31 18:44
 */
@Service(value = "goodsService")
public class GoodsServiceImpl implements IGoodsService {

    @Resource(name = "goodsDao")
    private GoodsDao goodsDao;

    @Override
    public List<Goods> queryList(Map<String, Object> searchMap) {
        PageHelper.startPage(Integer.valueOf(searchMap.get("pageStart").toString()),
                Integer.valueOf(searchMap.get("pageSize").toString()));
        return goodsDao.selectList(searchMap);
    }
}
