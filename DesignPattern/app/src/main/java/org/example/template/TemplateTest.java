/*
 * @Author: Ramon
 * @Date: 2025-03-29 10:46:28
 * @LastEditTime: 2025-04-16 11:33:33
 * @FilePath: /DesignPattern/app/src/main/java/org/example/template/TemplateTest.java
 * @Description: 
 */
package org.example.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TemplateTest {
    public static void templageTest() {
        System.out.println("-------H1型号悍马--------");
        System.out.println("H1型号的悍马是否需要喇叭声响？0-不需要   1-需要");
        String type = "0";
        try {
            type = (new BufferedReader(new InputStreamReader(System.in))).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HummerModel1 h1 = new HummerModel1();
        if(type.equals("0")){  
            h1.setAlarm(false);
        } else {
            h1.setAlarm(true);
        }
        h1.run();
        System.out.println("\n-------H2型号悍马--------");
        HummerModel2 h2 = new HummerModel2();
        h2.run();  
    }
}
