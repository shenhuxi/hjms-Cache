package com.pci.hjmos.cache.module.system.user.service;

import com.pci.hjmos.cache.api.Cache;
import com.pci.hjmos.cache.api.CacheDataLoader;
import com.pci.hjmos.cache.module.system.user.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class SystemUserImpl implements SystemUser {
  //  private final SystemUserRepository null;//;
    //注：1.用户要写的代码
    @Autowired
    private  Cache ehcacheService;

    @Override
    public SysUser getUserInfo(long userId) {
        //注：2.用户要写的代码
        SysUser result = (SysUser)ehcacheService.get(String.valueOf(userId), new CacheDataLoader<Object>() {
            @Override
            public Object loadData(String key) {
                return null;//.findById(Long.parseLong(key));
            }
        });
        return result;
    }

    /**
     * 使用注解形式--------------------------------------（对雪崩的支持需要考虑封装实现。）
     * 注：方法内部实现不考虑缓存逻辑，直接实现业务
     */
    @Override
    @Cacheable(value = "user", key = "System_User+#userName",sync = true, unless = "#result eq null")
    //描述：先查缓存，有值则不执行业务逻辑方法；sync可实现加锁防止雪崩、击透
    public SysUser getUserByName(String userName) {
        return null;//.findByName(userName);
    }

    @Override
    @CachePut(value = "user",key = "System_User+#userName")
    //描述：每次方法都会被执行，同时方法的返回值也被记录到缓存中，实现缓存与数据库的同步更新。
    public SysUser updateUser(SysUser user) {
        return null;//.updateUser(user);
    }

    @Override
    @Caching(put = {
            @CachePut(value = "user", key = "System_User+#user.userName"),
            @CachePut(value = "user", key = "#System_User+#user.userId")
    })
    //@UserSaveCache
    //添加多对缓存：用户新增成功后，我们要添加id–>user；username—>user的缓存
    public SysUser saveUser(SysUser user) {
        return null;//.saveUser(user);
    }

    @Override
    @CacheEvict(value = "user", key = "#root.method.name+#id")
    //描述：每次执行完方法后，清楚缓存
    public boolean deleteById(Long id) {
        return true;//.deleteById(id);
    }
}
