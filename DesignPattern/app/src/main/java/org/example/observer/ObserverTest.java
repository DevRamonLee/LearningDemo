/*
 * @Author: Ramon
 * @Date: 2025-04-27 10:19:34
 * @LastEditTime: 2025-04-27 10:21:15
 * @FilePath: /DesignPattern/app/src/main/java/org/example/observer/ObserverTest.java
 * @Description:
 */
package org.example.observer;

public class ObserverTest {
    public static void observerTest() {
        LiSi liSi = new LiSi();
        HanFeizi hanFeizi = new HanFeizi();
        hanFeizi.addObserver(liSi);
        hanFeizi.haveBreakfast();
        hanFeizi.haveFun();
        hanFeizi.deleteObserver(liSi);
    }
}
