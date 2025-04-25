/*
 * @Author: Ramon
 * @Date: 2025-04-25 10:34:39
 * @LastEditTime: 2025-04-25 10:34:53
 * @FilePath: /DesignPattern/app/src/main/java/org/example/strategy/GivenGreenLight.java
 * @Description:第二个妙计，求吴国太开绿灯放行
 */
package org.example.strategy;

public class GivenGreenLight implements IStrategy {
    public void operate() {
            System.out.println("求吴国太开绿灯,放行！");
    }
}
