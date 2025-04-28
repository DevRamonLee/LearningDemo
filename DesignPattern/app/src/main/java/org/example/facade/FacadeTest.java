/*
 * @Author: Ramon
 * @Date: 2025-04-27 11:06:44
 * @LastEditTime: 2025-04-27 11:08:19
 * @FilePath: /DesignPattern/app/src/main/java/org/example/facade/FacadeTest.java
 * @Description:
 */
package org.example.facade;

public class FacadeTest {
    public static void facadeTest() {
        ModernPostOffice office = new ModernPostOffice();
        office.sendLetter("Hello, dude", "Street 666");
    }
}
