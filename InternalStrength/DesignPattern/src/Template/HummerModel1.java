package Template;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/9/3 15:29
 */
public class HummerModel1 extends HummerModel {
    // H1 型号的悍马车鸣笛
    public void alarm() {
        System.out.println("悍马H1鸣笛");
    }

    // 引擎轰鸣
    public void engineBoom() {
        System.out.println("悍马H1引擎声音是这样的");
    }

    // 汽车发动
    @Override
    public void start() {
        System.out.println("悍马H1发动");
    }

    @Override
    public void stop() {
        System.out.println("悍马H1停车");
    }
}
