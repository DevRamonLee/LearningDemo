/*
 * @Author: Ramon
 * @Date: 2025-04-27 11:05:24
 * @LastEditTime: 2025-04-27 11:06:06
 * @FilePath: /DesignPattern/app/src/main/java/org/example/facade/Police.java
 * @Description:
 */
package org.example.facade;

public class Police {
    public void checkLetter(ILetterProcess letter) {
        System.out.println("警察检查信件");
    }
}
