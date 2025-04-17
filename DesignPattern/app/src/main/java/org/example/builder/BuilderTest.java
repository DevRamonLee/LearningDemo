/*
 * @Author: Ramon
 * @Date: 2025-04-17 10:35:15
 * @LastEditTime: 2025-04-17 10:39:34
 * @FilePath: /DesignPattern/app/src/main/java/org/example/builder/BuilderTest.java
 * @Description: 建造者模式测试类
 */
package org.example.builder;

public class BuilderTest {
    public static void  builderTest() {
        Director director = new Director();
        director.getABenzModel().run();
        director.getCBMWModel().run();
    }
}
