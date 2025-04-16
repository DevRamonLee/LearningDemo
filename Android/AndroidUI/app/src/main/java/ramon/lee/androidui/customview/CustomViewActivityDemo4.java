package ramon.lee.androidui.customview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import ramon.lee.androidui.R;
import ramon.lee.androidui.customview.entity.PieData;
import ramon.lee.androidui.customview.view.CheckView;
import ramon.lee.androidui.customview.view.PieView;

/**
 * Canvas 绘制
 */
public class CustomViewActivityDemo4 extends AppCompatActivity {
    PieView mPieView;
    ArrayList<PieData> mPieData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_demo4);
        mPieView = (PieView)findViewById(R.id.pie_view);

        initPieData();
        mPieView.setDate(mPieData);

        final CheckView checkView = (CheckView)findViewById(R.id.check_view);
        checkView.setAnimDuration(3000);
        findViewById(R.id.btn_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkView.check();
            }
        });
        findViewById(R.id.btn_uncheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkView.unCheck();
            }
        });
    }

    private void initPieData() {
        PieData pie1 = new PieData("木叶忍者",60);
        PieData pie2 = new PieData("沙忍", 40);
        PieData pie3 = new PieData("雾忍", 39);
        PieData pie4 = new PieData("云忍", 60);

        mPieData.add(pie1);
        mPieData.add(pie2);
        mPieData.add(pie3);
        mPieData.add(pie4);
    }
}
