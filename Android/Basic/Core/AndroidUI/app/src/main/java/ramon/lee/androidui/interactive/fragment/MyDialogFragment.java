package ramon.lee.androidui.interactive.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ramon.lee.androidui.R;

public class MyDialogFragment extends DialogFragment {

    MyDialogFragmentCallback callback;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 也可以在这里实现，可以加载一个自己实现的 view
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        // 设置 Dialog 进入和退出动画
        window.setWindowAnimations(R.style.dialog_animtion_style);
        // 内边框设置透明色
        window.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.7f;  // 外边框设置透明度
        windowParams.width=WindowManager.LayoutParams.MATCH_PARENT; // 设置宽度充满屏幕
        windowParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
        windowParams.gravity= Gravity.BOTTOM;
        window.setAttributes(windowParams);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog dialog =  new AlertDialog.Builder(getActivity())
                .setTitle("警告")
                .setMessage("你确定要挑战吗")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callback != null) {
                            callback.onPositiveButtonClick();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callback != null) {
                            callback.onNegativeButtonClick();
                        }
                    }
                }).create();
        return dialog;
    }

    public void setMyDialogClickCallback(MyDialogFragmentCallback clickCallback) {
        this.callback = clickCallback;
    }

    public interface MyDialogFragmentCallback {
        void onPositiveButtonClick();
        void onNegativeButtonClick();
    }
}
