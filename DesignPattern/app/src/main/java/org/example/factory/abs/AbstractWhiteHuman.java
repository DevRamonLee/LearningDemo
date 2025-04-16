/*
 * @Author: Ramon
 * @Date: 2025-04-15 13:30:54
 * @LastEditTime: 2025-04-15 13:32:19
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/abs/AbstractWhiteHuman.java
 * @Description: 
 */
package org.example.factory.abs;

public abstract class AbstractWhiteHuman implements Human2{
    //白色人种的颜色是白色的
    public void getColor(){
        System.out.println("白色人种的皮肤颜色是白色的！");
    }
    //白色人种讲话
    public void talk() {
        System.out.println("白色人种会说话，一般说的都是单字节。");
    }
}
