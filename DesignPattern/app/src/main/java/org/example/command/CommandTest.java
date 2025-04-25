/*
 * @Author: Ramon
 * @Date: 2025-04-24 12:59:26
 * @LastEditTime: 2025-04-24 12:59:57
 * @FilePath: /DesignPattern/app/src/main/java/org/example/command/CommandTest.java
 * @Description:
 */
package org.example.command;

public class CommandTest {
    public static void commandTest() {
             //定义我们的接头人
             Invoker xiaoSan = new Invoker();  //接头人就是小三
             //客户要求增加一项需求
             System.out.println("------------客户要求增加一项需求---------------");
             //客户给我们下命令来
             Command command = new AddRequirementCommand();
             //接头人接收到命令
             xiaoSan.setCommand(command);
             //接头人执行命令
             xiaoSan.action();
     }
}
