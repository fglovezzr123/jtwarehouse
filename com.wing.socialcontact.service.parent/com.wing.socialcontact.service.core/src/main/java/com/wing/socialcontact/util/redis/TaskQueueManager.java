package com.wing.socialcontact.util.redis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/**
 * <pre>
 * // 获得队列
 * TaskQueue tq = TaskQueueManager.get(TaskQueueManager.SMS_QUEUE);
 * 
 * // 添加任务到队列
 * String task = "task id";
 * tq.pushTask(task);
 * 
 * // 从队列中取出任务执行
 * String taskToDo = tq.popTask();
 * </pre>
 * 
 * @author liuhailong
 */
public class TaskQueueManager {

	protected final static Logger logger = Logger.getLogger(TaskQueueManager.class);

	private static Map<String, TaskQueueRedisImpl> queneMap = new ConcurrentHashMap<String, TaskQueueRedisImpl>();

	/**
	 * messageInfo队列名。
	 */
	public static final String SMS_QUEUE = "SMS_QUEUE";

	/**
	 * app消息队列名。
	 */
	public static final String RULE_QUEUE = "RULE_QUEUE";

	private static void initQueneMap() {
		logger.debug("初始化任务队列...");
		queneMap.put(RULE_QUEUE, new TaskQueueRedisImpl(RULE_QUEUE));
		logger.debug("建立队列：" + RULE_QUEUE);
		queneMap.put(SMS_QUEUE, new TaskQueueRedisImpl(SMS_QUEUE));
		logger.debug("建立队列：" + SMS_QUEUE);
	}

	static {
		initQueneMap();
	}

	public static TaskQueue get(String name) {
		return getRedisTaskQueue(name);
	}

	public static TaskQueue getRedisTaskQueue(String name) {
		return queneMap.get(name);
	}

}