/*
 * @Author: Ramon
 * @Date: 2025-04-17 10:29:09
 * @LastEditTime: 2025-04-17 10:29:31
 * @FilePath: /DesignPattern/app/src/main/java/org/example/builder/BenzBuilder.java
 * @Description: 
 */
package org.example.builder;

import java.util.ArrayList;

public class BenzBuilder extends CarBuilder {
    private BenzModel benz = new BenzModel();
    public CarModel getCarModel() {
            return this.benz;
    }
    public void setSequence(ArrayList<String> sequence) {
            this.benz.setSequence(sequence);
    }
}
