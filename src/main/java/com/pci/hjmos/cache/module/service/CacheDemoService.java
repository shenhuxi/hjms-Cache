package com.pci.hjmos.cache.module.service;

import com.pci.hjmos.cache.module.entity.User;

public interface CacheDemoService {
    public User getFromDB(Integer id);
}
