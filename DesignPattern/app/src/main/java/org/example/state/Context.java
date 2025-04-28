/*
 * @Author: Ramon
 * @Date: 2025-04-27 17:44:16
 * @LastEditTime: 2025-04-27 19:52:08
 * @FilePath: /DesignPattern/app/src/main/java/org/example/state/Context.java
 * @Description:
 */
package org.example.state;

public class Context {
    // 定义出电梯所有的状态
    public final static OpenningState openingState = new OpenningState();
    public final static ClosingState closingState = new ClosingState();
    public final static RunningState runningState = new RunningState();
    public final static StoppingState stoppingState = new StoppingState();

    // 定义一个电梯当前状态
    private LiftState liftState;

    public LiftState getLiftState() {
        return liftState;
    }
    public void setLiftState(LiftState liftState) {
        this.liftState = liftState;
        this.liftState.setContext(this);
    }

    public void open() {
        this.liftState.open();
    }

    public void close() {
        this.liftState.close();
    }

    public void run() {
        this.liftState.run();
    }

    public void stop() {
        this.liftState.stop();
    }
}
