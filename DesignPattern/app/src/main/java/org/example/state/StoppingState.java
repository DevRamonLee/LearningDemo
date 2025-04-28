/*
 * @Author: Ramon
 * @Date: 2025-04-27 18:00:18
 * @LastEditTime: 2025-04-27 18:42:03
 * @FilePath: /DesignPattern/app/src/main/java/org/example/state/StoppingState.java
 * @Description:
 */
package org.example.state;

public class StoppingState extends LiftState {

    @Override
    public void open() {
        super.context.setLiftState(Context.openingState);
        super.context.getLiftState().open();
    }

    @Override
    public void close() {
        // 停止状态本来就是关门的
    }

    @Override
    public void run() {
        // 停止状态开始运行
        super.context.setLiftState(Context.runningState);
        super.context.getLiftState().run();
    }

    @Override
    public void stop() {
        // 停止方法执行
        System.out.println("电梯停止了");
    }

}
