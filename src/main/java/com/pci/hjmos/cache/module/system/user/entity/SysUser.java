package com.pci.hjmos.cache.module.system.user.entity;

import lombok.Data;

@Data
public class SysUser {
    private long userId;

    private String userName;

    private  String password;
}
