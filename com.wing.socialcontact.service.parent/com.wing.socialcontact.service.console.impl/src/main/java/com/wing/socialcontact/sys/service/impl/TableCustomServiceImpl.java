/**  
 * @Project: tjy
 * @Title: TableCustomServiceImpl.java
 * @Package com.wing.socialcontact.sys.service.impl
 * @date 2016-6-17 上午11:57:51
 * @Copyright: 2016 
 */
package com.wing.socialcontact.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.sys.bean.SyTableCustom;
import com.wing.socialcontact.sys.dao.TableCustomDao;
import com.wing.socialcontact.sys.service.ITableCustomService;

/**
 * 
 * 类名：TableCustomServiceImpl
 * 功能：
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-6-17 上午11:57:51
 *
 */
@Service
public class TableCustomServiceImpl extends BaseServiceImpl<SyTableCustom> implements ITableCustomService{
	
	@Resource
	private TableCustomDao dao;
	
	public DataGrid selectTableCustoms(PageParam param,SyTableCustom t){
		DataGrid data=new DataGrid();
//		StringBuffer sb=new StringBuffer("from SyTableCustom t where 1=1 ");
//		List list=new ArrayList();
//		//查询条件
//		if(t.getTbType()!=null){
//			sb.append(" and t.tbType = ? ");
//			list.add(t.getTbType());	
//		}
//		if(StringUtils.isNotBlank(t.getFieldName())){
//			sb.append(" and t.fieldName like ? ");
//			list.add("%"+t.getFieldName()+"%");
//		}
//		if(StringUtils.isNotBlank(t.getFieldTitle())){
//			sb.append(" and t.fieldTitle like ? ");
//			list.add("%"+t.getFieldTitle()+"%");
//		}
//		if(StringUtils.isNotBlank(t.getFieldAnotherTitle())){
//			sb.append(" and t.fieldAnotherTitle like ? ");
//			list.add("%"+t.getFieldAnotherTitle()+"%");
//		}
//		
//		data.setTotal((Long)dao.findUniqueOne("select count(*)"+sb,list));
//		
//		//排序规则
//		if(StringUtils.isNotBlank(param.getSort())){
//			
//			param.appendOrderBy(sb);//排序
//			
//		}else{
//			sb.append(" order by t.fieldSort asc");
//		}
//		data.setRows(dao.findPage(sb.toString(),param.getPage(),param.getRows(),list));
		
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("tbType", t.getTbType());
		parm.put("fieldName", t.getFieldName());
		parm.put("fieldTitle", t.getFieldTitle());
		parm.put("fieldAnotherTitle", t.getFieldAnotherTitle());
		parm.put("orderStr", orderStr);
		List lst = dao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
		
	}
	
	public List<SyTableCustom> selectTableCustom(short type){
		SyTableCustom parm = new SyTableCustom();
		parm.setTbType(type);
		return dao.select(parm);
		//return dao.find("from SyTableCustom t where t.tbType=? order by t.fieldSort asc",type);
	}
	
	public String addTableCustom(SyTableCustom tc){

		SyTableCustom parm = new SyTableCustom();
		parm.setTbType(tc.getTbType());
		parm.setFieldName(tc.getFieldName());
		List lst = dao.select(parm);
		//Object obj=dao.findOne("from SyTableCustom t where t.tbType=? and  t.fieldName=? ",tc.getTbType(),tc.getFieldName());
		if(lst.size()==0){
			return dao.insert(tc)>0?MsgConfig.MSG_KEY_SUCCESS:MsgConfig.MSG_KEY_FAIL;
		}else{
			return "msg.SyTableCustom.unique";//已有此字段
		}
	}
	public String updateDevTableCustom(SyTableCustom tc){
		//Object obj=dao.findOne("from SyTableCustom t where t.tbType=? and  t.fieldName=? and t.id!=?",tc.getTbType(),tc.getFieldName(),tc.getId());
		SyTableCustom parm = new SyTableCustom();
		parm.setTbType(tc.getTbType());
		parm.setFieldName(tc.getFieldName());
		SyTableCustom obj = dao.selectOne(parm);
		if(obj==null || obj.getId().equals(tc.getId())){
			return super.updateByPrimaryKeyCache(tc,tc.getId())?MsgConfig.MSG_KEY_SUCCESS:MsgConfig.MSG_KEY_FAIL;
		}else{
			return "msg.SyTableCustom.unique";//已有此字段
		}
	}
	public String updateTableCustom(SyTableCustom tc){
		SyTableCustom oldtc=dao.selectByPrimaryKey(tc.getId());
		if(oldtc==null){
			return MsgConfig.MSG_KEY_NODATA;
		}
		oldtc.setFieldSort(tc.getFieldSort());
		oldtc.setFieldAnotherTitle(tc.getFieldAnotherTitle());
		oldtc.setIsExport(tc.getIsExport());
		oldtc.setIsPrint(tc.getIsPrint());
		//oldtc.setIsShow(tc.getIsShow());
		super.updateByPrimaryKeyCache(oldtc,oldtc.getId());
		return MsgConfig.MSG_KEY_SUCCESS;
	}
	public boolean deleteTableCustom(String[] ids){
	
		for(String id:ids){
			super.deleteByPrimaryKeyCache(id, SyTableCustom.class);//.delete("delete SyTableCustom where id=?",id);
		}
	
		return true;
	}

	@Override
	public SyTableCustom selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return super.selectByPrimaryKeyCache(id, SyTableCustom.class);
	}
	
}
