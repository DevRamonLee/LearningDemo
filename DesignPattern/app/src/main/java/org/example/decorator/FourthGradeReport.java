/*
 * @Author: Ramon
 * @Date: 2025-04-24 16:31:09
 * @LastEditTime: 2025-04-24 16:32:34
 * @FilePath: /DesignPattern/app/src/main/java/org/example/decorator/FourthGradeReport.java
 * @Description:
 */
package org.example.decorator;

public class FourthGradeReport extends SchoolReport {

    @Override
    public void report() {
        System.out.println("尊敬的XXX家长:");
        System.out.println("  ......");
        System.out.println("  语文 62  数学65 体育 98  自然  63");
        System.out.println("  .......");
        System.out.println("               家长签名：       ");
    }
    //家长签名
    public void sign(String name) {
        System.out.println("家长签名为："+name);
    }
}
