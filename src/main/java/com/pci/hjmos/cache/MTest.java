package com.pci.hjmos.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author czchen
 * @version 1.0
 * @date 2020/2/27 11:43
 */

@SpringBootApplication
@MapperScan("com.pci.hjmos.cache.module.repository")
public class MTest {

    public static void main(String[] args) {
        MTest a=new MTest();
        SpringApplication.run(MTest.class, args);
    }

}
