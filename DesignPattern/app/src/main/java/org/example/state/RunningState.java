/*
 * @Author: Ramon
 * @Date: 2025-04-27 17:59:45
 * @LastEditTime: 2025-04-27 18:38:14
 * @FilePath: /DesignPattern/app/src/main/java/org/example/state/RunningState.java
 * @Description:
 */
package org.example.state;

public class RunningState extends LiftState {

    @Override
    public void open() {
        // 电梯运行中，不能打开
    }

    @Override
    public void close() {
        // 运行中肯定是关门的
    }

    @Override
    public void run() {
        System.out.println("电梯上下运行");
    }

    @Override
    public void stop() {
        // 运行切换到停止状态
        super.context.setLiftState(Context.stoppingState);
        super.context.getLiftState().stop();
    }

}
