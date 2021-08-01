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
import ramon.lee.androidui.animation.frame.FrameAnimActivity;
import ramon.lee.androidui.animation.property.PropertyActivity;
import ramon.lee.androidui.animation.view.ViewAnimActivity;

/**
 * 动画和手势
 */
public class AnimationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_animation_demo0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FrameAnimActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_animation_demo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewAnimActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_animation_demo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PropertyActivity.class);
                startActivity(intent);
            }
        });
    }
}
