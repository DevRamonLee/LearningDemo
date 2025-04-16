/*
 * @Author: Ramon
 * @Date: 2025-03-31 17:19:42
 * @LastEditTime: 2025-03-31 18:16:39
 * @FilePath: /NIODemo/app/src/test/java/org/example/SocketChannelTest.java
 * @Description: 
 */
package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

import org.junit.Test;

public class SocketChannelTest {

    @Test
    public void testServerAndClient() throws InterruptedException {
        Thread serverThread = new Thread(this::runServer);
        Thread clientThread = new Thread(this::runClient);

        serverThread.start();
        Thread.sleep(1000); // 确保服务器先启动
        clientThread.start();

        // 主线程等待 serverThread 和 clientThread 执行完成
        serverThread.join();
        clientThread.join();
    }

    public void runServer() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(8090));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("服务器已启动，监听端口 8090...");

            while (true) {
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    if (key.isAcceptable()) {
                        SocketChannel clientChannel = serverChannel.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println("新客户端连接：" + clientChannel.getRemoteAddress());
                    } else if (key.isReadable()) {
                        SocketChannel clientChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int bytesRead = clientChannel.read(buffer);
                        if (bytesRead > 0) {
                            buffer.flip();
                            String message = new String(buffer.array(), 0, bytesRead);
                            System.out.println("收到客户端消息：" + message);

                            // 服务器回复消息
                            String response = "服务端已收到：" + message;
                            clientChannel.register(selector, SelectionKey.OP_WRITE, ByteBuffer.wrap(response.getBytes()));
                        } else if (bytesRead == -1) { // 客户端关闭连接
                            clientChannel.close();
                        }
                    } else if(key.isWritable()) {
                        // 发送消息给客户端
                        SocketChannel clientChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer)key.attachment();
                        clientChannel.write(buffer);
                        if (!buffer.hasRemaining()) {
                            key.interestOpsOr(SelectionKey.OP_READ);    // 发送完毕切换成读模式
                        }

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runClient() {
        try {
            Selector selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8090));

            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    if (key.isConnectable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        if (sc.finishConnect()) {
                            sc.register(selector, SelectionKey.OP_WRITE);
                            System.out.println("客户端连接成功：" + sc.getRemoteAddress());
                        }
                    } else if (key.isWritable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.wrap("Hello Server".getBytes());
                        sc.write(buffer);
                        sc.register(selector, SelectionKey.OP_READ); // 监听服务器的消息
                    } else if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        SocketChannel sc = (SocketChannel) key.channel();
                        int bytesRead = sc.read(buffer);
                        if (bytesRead > 0) {
                            buffer.flip();
                            System.out.println("收到服务器消息：" + new String(buffer.array(), 0, bytesRead));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}