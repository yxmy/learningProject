package com.yx.mygroup.learning.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yuanxin
 * @date 2022/1/12
 */
public class Nio2Server {

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8080));
            serverSocketChannel.configureBlocking(false);

            //获取selector
            final Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                //返回就绪的channel，阻塞，直到至少又一个channel就绪
                selector.select();

                System.out.println("返回就绪的channel");
                final Set<SelectionKey> selectionKeys = selector.selectedKeys();
                final Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    final SelectionKey selectionKey = iterator.next();
                    //删除
                    iterator.remove();
                    handler(selectionKey);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handler(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isAcceptable()) {
            System.out.println("有新的客户端建立连接");
            final SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
            socketChannel.configureBlocking(false);
            //注册事件
            socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
        } else if (selectionKey.isReadable()) {
            final SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            final int read = socketChannel.read(byteBuffer);
            if (read > 0) {
                byteBuffer.flip();
                System.out.println("读取客户端发送的数据：" + new String(byteBuffer.array(), 0, read));
                byteBuffer.clear();
            }
        }
    }

}
