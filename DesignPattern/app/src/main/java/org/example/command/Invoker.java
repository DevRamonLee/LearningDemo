/*
 * @Author: Ramon
 * @Date: 2025-04-24 12:58:17
 * @LastEditTime: 2025-04-24 12:58:43
 * @FilePath: /DesignPattern/app/src/main/java/org/example/command/Invoker.java
 * @Description:
 */
package org.example.command;

public class Invoker {
    //什么命令
    private Command command;
    //客户发出命令
    public void setCommand(Command command){
        this.command = command;
    }
    //执行客户的命令
    public void action(){
        this.command.execute();
    }
}
