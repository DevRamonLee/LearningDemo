/*
 * @Author: Ramon
 * @Date: 2025-04-28 10:07:32
 * @LastEditTime: 2025-04-28 10:09:08
 * @FilePath: /DesignPattern/app/src/main/java/org/example/expression/SymbolExpression.java
 * @Description:
 */
package org.example.expression;

public abstract class SymbolExpression extends Expression {
    protected Expression left;
    protected Expression right;

    // 所有解释公式只关心自己左右两个表达式的结果
    public SymbolExpression(Expression _left, Expression _right) {
        this.left = _left;
        this.right = _right;
    }
}
