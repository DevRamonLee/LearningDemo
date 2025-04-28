/*
 * @Author: Ramon
 * @Date: 2025-04-27 17:57:01
 * @LastEditTime: 2025-04-27 19:46:08
 * @FilePath: /DesignPattern/app/src/main/java/org/example/state/OpenningState.java
 * @Description:
 */
package org.example.state;

public class OpenningState extends LiftState {

    @Override
    public void open() {
        System.out.println("电梯打开了");
    }

    @Override
    public void close() {
        super.context.setLiftState(Context.closingState);
        super.context.getLiftState().close();
    }

    @Override
    public void run() {
        // 不能开着门运行
    }

    @Override
    public void stop() {
        // 不能开着门停止
    }

}
