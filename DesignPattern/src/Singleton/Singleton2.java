package Singleton;

/**
 * @Desc : double check 单例，也叫懒汉式单例，使用的时候去创建
 * @Author : Ramon
 * @create 2021/1/24 23:57
 */
public class Singleton2 {
    // volatile 避免指令重排带来的错误
    private volatile Singleton2 instance = null;

    private Singleton2() {}

    public Singleton2 getInstance() {
        // 这里是为了保证性能
        if (instance == null) {
            synchronized (Singleton2.class) {
                // 这里是判断前一个线程已经创建好了实例，避免重复创建
                if (instance == null) {
                    instance = new Singleton2();
                }
            }
        }
        return instance;
    }
}
