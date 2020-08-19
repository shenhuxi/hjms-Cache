package com.pci.hjmos.cache.module.service;

import com.pci.hjmos.cache.module.entity.User;
import com.pci.hjmos.cache.module.repository.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CacheDemoServiceImpl implements CacheDemoService {
    @Autowired
    SystemUserMapper systemUserMapper;
    @Autowired
    @Qualifier("genericJackson2redisTemplate")
    RedisTemplate<String, Object> genericJackson2redisTemplate;
    @Autowired
    @Qualifier("jdkredisTemplate")
    RedisTemplate<String, Object> redisTemplate;

    @Cacheable(cacheNames = {"user", "user"}, key = "#id")
    @Override
    public User getFromDB(Integer id) {
        System.out.println("模拟去db查询~~~" + id);
        User user = systemUserMapper.selectById(id);
        System.out.println(user);
        return user;
    }

    @Transactional
    public User update(User user) {
        int resultUser = systemUserMapper.updateById(user);
        System.out.println(user);
        redisTemplate.opsForValue().set("a",1);
        //System.out.println(1/0);
        return user;
    }
}
