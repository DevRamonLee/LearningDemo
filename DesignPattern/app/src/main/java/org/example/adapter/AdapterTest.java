/*
 * @Author: Ramon
 * @Date: 2025-04-25 12:08:57
 * @LastEditTime: 2025-04-25 12:10:16
 * @FilePath: /DesignPattern/app/src/main/java/org/example/adapter/AdapterTest.java
 * @Description:
 */
package org.example.adapter;

public class AdapterTest {
    public static void adapterTest() {
        IUserInfo userInfo = new OuterUserInfo();
        System.out.println("Phone number is : " + userInfo.getMobileNumber());
    }
}
