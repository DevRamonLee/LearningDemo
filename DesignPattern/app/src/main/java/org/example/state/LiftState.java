/*
 * @Author: Ramon
 * @Date: 2025-04-27 17:44:00
 * @LastEditTime: 2025-04-27 17:56:44
 * @FilePath: /DesignPattern/app/src/main/java/org/example/state/LiftState.java
 * @Description:
 */
package org.example.state;

public abstract class LiftState {
    // 定义了一个环境角色，负责封装状态变化引起的功能变化
    protected Context context;
    public void setContext(Context _context) {
        this.context = _context;
    }

    // 电梯的开门动作
    public abstract void open();
    // 关门动作
    public abstract void close();
    // 电梯运行
    public abstract void run();
    // 电梯停止
    public abstract void stop();
}
