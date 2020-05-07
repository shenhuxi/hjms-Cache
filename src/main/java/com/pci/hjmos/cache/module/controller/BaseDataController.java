
package com.pci.hjmos.cache.module.controller;

import com.pci.hjmos.cache.config_eh.MyEhCacheCacheManager;
import com.pci.hjmos.cache.module.entity.User;
import com.pci.hjmos.cache.module.service.CacheDemoService;
import com.pci.hjmos.cache.util.SpringUtil;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class BaseDataController {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CacheDemoService cacheDemoService;
//    @Autowired
//    private RedissonClient redissonClient;

    @GetMapping("/testCache")
    public Object testCache(Integer id ) {
        return cacheDemoService.getFromDB(id);
    }

    @GetMapping("/getCacheManager")
    public Object getCacheManager(Integer id ) {
        MyEhCacheCacheManager cacheManager = (MyEhCacheCacheManager)SpringUtil.getBean("cacheManager");
        EhCacheCache demoCarEhCacheCache = (EhCacheCache)cacheManager.getCache("demoCache");
        Ehcache demoCar = demoCarEhCacheCache.getNativeCache();
        Element element = demoCar.get(id);
        long expirationTime = element.getExpirationTime();
        long current = System.currentTimeMillis();
        return (expirationTime - current) / 1000;
    }

    @GetMapping("/set")
    public Object set(String key ,String value) {
        User zahngShan= new User().setId(1).setName("张三").setSex("男");
        redisTemplate.opsForValue().set(key,zahngShan);
        return "ok";
    }

    @GetMapping("/get")
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/delete")
    public Object delete(String key ) {
        return  redisTemplate.delete(key);
    }

    @GetMapping("/selectDB")
    public Object selectDB(int db ) {
        JedisConnectionFactory connectionFactory = (JedisConnectionFactory) redisTemplate.getConnectionFactory();
        connectionFactory.setDatabase(db);
        int database = connectionFactory.getDatabase();
        connectionFactory.setDatabase(0);
        return  1;
}

    @GetMapping("/getDB")
    public Object getDB() {
        JedisConnectionFactory connectionFactory = (JedisConnectionFactory) redisTemplate.getConnectionFactory();
        return  connectionFactory.getDatabase();
    }

//    @GetMapping("/testRedission")
//    public void set() {
//        // 设置字符串
//        RBucket<String> keyObj = redissonClient.getBucket("k1");
//        keyObj.set("v1236");
//    }
}