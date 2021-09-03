package ramon.lee.androidui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ramon.lee.androidui.R;
import ramon.lee.androidui.customview.CustomViewActivityDemo1;
import ramon.lee.androidui.customview.CustomViewActivityDemo2;
import ramon.lee.androidui.customview.CustomViewActivityDemo3;
import ramon.lee.androidui.customview.CustomViewActivityDemo4;
import ramon.lee.androidui.customview.CustomViewActivityDemo5;
import ramon.lee.androidui.customview.CustomViewActivityDemo6;

public class CustomViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_custom_view_demo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomViewActivityDemo1.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_custom_view_demo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomViewActivityDemo2.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_custom_view_demo3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomViewActivityDemo3.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_custom_view_demo4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomViewActivityDemo4.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_custom_view_demo5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomViewActivityDemo5.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_custom_view_demo6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomViewActivityDemo6.class);
                startActivity(intent);
            }
        });
    }
}
