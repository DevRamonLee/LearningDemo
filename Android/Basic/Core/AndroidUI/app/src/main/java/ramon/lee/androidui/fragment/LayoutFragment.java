package ramon.lee.androidui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    private Button btnFrameLayoutDemo1;
    private Button btnFrameLayoutDemo2;
    private Button btnFrameLayoutDemo3;
    private Button btnGridLayoutDemo1;
    private Button btnGridLayoutDemo2;
    private Button btnTableLayout;
    private Button btnDrawerLayout;
    private Button btnSlidingPaneLayoutDemo1;
    private Button btnSlidingPaneLayoutDemo2;
    private Button btnSlidingPaneLayoutDemo3;
    private Button btnRelativeLayout;
    private Button btnRelativeLayoutDemo2;
    private Button btnRelativeLayoutDemo3;
    private Button btnRelativeLayoutDemo4;
    private Button btnLinearLayoutDemo1;
    private Button btnLinearLayoutDemo2;
    private Button btnLinearLayoutDemo3;
    private Button btnLinearLayoutDemo4;
    private Button btnLinearLayoutDemo5;
    private Button btnLinearLayoutDemo6;
    private Button btnLinearLayoutDemo7;
    private Button btnConstraintLayoutDemo1;
    private Button btnConstraintLayoutDemo2;
    private Button btnConstraintLayoutDemo3;
    private Button btnConstraintLayoutDemo4;
    private Button btnConstraintLayoutDemo5;
    private Button btnConstraintLayoutDemo6;
    private Button btnConstraintLayoutDemo7;
    private Button btnConstraintLayoutDemo8;
    private Button btnConstraintLayoutDemo9;
    private Button btnConstraintLayoutDemo10;
    private Button btnConstraintLayoutDemo11;
    private Button btnConstraintLayoutDemo12;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnFrameLayoutDemo1 = view.findViewById(R.id.btn_frame_layout_demo1);
        btnFrameLayoutDemo2 = view.findViewById(R.id.btn_frame_layout_demo2);
        btnFrameLayoutDemo3 = view.findViewById(R.id.btn_frame_layout_demo3);
        btnGridLayoutDemo1 = view.findViewById(R.id.btn_grid_layout_demo1);
        btnGridLayoutDemo2 = view.findViewById(R.id.btn_grid_layout_demo2);
        btnTableLayout = view.findViewById(R.id.btn_table_layout);
        btnDrawerLayout = view.findViewById(R.id.btn_drawer_layout);
        btnSlidingPaneLayoutDemo1 = view.findViewById(R.id.btn_sliding_pane_demo1);
        btnSlidingPaneLayoutDemo2 = view.findViewById(R.id.btn_sliding_pane_demo2);
        btnSlidingPaneLayoutDemo3 = view.findViewById(R.id.btn_sliding_pane_demo3);
        btnRelativeLayout = view.findViewById(R.id.btn_relative_layout);
        btnRelativeLayoutDemo2 = view.findViewById(R.id.btn_relative_layout_demo2);
        btnRelativeLayoutDemo3 = view.findViewById(R.id.btn_relative_layout_demo3);
        btnRelativeLayoutDemo4 = view.findViewById(R.id.btn_relative_layout_demo4);
        btnLinearLayoutDemo1 = view.findViewById(R.id.btn_linear_layout_demo1);
        btnLinearLayoutDemo2 = view.findViewById(R.id.btn_linear_layout_demo2);
        btnLinearLayoutDemo3 = view.findViewById(R.id.btn_linear_layout_demo3);
        btnLinearLayoutDemo4 = view.findViewById(R.id.btn_linear_layout_demo4);
        btnLinearLayoutDemo5 = view.findViewById(R.id.btn_linear_layout_demo5);
        btnLinearLayoutDemo6 = view.findViewById(R.id.btn_linear_layout_demo6);
        btnLinearLayoutDemo7 = view.findViewById(R.id.btn_linear_layout_demo7);
        btnConstraintLayoutDemo1 = view.findViewById(R.id.btn_constraint_layout_demo1);
        btnConstraintLayoutDemo2 = view.findViewById(R.id.btn_constraint_layout_demo2);
        btnConstraintLayoutDemo3 = view.findViewById(R.id.btn_constraint_layout_demo3);
        btnConstraintLayoutDemo4 = view.findViewById(R.id.btn_constraint_layout_demo4);
        btnConstraintLayoutDemo5 = view.findViewById(R.id.btn_constraint_layout_demo5);
        btnConstraintLayoutDemo6 = view.findViewById(R.id.btn_constraint_layout_demo6);
        btnConstraintLayoutDemo7 = view.findViewById(R.id.btn_constraint_layout_demo7);
        btnConstraintLayoutDemo8 = view.findViewById(R.id.btn_constraint_layout_demo8);
        btnConstraintLayoutDemo9 = view.findViewById(R.id.btn_constraint_layout_demo9);
        btnConstraintLayoutDemo10 = view.findViewById(R.id.btn_constraint_layout_demo10);
        btnConstraintLayoutDemo11 = view.findViewById(R.id.btn_constraint_layout_demo11);
        btnConstraintLayoutDemo12 = view.findViewById(R.id.btn_constraint_layout_demo12);

        btnFrameLayoutDemo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FrameLayoutActivityDemo1.class);
                startActivity(intent);
            }
        });
        btnFrameLayoutDemo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FrameLayoutActivityDemo2.class);
                startActivity(intent);
            }
        });
        btnFrameLayoutDemo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FrameLayoutActivityDemo3.class);
                startActivity(intent);
            }
        });
        btnGridLayoutDemo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GridLayoutActivityDemo1.class);
                startActivity(intent);
            }
        });
        btnGridLayoutDemo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GridLayoutActivityDemo2.class);
                startActivity(intent);
            }
        });
        btnTableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TableLayoutActivity.class);
                startActivity(intent);
            }
        });
        btnDrawerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DrawerLayoutActivity.class);
                startActivity(intent);
            }
        });
        btnSlidingPaneLayoutDemo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SlidingPaneActivityDemo1.class);
                startActivity(intent);
            }
        });
        btnSlidingPaneLayoutDemo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SlidingPaneActivityDemo2.class);
                startActivity(intent);
            }
        });
        btnSlidingPaneLayoutDemo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SlidingPaneActivityDemo3.class);
                startActivity(intent);
            }
        });

        btnRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RelativeLayoutActivityDemo1.class);
                startActivity(intent);
            }
        });
        btnRelativeLayoutDemo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RelativeLayoutActivityDemo2.class);
                startActivity(intent);
            }
        });
        btnRelativeLayoutDemo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RelativeLayoutActivityDemo3.class);
                startActivity(intent);
            }
        });
        btnRelativeLayoutDemo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RelativeLayoutActivityDemo4.class);
                startActivity(intent);
            }
        });
        btnLinearLayoutDemo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinearLayoutActivityDemo1.class);
                startActivity(intent);
            }
        });

        btnLinearLayoutDemo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinearLayoutActivityDemo2.class);
                startActivity(intent);
            }
        });

        btnLinearLayoutDemo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinearLayoutActivityDemo3.class);
                startActivity(intent);
            }
        });

        btnLinearLayoutDemo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinearLayoutActivityDemo4.class);
                startActivity(intent);
            }
        });

        btnLinearLayoutDemo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinearLayoutActivityDemo5.class);
                startActivity(intent);
            }
        });

        btnLinearLayoutDemo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinearLayoutActivityDemo6.class);
                startActivity(intent);
            }
        });

        btnLinearLayoutDemo7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LinearLayoutActivityDemo7.class);
                startActivity(intent);
            }
        });

        btnConstraintLayoutDemo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo1.class);
                startActivity(intent);
            }
        });

        btnConstraintLayoutDemo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo2.class);
                startActivity(intent);
            }
        });

        btnConstraintLayoutDemo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo3.class);
                startActivity(intent);
            }
        });

        btnConstraintLayoutDemo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo4.class);
                startActivity(intent);
            }
        });

        btnConstraintLayoutDemo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo5.class);
                startActivity(intent);
            }
        });

        btnConstraintLayoutDemo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo6.class);
                startActivity(intent);
            }
        });

        btnConstraintLayoutDemo7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo7.class);
                startActivity(intent);
            }
        });

        btnConstraintLayoutDemo8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo8.class);
                startActivity(intent);
            }
        });

        btnConstraintLayoutDemo9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo9.class);
                startActivity(intent);
            }
        });

        btnConstraintLayoutDemo10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo10.class);
                startActivity(intent);
            }
        });

        btnConstraintLayoutDemo11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo11.class);
                startActivity(intent);
            }
        });

        btnConstraintLayoutDemo12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConstraintActivityDemo12.class);
                startActivity(intent);
            }
        });
    }
}

