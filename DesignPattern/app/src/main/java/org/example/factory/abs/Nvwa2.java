/*
 * @Author: Ramon
 * @Date: 2025-04-15 13:44:27
 * @LastEditTime: 2025-04-15 13:48:31
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/abs/Nvwa2.java
 * @Description: 
 */
package org.example.factory.abs;

public class Nvwa2 {

    public static void abstractFactoryTest() {
        AbstractHumanFactory2 maleFac = new MaleFactory();
        AbstractHumanFactory2 femaleFac = new FemaleFactory();

        System.out.println("开始生产白人女性\n");
        WhiteFemale whiteFemale = (WhiteFemale)femaleFac.createWhiteMan();
        whiteFemale.getColor();
        whiteFemale.getSex();
        whiteFemale.talk();

        System.out.println("开始生产黑人男性\n");
        BlackMale blackMale = (BlackMale)maleFac.createBlackMan();
        blackMale.getColor();
        blackMale.getSex();
        blackMale.talk();
    }
    
}
