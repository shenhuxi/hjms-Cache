package com.pci.hjmos.cache.config.redisconfig.redislock_redission.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedissonConfig {
    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redission() throws IOException {
        Config config = new Config();
        if(redisProperties.getCluster() == null){
            config.useSingleServer().setAddress("redis://"+redisProperties.getHost() + ":" + redisProperties.getPort())
                    .setPassword(redisProperties.getPassword());
        }else{
            String[] strNodes= redisProperties.getCluster().getNodes().stream().map(p -> "redis://" + p).toArray(String[]::new);
            config.useClusterServers().addNodeAddress(strNodes);
        }
        return Redisson.create(config);
    }

}
