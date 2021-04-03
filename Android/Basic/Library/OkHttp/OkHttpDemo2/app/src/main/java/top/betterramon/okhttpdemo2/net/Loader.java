package top.betterramon.okhttpdemo2.net;

/**
 * Created by Ramon Lee on 2019/8/7.
 */
public abstract class Loader extends Thread {
    @Override
    public void run() {
        execute();
    }
    protected abstract void execute();
}
