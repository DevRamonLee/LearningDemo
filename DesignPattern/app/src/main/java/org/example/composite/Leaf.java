/*
 * @Author: Ramon
 * @Date: 2025-04-26 10:23:38
 * @LastEditTime: 2025-04-26 10:24:21
 * @FilePath: /DesignPattern/app/src/main/java/org/example/composite/Leaf.java
 * @Description:树叶节点
 */
package org.example.composite;

public class Leaf extends Corp {
    public Leaf(String _name, String _position, int _salary) {
        super(_name, _position, _salary);
    }
}
