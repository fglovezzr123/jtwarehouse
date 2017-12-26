package com.wing.socialcontact.service.wx.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.service.wx.api.IKeywordsService;
import com.wing.socialcontact.service.wx.bean.Keywords;
import com.wing.socialcontact.service.wx.dao.KeywordsDao;
import com.wing.socialcontact.util.BeanMapUtils;
import com.wing.socialcontact.util.DateUtil;
import com.wing.socialcontact.util.ServletUtil;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
@Service
public class KeywordsServiceImpl implements IKeywordsService{
	@Resource
	private KeywordsDao keywordsDao;
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 */
	public int insertKeywords(Keywords t) {
		t.setCreateTime(new Date());
		t.setUserId(StringUtils.isBlank(t.getUserId())?"-1":t.getUserId());
		return keywordsDao.insert(t);
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int updateKeywords(Keywords t) {
		return keywordsDao.updateByPrimaryKey(t);
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int updateSelectiveKeywords(Keywords t) {
		return keywordsDao.updateByPrimaryKeySelective(t);
	}
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int deleteKeywords(Keywords t) {
		return keywordsDao.delete(t);
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public List<Keywords> queryKeywords(Keywords t) {
		return keywordsDao.select(t);
	}
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public Keywords getKeywords(String id) {
		return keywordsDao.selectByPrimaryKey(id);
	}
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllKeywords(PageParam param, Keywords t) {
		DataGrid data=new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());
		List<Keywords> list = keywordsDao.selectByParam(BeanMapUtils.toMap(t));
		PageInfo<Keywords> page = new PageInfo<Keywords>(list);
		data.setRows(list);
		data.setTotal(page.getTotal());
		return data;
	}
	/**
	 * top关键词查询
	 * @param keywrods
	 * @param types
	 * @param top
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllTop(int types,int top) {
		DataGrid data=new DataGrid();
		PageHelper.startPage(1, top<=0||top>20?5:top);
		Keywords t = new Keywords();
		t.setTypes(types);
		t.setExtProp("gecreateTime", DateUtil.string2Date(DateUtil.currentDayDateStartToString(DateUtil.currentDateToString())));
		
		List<Keywords> list = keywordsDao.selectTop(BeanMapUtils.toMap(t));
		PageInfo<Keywords> page = new PageInfo<Keywords>(list);
		data.setRows(list);
		data.setTotal(page.getTotal());
		return data;
	}
}