package com.wing.socialcontact.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.util.RedisCache;

/**
 * @author dijuli
 * 
 * @desc 基本服务层基类 所有的服务层都继承此基类
 *
 */

public abstract class BaseServiceImpl<T> {

	@Autowired
	protected Mapper<T> basedao;
	@Resource
	protected RedisCache redisCache;

	public boolean updateByPrimaryKeyCache(T obj, Object key) {
		String catchkey = "DB:" + obj.getClass().getName() + ":" + key.toString();
		if (basedao.updateByPrimaryKey(obj) > 0) {
			redisCache.put(catchkey, obj);
			//模糊删除列表缓存
			blurdelete(obj);
			return true;
		}
		return false;
	}
	public void blurdelete(T obj){
		//模糊删除列表缓存
		redisCache.removeall("DB:" + obj.getClass().getName() + "list:");
	}
	//删除实体类对应的所有
	public void deleteall( Class<?> obj){
		//模糊删除列表缓存
		redisCache.removeall("DB:" + obj.getName());
	}
	
	/**
	 * 删除一个对象
	 * 
	 * @param obj
	 *            对象
	 * @return boolean 删除成功或失败
	 */
	public boolean deleteByPrimaryKeyCache(Object key, Class<?> clazz) {
		String catchkey = "DB:" + clazz.getName() + ":" + key.toString();
		if (basedao.deleteByPrimaryKey(key) > 0) {
			redisCache.evict(catchkey);
			//模糊删除列表缓存
			redisCache.removeall("DB:" + clazz.getName() + "list:");
			return true;
		}
		return false;
	}
	

	public T selectByPrimaryKeyCache(Object key, Class<?> clazz) {
		String catchkey = "DB:" + clazz.getName() + ":" + key.toString();
		ValueWrapper obj = redisCache.get(catchkey);
		if (obj != null) {
			return (T) obj.get();
		} else {
			T v = basedao.selectByPrimaryKey(key);
			redisCache.put(catchkey, v);
			return v;
		}
	}
	
	public boolean insertCache(List res, Object key, Class<?> clazz) {
		String catchkey = "DB:" + clazz.getName() + "list:" + key.toString();
			redisCache.put(catchkey, res);
			return true;
	}
	
	public List<T> selectListByPrimaryKeyCache(Object key, Class<?> clazz) {
		String catchkey = "DB:" + clazz.getName() + "list:" + key.toString();
		ValueWrapper obj = redisCache.get(catchkey);
		if (obj != null) {
			return (List<T>) obj.get();
		} else {
			return null;
		}
	}

}
