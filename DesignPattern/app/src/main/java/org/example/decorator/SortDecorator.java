/*
 * @Author: Ramon
 * @Date: 2025-04-24 16:37:11
 * @LastEditTime: 2025-04-24 16:37:46
 * @FilePath: /DesignPattern/app/src/main/java/org/example/decorator/SortDecorator.java
 * @Description:
 */
package org.example.decorator;

public class SortDecorator extends Decorator {
    //构造函数
    public SortDecorator(SchoolReport sr){
        super(sr);
    }
    //告诉老爸学校的排名情况
    private void reportSort(){
        System.out.println("我是排名第38名...");
    }
    //老爸看完成绩单后再告诉他，加强作用
    @Override
    public void report(){
        super.report();
        this.reportSort();
    }
}
