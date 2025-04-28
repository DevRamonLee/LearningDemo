/*
 * @Author: Ramon
 * @Date: 2025-04-28 10:09:51
 * @LastEditTime: 2025-04-28 10:11:25
 * @FilePath: /DesignPattern/app/src/main/java/org/example/expression/AddExpression.java
 * @Description:非终结表达式，加法解释器
 */
package org.example.expression;

import java.util.HashMap;

public class AddExpression extends SymbolExpression {
    public AddExpression(Expression _left, Expression _right) {
        super(_left, _right);
    }

    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return super.left.interpreter(var) + super.right.interpreter(var);
    }
}
