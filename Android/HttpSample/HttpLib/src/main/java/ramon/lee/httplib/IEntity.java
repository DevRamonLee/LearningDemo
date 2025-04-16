package ramon.lee.httplib;

import android.util.JsonReader;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/28 1:06
 */
public interface IEntity {
    void readFromJson(JsonReader reader) throws AppException;
}
