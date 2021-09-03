package ChainResp;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/9/3 11:19
 */
public class Son extends Handler {
    public Son() {
        super(Handler.SON_LEVEL_REQUEST);
    }

    // 儿子的答复是
    protected void response(IWomen women) {
        System.out.println("----母亲向儿子请求---");
        System.out.println(women.getRequest());
        System.out.println("儿子的答复是同意\n");
    }
}
