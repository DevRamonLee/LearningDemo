/*
 * @Author: Ramon
 * @Date: 2025-04-25 10:35:43
 * @LastEditTime: 2025-04-25 10:35:57
 * @FilePath: /DesignPattern/app/src/main/java/org/example/strategy/BlockEnemy.java
 * @Description:第三个妙计，逃跑时孙夫人断后
 */
package org.example.strategy;

public class BlockEnemy implements IStrategy {
    public void operate() {
            System.out.println("孙夫人断后，挡住追兵");
    }
}