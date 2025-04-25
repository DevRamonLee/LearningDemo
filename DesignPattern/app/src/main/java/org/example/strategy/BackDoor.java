/*
 * @Author: Ramon
 * @Date: 2025-04-25 10:33:26
 * @LastEditTime: 2025-04-25 10:33:49
 * @FilePath: /DesignPattern/app/src/main/java/org/example/strategy/BackDoor.java
 * @Description:第一个妙计，找乔国老开后门
 */
package org.example.strategy;

public class BackDoor implements IStrategy {
    public void operate() {
            System.out.println("找乔国老帮忙，让吴国太给孙权施加压力");
    }
}