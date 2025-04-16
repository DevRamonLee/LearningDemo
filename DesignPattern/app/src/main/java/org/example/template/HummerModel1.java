/*
 * @Author: Ramon
 * @Date: 2025-03-29 10:46:28
 * @LastEditTime: 2025-04-16 11:22:47
 * @FilePath: /DesignPattern/app/src/main/java/org/example/template/HummerModel1.java
 * @Description: 
 */
package org.example.template;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/9/3 15:29
 */
public class HummerModel1 extends HummerModel {
    private boolean alarmFlag = false;
    // H1 型号的悍马车鸣笛
    public void alarm() {
        System.out.println("悍马H1鸣笛");
    }

    // 引擎轰鸣
    public void engineBoom() {
        System.out.println("悍马H1引擎声音是这样的");
    }

    // 汽车发动
    @Override
    public void start() {
        System.out.println("悍马H1发动");
    }

    @Override
    public void stop() {
        System.out.println("悍马H1停车");
    }

    @Override
    protected boolean isAlarm() {
        return alarmFlag;
    }

    public void setAlarm(boolean alarm) {
        this.alarmFlag = alarm;
    }
}
