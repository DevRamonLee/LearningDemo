/*
 * @Author: Ramon
 * @Date: 2025-04-28 10:22:49
 * @LastEditTime: 2025-04-28 10:36:33
 * @FilePath: /DesignPattern/app/src/main/java/org/example/expression/ExpressionTest.java
 * @Description:
 */
package org.example.expression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ExpressionTest {
    public static void expressionTest() {
        try {
            String expStr = getExpStr();
            HashMap<String, Integer> var = getValue(expStr);
            Calculator cal = new Calculator(expStr);
            System.out.println("运算结果为: " + cal.run(var));
        } catch (Exception e) {

        }
    }

    public static String getExpStr() throws IOException {
        System.out.println("请输入表达式");
        return (new BufferedReader(new InputStreamReader(System.in))).readLine();
    }

    // 获得值映射
    public static HashMap<String, Integer> getValue(String exprStr) throws  IOException {
        HashMap<String, Integer> map = new HashMap<>();
        for (char ch: exprStr.toCharArray()) {
            if (ch != '+' && ch != '-') {
                // 解决重复参数
                if (!map.containsKey(String.valueOf(ch))) {
                    System.out.println("请输入 " + ch + " 的值");
                    String in = (new BufferedReader(new InputStreamReader(System.in))).readLine();
                    map.put(String.valueOf(ch), Integer.valueOf(in));
                }
            }
        }
        return map;
    }
}
