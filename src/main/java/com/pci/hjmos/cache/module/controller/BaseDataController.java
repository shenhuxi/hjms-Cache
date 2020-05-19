
package com.pci.hjmos.cache.module.controller;

import com.pci.hjmos.cache.config_eh.MyEhCacheCacheManager;
import com.pci.hjmos.cache.module.entity.User;
import com.pci.hjmos.cache.module.service.CacheDemoService;
import com.pci.hjmos.cache.util.SpringUtil;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClusterConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    @GetMapping("/setTime")
    public Object setTime(String key ,String value,Long time) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        vo.set(key,value,time, TimeUnit.SECONDS);
        return "ok";
    }

    @GetMapping("/get")
    public Object get(String key) {
       // Set<String> ss = redisTemplate.keys("*N01*");
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

    @GetMapping("/testPool")
    public Object testPool(String key ,String value) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();

        List<User> list = new ArrayList<>();
        for(int i = 0;i< 10000;i++){
            list.add(new User().setName(key+1).setId(i).setSex(value));
        }
        list.parallelStream().forEach(u->{
            vo.set(u.getId().toString(),u);
            LettuceClusterConnection connection = (LettuceClusterConnection) redisTemplate.getConnectionFactory().getConnection();
            System.out.println("线程："+Thread.currentThread().getName()+"-----执行了："+u.getId());
        });

        return "ok";
    }

//    @GetMapping("/testRedission")
//    public void set() {
//        // 设置字符串
//        RBucket<String> keyObj = redissonClient.getBucket("k1");
//        keyObj.set("v1236");
//    }
}