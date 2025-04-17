/*
 * @Author: Ramon
 * @Date: 2025-04-17 10:30:15
 * @LastEditTime: 2025-04-17 10:30:45
 * @FilePath: /DesignPattern/app/src/main/java/org/example/builder/BMWBuilder.java
 * @Description: 
 */
package org.example.builder;

import java.util.ArrayList;

public class BMWBuilder extends CarBuilder {
    private BMWModel bmw = new BMWModel();
    public CarModel getCarModel() {
            return this.bmw;
    }
    public void setSequence(ArrayList<String> sequence) {
            this.bmw.setSequence(sequence);
    }
}