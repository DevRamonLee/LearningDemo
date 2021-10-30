package Proxy.Dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Desc : 动态代理 InvocationHandler 实现类
 * @Author : Ramon
 * @create 2021/9/18 11:05
 */
public class GamePlayIH implements InvocationHandler {
    // 被代理者
    Class cls = null;
    // 被代理的实例
    Object obj = null;
    // 我要代理谁
    public GamePlayIH(Object _obj) {
        this.obj = _obj;
    }

    // 调用被代理的方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       Object result = method.invoke(this.obj, args);
       if (method.getName().equalsIgnoreCase("login")) {
           System.out.println("有人用我的账号登录");
       }
       return result;
    }
}
