package com.pci.hjmos.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pci.hjmos.cache.module.entity.User;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/7/2 16:13
 * @Modified By
 */
public class TestSerializeLenth {

    public static void main(String[] args) {
        System.out.println(LocalDate.now());
        //1号选手
        Jackson2JsonRedisSerializer<Object> Jackson = new Jackson2JsonRedisSerializer<>(Object.class);

        //2号选手
        Jackson2JsonRedisSerializer<Object> Jackson2 = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        Jackson2.setObjectMapper(objectMapper);

        //3号选手
        JdkSerializationRedisSerializer jdk = new JdkSerializationRedisSerializer();

        //4号选手
        GenericJackson2JsonRedisSerializer  genericJackson2 = new GenericJackson2JsonRedisSerializer();


        List<User> listUser = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            User zahngShan= new User().setId(1).setName("张三").setAge(11);
            listUser.add(zahngShan);
        }
        //开始统计字节量serialize
        byte[]  JacksonLen= Jackson.serialize(listUser);
        System.out.println("打印Jackson序列化后大小："+JacksonLen.length);

        byte[]  Jackson2Len= Jackson2.serialize(listUser);
        System.out.println("打印Jackson2序列化后大小："+Jackson2Len.length);

        byte[]  jdkLen= jdk.serialize(listUser);
        System.out.println("打印jdk序列化后大小："+jdkLen.length);

        byte[]  genericJackson2Len= genericJackson2.serialize(listUser);
        System.out.println("打印genericJackson2序列化后大小："+genericJackson2Len.length);
    }
}
