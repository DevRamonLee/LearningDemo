package ramon.lee.androidui.interactive;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ramon.lee.androidui.R;

public class ToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        findViewById(R.id.btn_default_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDefaultToast();
            }
        });

        findViewById(R.id.btn_gravity_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCenterToast();
            }
        });

        findViewById(R.id.btn_toast_with_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastWithImage();
            }
        });

        findViewById(R.id.btn_custom_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomToast();
            }
        });
    }

    /**
     * 默认 Toast
     */
    private void showDefaultToast() {
        Toast.makeText(this, "默认 Toast", Toast.LENGTH_SHORT).show();
    }

    /**
     * 居中 Toast
     */
    private void showCenterToast() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "居中 Toast", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 带图片 Toast
     */
    private void showToastWithImage() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "带图片的Toast", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(getApplicationContext());
        imageCodeProject.setImageResource(R.drawable.img001);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

    /**
     * 自定义 Toast view
     */
    private void showCustomToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.ll_toast));
        ImageView image = (ImageView) layout
                .findViewById(R.id.img_toast);
        image.setImageResource(R.drawable.img001);
        TextView text = (TextView) layout.findViewById(R.id.tv_toast);
        text.setText("完全自定义Toast");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.RIGHT | Gravity.TOP, 12, 40);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}