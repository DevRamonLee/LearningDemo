package Template;

/**
 * @Desc : 模板方法模式 - 例： 实现一个悍马模型，悍马车有两个型号 H1 和 H2
 * @Author : Ramon
 * @create 2021/9/3 15:35
 */
public class Client {
    public static void main(String[] args) {
        // 生产一个 h1, h1 开始跑
        HummerModel h1 = new HummerModel1();
        h1.run();

        // 再生产一辆 h2
        HummerModel h2 = new HummerModel2();
        h2.run();
    }
}
