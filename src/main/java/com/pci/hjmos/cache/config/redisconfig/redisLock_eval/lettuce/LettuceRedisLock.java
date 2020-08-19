package com.pci.hjmos.cache.config.redisconfig.redisLock_eval.lettuce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisScriptingCommands;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/5/14 11:38
 * @Modified By
 */
@Service
public class LettuceRedisLock {
    public final static String LOCK_KEY = "hjmos_lock_";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    //分布式锁
    public boolean setLock(String key, String uuid, long expire) {
        RedisCallback<Boolean> callback = (connection) -> {
            RedisStringCommands redisStringCommands = connection.stringCommands();
            // EX = seconds; PX = milliseconds 单位不同
            return redisStringCommands.set((LOCK_KEY + key).getBytes(), uuid.getBytes(), Expiration.seconds(expire), RedisStringCommands.SetOption.ifAbsent());
        };
        Boolean result = false;
        try {
            result = redisTemplate.execute(callback);
        } catch (Exception e) {
            System.out.println("分布式锁key:{}，异常：{}");
        }
        boolean b = result == null ? false : result;
        System.out.println("获取普通锁key：{}，结果：{}");
        return b;
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

    public boolean unLock(String key, String password) {
        byte[] totalKey = (LOCK_KEY + key).getBytes();
        byte[] passwordByte = password.getBytes();
        // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
        // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
        RedisCallback<Long> callback = (connection) -> {
            //使用统一的底层接口执行脚本
            RedisScriptingCommands commands = connection.scriptingCommands();
            return (Long) (commands.eval(UNLOCK_LUA.getBytes(), ReturnType.INTEGER, 1, totalKey, passwordByte));
        };
        Long result = null;
        try {
            result = redisTemplate.execute(callback);
        } catch (Exception e) {
            System.out.println("分布式锁key:{}，异常：{}");
        }
        boolean b = result != null && result > 0;
        System.out.println("释放普通锁key：{}，结果：{}");
        return b;
    }
}
