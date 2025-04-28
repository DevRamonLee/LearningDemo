/*
 * @Author: Ramon
 * @Date: 2025-04-28 14:42:30
 * @LastEditTime: 2025-04-28 14:45:41
 * @FilePath: /DesignPattern/app/src/main/java/org/example/bridge/Circle.java
 * @Description:
 */
package org.example.bridge;

public class Circle extends Shape {
    private int x, y, radius;

    public Circle(int x, int y, int radius, DrawAPI drawAPI) {
        super(drawAPI);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw() {
        drawAPI.drawCircle(x, y, radius);
    }
}
