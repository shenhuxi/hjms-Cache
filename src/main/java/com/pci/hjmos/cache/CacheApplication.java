package com.pci.hjmos.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zengpeng on 2019/3/28
 */
@SpringBootApplication
@RestController
@EnableCaching
public class CacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

    @GetMapping("/test")
    public String upload(){

        // 1. 创建缓存管理器
        CacheManager cacheManager = new CacheManager("ehcache.xml");
        Cache cache = cacheManager.getCache("HelloWorldCache");
        Element element = new Element("key1", "value1");


        // 4. 将元素添加到缓存
        cache.put(element);

        // 5. 获取缓存
        Element value = cache.get("key1");
        System.out.println(value);
        System.out.println(value.getObjectValue());
        return "test";
    }


}
