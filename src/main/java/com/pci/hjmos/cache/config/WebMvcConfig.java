package com.pci.hjmos.cache.config;

import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Component
//public class WebMvcConfig extends WebMvcConfigurerAdapter {
//    @Override
//    public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
//        configurer.setDefaultTimeout(10000);
//        configurer.registerCallableInterceptors(timeoutInterceptor());
//    }
//    @Bean
//    public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
//        return new TimeoutCallableProcessingInterceptor();
//    }
//}