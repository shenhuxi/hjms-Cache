package com.pci.hjmos.cache.module.service;

import com.pci.hjmos.cache.module.entity.Person;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/8/13 13:59
 * @Modified By
 */
public class JdkSerializToRedisServiceImpl {

    public static void main(String[] args) throws Exception{
        ser(); // 序列化
        dser(); // 反序列话
    }


    public static void ser()throws Exception{
        File file = new File("C:\\Users\\ying\\Desktop\\gc" + File.separator + "hello.txt");

        List<Person> objects = new ArrayList();
        Person person ;
        for (int i = 0; i < 30; i++) {
            person = new Person( RandomStringUtils.randomAlphanumeric(3), RandomUtils.nextInt(0,100));
            objects.add(person);
        }
        System.out.println(objects);
        ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(objects);
        out.close();
    }

    public static  byte[] dser()throws Exception{
        File file = new File("C:\\Users\\ying\\Desktop\\gc" + File.separator + "hello.txt");
        FileInputStream fi = new FileInputStream(file);
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));

        byte[] b = new byte[100];
        fi.read(b);
        System.out.println("打印字节数组...每个字节");
        for (int i = 0; i < b.length; i++) {
            byte b1 = b[i];
            System.out.print(b1+",");
        }
        System.out.println();
        ArrayList<Person> objects = (ArrayList<Person>) input.readObject();
        input.close();
        System.out.println(objects);
        return b;
    }
}
