/*
 * @Author: Ramon
 * @Date: 2025-04-15 13:37:10
 * @LastEditTime: 2025-04-15 13:38:03
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/abs/WhiteMale.java
 * @Description: 
 */
package org.example.factory.abs;

public class WhiteMale extends AbstractWhiteHuman {

    @Override
    public void getSex() {
        System.out.println("我是白人男性");
    }
    
}
