package com.pci.hjmos.cache.module.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

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
    Integer deleted;
    Integer age;
    String name;
    String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    private Date birthday;
}
