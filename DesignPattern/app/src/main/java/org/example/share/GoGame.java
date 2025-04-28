/*
 * @Author: Ramon
 * @Date: 2025-04-28 12:53:06
 * @LastEditTime: 2025-04-28 13:00:10
 * @FilePath: /DesignPattern/app/src/main/java/org/example/share/GoGame.java
 * @Description:享元模式示例
 */
package org.example.share;

public class GoGame {
    public static void shareTest() {
        GoPiece blackPiece = GoPieceFactory.getGoPiece("Black");
        GoPiece whitePiece = GoPieceFactory.getGoPiece("White");

        GoPosition position1 = new GoPosition(1, 2);
        GoPosition position2 = new GoPosition(3, 0);

        position1.placePiece(whitePiece);
        position2.placePiece(blackPiece);
    }
}
