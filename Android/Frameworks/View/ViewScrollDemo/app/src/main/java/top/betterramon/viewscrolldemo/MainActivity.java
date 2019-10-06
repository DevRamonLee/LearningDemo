package top.betterramon.viewscrolldemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import top.betterramon.viewscrolldemo.conflict.ConflictDemo1Activity;
import top.betterramon.viewscrolldemo.conflict.ConflictDemo2Activity;
import top.betterramon.viewscrolldemo.scroll.ScrollActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.scroll_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScrollActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.scroll_conflict_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConflictDemo1Activity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.scroll_conflict_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConflictDemo2Activity.class);
                startActivity(intent);
            }
        });
    }
}
