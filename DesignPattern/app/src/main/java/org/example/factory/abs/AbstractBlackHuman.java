/*
 * @Author: Ramon
 * @Date: 2025-04-15 13:31:18
 * @LastEditTime: 2025-04-15 13:34:04
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/abs/AbstractBlackHuman.java
 * @Description: 
 */
package org.example.factory.abs;

public abstract class AbstractBlackHuman implements Human2{
    public void getColor() {
        System.out.println("黑色人种的皮肤颜色是黑色的！");
    }
    
    public void talk() {
        System.out.println("黑人会说话，一般人听不懂");
    }
}
