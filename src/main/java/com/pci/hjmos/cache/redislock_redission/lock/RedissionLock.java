package com.pci.hjmos.cache.redislock_redission.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if(isLock)
            System.out.println("成功获取到锁：线程为："+Thread.currentThread().getName());
        return  isLock;
    }

    public boolean unLock(String key) {
        redission.getLock(key).unlock();
        return  true;
    }
}
