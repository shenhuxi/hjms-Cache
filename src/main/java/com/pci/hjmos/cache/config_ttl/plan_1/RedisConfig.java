package com.pci.hjmos.cache.config_ttl.plan_1;//新建一个配置类

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

//@Configuration
//@EnableCaching //开启缓存，默认是rendis缓存，继承CachingConfigurerSupport ，直接重写里面的方法
public class RedisConfig extends CachingConfigurerSupport {

    private String host;
    private  int port;
    private String password;
    private final Environment env;
    @Autowired
    public RedisConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @DependsOn("springUtils")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        final JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();

        jedisConnectionFactory.setHostName(env.getProperty("spring.redis.host"));
        jedisConnectionFactory.setPort(Integer.parseInt(env.getProperty("spring.redis.port")));
        jedisConnectionFactory.setPassword(env.getProperty("spring.redis.password"));

        template.afterPropertiesSet();
        // redis存取对象的关键配置
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        // ObjectRedisSerializer类为java对象的序列化和反序列化工具类
        //Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        //JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(genericJackson2JsonRedisSerializer);
        // 为hashvalue添加序列化和反序列化类
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return template;
    }

    @Override
    @Bean
    public CacheManager cacheManager() {
	
        RedisConnectionFactory connectionFactory = redisTemplate().getConnectionFactory();
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);


        //上面实现的缓存读写
        RedisCacheWriterCustomer cacheWriterCustomer = new RedisCacheWriterCustomer(connectionFactory);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())).entryTtl(Duration.ofSeconds(30*60));

        return new RedisCacheManager(cacheWriterCustomer,configuration);
    }
}
