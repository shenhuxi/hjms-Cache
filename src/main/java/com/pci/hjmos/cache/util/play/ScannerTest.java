package com.pci.hjmos.cache.util.play;

import java.util.Scanner;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/7/8 9:32
 * @Modified By
 */
public class ScannerTest {
    /**
     * Scanner类中的方法
     * 优点一: 可以获取键盘输入的字符串
     * 优点二: 有现成的获取int,float等类型数据，非常强大，也非常方便；
     */
    public static void scannerInfo(){
        Scanner sc = new Scanner(System.in);
        System.out.println("ScannerTest, Please Enter Name:");
        String name = sc.nextLine();  //读取字符串型输入
        System.out.println("ScannerTest, Please Enter Age:");
        int age = sc.nextInt();    //读取整型输入
        System.out.println("ScannerTest, Please Enter Salary:");
        float salary = sc.nextFloat(); //读取float型输入
        System.out.println("Your Information is as below:");
        System.out.println("Name:" + name +"\n" + "Age:"+age + "\n"+"Salary:"+salary);
    }
}
