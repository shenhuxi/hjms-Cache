package com.pci.hjmos.cache.module.service;

import com.pci.hjmos.cache.module.entity.User;
import com.pci.hjmos.cache.module.repository.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheDemoServiceImpl implements CacheDemoService {
    @Autowired
    SystemUserMapper systemUserMapper;

    @Cacheable(cacheNames = {"user", "user"}, key = "#id")
    @Override
    public User getFromDB(Integer id) {
        System.out.println("模拟去db查询~~~" + id);
        User user = systemUserMapper.selectById(id);
        System.out.println(user);
        return user;
    }
}
