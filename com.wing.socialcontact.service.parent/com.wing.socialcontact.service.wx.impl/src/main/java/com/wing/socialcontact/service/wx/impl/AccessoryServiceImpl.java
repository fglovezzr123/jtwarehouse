package com.wing.socialcontact.service.wx.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IAccessoryService;
import com.wing.socialcontact.service.wx.bean.Accessory;
import com.wing.socialcontact.service.wx.bean.Accessory;
import com.wing.socialcontact.service.wx.dao.AccessoryDao;
import com.wing.socialcontact.service.wx.dao.AccessoryDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 
 * @author liangwj
 * @date 2017-03-27 11:24:18
 * @version 1.0
 */
@Service
public class AccessoryServiceImpl  extends BaseServiceImpl<Accessory>  implements IAccessoryService{
	/**
	 * 缓存的key值
	 */
	private static final String cache_name = "\"DB:Accessory:\" + ";
	
	@Resource
	private AccessoryDao accessoryDao;
	
	@Override
	public List selectAllAccessory() {
		return accessoryDao.selectAllAccessoryMap();
	}

	@Override
	public DataGrid selectAccessorys(PageParam param, Accessory accessory) {
		DataGrid data=new DataGrid();
		
		String orderStr = param.getOrderByString();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		///parm.put("tagName", accessory.getTagName());
		parm.put("orderStr", orderStr);
		List lst = accessoryDao.selectByParam(parm);
		PageInfo page = new PageInfo(lst);
		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}

	@Override
	public String addAccessory(Accessory accessory) {
		Accessory parm = new Accessory();
		//parm.setTagName(accessory.getTagName());
		//List lst = accessoryDao.select(parm);
		int res = accessoryDao.insert(accessory);
		if(res > 0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}else{
			return MsgConfig.MSG_KEY_FAIL;
		}
		/*if(lst.size()==0){
		}else{
			return "msg.newsclass.unique";//标签名称已存在
		}*/
	}

	@Override
	public String updateAccessory(Accessory accessory) {
		Accessory parm = new Accessory();
		//parm.setTagName(accessory.getTagName());
		Accessory obj = accessoryDao.selectOne(parm);
		if(obj==null || obj.getId().equals(accessory.getId())){
			if(super.updateByPrimaryKeyCache(accessory,accessory.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.newsclass.unique";//此名称已存在
		}
	}

	@Override
	public boolean deleteAccessorys(String[] ids) {
		//等待删除的对象集合
				int count = 0;
				for(String id:ids){
					if(StringUtils.isNotBlank(id)){
						String[] myids = id.split(",");
						for (String string : myids) {
							Accessory r=selectByPrimaryKey(string);
							if(r!=null){
								
								if(super.deleteByPrimaryKeyCache(string, Accessory.class))count++;
							}
						}
					}
				}
				return count>0;
	}
	
	
	

	@Override
	public Accessory selectByPrimaryKey(String key) {
		return super.selectByPrimaryKeyCache(key, Accessory.class);
	}

	@Override
	public Accessory selectById(String id) {
		return accessoryDao.selectByPrimaryKey(id);
	}

	@Override
	public void deleteAccessoryByTerm(Accessory dto) {
		Map parm = new HashMap();
		parm.put("superId",dto.getSuperId());
		accessoryDao.deleteByTerm(parm);
	}

	@Override
	public List<Accessory> selectByTerm(Accessory dto) {
		Map parm = new HashMap();
		parm.put("superId",dto.getSuperId());
		return accessoryDao.selectByParam(parm);
	}

}