package com.pci.hjmos.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.net.URL;

public class Test {
    public static void main(String[] args) {
        // 1. 创建缓存管理器
        String path = System.getProperty("user.dir");
        // 创建Cache管理器
        CacheManager cacheManager = CacheManager.create(path + "\\src\\main\\resources\\ehcache.xml");
        Cache cache = cacheManager.getCache("HelloWorldCache");
        Element element = new Element("key1", "value1");
    }
}
