/*
 * @Author: Ramon
 * @Date: 2025-04-14 10:38:59
 * @LastEditTime: 2025-04-14 10:40:13
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/normal/YellowHuman.java
 * @Description: 
 */
package org.example.factory.normal;

public class YellowHuman implements Human {

    @Override
    public void getColor() {
        System.out.println("黄色人种皮肤是黄色的");
    }

    @Override
    public void talk() {
        System.out.println("黄色人种会说话，一般说的是双字节");
    }
    
}
