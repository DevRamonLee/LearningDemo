package org.example.chain;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/9/3 11:21
 */
public class Women implements IWomen {
    /**
     * 通过一个 int 类型的参数来描述妇女的个人状况
     *
     */
    private int type = 0;
    // 妇女的请示
    private String request = "";

    // 构造函数传递过来请求
    public Women(int _type, String _request) {
        this.type = _type;
        // 为了便于显示，在这里做了点处理
        switch (this.type) {
            case 1:
                this.request = "女儿的请求是： " + _request;
                break;
            case 2:
                this.request = "妻子的请求是：" + _request;
                break;
            case 3:
                this.request = "母亲的请求是：" + _request;
        }
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public String getRequest() {
        return this.request;
    }
}
