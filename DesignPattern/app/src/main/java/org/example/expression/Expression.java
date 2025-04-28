/*
 * @Author: Ramon
 * @Date: 2025-04-28 10:03:00
 * @LastEditTime: 2025-04-28 10:04:03
 * @FilePath: /DesignPattern/app/src/main/java/org/example/expression/Expression.java
 * @Description:
 */
package org.example.expression;

import java.util.HashMap;

public abstract class Expression {
    // 解析公式和数值，其中 var 值是公式中的参数，value 是具体的数字
    public abstract int interpreter(HashMap<String, Integer> var);
}
