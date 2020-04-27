import com.pci.hjmos.cache.MTest;
import com.pci.hjmos.cache.config.CacheConfig;
import com.pci.hjmos.cache.module.service.CacheDemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MTest.class)
public class TestSpringBean {

    @Autowired
    private CacheDemoService cacheDemoService;
    @Autowired
    private CacheManager cacheManager;

    @Test
    public void test1() {
        cacheDemoService.getFromDB(1);
        cacheDemoService.getFromDB(1);

        // 校验缓存里的内容~~~~
        Cache demoCache = cacheManager.getCache("demoCache");
        System.out.println(demoCache.getName());
        System.out.println(demoCache.get(1, String.class));
    }

}