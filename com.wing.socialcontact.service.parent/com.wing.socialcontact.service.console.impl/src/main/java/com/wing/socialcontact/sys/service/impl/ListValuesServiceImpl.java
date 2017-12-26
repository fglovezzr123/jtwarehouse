/**  
 * @Project: tjy
 * @Title: ListValuesServiceImpl.java
 * @Package com.wing.socialcontact.sys.service.impl
 * @date 2016-4-15 下午2:36:49
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.ListValues;
import com.wing.socialcontact.sys.dao.ListValuesDao;
import com.wing.socialcontact.sys.service.IListValuesService;
import com.wing.socialcontact.sys.service.ISystemLogService;

/**
 * 
 * 类名：ListValuesServiceImpl
 * 功能：字典值管理 业务层实现
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-4-15 下午2:36:49
 *
 */
@Service
public class ListValuesServiceImpl extends BaseServiceImpl<ListValues> implements IListValuesService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:ListValues:\" + ";
		
	@Resource
	private ListValuesDao dao;
	@Resource
	private ISystemLogService systemLogServiceImpl;
	
	public DataGrid selectListValues(PageParam param,ListValues lv){
		DataGrid data=new DataGrid();
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("listValue", lv.getListValue());
		parm.put("listType", lv.getListType());
		parm.put("orderStr", orderStr);
		List lst = dao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
		
	}

	
	public String addListValues(ListValues lv){
		
		//Object obj=dao.findOne("from ListValues where listType=? and  listValue=? ",lv.getListType(),lv.getListValue());
		ListValues parm = new ListValues();
		parm.setListType(lv.getListType());
		parm.setListValue(lv.getListValue());
		parm.setDeleted(0);
		List lst = dao.select(parm);
		if(lst.size()==0){
			int res =dao.insert(lv);
			if(res>0){
				systemLogServiceImpl.saveLog("添加字典值", "类型:"+lv.getListType()+",字典值:"+lv.getListValue());
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.listvalue.unique";//此字典值已有
		}
	}

	public String updateListValues(ListValues lv){
		//Object obj=dao.findOne("from ListValues where id!=? and listType=? and  listValue=? ",lv.getId(),lv.getListType(),lv.getListValue());
		ListValues parm = new ListValues();
		parm.setListType(lv.getListType());
		parm.setListValue(lv.getListValue());
		parm.setDeleted(0);
		ListValues obj = dao.selectOne(parm);
		if(obj==null || obj.getId().equals(lv.getId())){
			if(super.updateByPrimaryKeyCache(lv,lv.getId())){
				systemLogServiceImpl.saveLog("修改字典值", "类型:"+lv.getListType()+",字典值:"+lv.getListValue());
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.listvalue.unique";//此字典值已有
		}
	}
	

	public boolean deleteListValues(String[] ids){
		//等待删除的对象集合
		List<Object> c=new ArrayList<Object>();
		int count = 0;
		for(String id:ids){
			ListValues lv=dao.selectByPrimaryKey(id);
			if(lv!=null){
				systemLogServiceImpl.saveLog("删除字典值", "字典值:"+lv.getListValue());
				c.add(lv);
				//删除缓存
				//super.deleteByPrimaryKeyCache(lv.getId(), ListValues.class);
				lv.setDeleted(1);
				super.updateByPrimaryKeyCache(lv, lv.getId());
				count++;
			}
		}
		return count>0;
	}

	public List<ListValues> selectAllListValues(){
		return dao.selectAll();
	}
	

	public List selectListByType(Integer type){
		Map parm = new HashMap();
		parm.put("listType", type);
		List lst = dao.selectByParam(parm);
		return lst;
		
	}


	public ListValues selectByPrimaryKey(String id) {
		return super.selectByPrimaryKeyCache(id, ListValues.class);
	}


	@Override
	public List selectListByType(Integer type, String value) {
		Map parm = new HashMap();
		parm.put("listType", type);
		parm.put("listValue", value);
		List lst = dao.selectByParam(parm);
		return lst;
	}
}
