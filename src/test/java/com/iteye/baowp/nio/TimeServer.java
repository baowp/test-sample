package com.iteye.baowp.nio;

import com.iteye.baowp.nio.handle.MultiplexerTimeServer;

import java.io.IOException;

/**
 * Created by baowp on 14-12-17.
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 9000;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
            }
        }
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-multiplexerTimerServer-001").start();
    }
}
