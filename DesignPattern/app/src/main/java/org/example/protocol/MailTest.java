/*
 * @Author: Ramon
 * @Date: 2025-04-18 10:16:18
 * @LastEditTime: 2025-04-18 10:33:32
 * @FilePath: /DesignPattern/app/src/main/java/org/example/protocol/MailTest.java
 * @Description: 
 */
package org.example.protocol;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MailTest {
    //发送账单的数量，这个值是从数据库中获得
    private static int MAX_COUNT = 6;
    public static void mailTest() {
        // 定义线程池，最多 6 个线程
        ExecutorService executor = Executors.newFixedThreadPool(MAX_COUNT);

        //模拟发送邮件
        int i=0;
        //把模板定义出来，这个是从数据库中获得
        Mail mail = new Mail(new AdvTemplate());
        mail.setTail("XX银行版权所有");
        while(i < MAX_COUNT){       
                Mail copyMail = (Mail)mail.clone();                    
                //以下是每封邮件不同的地方
                copyMail.setAppellation(getRandString(5)+" 先生（女士）");
                copyMail.setReceiver(getRandString(5)+"@"+getRandString(8) +".com"); 
                //然后发送邮件
                i++;
                executor.submit(() -> sendMail(copyMail));
        }
        // 等提交的任务执行完毕后关闭
        executor.shutdown();
    }

    //发送邮件
     public static void sendMail(Mail mail){
            System.out.println("Current thread " + Thread.currentThread().getName());
            System.out.println("标题："+mail.getSubject() + "\t收件人："+mail.getReceiver()+"\t...发送成功！");
     }  
     //获得指定长度的随机字符串
     public static String getRandString(int maxLength){
             String source ="abcdefghijklmnopqrskuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
             StringBuffer sb = new StringBuffer();
             Random rand = new Random();
             for(int i=0;i<maxLength;i++){
                     sb.append(source.charAt(rand.nextInt(source.length())));
             }
             return sb.toString();              
     }
}
