/*
 * @Author: Ramon
 * @Date: 2025-04-28 10:12:23
 * @LastEditTime: 2025-04-28 10:13:52
 * @FilePath: /DesignPattern/app/src/main/java/org/example/expression/SubExpression.java
 * @Description:非终结表达式，减法解释器
 */
package org.example.expression;

import java.util.HashMap;

public class SubExpression extends SymbolExpression {
    public SubExpression(Expression _left, Expression _right) {
        super(_left, _right);
    }

    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return super.left.interpreter(var) - super.right.interpreter(var);
    }
}
