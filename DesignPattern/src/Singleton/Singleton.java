package Singleton;

/**
 * @Desc : 饿汉式单例，使用前已经创建好了
 * @Author : Ramon
 * @create 2021/1/24 23:54
 */
public class Singleton {
    private static final Singleton instance = new Singleton();
    private Singleton(){}

    public static Singleton getInstance() {
        return instance;
    }
}
