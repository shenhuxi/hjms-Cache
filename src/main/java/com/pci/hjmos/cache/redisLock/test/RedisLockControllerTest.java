package com.pci.hjmos.cache.redisLock.test;

import com.pci.hjmos.cache.redisLock.RedissionLock;
import com.pci.hjmos.cache.redisLock.SimpleRedisLock;
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
@RequestMapping("/redisLock")
public class RedisLockControllerTest {
    @Autowired
    private SimpleRedisLock simpleRedisLock;

    @Autowired
    private RedissionLock redissionLock;

    @GetMapping("/setLock")
    public Object getUserById(String key, String uuid, long expire) {
        return simpleRedisLock.setLock(key,uuid,expire);
    }
    @PostMapping("/releaseDistributedLock")
    public Object releaseDistributedLock(String key, String uuid) {
        return simpleRedisLock.setLock(key,uuid,10);
    }

// ----------------------------------------------- redission -----------------------------------------------
    @GetMapping("/redission/setLock")//测试使用
    public Boolean setLock(String key, long expire) {
        redissionLock.setLock(key,expire);
        try {
            Thread.sleep(10000);
            System.out.println("执行业务逻辑");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return redissionLock.unLock(key);
    }
}
