package com.pci.hjmos.cache.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Cache<T>{

	/**
     * 增加接口,附带过期时间
     * @param key 缓存key
     * @param value 缓存值
     */
    void set(String key, T value);

    /**
     * 批量添加
     * @param maps 键值对集合
     */
    void multiSet(Map<String, T> maps);

    /**
     * 获取值接口:[此接口可防止雪崩、穿透；在高并发时通过锁，将不会把大佬请求发送给数据库]
     * 建议：增加
     * @param key 缓存key
     * @return 缓存值
     */
    T get(String key, CacheDataLoader<T> cacheDataLoader);

    /**
     * 批量获取
     * @param keys 缓存key集合
     * @return 值集合
     */
    List<T> multiGet(Collection<String> keys);

    /**
     * 删除
     * @param key 缓存key
     * @return 是否删除成功
     */
    Boolean delete(String key);

    /**
     * 批量删除
     * @param keys  删除key的集合
     * @return  成功删除的个数
     */
    Long batchDelete(Collection<String> keys);

    /**
     * 设置过期时间 单位（秒）
     * @param key  缓存键
     * @param expireTime  过期时间
     */
    void expireKey(String key, long expireTime);

    /**
     * 判断key是否存在
     * @param key 缓存key
     * @return 是否存在，true：存在  false：不存在
     */
    Boolean existsKey(String key);
}
