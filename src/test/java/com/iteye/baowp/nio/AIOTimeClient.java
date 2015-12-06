package com.iteye.baowp.nio;

import com.iteye.baowp.nio.handle.AsyncTimeClientHandler;

/**
 * Created by baowp on 15-1-11.
 */
public class AIOTimeClient {

    public static void main(String[] args){
        int port = 9000;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
            }
        }
        new Thread(new AsyncTimeClientHandler("127.0.0.1",port),"AIO-AsyncTimeClientHandler-001").start();
    }
}
