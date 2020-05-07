package com.pci.hjmos.cache.config_eh;

import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.util.StringUtils;

import java.time.Duration;

public class MyEhCacheCacheManager extends EhCacheCacheManager {
	/**
	 * Create a new EhCacheCacheManager for the given backing EhCache CacheManager.
	 *
	 * @param cacheManager the backing EhCache {@link CacheManager}
	 */
	public MyEhCacheCacheManager(CacheManager cacheManager) {
		super(cacheManager);
	}

	/**
	 * add cache using default configs
	 */
	@Override
	public Cache getCache(String name) {
		Cache cache = super.getCache(name);
		if (cache == null) {
			Long timeToLiveSeconds = null;
			Long timeToIdleSeconds = null;
			String[] array = StringUtils.delimitedListToStringArray(name, "#");
			name = array[0];
			if (array.length > 1) { // 解析TTL
				 timeToLiveSeconds = Long.parseLong(array[1]);// 注意单位我此处用的是秒，而非毫秒
				if (array.length ==3) {
					timeToIdleSeconds= Long.parseLong(array[2]);
				}
			}

			cache = getCache(name,1000,timeToLiveSeconds,timeToIdleSeconds);
		}
		return cache;
	}
	
	/** 获取自定义缓存 */
	public Cache getCache(CacheConfiguration cacheConfiguration) {
		String name = cacheConfiguration.getName();
		CacheManager cacheManager = getCacheManager();
		if(cacheManager.cacheExists(name)) return getCache(name);
		synchronized (this) {
			if(!cacheManager.cacheExists(name)) {
				cacheManager.addCache(new net.sf.ehcache.Cache(cacheConfiguration));
			}
		}
		return getCache(name);
	}
	
	/**
	 * 获取自定义缓存
	 * @param name
	 * @param maxElementsInMemory 超过此数量后按LRU规则擦除缓存（0无限制）
	 * @param timeToLiveSeconds 创建之后生存一段时间就失效
	 * @param timeToIdleSeconds 上次访问之后空闲一段时间就失效
	 * @return
	 */
	public Cache getCache(String name, int maxElementsInMemory, Long timeToLiveSeconds, Long timeToIdleSeconds) {
		CacheManager cacheManager = getCacheManager();
		if(cacheManager.cacheExists(name)) return getCache(name);
		synchronized (this) {
			if(!cacheManager.cacheExists(name)) {
				CacheConfiguration cacheConfiguration = new CacheConfiguration(name, maxElementsInMemory);
				if(timeToLiveSeconds!=null)
					cacheConfiguration.timeToLiveSeconds(timeToLiveSeconds);
				if(timeToIdleSeconds!=null)
					cacheConfiguration.timeToIdleSeconds(timeToIdleSeconds);
				cacheManager.addCache(new net.sf.ehcache.Cache(cacheConfiguration));
			}
		}
		return super.getCache(name);
	}
}