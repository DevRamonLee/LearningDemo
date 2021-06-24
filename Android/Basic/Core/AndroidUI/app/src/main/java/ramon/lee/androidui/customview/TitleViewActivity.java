package ramon.lee.androidui.customview;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ramon.lee.androidui.R;
import ramon.lee.androidui.customview.view.TitleView;


public class TitleViewActivity extends AppCompatActivity {

    private TitleView mTitleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_demo1);
        mTitleBar = (TitleView) findViewById(R.id.title_bar);

        mTitleBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TitleViewActivity.this, "You clicked the back button!" , Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}