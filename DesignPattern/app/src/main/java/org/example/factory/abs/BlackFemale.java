/*
 * @Author: Ramon
 * @Date: 2025-04-15 13:40:18
 * @LastEditTime: 2025-04-15 13:40:31
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/abs/BlackFemale.java
 * @Description: 
 */
package org.example.factory.abs;

public class BlackFemale extends AbstractBlackHuman {

    @Override
    public void getSex() {
        System.out.println("我是黑人女性");
    }
    
}
