package com.wing.socialcontact.service.wx.impl;

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
import com.wing.socialcontact.service.wx.api.ILibraryClassService;
import com.wing.socialcontact.service.wx.bean.TjyLibraryClass;
import com.wing.socialcontact.service.wx.dao.LibraryClassDao;
import com.wing.socialcontact.service.wx.dao.LibraryDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;

/**
 * 文库分类
 * @author zhangzheng
 *
 */
@Service
public class LibraryClassServiceImpl extends BaseServiceImpl<TjyLibraryClass> implements ILibraryClassService{

	private static final String cache_name = "\"DB:LibraryClass:\" + ";
	
	@Resource
	private LibraryDao LibraryDao;
	
	@Resource
	private LibraryClassDao LibraryClassDao;
	
	/**
	 * 文库分类列表
	 * @param param
	 * @param librarycla
	 * @return
	 */
	@Override
	public DataGrid selectLibraryClass(PageParam param,
			TjyLibraryClass librarycla) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("name", librarycla.getName());
		parm.put("level", librarycla.getLevel());
		List<Map<String,Object>> lst=LibraryClassDao.getclassMap(parm);

		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
		
	}
	/**
	 * 添加
	 */
	@Override
	public String addTjyLibraryClass(TjyLibraryClass dto) {

		TjyLibraryClass parm = new TjyLibraryClass();
		parm.setName(dto.getName());
		List lst = LibraryClassDao.select(parm);
		if(lst.size()==0){
			int res = LibraryClassDao.insert(dto);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.newsclass.unique";//
		}
	}
	/**
	 * 根据id获取
	 */
	@Override
	public TjyLibraryClass getTjyLibraryClassByid(String id) {
		return super.selectByPrimaryKeyCache(id, TjyLibraryClass.class);
	}
	/**
	 * 更新
	 */
	@Override
	public String updateTjyLibraryClass(TjyLibraryClass dto) {
		TjyLibraryClass parm = new TjyLibraryClass();
		parm.setName(dto.getName());
		TjyLibraryClass obj = LibraryClassDao.selectOne(parm);
		if(obj==null || obj.getId().equals(dto.getId())){
			if(super.updateByPrimaryKeyCache(dto,dto.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
		}else{
			return "msg.newsclass.unique";//
		}
		//updateByPrimaryKey(dto);
	}
	/**
	 * 删除
	 */
	@Override
	public String deleteTjyLibraryClass(String id) {
		List list = LibraryDao.getTjyLibraryByclassid(id);
		
		if(list.size()>0){
			return MsgConfig.MSG_KEY_SUCCESS;
		}
		 super.deleteByPrimaryKeyCache(id, TjyLibraryClass.class);
		 
		 return MsgConfig.MSG_KEY_SUCCESS;
	}
	/**
	 * 获取所有 List<TjyLibraryClass>
	 */
	@Override
	public List<TjyLibraryClass> getAllClass() {
		return null;
	}

	/**
	 * 获取所有List<Map<String, Object>>
	 */
	@Override
	public List<Map<String, Object>> selectAllClassMap() {
		return LibraryClassDao.selectAllclassMap();
	}

	/**
	 * 根据id获取
	 */
	@Override
	public TjyLibraryClass selectByPrimaryKey(String id) {
		return super.selectByPrimaryKeyCache(id,TjyLibraryClass.class);
	}

	/**
	 * 获取分类列表
	 */
	@Override
	public Object selectClass(PageParam param, TjyLibraryClass dto) {
		
		DataGrid data=new DataGrid();
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("name", dto.getName());
		List lst = LibraryClassDao.selectByName(parm);
		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		return data;
	}

	/**
	 * 根据父id查下级分类
	 */
	@Override
	public List<Map> querybyparent(String id) {
		Map parm = new HashMap();
		parm.put("id", id);
		return LibraryClassDao.selectByparent(parm);
	}

	/**
	 * 查询一级分类
	 */
	@Override
	public List<Map> selectonelevelclass() {
		return LibraryClassDao.selectonelevelclass();
	}

	/**
	 * 查询推荐二级分类
	 */
	@Override
	public List<Map> recommendclass() {
		return LibraryClassDao.recommendclass();
	}
	@Override
	public List selectLevelOne(TjyLibraryClass dto) {
		Map parm = new HashMap();
		parm.put("level", dto.getLevel());
		List<Map<String,Object>> lst=LibraryClassDao.getclassMap(parm);
		return lst;
	}
	@Override
	public List<Map> onelevelclass(Integer position) {
		Map parm = new HashMap();
		parm.put("position", position);
		return LibraryClassDao.onelevelclass(parm);
	}
}
