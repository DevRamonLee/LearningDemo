/*
 * @Author: Ramon
 * @Date: 2025-04-14 10:36:53
 * @LastEditTime: 2025-04-14 10:38:30
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/normal/BlackHuman.java
 * @Description: 
 */
package org.example.factory.normal;

public class BlackHuman implements Human {

    @Override
    public void getColor() {
        System.out.println("黑色人种皮肤是黑色的");
    }

    @Override
    public void talk() {
        System.out.println("黑人会说话，一般听不懂");
    }
    
}
