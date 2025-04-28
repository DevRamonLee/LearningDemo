/*
 * @Author: Ramon
 * @Date: 2025-04-27 15:29:37
 * @LastEditTime: 2025-04-27 15:30:22
 * @FilePath: /DesignPattern/app/src/main/java/org/example/visitor/IVisitor.java
 * @Description:
 */
package org.example.visitor;

public interface IVisitor {
    //首先，定义我可以访问普通员工
    public void visit(CommonEmployee commonEmployee);
    //其次，定义我还可以访问部门经理
    public void visit(Manager manager);
}
