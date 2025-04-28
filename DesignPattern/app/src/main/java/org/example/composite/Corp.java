/*
 * @Author: Ramon
 * @Date: 2025-04-26 10:20:50
 * @LastEditTime: 2025-04-26 10:23:14
 * @FilePath: /DesignPattern/app/src/main/java/org/example/composite/Corp.java
 * @Description:
 */
package org.example.composite;

public abstract class Corp {
    private String name = "";
    private String position = "";
    private int salary = 0;
    public Corp(String _name, String _position, int _salary) {
        this.name = _name;
        this.position = _position;
        this.salary = _salary;
    }

    // 获取员工信息
    public String getInfo() {
        String info = "";
             info = "姓名：" + this.name;
             info = info + "\t职位："+ this.position;
             info = info + "\t薪水：" + this.salary;
             return info;
    }
}
