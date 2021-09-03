package Template;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/9/3 15:26
 */
public abstract class HummerModel {
    public abstract void start();   // 发动
    public abstract void stop();    // 停止
    public abstract void alarm();   // 喇叭
    public abstract void engineBoom();  // 引擎响

    // 模板方法，跑起来
    public void run() {
        // 先发动汽车
        this.start();
        // 引擎开始轰鸣
        this.engineBoom();
        // 开始跑，跑的过程中遇到一只狗，按喇叭
        this.alarm();
        // 到达目的地，停车
        this.stop();
    }
}
