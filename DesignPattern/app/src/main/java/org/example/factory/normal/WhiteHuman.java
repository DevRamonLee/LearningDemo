/*
 * @Author: Ramon
 * @Date: 2025-04-14 10:40:27
 * @LastEditTime: 2025-04-14 10:41:44
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/normal/WhiteHuman.java
 * @Description: 
 */
package org.example.factory.normal;

public class WhiteHuman implements Human {

    @Override
    public void getColor() {
        System.out.println("白色人种的皮肤是白色的");
    }

    @Override
    public void talk() {
        System.out.println("白色人种会说话，一般是单字节");
    }
    
}
