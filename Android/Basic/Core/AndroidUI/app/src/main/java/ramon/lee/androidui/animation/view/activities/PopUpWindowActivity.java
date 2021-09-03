package ramon.lee.androidui.animation.view.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

import ramon.lee.androidui.R;

/**
 * PopUpWindow 出现和消失的动画
 */
public class PopUpWindowActivity extends AppCompatActivity {

    private Button popBtn;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);

        initWindow();
        popBtn = findViewById(R.id.show_pop);
        popBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAsDropDown(popBtn);
                }
            }
        });
    }

    private void initWindow() {
        View view = new View(this);
        view.setBackground(getDrawable(R.drawable.xiao_xin_and_xiao_bai));
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.pop_anim);
    }
}
