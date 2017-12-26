/**  
 * @Project: tjy
 * @Title: DistrictServiceImpl.java
 * @Package com.wing.socialcontact.sys.service.impl
 * @date 2016-6-19 下午2:03:19
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
import com.wing.socialcontact.sys.bean.SyDistrict;
import com.wing.socialcontact.sys.dao.DistrictDao;
import com.wing.socialcontact.sys.service.IDistrictService;

/**
 * 
 * 类名：DistrictServiceImpl
 * 功能：
 * 详细：
 * 作者：dijuli
 * 版本：1.0
 * 日期：2016-6-19 下午2:03:19
 *
 */
@Service
public class DistrictServiceImpl extends BaseServiceImpl<SyDistrict> implements IDistrictService{
	
	@Resource
	private DistrictDao dao;
	
	public DataGrid selectDistrict(PageParam param,SyDistrict dis){
		DataGrid data=new DataGrid();
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("superId", dis.getSuperId());
		parm.put("disName", dis.getDisName());
		parm.put("orderStr", orderStr);
		List lst = dao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Long selectDistrictCount(SyDistrict dis){
		
		
		SyDistrict parm = new SyDistrict();
		parm.setSuperId(dis.getSuperId());
		parm.setDisName(dis.getDisName());
		
		return (long)dao.selectDistrictCount(parm);//(Long)dao.findUniqueOne(sb.toString(),list);
		
	}
	
	
	public String addDistrict(SyDistrict dis){
		//Object obj=dao.findOne("from SyDistrict where superId=? and  disName=? ",dis.getSuperId(),dis.getDisName());
		SyDistrict parm = new SyDistrict();
		parm.setSuperId(dis.getSuperId());
		parm.setDisName(dis.getDisName());
		SyDistrict obj = dao.selectOne(parm);
		if(obj==null){
			return dao.insert(dis)>0?MsgConfig.MSG_KEY_SUCCESS:MsgConfig.MSG_KEY_FAIL;
		}else{
			return "msg.district.unique";//名称重复
		}
		
		
	}
	public String updateDistrict(SyDistrict dis){
		SyDistrict parm = new SyDistrict();
		parm.setSuperId(dis.getSuperId());
		parm.setDisName(dis.getDisName());
		SyDistrict obj = dao.selectOne(parm);
		if(obj==null || obj.getId().equals(dis.getId())){
			return super.updateByPrimaryKeyCache(dis,dis.getId())?MsgConfig.MSG_KEY_SUCCESS:MsgConfig.MSG_KEY_FAIL;
		}else{
			return "msg.district.unique";//名称重复
		}
	}
	
	public boolean deleteDistrict(String[] ids){
		for(String id:ids){
			super.deleteByPrimaryKeyCache(id, SyDistrict.class);
		}
	
		return true;
	}
	@SuppressWarnings("rawtypes")
	public List selectAllDistrict(){
		List lst = dao.selectByParam(new HashMap());
		return lst;//dao.find("from SyDistrict where superId=? order by disSort asc",id);
		//return dao.find("select new Map(d.id as id,d.disName as name,d.superId as superId)from SyDistrict d order by disSort asc");
	}
	
	@SuppressWarnings("rawtypes")
	public List selectDistrictBySuperId(String id){
		Map<String,Object> parm = new HashMap<String,Object>();
		parm.put("superId", id);
		List lst = dao.selectByParam(parm);
		return lst;//dao.find("from SyDistrict where superId=? order by disSort asc",id);
	}
	@Override
	public SyDistrict selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return super.selectByPrimaryKeyCache(id, SyDistrict.class);
	}
	
	@Override
	public List selectDistrictByType(String type) {
		Map<String,Object> parm = new HashMap<String,Object>();
		parm.put("type", type);
		List lst = dao.selectByParam(parm);
		return lst;
	}
	
	/**
	 * 根据条件获取地区信息
	 * @param param
	 * @return
	 */
	public List selectByParam(Map param){
		List lst = dao.selectByParam(param);
		return lst;
	}
}
