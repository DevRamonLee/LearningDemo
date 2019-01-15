package com.li.ramon.netdeviceconnect.nsd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.li.ramon.netdeviceconnect.R;

/*初步介绍如何使用 Nsd 服务*/
public class NsdActivity extends AppCompatActivity {
    NsdHelper nsdHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsd);
        nsdHelper = NsdHelper.getInstance(NsdActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(nsdHelper != null){
            nsdHelper.registerService();
            nsdHelper.discoverService();
        }
    }

    @Override
    protected void onStop() {
        if(nsdHelper != null){
            nsdHelper.tearDown();
        }
        super.onStop();
    }
}
