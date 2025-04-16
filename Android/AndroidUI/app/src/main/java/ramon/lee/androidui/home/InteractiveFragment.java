package ramon.lee.androidui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ramon.lee.androidui.R;
import ramon.lee.androidui.interactive.DialogActivity;
import ramon.lee.androidui.interactive.FloatActivity;
import ramon.lee.androidui.interactive.MenuActivity;
import ramon.lee.androidui.interactive.NotificationActivity;
import ramon.lee.androidui.interactive.PopUpWindowActivity;
import ramon.lee.androidui.interactive.ToastActivity;

/**
 *  Android 交互视图
 *  1. Menu
 *  2. Dialog
 *  3. Toast
 *  4. Notification
 *  5. PopupWindow
 *  6. Floating window
 */
public class InteractiveFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_interactive, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_interactive_demo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_interactive_demo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_interactive_demo3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ToastActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_interactive_demo4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_interactive_demo5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PopUpWindowActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_interactive_demo6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FloatActivity.class);
                startActivity(intent);
            }
        });
    }
}
