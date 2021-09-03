package ChainResp;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/9/3 11:02
 */
public class Husband extends Handler {
    public Husband() {
        super(Handler.HUSBAND_LEVEL_REQUEST);
    }

    @Override
    protected void response(IWomen women) {
        System.out.println("----妻子向丈夫请求---");
        System.out.println(women.getRequest());
        System.out.println("丈夫的答复是同意\n");
    }
}
