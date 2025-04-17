/*
 * @Author: Ramon
 * @Date: 2025-04-17 10:25:55
 * @LastEditTime: 2025-04-17 10:26:18
 * @FilePath: /DesignPattern/app/src/main/java/org/example/builder/BenzModel.java
 * @Description: 
 */
package org.example.builder;

public class BenzModel extends CarModel {
    protected void alarm() {
            System.out.println("奔驰车的喇叭声音是这个样子的...");
    }
    protected void engineBoom() {
            System.out.println("奔驰车的引擎室这个声音的...");
    }
    protected void start() {
            System.out.println("奔驰车跑起来是这个样子的...");
    }
    protected void stop() {
            System.out.println("奔驰车应该这样停车...");
    }
}