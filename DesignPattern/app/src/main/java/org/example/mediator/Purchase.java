/*
 * @Author: Ramon
 * @Date: 2025-04-24 10:46:14
 * @LastEditTime: 2025-04-24 10:46:54
 * @FilePath: /DesignPattern/app/src/main/java/org/example/mediator/Purchase.java
 * @Description: 
 */
package org.example.mediator;

public class Purchase extends AbstractColleague{
    public Purchase(AbstractMediator _mediator){
            super(_mediator);
    }
    //采购IBM电脑
    public void buyIBMcomputer(int number){
            super.mediator.execute("purchase.buy", number);
    }
    //不再采购IBM电脑
    public void refuseBuyIBM(){
            System.out.println("不再采购IBM电脑");
    }
}