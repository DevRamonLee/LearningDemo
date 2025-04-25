/*
 * @Author: Ramon
 * @Date: 2025-03-29 10:45:43
 * @LastEditTime: 2025-04-24 15:14:44
 * @FilePath: /DesignPattern/app/src/main/java/org/example/chain/ChainTest.java
 * @Description:
 */
package org.example.chain;

import java.util.ArrayList;
import java.util.Random;

public class ChainTest {
    public static void chainTest() {
        // 随机挑选几个女性
        Random rand = new Random();
        ArrayList<IWomen> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            arrayList.add(new Women(rand.nextInt(4), "我要去逛街"));
        }
        // 定义三个请示对象
        Handler father = new Father();
        Handler husband = new Husband();
        Handler son = new Son();

        // 设置请示顺序
        father.setNext(husband);
        husband.setNext(son);
        for (IWomen women: arrayList) {
            father.handleMessage(women);
        }
    }
}