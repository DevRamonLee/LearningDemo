/*
 * @Author: Ramon
 * @Date: 2025-04-24 12:55:52
 * @LastEditTime: 2025-04-24 12:56:35
 * @FilePath: /DesignPattern/app/src/main/java/org/example/command/DeletePageCommand.java
 * @Description:
 */
package org.example.command;

public class DeletePageCommand extends Command {
    //执行删除一个页面的命令
    public void execute() {
            //找到页面组
            super.pg.find();
            //删除一个页面
            super.rg.delete();
            //给出计划
            super.rg.plan();
    }
}