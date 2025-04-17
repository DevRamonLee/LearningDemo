/*
 * @Author: Ramon
 * @Date: 2025-04-17 10:28:24
 * @LastEditTime: 2025-04-17 10:28:44
 * @FilePath: /DesignPattern/app/src/main/java/org/example/builder/CarBuilder.java
 * @Description: 
 */
package org.example.builder;

import java.util.ArrayList;

public abstract class CarBuilder {        
    //建造一个模型，你要给我一个顺序要，就是组装顺序
    public abstract void setSequence(ArrayList<String> sequence);        
    //设置完毕顺序后，就可以直接拿到这个车辆模型
    public abstract CarModel getCarModel();
}
