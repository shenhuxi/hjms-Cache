package com.pci.hjmos.cache.module.system.user.controller;

import com.pci.hjmos.cache.module.system.user.entity.SysUser;
import com.pci.hjmos.cache.module.system.user.service.SystemUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class SystemUserController {
    private final SystemUser systemUser;

    public SystemUserController(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    @GetMapping("/getUserInfo")
    public SysUser getUserInfo(Long id){
        return  systemUser.getUserInfo(id);
    }
}
