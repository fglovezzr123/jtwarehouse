package com.tojoy.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Set;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisCache implements Cache {

	private RedisTemplate<String, Object> redisTemplate;

	private Long liveTime;

	public long getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(long liveTime) {
		this.liveTime = liveTime;
	}

	private String name;

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Object getNativeCache() {
		return this.redisTemplate;
	}

	/**
	 * 获取缓存
	 */
	public ValueWrapper get(Object key) {
		final String keyf = key.toString();
		Object object = null;
		object = redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) throws DataAccessException {

				byte[] key = keyf.getBytes();
				byte[] value = connection.get(key);
				if (value == null) {
					return null;
				}
				return toObject(value);

			}
		});
		return (object != null ? new SimpleValueWrapper(object) : null);
	}

	/**
	 * 添加缓存
	 */
	public void put(Object key, Object value, Long expireTime) {
		if (value == null) {
			return;
		}

		final String keyf = key.toString();
		final Object valuef = value;
		final long expire = (expireTime == null || expireTime.longValue() <= 0)
				? (this.liveTime == null ? 86400 : this.liveTime) : expireTime.longValue();
		redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyb = keyf.getBytes();
				byte[] valueb = toByteArray(valuef);
				connection.set(keyb, valueb);
				if (expire > 0) {
					connection.expire(keyb, expire);
				}
				return 1L;
			}
		});
		
	}

	/**
	 * 添加缓存
	 */
	public void put(Object key, Object value) {
		put(key, value, null);
	}
	

	/**
	 * redis : hset
	 * @param key :
	 * @param field :
	 * @param val :
	 * @return
	 */
	public Boolean hset(final String key, final String field, final Object val) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<Object> serialize = (RedisSerializer<Object>)redisTemplate.getDefaultSerializer();
				byte[] keyByte = key.getBytes();
				return connection.hSet(keyByte, field.getBytes(), serialize.serialize(val));
			}
		});
	}

	/**
	 * redis : hget
	 * @param key :
	 * @param field :
	 * @return
	 */
	public Object hget(final String key, final String field) {

		byte[] objrs = redisTemplate.execute(new RedisCallback<byte[]>() {
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = key.getBytes();
				return connection.hGet(keyByte, field.getBytes());
			}
		});

		return redisTemplate.getDefaultSerializer().deserialize(objrs);
	}

	/**
	 * redis : hdel
	 * @param key :
	 * @param field :
	 * @return
	 */
	public Long hdel(final String key, final String field) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = key.getBytes();
				return connection.hDel(keyByte, field.getBytes());
			}
		});
	}

	/**
	 * 描述 : <Object转byte[]>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param obj
	 * @return
	 */
	private byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return bytes;
	}

	/**
	 * 描述 : <byte[]转Object>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param bytes
	 * @return
	 */
	private Object toObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	/**
	 * 删除缓存
	 */
	public void evict(Object key) {
		// System.out.println("evict del key:" + key);
		final String keyf = key.toString();
		redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.del(keyf.getBytes());
			}
		});
	}

	public void clear() {
		redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}

	@Override
	public <T> T get(Object arg0, Class<T> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValueWrapper putIfAbsent(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 消息队列存消息
	 * 
	 * @Title: putToQueue
	 * @Description: TODO
	 * @param key
	 * @param value
	 * @return
	 * @return: Long
	 * @author: zengmin
	 * @date: 2017年6月27日 上午8:49:01
	 */
	public Long putToQueue(String key, String value) {
		final String keyf = key;
		final String valuef = value;
		Long l = (Long) this.getRedisTemplate().execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyb = keyf.getBytes();
				byte[] valueb = valuef.getBytes();
				// TODO Auto-generated method stub
				return connection.lPush(keyb, valueb);
			}
		});
		return l;
	}

	/**
	 * 消息队列读取消息 （读过队列中消息就没有了）key 是消息频道
	 * 
	 * @Title: getFromQueue
	 * @Description: TODO
	 * @param key
	 * @return
	 * @return: String
	 * @author: zengmin
	 * @date: 2017年6月27日 上午8:50:00
	 */
	public String getFromQueue(String key) {
		// TODO Auto-generated method stub
		final String keyf = key;
		byte[] b = (byte[]) this.getRedisTemplate().execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				return connection.lPop(keyf.getBytes());
			}
		});
		if (b != null) {
			return new String(b);
		}
		return null;
	}
	
	/**
	 * 模糊删除缓存
	 * 
	 * Set<byte[]> keys(byte[] pattern);
	 */
	public void removeall (Object key) {
		final String keyf = key.toString()+"*";
		redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				Set<byte[]> keys = connection.keys(keyf.getBytes());
				
				for(byte[] key : keys){
					String str2 = new String(key);
					System.out.println(str2);
					connection.del(key);
				}
				return  null;
				
			}
		});
	}

}
