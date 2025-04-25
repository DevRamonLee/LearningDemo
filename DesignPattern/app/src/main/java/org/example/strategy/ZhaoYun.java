/*
 * @Author: Ramon
 * @Date: 2025-04-25 10:39:32
 * @LastEditTime: 2025-04-25 10:44:11
 * @FilePath: /DesignPattern/app/src/main/java/org/example/strategy/ZhaoYun.java
 * @Description:
 */
package org.example.strategy;

public class ZhaoYun {
    //赵云出场了，他根据诸葛亮给他的交代，依次拆开妙计
    public static void strategyTest() {
            Context context;
            //刚刚到吴国的时候拆第一个
            System.out.println("---刚刚到吴国的时候拆第一个---");
            context = new Context(new BackDoor()); //拿到妙计
            context.operate();  //拆开执行
            System.out.println("\n");
            //刘备乐不思蜀了，拆第二个了
            System.out.println("---刘备乐不思蜀了，拆第二个了---");
            context = new Context(new GivenGreenLight());
            context.operate();  //执行了第二个锦囊
            System.out.println("\n");
            //孙权的小兵追了，咋办？拆第三个
            System.out.println("---孙权的小兵追了，咋办？拆第三个---");
            context = new Context(new BlockEnemy());
            context.operate();  //孙夫人退兵
     }
}