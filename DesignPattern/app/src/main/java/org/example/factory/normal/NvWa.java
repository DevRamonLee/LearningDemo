/*
 * @Author: Ramon
 * @Date: 2025-04-14 10:49:49
 * @LastEditTime: 2025-04-14 10:55:02
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/normal/NvWa.java
 * @Description: 
 */
package org.example.factory.normal;

public class NvWa {
    public static void normalFactoryTest() {
        AbstractHumanFactory luZi = new HumanFactory();
        System.out.println("造出的第一批人是白色人种");
        Human whiteHuman = luZi.createHuman(WhiteHuman.class);
        whiteHuman.getColor();
        whiteHuman.talk();
        
        System.out.println("\n第二批人是黑色人种");
        Human blackHuman = luZi.createHuman(BlackHuman.class);
        blackHuman.getColor();
        blackHuman.talk();

        System.out.println("\n第三批造出的人是黄色人种");
        Human yellowHuman = luZi.createHuman(YellowHuman.class);
        yellowHuman.getColor();
        yellowHuman.talk();
    }
    
}
