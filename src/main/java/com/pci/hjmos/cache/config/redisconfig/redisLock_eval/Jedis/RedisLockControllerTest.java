package com.pci.hjmos.cache.config.redisconfig.redisLock_eval.Jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/5/11 12:07
 * @Modified By
 */
@RestController
@RequestMapping("/JedisLock")
public class RedisLockControllerTest {
    @Autowired
    private SimpleRedisLock simpleRedisLock;


    @GetMapping("/setLock")
    public Object getUserById(String key, String uuid, long expire) {
        return simpleRedisLock.setLock(key,uuid,expire);
    }
    @PostMapping("/releaseDistributedLock")
    public Object releaseDistributedLock(String key, String uuid) {
        return simpleRedisLock.setLock(key,uuid,10);
    }
}
