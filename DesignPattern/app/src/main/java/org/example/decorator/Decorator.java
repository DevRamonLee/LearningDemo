/*
 * @Author: Ramon
 * @Date: 2025-04-24 16:35:03
 * @LastEditTime: 2025-04-24 16:35:29
 * @FilePath: /DesignPattern/app/src/main/java/org/example/decorator/Decorator.java
 * @Description:
 */
package org.example.decorator;


public abstract class Decorator extends SchoolReport{
    //首先我要知道是哪个成绩单
    private SchoolReport sr;
    //构造函数，传递成绩单过来
    public Decorator(SchoolReport sr){
        this.sr = sr;
    }
    //成绩单还是要被看到的
    public void report(){
        this.sr.report();
    }
    //看完还是要签名的
    public void sign(String name){
        this.sr.sign(name);
    }
}
