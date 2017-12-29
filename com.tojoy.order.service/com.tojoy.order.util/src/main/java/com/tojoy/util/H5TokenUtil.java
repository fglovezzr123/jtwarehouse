package com.tojoy.util;

import java.util.UUID;

import org.springframework.cache.Cache.ValueWrapper;

/**
 * 网易云工具类
 * 
 * @ClassName: IMUtil
 * @Description: TODO
 * @author: zhangfan
 * @date:2017年9月4日
 */
public class H5TokenUtil {
	private static long ex = 120l; // 有效期2分钟 测试使用。
	
	public static String getUserH5Token(String userid) {
		RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
		ValueWrapper vw = redisCache.get("H5_TOKEN_" + userid);
		String token = null;
		if (vw != null) {
			token = (String) vw.get();
			return token;
		}
		token = genH5Token(userid);
		// 返回token
		redisCache.put("H5_TOKEN_" + userid,token, ex);
		return token;
	}


	private static String genH5Token(String userid) {
		RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		String token = id+userid;
		redisCache.put(token,userid, ex);
		return token;
	}

	private static String getUserByH5Token(String token) {
		RedisCache redisCache = (RedisCache) SpringContextUtil.getBean("redisCache");
		ValueWrapper vw = redisCache.get(token);
		String userid = null;
		if (vw != null) {
			userid = (String) vw.get();
			return userid;
		}
		return "";
	}

	public static void main(String[] args) throws Exception {

		
	}
}
