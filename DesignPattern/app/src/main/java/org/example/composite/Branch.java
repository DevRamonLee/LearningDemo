/*
 * @Author: Ramon
 * @Date: 2025-04-26 10:24:47
 * @LastEditTime: 2025-04-26 10:27:17
 * @FilePath: /DesignPattern/app/src/main/java/org/example/composite/Branch.java
 * @Description:
 */
package org.example.composite;

import java.util.ArrayList;

public class Branch extends Corp {

    ArrayList<Corp> subordinateList = new ArrayList<>();
    public Branch(String _name, String _position, int _salary) {
        super(_name, _position, _salary);
    }

    public void addSubordinate(Corp corp) {
        this.subordinateList.add(corp);
    }

    public ArrayList<Corp> getSubordinate() {
        return this.subordinateList;
    }
}
