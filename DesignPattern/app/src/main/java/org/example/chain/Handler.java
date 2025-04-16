package org.example.chain;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/9/3 10:35
 */
public abstract class Handler {
    public final static int FATHER_LEVEL_REQUEST = 1;
    public final static int HUSBAND_LEVEL_REQUEST = 2;
    public final static int SON_LEVEL_REQUEST = 3;

    // 能处理的级别
    private int level = 0;
    // 责任传递，下一个责任人是谁
    private Handler nextHandler;
    // 每个类都要说明一下自己能处理的请求
    public Handler(int _level) {
        this.level = _level;
    }

    // 一个女性（女儿、妻子或者是母亲）要求逛街，你要处理这个请求
    public final void handleMessage(IWomen women) {
        if (women.getType() == this.level) {
            this.response(women);
        } else {
            if (this.nextHandler != null) { // 有后续环节，把请求往后传递
                this.nextHandler.handleMessage(women);
            } else {    // 已经没有后续处理者了，不用处理了
                System.out.println("--- 没地方请示了，按不同意处理 ---");
            }
        }
    }

    /**
     * 如果不属于你处理的请求，你应该让她找下一个环节的人
     */
    public void setNext(Handler _handler) {
        this.nextHandler = _handler;
    }

    // 回应请求，这里用到了模板方法模式，父类定义了一套流程 handleMessage，关键细节交给子类去实现
    protected abstract void response(IWomen women);
}
