/*
 * @Author: Ramon
 * @Date: 2025-03-31 21:43:11
 * @LastEditTime: 2025-04-01 23:31:48
 * @FilePath: /NIODemo/app/src/test/java/org/example/AsyncChannelTest.java
 * @Description: 
 */
package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class AsyncChannelTest {

    Object a;
    @Test
    public void testAsyncChannel()  throws InterruptedException {
        Thread serverThread = new Thread(this::runServer);
        Thread clientThread = new Thread(this::runClient);

        serverThread.start();
        Thread.sleep(1000); // 确保服务器先启动
        clientThread.start();
        Thread.sleep(5000);
    }

    public void runServer() {
        try {
            // 1. 创建异步服务器通道
            AsynchronousServerSocketChannel serverChannel =
            AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8090));
            System.out.println("服务器启动，监听端口 8090...");

            // 2. 等待客户端连接（异步回调）
            serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                @Override
                public void completed(AsynchronousSocketChannel clientChannel, Void attachment) {
                    System.out.println("客户端连接：" + clientChannel);

                    // 继续监听新的客户端连接
                    serverChannel.accept(null, this);

                    // 3. 处理客户端数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    clientChannel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer buffer) {
                            if (result > 0) {
                                buffer.flip();
                                String msg = new String(buffer.array(), 0, result);
                                System.out.println("收到客户端消息：" + msg);

                                // 4. 发送响应
                                ByteBuffer responseBuffer = ByteBuffer.wrap(("服务器收到：" + msg).getBytes());
                                clientChannel.write(responseBuffer, responseBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                                    @Override
                                    public void completed(Integer writeResult, ByteBuffer responseBuffer) {
                                        if (responseBuffer.hasRemaining()) {
                                            clientChannel.write(responseBuffer, responseBuffer, this); // 继续写入剩余数据
                                        } else {
                                            responseBuffer.flip(); // 确保从头读取
                                            String response = StandardCharsets.UTF_8.decode(responseBuffer).toString();
                                            System.out.println("服务器即将发送的消息：" + response);
                                        }
                                    }
                                
                                    @Override
                                    public void failed(Throwable exc, ByteBuffer responseBuffer) {
                                        System.err.println("服务器发送数据失败：" + exc.getMessage());
                                    }
                                });
                            }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            System.err.println("读取失败：" + exc.getMessage());
                        }
                    });
                }

                @Override
                public void failed(Throwable exc, Void attachment) {
                    System.err.println("客户端连接失败：" + exc.getMessage());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void runClient() {
        try {
            // 1. 创建异步客户端通道
            AsynchronousSocketChannel clientChannel = AsynchronousSocketChannel.open();

            // 2. 连接服务器
            clientChannel.connect(new InetSocketAddress("127.0.0.1", 8090), null,
                    new CompletionHandler<Void, Void>() {
                        @Override
                        public void completed(Void result, Void attachment) {
                            System.out.println("成功连接到服务器");

                            // 3. 发送数据
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            buffer.put("你好，服务器".getBytes());
                            buffer.flip();
                            clientChannel.write(buffer);

                            // 4. 读取服务器的响应
                            buffer.clear();
                            clientChannel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                                @Override
                                public void completed(Integer result, ByteBuffer buffer) {
                                    if (result > 0) {
                                        buffer.flip(); // 关键：翻转 Buffer，确保正确读取数据
                                        byte[] data = new byte[buffer.remaining()]; // 只获取实际读取的数据
                                        buffer.get(data);
                                        System.out.println("收到服务器回复：" + new String(data)); // 确保完整输出
                                    }
                                }

                                @Override
                                public void failed(Throwable exc, ByteBuffer attachment) {
                                    System.err.println("读取失败：" + exc.getMessage());
                                }
                            });
                        }

                        @Override
                        public void failed(Throwable exc, Void attachment) {
                            System.err.println("连接失败：" + exc.getMessage());
                        }
                    });
        } catch(IOException e) {
                e.printStackTrace();
        }
    }
}
