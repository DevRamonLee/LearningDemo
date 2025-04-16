package ramon.lee.httpsample.data;

import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ramon.lee.httplib.AppException;
import ramon.lee.httplib.IEntity;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/28 1:18
 */
public class ListUser implements IEntity {
    public List<User> users = new ArrayList<>();

    @Override
    public void readFromJson(JsonReader reader) throws AppException {
        try {
            //开始解析数组
            reader.beginArray();
            while(reader.hasNext()){
                User user = new User();
                user.readFromJson(reader);
                users.add(user);
            }
            //结束解析数组
            reader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
