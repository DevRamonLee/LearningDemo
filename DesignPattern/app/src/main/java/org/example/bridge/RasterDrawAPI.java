/*
 * @Author: Ramon
 * @Date: 2025-04-28 14:40:14
 * @LastEditTime: 2025-04-28 14:40:28
 * @FilePath: /DesignPattern/app/src/main/java/org/example/bridge/RasterDrawAPI.java
 * @Description:
 */
package org.example.bridge;

public class RasterDrawAPI implements DrawAPI {
    @Override
    public void drawCircle(int x, int y, int radius) {
        System.out.println("用像素画一个圆");
    }
}
