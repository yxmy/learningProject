package com.yx.mygroup.learning.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yuanxin
 * @date 2022/1/12
 */
public class BioServer {

    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(8080));
            System.out.println("开启服务");

            while (true) {
                System.out.println("等待客户端建立连接");
                //阻塞，等待客户端链接
                final Socket socket = serverSocket.accept();
                System.out.println("建立连接：" + socket);

                executorService.execute(() -> {
                    try {
                        handler(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handler(Socket socket) throws IOException {
        while (true) {
            byte [] bytes = new byte[1024];
            System.out.println("等待读取数据");
            final int read = socket.getInputStream().read(bytes);
            if (read!=-1) {
                System.out.println("读取客户端发送的数据：" + new String(bytes, 0, read));
            } else {
                break;
            }
        }
    }

}
