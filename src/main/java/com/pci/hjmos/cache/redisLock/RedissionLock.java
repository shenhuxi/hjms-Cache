package com.pci.hjmos.cache.redisLock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCommands;

import java.util.concurrent.TimeUnit;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/5/18 15:31
 * @Modified By
 */
@Service
public class RedissionLock {
    @Autowired
    RedissonClient redission;

    public boolean setLock(String key, long expire) {
        RLock redLock = redission.getLock(key);
        boolean isLock = false;
        try {
            // 500ms拿不到锁, 就认为获取锁失败。expire是锁失效时间。
            isLock = redLock.tryLock(500,expire*1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return  isLock;
    }

    public boolean unLock(String key) {
        redission.getLock(key).unlock();
        return  true;
    }
}
