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
import com.wing.socialcontact.config.MsgConfig;
import com.wing.socialcontact.service.wx.api.IGlobalWhitelistService;
import com.wing.socialcontact.service.wx.bean.GlobalWhitelist;
import com.wing.socialcontact.service.wx.bean.TjyUser;
import com.wing.socialcontact.service.wx.dao.GlobalWhitelistDao;
import com.wing.socialcontact.service.wx.dao.TjyUserDao;
import com.wing.socialcontact.util.RedisCache;

/**
 * 
 * @author liangwj
 * @version 1.0
 */
@Service
public class GlobalWhitelistServiceImpl implements IGlobalWhitelistService{
	private static final String CACHE_PREFIX = "TJY:IGlobalWhitelist:";
	@Resource
	private RedisCache redisCache;
	@Resource
	private GlobalWhitelistDao globalWhitelistDao;
	@Resource
	private TjyUserDao tjyUserDao;
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 */
	public int insertGlobalWhitelist(GlobalWhitelist t) {
		return globalWhitelistDao.insert(t);
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int updateGlobalWhitelist(GlobalWhitelist t) {
		return globalWhitelistDao.updateByPrimaryKey(removeCache(t));
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int updateSelectiveGlobalWhitelist(GlobalWhitelist t) {
		return globalWhitelistDao.updateByPrimaryKeySelective(removeCache(t));
	}
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public int deleteGlobalWhitelist(GlobalWhitelist t) {
		return globalWhitelistDao.delete(removeCache(t));
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public List<GlobalWhitelist> queryGlobalWhitelist(GlobalWhitelist t) {
		return addCaches(globalWhitelistDao.select(t));
	}
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public GlobalWhitelist getGlobalWhitelist(String id) {
		return addCache(globalWhitelistDao.selectByPrimaryKey(id));
	}
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public GlobalWhitelist getGlobalWhitelistByUk(String userId,String types) {
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(types)){
			return null;
		}else{
			GlobalWhitelist gw = globalWhitelistDao.selectOne(new GlobalWhitelist(userId, types));
			addCache(gw);
			return gw;
		}
	}
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllGlobalWhitelist(PageParam param, GlobalWhitelist t) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<GlobalWhitelist> list = globalWhitelistDao.selectByParam(t);
		PageInfo<GlobalWhitelist> page = new PageInfo<GlobalWhitelist>(list);
		return new DataGrid(page);
	}
	/**
	 * 分页查询
	 * @param param
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public DataGrid selectAllTjyUser(PageParam param, TjyUser t) {
		PageHelper.startPage(param.getPage(), param.getRows());
		List<TjyUser> list = tjyUserDao.query(t);
		PageInfo<TjyUser> page = new PageInfo<TjyUser>(list);
		return new DataGrid(page);
	}
	
	private List<GlobalWhitelist> addCaches(List<GlobalWhitelist> list){
		if(list!=null&&list.size()>0){
			for(GlobalWhitelist t:list){
				addCache(t);
			}
		}
		return list;
	}
	private GlobalWhitelist addCache(GlobalWhitelist t){
		if(t!=null&&StringUtils.isNotBlank(t.getUserId())
				&&StringUtils.isNotBlank(t.getTypes())){
			redisCache.put(CACHE_PREFIX+t.getTypes()+":"+t.getUserId(),t);
		}
		return t;
	}
	private GlobalWhitelist removeCache(GlobalWhitelist t){
		if(t!=null&&StringUtils.isNotBlank(t.getUserId())
				&&StringUtils.isNotBlank(t.getTypes())){
			redisCache.evict(CACHE_PREFIX+t.getTypes()+":"+t.getUserId());
		}
		return t;
	}
	/**
	 * 批量添加白名单
	 * @param types
	 * @param ids
	 * @return
	 */
	public String insertGlobalWhitelists(String types, String ids) {
		String[] idArr = ids.split(";");
		for(String id : idArr){
			if(StringUtils.isNotBlank(id)){
				GlobalWhitelist g = new GlobalWhitelist();
				g.setTypes(types);
				g.setUserId(id);
				
				if(globalWhitelistDao.selectCount(g)==0){
					g.setCreateTime(new Date());
					globalWhitelistDao.insert(g);
				}
			}
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}
	/**
	 * 批量删除
	 * @param t
	 * @return
	 * @author liangwj
	 */
	public String deleteGlobalWhitelists( String ids) {
		String[] idArr = ids.split(";");
		for(String id : idArr){
			if(StringUtils.isNotBlank(id)){
				removeCache(globalWhitelistDao.selectByPrimaryKey(id));
				globalWhitelistDao.deleteByPrimaryKey(id);
			}
		}
		return MsgConfig.MSG_KEY_SUCCESS;
	}
}