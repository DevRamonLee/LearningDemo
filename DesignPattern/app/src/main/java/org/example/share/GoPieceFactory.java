/*
 * @Author: Ramon
 * @Date: 2025-04-28 12:51:05
 * @LastEditTime: 2025-04-28 12:52:50
 * @FilePath: /DesignPattern/app/src/main/java/org/example/share/GoPieceFactory.java
 * @Description:
 */
package org.example.share;

import java.util.HashMap;
import java.util.Map;

public class GoPieceFactory {
    private static final Map<String, GoPiece> pieceMap = new HashMap<>();

    public static GoPiece getGoPiece(String color) {
        GoPiece piece = pieceMap.get(color);
        if (piece == null) {
            piece = new ConcreteGoPiece(color);
            pieceMap.put(color, piece);
        }
        return piece;
    }
}
