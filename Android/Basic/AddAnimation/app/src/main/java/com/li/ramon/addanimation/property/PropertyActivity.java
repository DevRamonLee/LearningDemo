package com.li.ramon.addanimation.property;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.li.ramon.addanimation.R;

public class PropertyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
        findViewById(R.id.cross_in_fade_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.cross_in_fade_out:
                        intent = new Intent(PropertyActivity.this, CrossfadeActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
