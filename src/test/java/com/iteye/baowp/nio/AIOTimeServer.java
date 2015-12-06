package com.iteye.baowp.nio;

import com.iteye.baowp.nio.handle.AsyncTimeServerHandler;

/**
 * Created by baowp on 15-1-11.
 */
public class AIOTimeServer {

    public static void main(String[] args) {
        int port = 9000;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
            }
        }
        AsyncTimeServerHandler timeServerHandler = new AsyncTimeServerHandler(port);
        new Thread(timeServerHandler, "AIO-AsyncTimeServerHandler-001").start();
    }
}
