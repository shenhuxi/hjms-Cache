package com.pci.hjmos.cache.config_eh;

import com.pci.hjmos.cache.util.SpringUtil;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@Component
public class CreateCacheManager implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String cacheManager = "cacheManager";
        //这里来注册一个CacheManager
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.refresh();
        //遍历
        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println("name:" + name + ",class:" + ctx.getBean(name).getClass());
        }
        //先销毁bean
        BeanDefinitionRegistry beanDefReg = (DefaultListableBeanFactory) ctx.getBeanFactory();
        beanDefReg.getBeanDefinition(cacheManager);
        beanDefReg.removeBeanDefinition(cacheManager);

        EhCacheCacheManager ehCacheCacheManager = new MyEhCacheCacheManager(net.sf.ehcache.CacheManager.create());
        ctx.getBeanFactory().registerSingleton(cacheManager,ehCacheCacheManager);
        ctx.refresh();

        Object obj = SpringUtil.getBean(cacheManager);
    }

}