package com.iteye.baowp.nio;

import com.iteye.baowp.nio.handle.TimeClientHandle;

/**
 * Created by baowp on 15-1-10.
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 9000;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
            }
        }
        new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();
    }
}
