/*
 * @Author: Ramon
 * @Date: 2025-04-28 12:47:49
 * @LastEditTime: 2025-04-28 12:50:46
 * @FilePath: /DesignPattern/app/src/main/java/org/example/share/GoPosition.java
 * @Description:
 */
package org.example.share;

// UnsharedConcreteFlyweight 类，代表棋子的位置
public class GoPosition {
    private final int x;
    private final int y;

    public GoPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 提供额外的方法来操作棋子
    public void placePiece(GoPiece piece) {
        System.out.println("Placing " + piece.getColor() + " piece at (" + x + ", " + y + ")");
    }
}
