/*
 * @Author: Ramon
 * @Date: 2025-04-27 13:44:27
 * @LastEditTime: 2025-04-27 13:44:56
 * @FilePath: /DesignPattern/app/src/main/java/org/example/memento/Memento.java
 * @Description:备忘录模式备忘录角色
 */
package org.example.memento;

public class Memento {
    //男孩的状态
    private String state = "";
    //通过构造函数传递状态信息
    public Memento(String _state){
            this.state = _state;
    }
    public String getState() {
            return state;
    }
    public void setState(String state) {
            this.state = state;
    }
}
