/*
 * @Author: Ramon
 * @Date: 2025-04-25 10:36:57
 * @LastEditTime: 2025-04-25 10:39:14
 * @FilePath: /DesignPattern/app/src/main/java/org/example/strategy/Context.java
 * @Description:封装类，用于承载策略
 */
package org.example.strategy;

public class Context {
    //构造函数，你要使用哪个妙计
    private IStrategy straegy;
    public Context(IStrategy strategy){
            this.straegy = strategy;
    }
    //使用计谋了，看我出招了
    public void operate(){
            this.straegy.operate();
    }
}