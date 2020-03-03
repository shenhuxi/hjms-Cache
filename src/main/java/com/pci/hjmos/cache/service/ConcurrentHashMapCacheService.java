package com.pci.hjmos.cache.service;

import com.pci.hjmos.cache.api.Cache;
import com.pci.hjmos.cache.api.CacheDataLoader;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Order(1)
@Component
public class ConcurrentHashMapCacheService<T> implements Cache<T> {
	private ConcurrentHashMap<String, T> dataMap=new ConcurrentHashMap<>(); //展示用ConcurrentHashMap完成存储，待会改为EhCache

	@Override
	public void set(String key, T value) {
		dataMap.put(key,value);
	}

	@Override
	public void multiSet(Map<String, T> maps) {
		dataMap.putAll(maps);
	}

	@Override
	public T get(String key, CacheDataLoader<T> loader) {
		T o = getData(key);
		if (o == null) {
			synchronized(this){//加锁排队；防止请求大量访问数据库
				o = getData(key);
				if (o == null) {
					T ln = loader.loadData(key);
					set(key,ln);
				}
			}
		}
		return o;
	}

	private T getData(String key) {
		return dataMap.get(key);
	}
	@Override
	public List<T> multiGet(Collection<String> keys) {
		List<T> a = new ArrayList<>(keys.size());
		for (String key : keys) {
			a.add(dataMap.get(key));
		}
		return a;
	}

	@Override
	public Boolean delete(String key) {
		return dataMap.remove(key)!=null;
	}

	@Override
	public Long batchDelete(Collection<String> keys) {
		long count = 0;
		for (String key : keys) {
			if (dataMap.remove(key)!=null)
				count++;
		}
		return count;
	}

	@Override
	public void expireKey(String key, long expireTime) {
		// TODO 暂时不能实现过期时间设置
	}

	@Override
	public Boolean existsKey(String key) {
		return dataMap.contains(key);
	}

}
