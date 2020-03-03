package com.pci.hjmos.cache.module.system.user.repository;

import com.pci.hjmos.cache.module.system.user.entity.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserRepository {
    SysUser findById(Long id);

    SysUser findByName(String userName);

    SysUser saveUser(SysUser user);

    SysUser updateUser(SysUser user);

    boolean deleteById(Long id);
}
