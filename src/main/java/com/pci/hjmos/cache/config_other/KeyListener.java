package com.pci.hjmos.cache.config_other;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.*;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class KeyListener extends KeyExpirationEventMessageListener {

    public KeyListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }
 
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        //过期的key
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        //System.out.println("2监听到了key:"+key);
    }

    @Override
    protected void doRegister(RedisMessageListenerContainer listenerContainer) {
        listenerContainer.addMessageListener(this, new PatternTopic("__keyspace@*__:a*"));
    }
}