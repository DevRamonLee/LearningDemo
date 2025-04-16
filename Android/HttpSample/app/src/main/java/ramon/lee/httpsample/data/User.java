package ramon.lee.httpsample.data;

import android.util.JsonReader;

import java.io.IOException;

import ramon.lee.httplib.AppException;
import ramon.lee.httplib.IEntity;

/**
 * @Desc :
 * @Author : Ramon
 * @create 2021/3/21 22:14
 */
public class User implements IEntity {
    int id;
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public void readFromJson(JsonReader reader) throws AppException{
        try {
            reader.beginObject();
            String node;
            while (reader.hasNext()) {
                node = reader.nextName();
                if ("id".equalsIgnoreCase(node)) {
                    id = reader.nextInt();
                } else if ("name".equalsIgnoreCase(node)) {
                    name = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        } catch (IOException e) {
            throw new AppException(AppException.ErrorType.IO, e.getMessage());
        }
    }
}
