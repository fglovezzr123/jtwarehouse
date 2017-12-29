package com.tojoy.util.redis;

import com.tojoy.util.SpringContextUtil;
import org.apache.log4j.Logger;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisManager {

	private static JedisPool jedisPool;

	protected final static Logger logger = Logger.getLogger(RedisManager.class);

	static {
		try {
			//init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void init() throws Exception {
		JedisConnectionFactory jf = (JedisConnectionFactory) SpringContextUtil.getBean("redisConnectionFactory");
		jedisPool = new JedisPool(jf.getPoolConfig(), jf.getHostName(), jf.getPort());
	}

	/**
	 * 使用完成后，必须调用 returnResource 还回。
	 * 
	 * @return 获取Jedis对象
	 */
	public static Jedis getResource() {
		Jedis jedis = jedisPool.getResource();
		if (logger.isDebugEnabled()) {
			logger.debug("获得链接：" + jedis);
		}
		return jedis;
	}

	/**
	 * 获取Jedis对象。
	 * 
	 * 用完后，需要调用returnResource放回连接池。
	 * 
	 * @param db
	 *            数据库序号
	 * @return
	 */
	public static Jedis getResource(int db) {
		Jedis jedis = jedisPool.getResource();
		jedis.select(db);
		if (logger.isDebugEnabled()) {
			logger.debug("获得链接：" + jedis);
		}
		return jedis;
	}

	/**
	 * @param jedis
	 */
	public static void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
			if (logger.isDebugEnabled()) {
				logger.debug("放回链接：" + jedis);
			}
		}
	}

	/**
	 * 需要通过Spring确认这个方法被调用。
	 * 
	 * @throws Exception
	 */
	public static void destroy() throws Exception {
		jedisPool.destroy();
	}
}