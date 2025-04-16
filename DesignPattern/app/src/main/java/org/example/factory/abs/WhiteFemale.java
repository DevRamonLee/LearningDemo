/*
 * @Author: Ramon
 * @Date: 2025-04-15 13:38:26
 * @LastEditTime: 2025-04-15 13:39:07
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/abs/WhiteFemale.java
 * @Description: 
 */
package org.example.factory.abs;

public class WhiteFemale extends AbstractWhiteHuman {

    @Override
    public void getSex() {
        System.out.println("我是白人女性");
    }
}
