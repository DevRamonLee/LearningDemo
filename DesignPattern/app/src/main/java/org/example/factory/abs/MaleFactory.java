/*
 * @Author: Ramon
 * @Date: 2025-04-15 13:43:19
 * @LastEditTime: 2025-04-15 13:43:33
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/abs/MaleFactory.java
 * @Description: 
 */
package org.example.factory.abs;

public class MaleFactory extends AbstractHumanFactory2{

    @Override
    public Human2 createBlackMan() {
        return new BlackMale();
    }

    @Override
    public Human2 createWhiteMan() {
        return new WhiteMale();
    }
}
