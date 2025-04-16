/*
 * @Author: Ramon
 * @Date: 2025-03-29 11:34:09
 * @LastEditTime: 2025-04-14 10:00:36
 * @FilePath: /DesignPattern/app/src/main/java/org/example/Utils.java
 * @Description: 工具类
 */
package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    /**
     * 打印当前
     */
    public static void printTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss.SSS");
        System.out.println("当前时间是：" + now.format(formatter));
    }
}