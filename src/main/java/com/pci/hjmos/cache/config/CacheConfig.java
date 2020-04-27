package com.pci.hjmos.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
public class CacheConfig {
    @Autowired
    private Environment env;

    @Bean
    public ConcurrentMapCacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        //cacheManager.setStoreByValue(true); //true表示缓存一份副本，否则缓存引用
        return cacheManager;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        final JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(env.getProperty("spring.redis.host"));
        jedisConnectionFactory.setPort(Integer.parseInt(env.getProperty("spring.redis.port")));
        jedisConnectionFactory.setPassword(env.getProperty("spring.redis.password"));
        jedisConnectionFactory.setDatabase(1);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplateListener(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        final JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();

        jedisConnectionFactory.setHostName(env.getProperty("spring.redis.host"));
        jedisConnectionFactory.setPort(Integer.parseInt(env.getProperty("spring.redis.port")));
        jedisConnectionFactory.setPassword(env.getProperty("spring.redis.password"));

        template.setConnectionFactory(jedisConnectionFactory);
        template.afterPropertiesSet();
        // redis存取对象的关键配置
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        // ObjectRedisSerializer类为java对象的序列化和反序列化工具类
       Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        //JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // 为hashvalue添加序列化和反序列化类
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

}
