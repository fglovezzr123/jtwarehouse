package com.wing.socialcontact.service.wx.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.wing.socialcontact.service.wx.dao.AttentionDao;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.wing.socialcontact.util.RedisCache;
import com.wing.socialcontact.service.wx.api.IAttentionService;
import com.wing.socialcontact.service.wx.bean.Attention;

/**
 * 
 * @author liangwj
 * @date 2017-03-26
 * @version 1.0
 */
@Service
@CacheConfig(cacheNames = "default")
public class AttentionServiceImpl implements IAttentionService{
	private static final String CACHE_PREFIX = "TJY:Attention:";
	@Resource
	private RedisCache redisCache;
	
	@Resource
	private AttentionDao attentionDao;
	/**
	 * 新增
	 * @param t
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int insertAttention(Attention t) {
		redisCache.evict(CACHE_PREFIX+t.getFkId()+":"+t.getUserId());
		return attentionDao.insert(t);
	}
	/**
	 * 修改
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int updateAttention(Attention t) {
		redisCache.evict(CACHE_PREFIX+t.getFkId()+":"+t.getUserId());
		return attentionDao.updateByPrimaryKey(t);
	}
	/**
	 * 删除
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public int deleteAttention(Attention t) {
		redisCache.evict(CACHE_PREFIX+t.getFkId()+":"+t.getUserId());
		return attentionDao.delete(t);
	}
	/**
	 * 查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public List<Attention> queryAttention(Attention t) {
		return attentionDao.select(t);
	}
	/**
	 * 单个查询
	 * @param t
	 * @return
	 * @author liangwj
	 * @date 2017-03-26
	 */
	public Attention getAttention(String id) {
		return attentionDao.selectByPrimaryKey(id);
	}
	
	public String saveOrDelAttention(Attention attention) {
		Attention parm = new Attention();
		parm.setFkId(attention.getFkId());
		parm.setUserId(attention.getUserId());
		parm.setAttType(attention.getAttType());
		List lst = attentionDao.select(parm);
		if(lst.size()==0){
			int res = attentionDao.insert(attention);
			if(res > 0){
				return "收藏成功";
			}else{
				return "收藏失败";
			}
		}else{
			attentionDao.delete((Attention) lst.get(0));
			redisCache.evict(CACHE_PREFIX+((Attention) lst.get(0)).getFkId()+":"+((Attention) lst.get(0)).getUserId());
			return "取消收藏成功";
		}
	}
	/**
	 * 查询关注总人数
	 * @param attention
	 * @param fkId
	 * @param userId 
	 * @return
	 * @author liangwj
	 */
	public int selectCount(Attention attention) {
		return attentionDao.selectCount(attention);
	}
	/**
	 * 查询当前用户的关注情况
	 * @param fkId
	 * @param userId 
	 * @return
	 * @author liangwj
	 */
	@SuppressWarnings("rawtypes")
	public Attention getAttentionByFkIdAndUserId(String userId, String fkId) {
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(fkId)){
			return null;
		}
		
		Attention t = redisCache.get(CACHE_PREFIX+fkId+":"+userId,Attention.class);
		if(t!=null){
			return t;
		}
		
		t = new Attention();
		t.setUserId(userId);
		t.setFkId(fkId);
		
		List list =  attentionDao.select(t);
		if(list.size()>0){
			redisCache.put(CACHE_PREFIX+fkId+":"+userId, list.get(0));
			return (Attention) list.get(0);
		}else{
			return null;
		}
	}
}