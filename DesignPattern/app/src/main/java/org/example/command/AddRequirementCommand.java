/*
 * @Author: Ramon
 * @Date: 2025-04-24 12:55:14
 * @LastEditTime: 2025-04-24 12:55:38
 * @FilePath: /DesignPattern/app/src/main/java/org/example/command/AddRequirementCommand.java
 * @Description: 
 */
package org.example.command;

public class AddRequirementCommand extends Command {
    //执行增加一项需求的命令
    public void execute() {
            //找到需求组
            super.rg.find();           
            //增加一份需求
            super.rg.add();            
            //给出计划
            super.rg.plan();
    }
}
