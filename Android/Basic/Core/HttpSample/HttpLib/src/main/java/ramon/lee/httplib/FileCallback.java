package ramon.lee.httplib;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/21 23:58
 */
public abstract class FileCallback extends AbstractCallback<String>{
    @Override
    protected String bindData(String path) throws Exception {
        return path;
    }
}
