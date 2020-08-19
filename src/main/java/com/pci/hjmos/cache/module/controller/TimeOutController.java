package com.pci.hjmos.cache.module.controller;

import com.pci.hjmos.cache.module.service.TimeOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/8/19 11:35
 * @Modified By
 */
@RestController
public class TimeOutController {
    @Autowired
    TimeOutService timeOutService;

    @GetMapping("/time-out")
    @Async
    public Callable<String> timeOut()  {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("哈哈哈");
                return "请求成功+123";
            }
        };
        return callable;
    }
}
