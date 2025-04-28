/*
 * @Author: Ramon
 * @Date: 2025-04-28 14:46:00
 * @LastEditTime: 2025-04-28 14:49:08
 * @FilePath: /DesignPattern/app/src/main/java/org/example/bridge/BridgeTest.java
 * @Description:
 */
package org.example.bridge;

public class BridgeTest {
    public static void bridgeTest() {
        Shape vectorCircle = new Circle(100, 10, 10, new VectorDrawAPI());
        Shape rasterCircle = new Circle(100, 10, 10, new RasterDrawAPI());

        vectorCircle.draw();
        rasterCircle.draw();
    }
}
