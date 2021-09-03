package ChainResp;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/9/3 10:57
 */
public class Father extends Handler {

    // 父亲只处理女儿的请求
    public Father() {
        super(Handler.FATHER_LEVEL_REQUEST);
    }

    @Override
    protected void response(IWomen women) {
        System.out.println("----女儿向父亲请求---");
        System.out.println(women.getRequest());
        System.out.println("父亲的答复是同意\n");
    }
}
