package com.pci.hjmos.cache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 缓存过期监听类
 * @author zyting
 * @sinne 2020-04-07
 */
//@Component
@Slf4j
public class CacheSunscribeThread implements ApplicationRunner {

    @Autowired(required = false)
    private RedisTemplate<String, Object> redisTemplate;

    private void sendDataToMq(Message message) {
        log.info("old版本监听到:"+message.toString());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(() -> redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.pSubscribe((message, pattern) -> {
                    //业务处理
                    sendDataToMq(message);
                }, "*@0*".getBytes());
                return null;
            }
        })).start();
    }
}
