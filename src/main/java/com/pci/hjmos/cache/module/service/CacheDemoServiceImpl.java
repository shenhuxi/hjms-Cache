package com.pci.hjmos.cache.module.service;

import com.pci.hjmos.cache.module.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheDemoServiceImpl implements CacheDemoService {

    @Cacheable(cacheNames = {
            "demoCache#60#10", "demoCar#20"
    }, key = "#id")
    @Override
    public Object getFromDB(Integer id) {
        System.out.println("模拟去db查询~~~" + id);
        return new User().setId(1).setName("张三").setSex("男");
        //return null;//测试穿透
    }
}
