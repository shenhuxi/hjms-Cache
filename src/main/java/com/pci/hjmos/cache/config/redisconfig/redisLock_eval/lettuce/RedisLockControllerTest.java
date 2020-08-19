package com.pci.hjmos.cache.config.redisconfig.redisLock_eval.lettuce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/5/11 12:07
 * @Modified By
 */
@RestController("RedisLockControllerTest2")
@RequestMapping("/lettuceLock")
public class RedisLockControllerTest {
    @Autowired
    private LettuceRedisLock lettuceRedisLock;


    @GetMapping("/setLock")
    public Object getUserById(String key, String uuid, long expire) {
        return lettuceRedisLock.setLock(key,uuid,expire);
    }
    @GetMapping("/redission/setLock")//测试使用
    public Boolean setLock(String key, long expire) {
        //生成随机秘钥
        String password = UUID.randomUUID().toString();
        lettuceRedisLock.setLock(key,password,expire);
        try {
            Thread.sleep(10000);
            System.out.println("执行业务逻辑");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return lettuceRedisLock.unLock(key,password);
    }
}
