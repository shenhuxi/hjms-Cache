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
@Accessors(chain = true)
public class User implements Serializable{

    Integer id;
    Integer deleted;
    Integer age;
    private String name ;
    String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    private Date birthday;

    public User() {
        name = null;
    }

    public User(String name) {
        this.name = name;
    }

    public User(Integer id, Integer deleted, Integer age, String name, String email, Date birthday) {
        this.id = id;
        this.deleted = deleted;
        this.age = age;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
