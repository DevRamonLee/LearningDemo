package ramon.lee.androidui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ramon.lee.androidui.R;
import ramon.lee.androidui.layout.ConstraintActivityDemo1;
import ramon.lee.androidui.layout.ConstraintActivityDemo10;
import ramon.lee.androidui.layout.ConstraintActivityDemo11;
import ramon.lee.androidui.layout.ConstraintActivityDemo12;
import ramon.lee.androidui.layout.ConstraintActivityDemo2;
import ramon.lee.androidui.layout.ConstraintActivityDemo3;
import ramon.lee.androidui.layout.ConstraintActivityDemo4;
import ramon.lee.androidui.layout.ConstraintActivityDemo5;
import ramon.lee.androidui.layout.ConstraintActivityDemo6;
import ramon.lee.androidui.layout.ConstraintActivityDemo7;
import ramon.lee.androidui.layout.ConstraintActivityDemo8;
import ramon.lee.androidui.layout.ConstraintActivityDemo9;
import ramon.lee.androidui.layout.DrawerLayoutActivity;
import ramon.lee.androidui.layout.FrameLayoutActivityDemo1;
import ramon.lee.androidui.layout.FrameLayoutActivityDemo2;
import ramon.lee.androidui.layout.FrameLayoutActivityDemo3;
import ramon.lee.androidui.layout.GridLayoutActivityDemo1;
import ramon.lee.androidui.layout.GridLayoutActivityDemo2;
import ramon.lee.androidui.layout.LinearLayoutActivityDemo1;
import ramon.lee.androidui.layout.LinearLayoutActivityDemo2;
import ramon.lee.androidui.layout.LinearLayoutActivityDemo3;
import ramon.lee.androidui.layout.LinearLayoutActivityDemo4;
import ramon.lee.androidui.layout.LinearLayoutActivityDemo5;
import ramon.lee.androidui.layout.LinearLayoutActivityDemo6;
import ramon.lee.androidui.layout.LinearLayoutActivityDemo7;
import ramon.lee.androidui.layout.RelativeLayoutActivityDemo1;
import ramon.lee.androidui.layout.RelativeLayoutActivityDemo2;
import ramon.lee.androidui.layout.RelativeLayoutActivityDemo3;
import ramon.lee.androidui.layout.RelativeLayoutActivityDemo4;
import ramon.lee.androidui.layout.SlidingPaneActivityDemo1;
import ramon.lee.androidui.layout.SlidingPaneActivityDemo2;
import ramon.lee.androidui.layout.SlidingPaneActivityDemo3;
import ramon.lee.androidui.layout.TableLayoutActivity;

public class LayoutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_frame_layout_demo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FrameLayoutActivityDemo1.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_frame_layout_demo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FrameLayoutActivityDemo2.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_frame_layout_demo3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FrameLayoutActivityDemo3.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_grid_layout_demo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GridLayoutActivityDemo1.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_grid_layout_demo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GridLayoutActivityDemo2.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_table_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TableLayoutActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_drawer_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DrawerLayoutActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_sliding_pane_demo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SlidingPaneActivityDemo1.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_sliding_pane_demo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SlidingPaneActivityDemo2.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_sliding_pane_demo3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SlidingPaneActivityDemo3.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_relative_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RelativeLayoutActivityDemo1.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_relative_layout_demo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RelativeLayoutActivityDemo2.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_relative_layout_demo3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RelativeLayoutActivityDemo3.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_relative_layout_demo4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RelativeLayoutActivityDemo4.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_linear_layout_demo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinearLayoutActivityDemo1.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_linear_layout_demo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinearLayoutActivityDemo2.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_linear_layout_demo3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinearLayoutActivityDemo3.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_linear_layout_demo4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinearLayoutActivityDemo4.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_linear_layout_demo5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinearLayoutActivityDemo5.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_linear_layout_demo6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinearLayoutActivityDemo6.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_linear_layout_demo7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinearLayoutActivityDemo7.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_constraint_layout_demo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo1.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_constraint_layout_demo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo2.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_constraint_layout_demo3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo3.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_constraint_layout_demo4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo4.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_constraint_layout_demo5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo5.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_constraint_layout_demo6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo6.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_constraint_layout_demo7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo7.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_constraint_layout_demo8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo8.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_constraint_layout_demo9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo9.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_constraint_layout_demo10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo10.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_constraint_layout_demo11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo11.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.btn_constraint_layout_demo12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo12.class);
                startActivity(intent);
            }
        });
    }
}

