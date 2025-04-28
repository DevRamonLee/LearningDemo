/*
 * @Author: Ramon
 * @Date: 2025-04-27 15:32:47
 * @LastEditTime: 2025-04-27 15:33:42
 * @FilePath: /DesignPattern/app/src/main/java/org/example/visitor/Manager.java
 * @Description:
 */
package org.example.visitor;

public class Manager extends Employee {
    //这类人物的职责非常明确：业绩
    private String performance;
    public String getPerformance() {
            return performance;
    }
    public void setPerformance(String performance) {
            this.performance = performance;
    }
    //部门经理允许访问者访问
    @Override
    public void accept(IVisitor visitor){
            visitor.visit(this);
    }
}
