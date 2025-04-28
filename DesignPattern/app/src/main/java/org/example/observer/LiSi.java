/*
 * @Author: Ramon
 * @Date: 2025-04-27 10:17:20
 * @LastEditTime: 2025-04-27 10:18:37
 * @FilePath: /DesignPattern/app/src/main/java/org/example/observer/LiSi.java
 * @Description:
 */
package org.example.observer;

import java.util.Observable;
import java.util.Observer;

public class LiSi implements Observer{
    //首先李斯是个观察者，一旦韩非子有活动，他就知道，他就要向老板汇报
    public void update(Observable observable,Object obj){
            System.out.println("李斯：观察到李斯活动，开始向老板汇报了...");
            this.reportToQiShiHuang(obj.toString());
            System.out.println("李斯：汇报完毕...\n");
    }
    //汇报给秦始皇
    private void reportToQiShiHuang(String reportContext){
            System.out.println("李斯：报告，秦老板！韩非子有活动了--->"+reportContext);
    }
}
