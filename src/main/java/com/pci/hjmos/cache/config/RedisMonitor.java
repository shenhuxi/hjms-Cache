package com.pci.hjmos.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/4/9 8:35
 * @Modified By
 */

@Configuration
public class RedisMonitor {

    @Autowired(required = false)
    private RedisTemplate<String, String> redisTemplate;
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisTemplate.getConnectionFactory());
        return redisMessageListenerContainer;
    }

}



