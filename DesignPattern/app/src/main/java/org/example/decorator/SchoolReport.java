/*
 * @Author: Ramon
 * @Date: 2025-04-24 16:30:36
 * @LastEditTime: 2025-04-24 16:33:13
 * @FilePath: /DesignPattern/app/src/main/java/org/example/decorator/SchoolReport.java
 * @Description:
 */
package org.example.decorator;

public abstract class SchoolReport {
    //成绩单主要展示的就是你的成绩情况
    public abstract void report();
    //成绩单要家长签字，这个是最要命的
    public abstract void sign(String name);
}
