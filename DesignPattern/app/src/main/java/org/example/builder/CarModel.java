/*
 * @Author: Ramon
 * @Date: 2025-04-17 10:18:31
 * @LastEditTime: 2025-04-17 10:26:53
 * @FilePath: /DesignPattern/app/src/main/java/org/example/builder/CarModel.java
 * @Description: 
 */
package org.example.builder;

import java.util.ArrayList;

public abstract class CarModel {
    // 这个参数用于指定各个基本方法的执行顺序
    private ArrayList<String> sequence = new ArrayList<>();

    protected abstract void start();
    protected abstract void stop();
    protected abstract void alarm();
    protected abstract void engineBoom();

    // 模板方法
    final public void run() {
        for (int i = 0; i < this.sequence.size(); i++) {
            String actionName = this.sequence.get(i);

            if (actionName.equalsIgnoreCase("start")) {
                this.start();
            }
            if (actionName.equalsIgnoreCase("stop")) {
                this.stop();
            }
            if (actionName.equalsIgnoreCase("alarm")) {
                this.alarm();
            }
            if (actionName.equalsIgnoreCase("engin")) {
                this.engineBoom();
            }
        }
    }

    // 设置执行顺序
    final public void setSequence(ArrayList<String> sequence) {
        this.sequence = sequence;
    }
}
