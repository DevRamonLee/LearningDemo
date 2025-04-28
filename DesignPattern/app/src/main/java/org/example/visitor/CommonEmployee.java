/*
 * @Author: Ramon
 * @Date: 2025-04-27 15:31:33
 * @LastEditTime: 2025-04-27 15:32:23
 * @FilePath: /DesignPattern/app/src/main/java/org/example/visitor/CommonEmployee.java
 * @Description:
 */
package org.example.visitor;

public class CommonEmployee extends Employee {
    //工作内容，这非常重要，以后的职业规划就是靠它了
    private String job;
    public String getJob() {
            return job;
    }
    public void setJob(String job) {
            this.job = job;
    }
    //我允许访问者访问
    @Override
    public void accept(IVisitor visitor){
         visitor.visit(this);
    }
}
