package com.li.ramon.effectivenavigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button collectionBtn;//Viewpager 和 tab
    private Button drawerBtn;//DrawerLayout
    private Button slidingBtn;//SlidingMenu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    protected void init(){
        collectionBtn = findViewById(R.id.collection_btn);
        drawerBtn = findViewById(R.id.drawer_btn);
        slidingBtn = findViewById(R.id.sliding_btn);
        collectionBtn.setOnClickListener(this);
        drawerBtn.setOnClickListener(this);
        slidingBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.collection_btn:
                intent = new Intent(this, CollectionDemoActivity.class);
                break;
            case R.id.drawer_btn:
                intent = new Intent(this, DrawerDemoActivity.class);
                break;
            case R.id.sliding_btn:
                intent = new Intent(this, SlidingMenuActivity.class);
                break;
        }
        if(intent != null)
            startActivity(intent);
    }
}
