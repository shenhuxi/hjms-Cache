package com.pci.hjmos.cache.module.system.user.service;

import com.pci.hjmos.cache.module.system.user.entity.SysUser;

public interface SystemUser {
    SysUser getUserInfo(long userId);

    SysUser getUserByName(String userName);

    SysUser saveUser(SysUser user);

    SysUser updateUser(SysUser user);

    boolean deleteById(Long id);
}
