package com.pci.hjmos.cache.module.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/5/6 14:15
 * @Modified By
 */
@Data
@Accessors(chain = true)
public class User implements Serializable{
    Integer id;
    String name;
    String sex;
}
