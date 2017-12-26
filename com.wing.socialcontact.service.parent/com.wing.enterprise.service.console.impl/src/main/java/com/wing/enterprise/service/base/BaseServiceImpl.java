package com.wing.enterprise.service.base;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;

import tk.mybatis.mapper.common.Mapper;

import com.wing.socialcontact.util.RedisCache;


/**
 * @author dijuli
 * 
 * @desc	基本服务层基类 	所有的服务层都继承此基类
 *
 */

public abstract class BaseServiceImpl<T>{

	@Autowired
	protected Mapper<T> basedao;
	@Resource
	private RedisCache redisCache;
	
	public boolean updateByPrimaryKeyCache(T obj,Object key){
		String catchkey = "\"DB:"+obj.getClass().getName()+":\" + "+key.toString();
		if(basedao.updateByPrimaryKey(obj)>0){
			redisCache.put(catchkey, obj);
			return true;
		}
		return false;
	}
	/**
	 * 删除一个对象
	 * @param obj	对象
	 * @return	boolean	删除成功或失败
	 */
	public boolean deleteByPrimaryKeyCache(Object key,Class<?> clazz){
		String catchkey = "\"DB:"+clazz.getName()+":\" + "+key.toString();
		if(basedao.deleteByPrimaryKey(key)>0){
			redisCache.evict(catchkey);
			return true;
		}
		return false;
	}
	

	public T selectByPrimaryKeyCache(Object key,Class<?> clazz){
		String catchkey = "\"DB:"+clazz.getName()+":\" + "+key.toString();
		ValueWrapper obj = redisCache.get(catchkey);
		if(obj!=null){
			return (T)obj.get();
		}else{
			T v = basedao.selectByPrimaryKey(key);
			redisCache.put(catchkey,v);
			return v;
		}
	}
	
	
}

	