/*
 * @Author: Ramon
 * @Date: 2025-04-24 10:47:54
 * @LastEditTime: 2025-04-24 10:48:29
 * @FilePath: /DesignPattern/app/src/main/java/org/example/mediator/Sale.java
 * @Description: 
 */
package org.example.mediator;

import java.util.Random;

public class Sale extends AbstractColleague {
    public Sale(AbstractMediator _mediator){
            super(_mediator);
    }
    //销售IBM电脑
    public void sellIBMComputer(int number){
            super.mediator.execute("sale.sell", number);
            System.out.println("销售IBM电脑"+number+"台");          
    }
    //反馈销售情况，0~100之间变化，0代表根本就没人买，100代表非常畅销，出一个卖一个
    public int getSaleStatus(){
            Random rand = new Random(System.currentTimeMillis());
            int saleStatus = rand.nextInt(100);
            System.out.println("IBM电脑的销售情况为："+saleStatus);
            return saleStatus;
    }
    //折价处理
    public void offSale(){
            super.mediator.execute("sale.offsell");
    }
}