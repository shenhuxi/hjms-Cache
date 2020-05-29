package com.pci.hjmos.cache.redislock_redission;

import com.pci.hjmos.cache.redislock_redission.lock.RedissionLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/5/11 12:07
 * @Modified By
 */
@RestController
@RequestMapping("/redissionLock")
public class RedissionLockControllerTest {

    @Autowired
    private RedissionLock redissionLock;
// ----------------------------------------------- redission -----------------------------------------------
    @GetMapping("/redission/setLock")//测试使用
    public Boolean setLock(String key, long expire) {
        redissionLock.setLock(key,expire);
//        try {
//            //Thread.sleep(10000);
//            System.out.println("执行业务逻辑");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return true;//redissionLock.unLock(key);
    }
}
