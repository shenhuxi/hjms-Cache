package com.pci.hjmos.cache.service;

import com.pci.hjmos.cache.api.CacheDataLoader;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Order(2)
@Component
public class EhcacheService implements com.pci.hjmos.cache.api.Cache {
    private final Cache currentCache ;
    public EhcacheService() {
        CacheManager cacheManager = new CacheManager();
        this.currentCache = cacheManager.getCache("defaultCache");
    }

    /**
     * 增加接口,附带过期时间
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    @Override
    public void set(String key, Object value) {
        currentCache.put(new Element(key,value));
    }

    /**
     * 获取值接口:[此接口可防止雪崩、穿透；在高并发时通过锁，将不会把大佬请求发送给数据库]
     * 建议：增加
     *
     * @param key             缓存key
     * @param cacheDataLoader
     * @return 缓存值
     */
    @Override
    public Object get(String key, CacheDataLoader cacheDataLoader) {
        return null;
    }

    /**
     * 删除
     *
     * @param key 缓存key
     * @return 是否删除成功
     */
    @Override
    public Boolean delete(String key) {
        return null;
    }

    /**
     * 设置过期时间 单位（秒）
     *
     * @param key        缓存键
     * @param expireTime 过期时间
     */
    @Override
    public void expireKey(String key, long expireTime) {

    }

    /**
     * 判断key是否存在
     *
     * @param key 缓存key
     * @return 是否存在，true：存在  false：不存在
     */
    @Override
    public Boolean existsKey(String key) {
        return null;
    }

    /**
     * 批量删除
     *
     * @param keys 删除key的集合
     * @return 成功删除的个数
     */
    @Override
    public Long batchDelete(Collection keys) {
        return null;
    }

    /**
     * 批量获取
     *
     * @param keys 缓存key集合
     * @return 值集合
     */
    @Override
    public List multiGet(Collection keys) {
        return null;
    }

    /**
     * 批量添加
     *
     * @param maps 键值对集合
     */
    @Override
    public void multiSet(Map maps) {

    }
}
