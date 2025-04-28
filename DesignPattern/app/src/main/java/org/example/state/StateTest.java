/*
 * @Author: Ramon
 * @Date: 2025-04-27 19:47:04
 * @LastEditTime: 2025-04-27 19:50:55
 * @FilePath: /DesignPattern/app/src/main/java/org/example/state/StateTest.java
 * @Description:
 */
package org.example.state;

public class StateTest {
    public static void stateTest() {
        Context context = new Context();
        context.setLiftState(Context.closingState);
        context.open();
        context.close();
        context.run();
        context.stop();
    }
}
