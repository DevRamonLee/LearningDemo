package com.example.mengli.notifyuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/* 一个只能通过 Notification 启动的展示界面*/
public class ResultActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);
        Log.i("meng","ResultActivity2 task id :" + getTaskId());
    }
}
