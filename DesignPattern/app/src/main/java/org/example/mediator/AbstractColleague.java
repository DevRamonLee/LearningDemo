/*
 * @Author: Ramon
 * @Date: 2025-04-24 10:45:09
 * @LastEditTime: 2025-04-24 10:45:36
 * @FilePath: /DesignPattern/app/src/main/java/org/example/mediator/AbstractColleague.java
 * @Description: 
 */
package org.example.mediator;

public abstract class AbstractColleague {
    protected AbstractMediator mediator;
    public AbstractColleague(AbstractMediator _mediator){
            this.mediator = _mediator;
    }
}