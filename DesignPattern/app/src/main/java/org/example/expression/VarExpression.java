/*
 * @Author: Ramon
 * @Date: 2025-04-28 10:05:16
 * @LastEditTime: 2025-04-28 10:06:58
 * @FilePath: /DesignPattern/app/src/main/java/org/example/expression/VarExpression.java
 * @Description:
 */
package org.example.expression;

import java.util.HashMap;

public class VarExpression extends Expression {
    private String key;
    public VarExpression(String _key) {
        this.key = _key;
    }

    // 从 map 中根据 key 取出值
    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return var.get(this.key);
    }
}
