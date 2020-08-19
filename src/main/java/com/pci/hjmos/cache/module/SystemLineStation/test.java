package com.pci.hjmos.cache.module.SystemLineStation;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/5/22 20:56
 * @Modified By
 */
public class test {
    public static void ss() {

        int i= 1;
        while(i<=15){
            i++;
            if(i%3!=2)
                continue;
            else
                System.out.println(i);break;
        }
    }

    public int count(int i){
        int count = 0;
        for (int j = 1; j <= i; j++) {
            count+=j ;
        }
        return count;
    }
}
