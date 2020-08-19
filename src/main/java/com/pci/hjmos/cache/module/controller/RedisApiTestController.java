package com.pci.hjmos.cache.module.controller;

import com.pci.hjmos.cache.module.entity.Person;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/7/29 9:32
 * @Modified By
 */
@RestController
@RequestMapping("/redi-api-test")
public class RedisApiTestController {

    @Autowired
    @Qualifier("jdkredisTemplate")
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    @Qualifier("jackson2redisTemplate")
    RedisTemplate<String, Object> jackson2redisTemplate;

    @Autowired
    @Qualifier("jacksonredisTemplate")
    RedisTemplate<String, Object> jacksonredisTemplate;


    //**测试缓存的Jdk为什么乱码
    @GetMapping("/jdk-seralize")
    public String jdkSeralize( ) throws Exception {
        //List  ：  JDK>json> GJson
//        List<Person> objects = new ArrayList();
//        Person person ;
//        for (int i = 0; i < 300; i++) {
//            person = new Person( "王麻子", RandomUtils.nextInt(0,100));
//            objects.add(person);
//        }
        //Map
        Map<String,Object> objects = new HashMap<>(10);
        for (int i = 0; i < 1; i++) {
            objects.put(RandomStringUtils.randomAlphanumeric(5), new Person( "王麻子", RandomUtils.nextInt(0,100)));
        }

        JdkSerializationRedisSerializer jdk_serializer = new JdkSerializationRedisSerializer();
        long start = System.nanoTime();
        byte[] serialize = jdk_serializer.serialize(objects);
        long start01 = System.nanoTime();
        System.out.println("jdk_serializer序列化时间："+(start01-start)+"ns");
        System.out.println("jdk_serializer序列化后的长度："+serialize.length);
        redisTemplate.opsForValue().set("jdk-seralize",objects);
        redisTemplate.opsForHash().putAll("jdk-seralize_hash",objects);
        System.out.println("jdk_serializer存redis时间："+(System.nanoTime()-start01)+"ns");
        System.out.println();

        Jackson2JsonRedisSerializer<Object> json_serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        long start1 = System.nanoTime();
        byte[] serialize1 = json_serializer.serialize(objects);
        long start11 = System.nanoTime();
        System.out.println("json_serializer序列化时间："+(start11-start1)+"ns");
        System.out.println("json_serializer序列化后的长度："+serialize1.length);
        jacksonredisTemplate.opsForValue().set("json_serializer",objects);
        System.out.println("json_serializer存redis时间："+(System.nanoTime()-start11)+"ns");
        System.out.println();

        GenericJackson2JsonRedisSerializer generic_serializer = new GenericJackson2JsonRedisSerializer();
        long start2 = System.nanoTime();
        byte[] serialize2 = generic_serializer.serialize(objects);
        long start21 = System.nanoTime();
        System.out.println("generic_serializer序列化时间："+(start21-start2)+"ns");
        System.out.println("generic_serializer序列化后的长度："+serialize2.length);
        jackson2redisTemplate.opsForValue().set("json2_serializer",objects);
        System.out.println("generic_serializer存redis时间："+(System.nanoTime()-start21)+"ns");
        return "ok";
    }


    //**性能测试
    @GetMapping("/redis-tps")
    public Object redisTps( ) throws Exception {

        Map<String,Object> objects = new HashMap<>(10);
        ValueOperations<String, Object> ov = redisTemplate.opsForValue();
        long  star =System.currentTimeMillis();
        for (int i = 0; i < 170000; i++) {
            objects.put(RandomStringUtils.randomAlphanumeric(5),RandomStringUtils.randomAlphanumeric(50));
        }
        ov.multiSet(objects);
        return System.currentTimeMillis()-star;
    }

    //**list 新增对象
    @GetMapping("/redis-list-add")
    public Object redisListAdd(Person person) throws Exception {
        ListOperations<String, Object> ov = jackson2redisTemplate.opsForList();
        ov.leftPush("userList",person);
        System.out.println("调用了此节点！");
        return "新增成功！";
    }
    //**list 移除对象
    @GetMapping("/redis-list-remove")
    public Object redisListRemove(Person person) throws Exception {
        ListOperations<String, Object> ov = jackson2redisTemplate.opsForList();
        ov.remove("userList",1,person);
        return "新增成功1！";
    }
    //**has(key)
    @GetMapping("/redis-has-key")
    public Object redisHasKey(String key) throws Exception {
        return jackson2redisTemplate.hasKey(key);
    }
    //**has(key)
    @GetMapping("/redis-hash-put")
    public Object redisHasKey(String key,String field,String value) throws Exception {
        HashOperations<String, Object, Object> oh = jackson2redisTemplate.opsForHash();
        oh.put(key,field,value);
        return "新增hash成功！";
    }

    //**测试超时时间
    @GetMapping("/timeout")
    public String testCache(Integer id ) throws InterruptedException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session=request.getSession(true);
        session.setMaxInactiveInterval(3);//3600秒，注du意服务器端的3600秒，而不是客zhi户端的
        Thread.sleep(5000);
        return "ok";
    }


}
