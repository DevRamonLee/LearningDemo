/*
 * @Author: Ramon
 * @Date: 2025-04-24 10:42:17
 * @LastEditTime: 2025-04-24 11:03:51
 * @FilePath: /DesignPattern/app/src/main/java/org/example/mediator/AbstractMediator.java
 * @Description: 
 */
package org.example.mediator;

public abstract class AbstractMediator {
    protected Purchase purchase;
    protected Sale sale;
    protected Stock stock;

    public void setPurchase(Purchase purchase) { this.purchase = purchase; }
    public void setSale(Sale sale) { this.sale = sale; }
    public void setStock(Stock stock) { this.stock = stock; }

    //中介者最重要的方法叫做事件方法，处理多个对象之间的关系
    public abstract void execute(String str,Object...objects);
}
