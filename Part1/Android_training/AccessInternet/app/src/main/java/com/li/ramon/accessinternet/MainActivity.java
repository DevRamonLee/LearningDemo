package com.li.ramon.accessinternet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.li.ramon.accessinternet.connect.ConnectNetActivity;
import com.li.ramon.accessinternet.parserxml.NetworkActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button connectInternet;//连接到网络
    private Button networkStatus;//管理网络连接状态
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    protected  void init(){
        connectInternet = (Button) findViewById(R.id.connect_net);
        networkStatus = (Button) findViewById(R.id.network_status);
        connectInternet.setOnClickListener(this);
        networkStatus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.connect_net:
                intent = new Intent(MainActivity.this,ConnectNetActivity.class);
                startActivity(intent);
                break;
            case R.id.network_status:
                intent = new Intent(MainActivity.this,NetworkActivity.class);
                startActivity(intent);
                break;
        }

    }
}
