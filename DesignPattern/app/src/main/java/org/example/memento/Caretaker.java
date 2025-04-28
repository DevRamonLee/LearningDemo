/*
 * @Author: Ramon
 * @Date: 2025-04-27 13:47:20
 * @LastEditTime: 2025-04-27 13:47:47
 * @FilePath: /DesignPattern/app/src/main/java/org/example/memento/Caretaker.java
 * @Description:
 */
package org.example.memento;

public class Caretaker {
    private Memento memento;
     public Memento getMemento() {
             return memento;
     }
     public void setMemento(Memento memento) {
             this.memento = memento;
     }
}
