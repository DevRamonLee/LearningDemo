/*
 * @Author: Ramon
 * @Date: 2025-04-27 13:43:43
 * @LastEditTime: 2025-04-27 13:45:25
 * @FilePath: /DesignPattern/app/src/main/java/org/example/memento/Boy.java
 * @Description:备忘录模式发起人角色
 */
package org.example.memento;

public class Boy {
    //男孩的状态
    private String state = "";
    //认识女孩子后状态肯定改变，比如心情、手中的花等
    public void changeState(){
            this.state = "心情可能很不好";
    }
    public String getState() {
            return state;
    }
    public void setState(String state) {
            this.state = state;
    }
    //保留一个备份
    public Memento createMemento(){
            return new Memento(this.state);
    }
    //恢复一个备份
    public void restoreMemento(Memento _memento){
         this.setState(_memento.getState());
    }
}