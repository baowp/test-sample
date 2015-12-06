package com.iteye.baowp.io;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 12/17/13
 * Time: 1:13 PM
 */
public class SocketTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final int port = 9000;

    @Test
    public void client() {
        try {
            //1.建立客户端socket连接，指定服务器位置及端口
            Socket socket = new Socket("localhost", port);
            //2.得到socket读写流
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            //输入流
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            //3.利用流按照一定的操作，对socket进行读写操作
            String info = "用户名：Tom,用户密码：123456";
            pw.write(info);
            pw.flush();
            socket.shutdownOutput();
            //接收服务器的相应
            String reply = null;
            while (!((reply = br.readLine()) == null)) {
                logger.info("接收服务器的信息：" + reply);
            }
            //4.关闭资源
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void server() {
        try {
            //1.建立一个服务器Socket(ServerSocket)绑定指定端口
            ServerSocket serverSocket = new ServerSocket(port);
            for (int i = 0; i < 3; i++) {
                //2.使用accept()方法阻止等待监听，获得新连接
                Socket socket = serverSocket.accept();
                //3.获得输入流
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                //获得输出流
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                //4.读取用户输入信息
                String info = null;
                while (!((info = br.readLine()) == null)) {
                    logger.info("我是服务器，用户信息为：" + info);
                }
                //给客户一个响应
                String reply = "welcome" + i;
                pw.write(reply);
                pw.flush();
                //5.关闭资源
                pw.close();
                os.close();
                br.close();
                is.close();
                socket.close();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

