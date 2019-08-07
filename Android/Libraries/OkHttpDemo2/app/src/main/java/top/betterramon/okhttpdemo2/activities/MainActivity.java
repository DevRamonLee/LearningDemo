package top.betterramon.okhttpdemo2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import top.betterramon.okhttpdemo2.R;
import top.betterramon.okhttpdemo2.bean.UploadImageKey;
import top.betterramon.okhttpdemo2.conf.Url;
import top.betterramon.okhttpdemo2.net.Error;
import top.betterramon.okhttpdemo2.net.ILoader;
import top.betterramon.okhttpdemo2.net.UrlLib;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 测试 Okhttp 库
        UrlLib.getInstance().buildRequest(Url.FRIENDS, FriendsList.class, new ILoader.Callback<FriendsList>() {
            @Override
            public void onResult(FriendsList data) {
                for(Friend friend : data.getData()) {
                    Log.i("MainActivity", "Friend name is: " + friend.getName());
                }
            }

            @Override
            public void onError(Error error) {

            }
        });
    }
}
