/*
 * @Author: Ramon
 * @Date: 2025-04-28 10:14:04
 * @LastEditTime: 2025-04-28 11:02:25
 * @FilePath: /DesignPattern/app/src/main/java/org/example/expression/Calculator.java
 * @Description:解析器封装类
 */
package org.example.expression;

import java.util.HashMap;
import java.util.Stack;

public class Calculator {
    // 定义表达式
    private Expression expression;
    // 构造函数传参并解析

    public Calculator(String expStr) {
        // 定义一个栈，负责运算先后顺序
        Stack<Expression> stack = new Stack<Expression>();
        // 将表达式拆分为字符数组
        char arr[] = expStr.toCharArray();
        Expression left = null;
        Expression right = null;

        for (int i = 0; i < arr.length; i++) {
            switch (arr[i]) {
                case '+':
                    left = stack.pop();
                    right = new VarExpression(String.valueOf(arr[++i]));
                    stack.push(new AddExpression(left, right));
                    break;
                case '-':
                    left = stack.pop();
                    right = new VarExpression(String.valueOf(arr[++i]));
                    stack.push(new SubExpression(left, right));
                    break;
                default:
                    // 数字或者变量（目前只处理数字）
                    stack.push(new VarExpression(getFullNumber(arr, i)));
                    // 注意跳过已经读取的数字字符
                    while (i + 1 < arr.length && Character.isDigit(arr[i + 1])) {
                        i++;
                    }
                    break;
            }
        }
        this.expression = stack.pop();
    }

    // 读取完整数字或者变量
    private String getFullNumber(char[] arr, int start) {
        StringBuilder sb = new StringBuilder();
        sb.append(arr[start]);
        int i = start + 1;
        while (i < arr.length && Character.isDigit(arr[i])) {
            sb.append(arr[i]);
            i++;
        }
        return sb.toString();
    }


    // 开始运算
    public int run(HashMap<String, Integer> var) {
        return this.expression.interpreter(var);
    }
}
