package com.pci.hjmos.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author czchen
 * @version 1.0
 * @date 2020/2/27 11:43
 */

@SpringBootApplication
//@EnableAsync
//@EnableWebMvc//WebMvcAutoConfiguration//ConditionalOnMissingBean条件：无bean//Deprecated不推荐
@MapperScan("com.pci.hjmos.cache.module.repository")
public class MTest {

    public static void main(String[] args) {
        MTest a=new MTest();
        SpringApplication.run(MTest.class, args);
    }

}
