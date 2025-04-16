/*
 * @Author: Ramon
 * @Date: 2025-04-15 13:39:27
 * @LastEditTime: 2025-04-15 13:40:05
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/abs/BlackMale.java
 * @Description: 
 */
package org.example.factory.abs;

public class BlackMale extends AbstractBlackHuman {

    @Override
    public void getSex() {
        System.out.println("我是黑人男性");
    }
    
}
