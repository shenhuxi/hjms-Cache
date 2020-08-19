package com.pci.hjmos.cache.util.play;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LockWindows {
    private static final Long sleepTime = 10*1000L;

    public static void main(String[] args) throws InterruptedException {
        String batPath = "C:\\Users\\ying\\zpFile\\newLock\\lock.bat";
        File batFile = new File(batPath);
        boolean batFileExist = batFile.exists();
        System.out.println("batFileExist:" + batFileExist);

        new Thread(() -> {
            while (true) try {
                if (batFileExist) {
                    callCmd(batPath);
                }
                Thread.sleep(sleepTime);
            } catch (InterruptedException  e) {
                e.printStackTrace();
            }
        }).start();


    }

    private static void  callCmd(String locationCmd){
        StringBuilder sb = new StringBuilder();
        try {
            Process child = Runtime.getRuntime().exec("cmd /c start  "+locationCmd);
            InputStream in = child.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
            String line;
            while((line=bufferedReader.readLine())!=null)
            {
                sb.append(line + "\n");
            }
            in.close();
            try {
                child.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println("sb:" + sb.toString());
            System.out.println("callCmd execute finished");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}