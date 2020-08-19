package com.pci.hjmos.cache.module.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/8/19 12:46
 * @Modified By
 */
@Service
public class TimeOutService {

    @Async
    public  Callable<String>  runService(){
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(25000);
                return "请求成功+123";
            }
        };
        return callable;
    }
}
