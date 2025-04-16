/*
 * @Author: Ramon
 * @Date: 2025-03-31 17:19:03
 * @LastEditTime: 2025-03-31 17:19:27
 * @FilePath: /NIODemo/app/src/test/java/org/example/FileChannelTest.java
 * @Description: 
 */
package org.example;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

public class FileChannelTest {
    @Test public void testFileChannel() throws Exception {
        System.out.println("Current working directory: " + System.getProperty("user.dir"));

        ByteBuffer buf = ByteBuffer.allocate(1024);
        FileChannel fChannel = FileChannel.open(Paths.get("data.txt"), StandardOpenOption.READ);
        fChannel.read(buf);
        System.out.println("开始切换 buf 为读模式， position=" + buf.position());
        //从Buffer中取数据
        buf.flip();
        System.out.println("切换buf为读模式完成,position = " + buf.position());
        System.out.println(new String(buf.array()));
        buf.clear();//不是是清除，而将数据标记为脏数据(无用数据)
        //释放资源
        fChannel.close();
    }

    @Test public void testFileChannel2()throws Exception{
        //构建一个Buffer对象(缓冲区)：JVM内存
        ByteBuffer buf=ByteBuffer.allocate(2);
        //构建一个文件通道对象(可以读写数据)
        FileChannel fChannel=FileChannel.open(Paths.get("data.txt"),StandardOpenOption.READ);//读模式
        //将文件内容读到缓冲区(ByteBuffer)
        int len=-1;
        do{
            len=fChannel.read(buf);
            System.out.println("切换buf模式，开始从buf读数据");
            buf.flip();
            //判定缓冲区中是否有剩余数据
            while(buf.hasRemaining()){
                System.out.print((char)buf.get());//每次都1个字节
            }
            System.out.println();
            buf.flip();
            buf.clear();//每次读数据应将原数据设置为无效。
        }while(len!=-1);
        //释放资源
        fChannel.close();
    }    
}
