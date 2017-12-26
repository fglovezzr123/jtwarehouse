package com.tojoy.util.redis;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

/**
 * 任务队列Redis实现。
 * 
 * 采用每次获取Jedis并放回pool的方式。 如果获得Jedis后一直不放手，反复重用，两个操作耗时可以降低1/3。
 * 暂时先忍受这种低性能，不明确Jedis是否线程安全。
 *
 */
public class TaskQueueRedisImpl implements TaskQueue {

	private final static int REDIS_DB_IDX = 9;

	private final static Logger logger = Logger.getLogger(TaskQueueRedisImpl.class);

	private final String name;

	/**
	 * 构造函数。
	 * 
	 * @param name
	 */
	public TaskQueueRedisImpl(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwssi.common.mq.TaskQueue#getName()
	 */
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwssi.common.mq.TaskQueue#pushTask(String)
	 */
	public void pushTask(String task) {
		Jedis jedis = null;
		try {
			jedis = RedisManager.getResource(REDIS_DB_IDX);
			jedis.lpush(this.name, task);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				RedisManager.returnResource(jedis);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwssi.common.mq.TaskQueue#popTask()
	 */
	public String popTask() {
		Jedis jedis = null;
		String task = null;
		try {
			jedis = RedisManager.getResource(REDIS_DB_IDX);
			task = jedis.rpop(this.name);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				RedisManager.returnResource(jedis);
			}
		}
		return task;
	}

}