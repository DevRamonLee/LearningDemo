/*
 * @Author: Ramon
 * @Date: 2025-04-14 10:15:33
 * @LastEditTime: 2025-04-14 10:16:27
 * @FilePath: /DesignPattern/app/src/main/java/org/example/factory/normal/Human.java
 * @Description: 
 */
package org.example.factory.normal;

public interface Human {
    // 每个人种的皮肤都有相应的颜色
    public void getColor();
    // 人类会说话
    public void talk();
}
