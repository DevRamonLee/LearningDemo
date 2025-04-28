/*
 * @Author: Ramon
 * @Date: 2025-04-27 17:59:19
 * @LastEditTime: 2025-04-27 18:37:01
 * @FilePath: /DesignPattern/app/src/main/java/org/example/state/ClosingState.java
 * @Description:
 */
package org.example.state;

public class ClosingState extends LiftState {

    @Override
    public void open() {
        // 设置为开门状态
        super.context.setLiftState(Context.openingState);
        super.context.getLiftState().open();
    }

    @Override
    public void close() {
        // 关闭动作
        System.out.println("电梯门关闭");
    }

    @Override
    public void run() {
        // 电梯门关了后可以运行
        super.context.setLiftState(Context.runningState);
        super.context.getLiftState().run();
    }

    @Override
    public void stop() {
        // 电梯门关着，但是不按楼层，它是停止状态
        super.context.setLiftState(Context.stoppingState);
        super.context.getLiftState().stop();
    }

}
