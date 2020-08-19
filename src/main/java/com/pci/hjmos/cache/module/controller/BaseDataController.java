
package com.pci.hjmos.cache.module.controller;

import com.pci.hjmos.cache.config.redisconfig.config_eh.MyEhCacheCacheManager;
import com.pci.hjmos.cache.module.entity.User;
import com.pci.hjmos.cache.module.repository.SystemUserMapper;
import com.pci.hjmos.cache.module.service.CacheDemoService;
import com.pci.hjmos.cache.util.SpringUtil;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClusterConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cache")
public class BaseDataController {

    @Autowired
    @Qualifier("jdkredisTemplate")
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    @Qualifier("jackson2redisTemplate")
    RedisTemplate<String, Object> jackson2redisTemplate;
    @Autowired
    @Qualifier("jacksonredisTemplate")
    RedisTemplate<String, Object> jacksonredisTemplate;
    @Autowired
    @Qualifier("genericJackson2redisTemplate")
    RedisTemplate<String, Object> genericJackson2redisTemplate;

    @Autowired
    private CacheDemoService cacheDemoService;

    @Autowired
    SystemUserMapper systemUserMapper;
//    @Autowired
//    private RedissonClient redissonClient;

    @GetMapping("/testCache")
    public User testCache(Integer id ) {
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
        User zahngShan= systemUserMapper.selectById(Integer.valueOf(value));
        //1.jdk
        redisTemplate.opsForValue().set(key+"jdk",zahngShan);
        Object o = redisTemplate.opsForValue().get(key+"jdk");
        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
        final byte[] serialize = serializer.serialize(zahngShan);
        System.out.println("jdk打印序列化后字节大小："+serialize.length);
        //序列化到文件
        File file = new File("C:\\Users\\ying\\Desktop\\serializer" + File.separator + "hello.txt");
        try {
            ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(zahngShan);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        //2.1json2
        jacksonredisTemplate.opsForValue().set(key+"jackson",zahngShan);
        Jackson2JsonRedisSerializer<Object>  jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        byte[] serialize1 = jacksonSerializer.serialize(zahngShan);
        System.out.println("json打印序列化后字节大小："+serialize1.length);
//
//
//        System.out.println("jackson打印对象："+o21);
//
//        //2.json2
//        jackson2redisTemplate.opsForValue().set(key+"jackson2",zahngShan);
//        Object o2 = jackson2redisTemplate.opsForValue().get(key+"jackson2");
//        System.out.println("jackson2打印对象："+o2);
//
//        //3. genericJackson2
//        genericJackson2redisTemplate.opsForValue().set(key+"genericJackson2",zahngShan);
//        Object o3 = genericJackson2redisTemplate.opsForValue().get(key+"genericJackson2");
//        System.out.println("genericJackson2打印对象："+o3);
       return serialize;
    }
    @GetMapping("/setTime")
    public Object setTime(String key ,String value,Long time) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        vo.set(key,value,time, TimeUnit.SECONDS);
        System.out.println("修改了代码....");
        return "ok";
    }

    @GetMapping("/get")
    public Object get(String key) {
         return "哈哈!get"+key;
    }

    @PostMapping("/post")
    public Object post(String key) {
        return "哈哈!post"+key;
    }

    @PutMapping("/put")
    public Object put(String key) {
        return "哈哈!put"+key;
    }


    @DeleteMapping("/delete")
    public Object delete(String key) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "哈哈222!delete"+key;
    }
    @GetMapping("/deleteRedis")
    public Object deleteRedis(String key ) {
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
            User 张三 = new User("张三");
            张三.getName();
            list.add(new User("张三"));
        }
        list.parallelStream().forEach(u->{
            vo.set(u.getId().toString(),u);
            LettuceClusterConnection connection = (LettuceClusterConnection) redisTemplate.getConnectionFactory().getConnection();
            System.out.println("线程："+Thread.currentThread().getName()+"-----执行了："+u.getId());
        });

        return "ok";
    }
    //测试redis事务的支持
    @GetMapping("/testTransaction_update")
    public User testTransaction(User user ) {
        return cacheDemoService.update(user);
    }
}