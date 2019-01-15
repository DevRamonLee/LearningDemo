package ramon.better.com.lifecycle.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ramon.better.com.lifecycle.R;

public class TargetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
    }
}
