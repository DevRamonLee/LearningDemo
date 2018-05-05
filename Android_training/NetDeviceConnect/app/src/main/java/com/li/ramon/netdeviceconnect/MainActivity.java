package com.li.ramon.netdeviceconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.li.ramon.netdeviceconnect.nsd.NsdActivity;
import com.li.ramon.netdeviceconnect.wifip2p.WifiDirectActivity;
import com.li.ramon.netdeviceconnect.wifip2p.discoverservice.P2pDiscoverServiceActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button nsdBtn;// nsd
    private Button wifiP2p;// wifi p2p 连接
    private Button p2pService;// wifi p2p 服务
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    protected  void init(){
        nsdBtn = (Button) findViewById(R.id.nsd);
        wifiP2p = (Button) findViewById(R.id.wifi_p2p);
        p2pService = (Button) findViewById(R.id.p2p_service);
        nsdBtn.setOnClickListener(this);
        wifiP2p.setOnClickListener(this);
        p2pService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.nsd:
                intent = new Intent(MainActivity.this,NsdActivity.class);
                startActivity(intent);
                break;
            case R.id.wifi_p2p:
                intent = new Intent(MainActivity.this,WifiDirectActivity.class);
                startActivity(intent);
                break;
            case R.id.p2p_service:
                intent = new Intent(MainActivity.this, P2pDiscoverServiceActivity.class);
                startActivity(intent);
                break;
        }
    }
}
