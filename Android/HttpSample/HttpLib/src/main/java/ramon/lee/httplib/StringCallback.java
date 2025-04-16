package ramon.lee.httplib;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/21 23:57
 */
public abstract class StringCallback extends AbstractCallback<String>{
    @Override
    protected String bindData(String result) throws AppException {
        return result;
    }
}
