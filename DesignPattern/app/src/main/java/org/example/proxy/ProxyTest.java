/*
 * @Author: Ramon
 * @Date: 2025-03-29 11:24:25
 * @LastEditTime: 2025-03-29 21:41:09
 * @FilePath: /DesignPattern/app/src/main/java/org/example/proxy/ProxyTest.java
 * @Description: 
 */
package org.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.example.Utils;
import org.example.proxy.dynamic.cglib.Target;
import org.example.proxy.dynamic.jdk.GamePlayIH;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyTest {
    /**
     * 普通代理测试方法
     */
    public static void testProxyNormal() {
        // 定义一个代练者
        org.example.proxy.normal.IGamePlayer proxy = new org.example.proxy.normal.GamePlayerProxy("张三");
        // 开始打游戏，记下时间戳
        Utils.printTime();
        proxy.login("zhangSan", "password");
        proxy.killBoss();
        proxy.upgrade();
        Utils.printTime();
    }

    /*
     * 强制代理测试方法，必须
     */
    public static void testProxyForce() {
        /**
         * 测试一： 直接访问真实角色
         */
        org.example.proxy.force.IGamePlayer player = new org.example.proxy.force.GamePlayer("张三");
        Utils.printTime();
        player.login("zhangSan", "password");
        player.killBoss();
        player.upgrade();
        Utils.printTime();

        /**
         * 测试二：直接访问代理类
         */
        org.example.proxy.force.IGamePlayer player2 = new org.example.proxy.force.GamePlayer("张三");
        org.example.proxy.force.IGamePlayer proxy2 = new org.example.proxy.force.GamePlayerProxy(player2);
        // 开始打游戏，记下时间戳
        Utils.printTime();
        proxy2.login("zhangSan", "password");
        proxy2.killBoss();
        proxy2.upgrade();
        Utils.printTime();

        /**
         * 测试三：使用真实角色获取代理类
         */
        org.example.proxy.force.IGamePlayer player3 = new org.example.proxy.force.GamePlayer("张三");
        org.example.proxy.force.IGamePlayer proxy3 = player3.getProxy();
        Utils.printTime();
        proxy3.login("zhangSan", "password");
        proxy3.killBoss();
        proxy3.upgrade();
        Utils.printTime();
    }

    /**
     * Java JDK 动态代理
     */
    public static void testProxyJdk() {
        org.example.proxy.dynamic.jdk.IGamePlayer player = new org.example.proxy.dynamic.jdk.GamePlayer("张三");
        InvocationHandler handler = new GamePlayIH(player);
        Utils.printTime();
        // 获得类的 class loader
        ClassLoader cl = player.getClass().getClassLoader();
        // 动态产生一个代理
        org.example.proxy.dynamic.jdk.IGamePlayer proxy = (org.example.proxy.dynamic.jdk.IGamePlayer) Proxy.newProxyInstance(cl, new Class[]{org.example.proxy.dynamic.jdk.IGamePlayer.class}, handler);
        // 登录
        proxy.login("zhangsan", "password");
        // 开始杀怪
        proxy.killBoss();
        // 升级
        proxy.upgrade();
        Utils.printTime();
    }

    /*
     * CGLIB 动态代理实现方式
     */
    public static void testProxyCglib() {
         // 1. 创建 Enhancer（代理增强器）
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class); // 设置要代理的类

        // 2. 设置回调（拦截器）
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("Before method: " + method.getName()); // 方法前增强
                Object result = proxy.invokeSuper(obj, args);  // 调用原方法
                System.out.println("After method: " + method.getName());  // 方法后增强
                return result;
            }
        });

        // 3. 创建代理对象
        Target proxy = (Target) enhancer.create();
        proxy.sayHello();  // 调用代理方法
    }
}
