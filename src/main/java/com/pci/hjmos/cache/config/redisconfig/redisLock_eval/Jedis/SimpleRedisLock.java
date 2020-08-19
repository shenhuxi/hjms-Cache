package com.pci.hjmos.cache.config.redisconfig.redisLock_eval.Jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.ArrayList;
import java.util.List;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/5/14 11:38
 * @Modified By
 */
@Service
public class SimpleRedisLock {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    //分布式锁
    public boolean setLock(String key, String uuid, long expire) {
        RedisCallback<String> callback = (connection) -> {
            JedisCommands commands = (JedisCommands) connection.getNativeConnection();
            // EX = seconds; PX = milliseconds 单位不同
            return commands.set(key, uuid, "NX", "PX", expire);
        };
        String result = redisTemplate.execute(callback);
        return !StringUtils.isEmpty(result);
    }

    private static final String UNLOCK_LUA;

    static {
        UNLOCK_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] " +
                "then " +
                "    return redis.call(\"del\",KEYS[1]) " +
                "else " +
                "    return 0 " +
                "end ";
    }

    private boolean releaseDistributedLock(String key, String requestId) {
        List<String> keys = new ArrayList<>();
        keys.add(key);
        List<String> args = new ArrayList<>();
        args.add(requestId);
        // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
        // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
        RedisCallback<Long> callback = (connection) -> {
            Object nativeConnection = connection.getNativeConnection();
            // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
            // 集群模式
            if (nativeConnection instanceof JedisCluster) {
                return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
            }
            // 单机模式
            else if (nativeConnection instanceof Jedis) {
                return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
            }
            return 0L;
        };
        Long result = redisTemplate.execute(callback);
        return result != null && result > 0;
    }
}
