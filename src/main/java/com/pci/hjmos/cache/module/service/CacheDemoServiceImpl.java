package com.pci.hjmos.cache.module.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheDemoServiceImpl implements CacheDemoService {

    @Cacheable(cacheNames = "demoCache", key = "#id",unless = "",condition = "")
    @Override
    public Object getFromDB(Integer id) {
        System.out.println("模拟去db查询~~~" + id);
        return "hello cache...";
    }
}
