/*
 * @Author: Ramon
 * @Date: 2025-04-28 12:45:27
 * @LastEditTime: 2025-04-28 12:46:41
 * @FilePath: /DesignPattern/app/src/main/java/org/example/share/ConcreteGoPiece.java
 * @Description:
 */
package org.example.share;

// 可共享类，代表黑棋和白棋
public class ConcreteGoPiece implements GoPiece {
    // 注意这里使用的是 final
    private final String color;

    public ConcreteGoPiece(String color) {
        this.color = color;
    }

    @Override
    public String getColor() {
        return color;
    }
}
