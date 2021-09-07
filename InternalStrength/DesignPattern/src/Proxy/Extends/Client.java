package Proxy.Extends;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/9/5 21:44
 */
public class Client {
    public static void main(String[] args) {
        // 定义一个代练者
        IGamePlayer proxy = new GamePlayerProxy("张三");
        // 开始打游戏，记下时间戳
        System.out.println("开始时间是： 2021-09-25-21.45");
        proxy.login("zhangSan", "password");
        proxy.killBoss();
        proxy.upgrade();
        System.out.println("结束时间是： 2021-09-25-22.45");
    }
}
