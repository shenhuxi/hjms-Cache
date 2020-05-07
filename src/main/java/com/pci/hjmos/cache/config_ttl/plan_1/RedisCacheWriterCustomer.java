package com.pci.hjmos.cache.config_ttl.plan_1;

import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class RedisCacheWriterCustomer implements RedisCacheWriter {


    private final RedisConnectionFactory connectionFactory;

    private final Duration sleepTime;



    /**
     * @param connectionFactory must not be {@literal null}.
     */
    public RedisCacheWriterCustomer(RedisConnectionFactory connectionFactory) {
        this(connectionFactory, Duration.ZERO);
    }

    /**
     * @param connectionFactory must not be {@literal null}.
     * @param sleepTime sleep time between lock request attempts. Must not be {@literal null}. Use {@link Duration#ZERO}
     *          to disable locking.
     */
    RedisCacheWriterCustomer(RedisConnectionFactory connectionFactory, Duration sleepTime) {

        Assert.notNull(connectionFactory, "ConnectionFactory must not be null!");
        Assert.notNull(sleepTime, "SleepTime must not be null!");

        this.connectionFactory = connectionFactory;
        this.sleepTime = sleepTime;
    }

    @Override
    public void put(String name, byte[] key, byte[] value, @Nullable Duration ttl) {

        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        Assert.notNull(value, "Value must not be null!");

        execute(name, connection -> {

            //当设置了过期时间，则修改取出
            //@Cacheable(value="user-key#key_expire=1200",key = "#id",condition = "#id != 2")
            //name 对应 value
            //key 对应 value :: key

            //判断name里面是否设置了过期时间，如果设置了则对key进行缓存，并设置过期时间
            int index = name.lastIndexOf(CachaeKeys.KEY_EXPIRE_TIME);
            if (index  > 0){
                //取出对应的时间 1200 index + 1是还有一个=号
                String expireString = name.substring(index  + 1 + CachaeKeys.KEY_EXPIRE_TIME.length());
                long expireTime = Long.parseLong(expireString);
                String currentKey = new String(key);
                currentKey = currentKey.replace(name.substring(index), "");
                connection.set(currentKey.getBytes(), value, Expiration.from(expireTime, TimeUnit.SECONDS), RedisStringCommands.SetOption.upsert());
            }else if (shouldExpireWithin(ttl)) {
                connection.set(key, value, Expiration.from(ttl.toMillis(), TimeUnit.MILLISECONDS), RedisStringCommands.SetOption.upsert());
            } else {
                connection.set(key, value);
            }
            return "OK";
        });
    }

    /**
     * Get the binary value representation from Redis stored for the given key.
     *
     * @param name must not be {@literal null}.
     * @param key  must not be {@literal null}.
     * @return {@literal null} if key does not exist.
     */
    @Override
    public byte[] get(String name, byte[] key) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");

        int index = name.lastIndexOf(CachaeKeys.KEY_EXPIRE_TIME);
        if(index>0){
            String currentKey = new String(key);
            currentKey = currentKey.replace(name.substring(index), "");
            byte[] bytes = currentKey.getBytes();

            return execute(name, connection -> connection.get(bytes));
        }

        return execute(name, connection -> connection.get(key));
    }

    /**
     * Write the given value to Redis if the key does not already exist.
     *
     * @param name  The cache name must not be {@literal null}.
     * @param key   The key for the cache entry. Must not be {@literal null}.
     * @param value The value stored for the key. Must not be {@literal null}.
     * @param ttl   Optional expiration time. Can be {@literal null}.
     * @return {@literal null} if the value has been written, the value stored for the key if it already exists.
     */
    @Override
    public byte[] putIfAbsent(String name, byte[] key, byte[] value, Duration ttl) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");
        Assert.notNull(value, "Value must not be null!");

        return execute(name, connection -> {

            if (isLockingCacheWriter()) {
                doLock(name, connection);
            }

            try {
                if (connection.setNX(key, value)) {

                    if (shouldExpireWithin(ttl)) {
                        connection.pExpire(key, ttl.toMillis());
                    }
                    return null;
                }

                return connection.get(key);
            } finally {

                if (isLockingCacheWriter()) {
                    doUnlock(name, connection);
                }
            }
        });
    }

    /**
     * Remove the given key from Redis.
     *
     * @param name The cache name must not be {@literal null}.
     * @param key  The key for the cache entry. Must not be {@literal null}.
     */
    @Override
    public void remove(String name, byte[] key) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(key, "Key must not be null!");

        execute(name, connection -> connection.del(key));
    }

    /**
     * Remove all keys following the given pattern.
     *
     * @param name    The cache name must not be {@literal null}.
     * @param pattern The pattern for the keys to remove. Must not be {@literal null}.
     */
    @Override
    public void clean(String name, byte[] pattern) {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(pattern, "Pattern must not be null!");

        execute(name, connection -> {

            boolean wasLocked = false;

            try {

                if (isLockingCacheWriter()) {
                    doLock(name, connection);
                    wasLocked = true;
                }

                byte[][] keys = Optional.ofNullable(connection.keys(pattern)).orElse(Collections.emptySet())
                        .toArray(new byte[0][]);

                if (keys.length > 0) {
                    connection.del(keys);
                }
            } finally {

                if (wasLocked && isLockingCacheWriter()) {
                    doUnlock(name, connection);
                }
            }

            return "OK";
        });
    }


    private <T> T execute(String name, Function<RedisConnection, T> callback) {

        RedisConnection connection = connectionFactory.getConnection();
        try {

            checkAndPotentiallyWaitUntilUnlocked(name, connection);
            return callback.apply(connection);
        } finally {
            connection.close();
        }
    }

    private void checkAndPotentiallyWaitUntilUnlocked(String name, RedisConnection connection) {

        if (!isLockingCacheWriter()) {
            return;
        }

        try {

            while (doCheckLock(name, connection)) {
                Thread.sleep(sleepTime.toMillis());
            }
        } catch (InterruptedException ex) {

            // Re-interrupt current thread, to allow other participants to react.
            Thread.currentThread().interrupt();

            throw new PessimisticLockingFailureException(String.format("Interrupted while waiting to unlock cache %s", name),
                    ex);
        }
    }

    private boolean isLockingCacheWriter() {
        return !sleepTime.isZero() && !sleepTime.isNegative();
    }

    private static boolean shouldExpireWithin(@Nullable Duration ttl) {
        return ttl != null && !ttl.isZero() && !ttl.isNegative();
    }

    boolean doCheckLock(String name, RedisConnection connection) {
        return connection.exists(createCacheLockKey(name));
    }

    private static byte[] createCacheLockKey(String name) {
        return (name + "~lock").getBytes(StandardCharsets.UTF_8);
    }

    private Boolean doLock(String name, RedisConnection connection) {
        return connection.setNX(createCacheLockKey(name), new byte[0]);
    }

    private Long doUnlock(String name, RedisConnection connection) {
        return connection.del(createCacheLockKey(name));
    }
}