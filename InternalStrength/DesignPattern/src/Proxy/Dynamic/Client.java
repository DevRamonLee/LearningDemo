package Proxy.Dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/9/18 11:08
 */
public class Client {
    public static void main(String[] args) {
        IGamePlayer player = new GamePlayer("张三");
        InvocationHandler handler = new GamePlayIH(player);
        // 开始打游戏，记下时间
        System.out.println("开始时间是： 2021-09-18");
        // 获得类的 class loader
        ClassLoader cl = player.getClass().getClassLoader();
        // 动态产生一个代理
        IGamePlayer proxy = (IGamePlayer) Proxy.newProxyInstance(cl, new Class[]{IGamePlayer.class}, handler);
        // 登录
        proxy.login("zhangsan", "password");
        // 开始杀怪
        proxy.killBoss();
        // 升级
        proxy.upgrade();
        // 记录结束游戏时间
        System.out.println("结束时间是： 2021-09-19");
    }
}
