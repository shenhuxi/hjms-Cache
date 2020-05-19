package com.pci.hjmos.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pci.hjmos.cache.config_eh.MyEhCacheCacheManager;
import com.pci.hjmos.cache.config_ttl.plan_3.MyRedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
public class CacheConfig extends CachingConfigurerSupport {
    @Autowired
    private Environment env;

    //程序使用的cacheManager 设置
//    @Override
//    @Bean
//    public CacheManager cacheManager() {
//       // net.sf.ehcache.CacheManager ehCacheCacheManager = net.sf.ehcache.CacheManager.create();
//        return redisCacheManager();
//    }
    //EHCache配置ttl的方式
    @Bean
    public CacheManager eHCacheManager() {
       return new MyEhCacheCacheManager(net.sf.ehcache.CacheManager.create());
    }

    //redis配置ttl的方式
    @Bean
    @Primary
    public CacheManager redisCacheManager(LettuceConnectionFactory connectionFactory) {

        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        //自己实现的缓存读写
       // RedisCacheWriterCustomer cacheWriterCustomer = new RedisCacheWriterCustomer(connectionFactory)

        //序列化方式
        //JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
        //GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);

        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));

        MyRedisCacheManager redisCacheManager = new MyRedisCacheManager(redisCacheWriter, configuration);
        return redisCacheManager;
    }

//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        final JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName(env.getProperty("spring.redis.host"));
//        jedisConnectionFactory.setPort(Integer.parseInt(env.getProperty("spring.redis.port")));
//        jedisConnectionFactory.setPassword(env.getProperty("spring.redis.password"));
//        jedisConnectionFactory.setDatabase(0);
//        return jedisConnectionFactory;
//    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        final JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
//
//        jedisConnectionFactory.setHostName(env.getProperty("spring.redis.host"));
//        jedisConnectionFactory.setPort(Integer.parseInt(env.getProperty("spring.redis.port")));
//        jedisConnectionFactory.setPassword(env.getProperty("spring.redis.password"));

        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        // redis存取对象的关键配置
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        // ObjectRedisSerializer类为java对象的序列化和反序列化工具类
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        //JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        template.setValueSerializer(serializer);
        // 为hashvalue添加序列化和反序列化类
        template.setHashValueSerializer(serializer);
        return template;
    }



    //     使用ConcurrentMapCache作为注解缓存
//    @Bean
//    public ConcurrentMapCacheManager cacheManager() {
//        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
//        //cacheManager.setStoreByValue(true); //true表示缓存一份副本，否则缓存引用
//        return cacheManager;
//    }

    //    @Bean
//    public RedisTemplate<String, String> redisTemplateListener(JedisConnectionFactory jedisConnectionFactory) {
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        template.setConnectionFactory(jedisConnectionFactory);
//        return template;
//    }

}
