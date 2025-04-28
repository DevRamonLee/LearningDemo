/*
 * @Author: Ramon
 * @Date: 2025-04-28 14:39:18
 * @LastEditTime: 2025-04-28 14:39:29
 * @FilePath: /DesignPattern/app/src/main/java/org/example/bridge/VectorDrawAPI.java
 * @Description:
 */
package org.example.bridge;

public class VectorDrawAPI implements DrawAPI {
    @Override
    public void drawCircle(int x, int y, int radius) {
        System.out.println("用矢量图画一个圆");
    }
}
