package com.yx.mygroup.learning.io;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanxin
 * @date 2022/1/12
 */
public class NioServer {


    public static void main(String[] args) {
        List<SocketChannel> socketChannels = new ArrayList<>();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8080));
            //channel非阻塞
            serverSocketChannel.configureBlocking(false);
            System.out.println("NioServer 启动...");

            while (true) {
                Thread.sleep(1000);
                final SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel == null) {
                    System.out.println("没有新的客户端连接");
                } else {
                    System.out.println("新的客户端建立连接");
                    socketChannel.configureBlocking(false);
                    socketChannels.add(socketChannel);
                }
                for (SocketChannel channel : socketChannels) {
                    final int read = channel.read(byteBuffer);
                    byteBuffer.flip();
                    System.out.println("读取客户端发送的数据：" + new String(byteBuffer.array(), 0, read));
                    byteBuffer.clear();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
