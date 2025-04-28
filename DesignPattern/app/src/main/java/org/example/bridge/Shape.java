/*
 * @Author: Ramon
 * @Date: 2025-04-28 14:41:10
 * @LastEditTime: 2025-04-28 14:41:57
 * @FilePath: /DesignPattern/app/src/main/java/org/example/bridge/Shape.java
 * @Description:定义形状，抽象部分
 */
package org.example.bridge;

public abstract class Shape {
    protected DrawAPI drawAPI;

    protected Shape(DrawAPI drawAPI) {
        this.drawAPI = drawAPI;
    }

    public abstract void draw();
}
