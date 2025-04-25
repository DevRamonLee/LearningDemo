/*
 * @Author: Ramon
 * @Date: 2025-04-24 10:48:53
 * @LastEditTime: 2025-04-24 11:05:46
 * @FilePath: /DesignPattern/app/src/main/java/org/example/mediator/MediatorTest.java
 * @Description: 
 */
package org.example.mediator;

public class MediatorTest {
    public static void mediatorTest() {
        AbstractMediator mediator = new Mediator();
        Purchase purchase = new Purchase(mediator);
        Sale sale = new Sale(mediator);
        Stock stock = new Stock(mediator);
        // 绑定回中介者
        mediator.setPurchase(purchase);
        mediator.setSale(sale);
        mediator.setStock(stock);
        
        //采购人员采购电脑
        System.out.println("------采购人员采购电脑--------");
        purchase.buyIBMcomputer(100);              
        //销售人员销售电脑
        System.out.println("\n------销售人员销售电脑--------");
        sale.sellIBMComputer(1);
        //库房管理人员管理库存
        System.out.println("\n------库房管理人员清“库处理--------");
        stock.clearStock();
     }
}
