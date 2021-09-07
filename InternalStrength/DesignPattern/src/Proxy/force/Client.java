package Proxy.force;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/9/5 21:44
 */
public class Client {
    public static void main(String[] args) {
        /**
         * 测试一： 直接访问真实角色
         */
        IGamePlayer player = new GamePlayer("张三");
        // 开始打游戏，记下时间戳
        System.out.println("开始时间是： 2021-09-25-21.45");
        player.login("zhangSan", "password");
        player.killBoss();
        player.upgrade();
        System.out.println("结束时间是： 2021-09-25-22.45");

        /**
         * 测试二：直接访问代理类
         */
        IGamePlayer player2 = new GamePlayer("张三");
        IGamePlayer proxy2 = new GamePlayerProxy(player2);
        // 开始打游戏，记下时间戳
        System.out.println("开始时间是： 2021-09-25-21.45");
        proxy2.login("zhangSan", "password");
        proxy2.killBoss();
        proxy2.upgrade();
        System.out.println("结束时间是： 2021-09-25-22.45");

        /**
         * 测试三：使用真实角色获取代理类
         */
        IGamePlayer player3 = new GamePlayer("张三");
        IGamePlayer proxy3 = player3.getProxy();
        // 开始打游戏，记下时间戳
        System.out.println("开始时间是： 2021-09-25-21.45");
        proxy3.login("zhangSan", "password");
        proxy3.killBoss();
        proxy3.upgrade();
        System.out.println("结束时间是： 2021-09-25-22.45");
    }
}
