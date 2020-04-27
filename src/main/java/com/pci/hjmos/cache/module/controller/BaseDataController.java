
package com.pci.hjmos.cache.module.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cache")
public class BaseDataController {

    @Autowired
    RedisTemplate<String, String> redisTemplateListener;

//    @Autowired
//    private RedissonClient redissonClient;

    @GetMapping("/set")
    public Object set(String key ,String value) {
        redisTemplateListener.opsForValue().set(key,value);
        return "ok";
    }

    @GetMapping("/get")
    public Object get(String key) {
        return redisTemplateListener.opsForValue().get(key);
    }

    @GetMapping("/delete")
    public Object delete(String key ) {
        return  redisTemplateListener.delete(key);
    }

    @GetMapping("/selectDB")
    public Object selectDB(int db ) {
        JedisConnectionFactory connectionFactory = (JedisConnectionFactory) redisTemplateListener.getConnectionFactory();
        connectionFactory.setDatabase(db);
        int database = connectionFactory.getDatabase();




        connectionFactory.setDatabase(0);
        return  1;
}

    @GetMapping("/getDB")
    public Object getDB() {
        JedisConnectionFactory connectionFactory = (JedisConnectionFactory) redisTemplateListener.getConnectionFactory();
        return  connectionFactory.getDatabase();
    }

//    @GetMapping("/testRedission")
//    public void set() {
//        // 设置字符串
//        RBucket<String> keyObj = redissonClient.getBucket("k1");
//        keyObj.set("v1236");
//    }
}