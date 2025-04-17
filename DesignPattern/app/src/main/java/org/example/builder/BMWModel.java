/*
 * @Author: Ramon
 * @Date: 2025-04-17 10:27:18
 * @LastEditTime: 2025-04-17 10:27:31
 * @FilePath: /DesignPattern/app/src/main/java/org/example/builder/BMWModel.java
 * @Description: 
 */
package org.example.builder;

public class BMWModel extends CarModel {
    protected void alarm() {
            System.out.println("宝马车的喇叭声音是这个样子的...");
    }
    protected void engineBoom() {
            System.out.println("宝马车的引擎室这个声音的...");
    }
    protected void start() {
            System.out.println("宝马车跑起来是这个样子的...");
    }
    protected void stop() {
            System.out.println("宝马车应该这样停车...");
    }
}