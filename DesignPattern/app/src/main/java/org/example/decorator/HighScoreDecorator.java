/*
 * @Author: Ramon
 * @Date: 2025-04-24 16:38:27
 * @LastEditTime: 2025-04-24 16:39:14
 * @FilePath: /DesignPattern/app/src/main/java/org/example/decorator/HighScoreDecorator.java
 * @Description:
 */
package org.example.decorator;

public class HighScoreDecorator extends Decorator {
    //构造函数
    public HighScoreDecorator(SchoolReport sr){
        super(sr);
    }
    //我要汇报最高成绩
    private void reportHighScore(){
        System.out.println("这次考试语文最高是75，数学是78，自然是80");
    }
    //我要在老爸看成绩单前告诉他最高成绩，否则等他一看，就抡起扫帚揍我，我哪里还有机会说啊
    @Override
    public void report(){
        this.reportHighScore();
        super.report();
    }
}
