package com.wing.socialcontact.service.wx.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wing.socialcontact.common.model.DataGrid;
import com.wing.socialcontact.common.model.PageParam;
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.ILibraryService;
import com.wing.socialcontact.service.wx.bean.Club;
import com.wing.socialcontact.service.wx.bean.TjyLibrary;
import com.wing.socialcontact.service.wx.bean.TjyNews;
import com.wing.socialcontact.service.wx.dao.LibraryClassDao;
import com.wing.socialcontact.service.wx.dao.LibraryDao;
import com.wing.socialcontact.sys.service.impl.BaseServiceImpl;
/**
 * 
 * @author zhangzheng
 * 文库管理
 */
@Service
public class LibraryServiceImpl extends BaseServiceImpl<TjyLibrary> implements ILibraryService {

	/**
	 * 缓存名称
	 */
	private static final String cache_name = "\"DB:Library:\" + ";
	
	@Resource
	private LibraryClassDao LibraryClassDao;
	
	@Resource
	private LibraryDao LibraryDao;
	
	

	/**
	 * 文库列表查询
	 * @param param
	 * @param libraryc
	 * @param stime
	 * @param etime
	 * @return
	 */
	@Override
	public DataGrid selectLibrary(PageParam param, TjyLibrary libraryc,Date stime,Date etime) {
		DataGrid data=new DataGrid();
		String orderStr = param.getOrderByString();
		
		PageHelper.startPage(param.getPage(), param.getRows());
		Map parm = new HashMap();
		parm.put("title", libraryc.getTitle());
		parm.put("classId", libraryc.getClassId());
		parm.put("oneclass", libraryc.getOneclass());
		parm.put("stime", stime);
		parm.put("etime", etime);
		List<Map<String,Object>> lst=LibraryDao.getMap(parm);

		PageInfo page = new PageInfo(lst);

		data.setRows(lst);
		data.setTotal(page.getTotal());
		
		return data;
	}
	
	
	/**
	 * 批量删除文库
	 * @param id
	 * @return
	 */
	@Override
	public boolean deleteTjyLibrary(String[] ids) {
		for(String id : ids){
			TjyLibrary dto = super.selectByPrimaryKeyCache(id, TjyLibrary.class);
			 dto.setDeleted(1);
			 super.updateByPrimaryKeyCache(dto, id);
		}
		
		return true;
	}
	/**
	 * 新增文库
	 * @param dto
	 * @return
	 */
	@Override
	public String addTjyLibrary(TjyLibrary dto) {
			int res = LibraryDao.insert(dto);
			super.blurdelete(dto);
			if(res > 0){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
	}
	/**
	 * 更新文库
	 * @param dto
	 * @return
	 */
	@Override
	public String updateTjyLibrary(TjyLibrary dto) {
			if(super.updateByPrimaryKeyCache(dto,dto.getId())){
				return MsgConfig.MSG_KEY_SUCCESS;
			}else{
				return MsgConfig.MSG_KEY_FAIL;
			}
	}
	
	/**
	 * 根据id获取文库
	 * @param id
	 * @return
	 */
	@Override
	public TjyLibrary getTjyLibraryByid(String id) {
		return LibraryDao.selectByPrimaryKey(id);
	}


	/**
	 * 根据类别id获取文库list
	 * @param classid
	 * @return
	 */
	@Override
	public List<Map> getTjyLibraryByclassid(String classid) {
		List res  = selectListByPrimaryKeyCache(classid,TjyLibrary.class);
		if(res==null){
			res = LibraryDao.getTjyLibraryByclassid(classid);
			insertCache(res,classid,TjyLibrary.class);
		}
		return res;
	}


	/**
	 * 根据文库id获取详情
	 * @param libraryid
	 * @return
	 */
	@Override
	public Map getLibraryByid(String libraryid) {
		return LibraryDao.getLibraryByid(libraryid);
	}



	/**
	 * 根据文库id添加阅读次数
	 * @param libraryid
	 */
	@Override
	public void addreadtimes(String libraryid) {
		TjyLibrary library = LibraryDao.selectByPrimaryKey(libraryid);
		library.setReadtimes(library.getReadtimes()+1);
		super.updateByPrimaryKeyCache(library,libraryid);
		
	}


	/**
	 * 条件分页查询文库列表
	 */
	@Override
	public List<Map> getTjyLibraryByTerm(String classId, Integer page,
			Integer size, Integer today, String key, Integer readtimes,
										 long contentVisibleRange) {
		Map parm = new HashMap();
		parm.put("title", key);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		parm.put("classId", classId);
		parm.put("today", today);
		parm.put("readtimes", readtimes);
		parm.put("contentVisibleRange",contentVisibleRange);
		List lst= selectListByPrimaryKeyCache(classId+page+size+today+key+readtimes,TjyLibrary.class);
		if(lst==null){
			lst=LibraryDao.getTjyLibraryByTerm(parm);
			insertCache(lst,classId+page+size+today+key+readtimes,TjyLibrary.class);
		}
		return lst;
	}


	@Override
	public List<Map> selbyonelevelid(String classid) {
		Map parm = new HashMap();
		parm.put("classid", classid);
		List res  = selectListByPrimaryKeyCache(classid,TjyLibrary.class);
		if(res==null){
			res = LibraryDao.selbyonelevelid(parm);
			insertCache(res,classid,TjyLibrary.class);
		}
		return res;
	}


	@Override
	public List<Map> getLibraryByoneLevel(String classid, Integer page,
			Integer size, Integer today, String key, Integer readtimes) {
		Map parm = new HashMap();
		parm.put("title", key);
		parm.put("start", (page-1)*size);
		parm.put("size", size);
		parm.put("classId", classid);
		parm.put("today", today);
		parm.put("readtimes", readtimes);
		List lst= selectListByPrimaryKeyCache(page+classid+size+today+key+readtimes,TjyLibrary.class);
		if(lst==null){
			lst=LibraryDao.getLibraryByoneLevel(parm);
			insertCache(lst,classid,TjyLibrary.class);
		}
		return lst;
	}

}
