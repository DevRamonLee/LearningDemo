/*
 * @Author: Ramon
 * @Date: 2025-04-24 16:39:36
 * @LastEditTime: 2025-04-24 16:40:59
 * @FilePath: /DesignPattern/app/src/main/java/org/example/decorator/Father.java
 * @Description:
 */
package org.example.decorator;

public class Father {
    public static void decoratorTest() {
        SchoolReport sp = new FourthGradeReport();
        sp = new HighScoreDecorator(sp);
        sp = new SortDecorator(sp);
        sp.report();
        sp.sign("老三");
    }
}
