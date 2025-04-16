/*
 * @Author: Ramon
 * @Date: 2025-04-15 13:41:27
 * @LastEditTime: 2025-04-15 13:43:05
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/abs/FemaleFactory.java
 * @Description: 
 */
package org.example.factory.abs;

public class FemaleFactory extends AbstractHumanFactory2 {

    @Override
    public Human2 createBlackMan() {
        return new BlackFemale();
    }

    @Override
    public Human2 createWhiteMan() {
        return new WhiteFemale();
    }
}
