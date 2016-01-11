package com.iteye.baowp.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by baoweipeng on 1/11/16.
 */
public class ThreadStopExample {

    private static boolean stop;

    public static void main(String[] args) throws InterruptedException{
        Thread workThread=new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(!stop){
                    i++;
                    try{
                        TimeUnit.MILLISECONDS.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println(i);
                }
            }
        });
        workThread.start();
        TimeUnit.SECONDS.sleep(3);
        stop=true;
        System.out.println("stop is already true");
        Thread.sleep(3000);
    }
}
