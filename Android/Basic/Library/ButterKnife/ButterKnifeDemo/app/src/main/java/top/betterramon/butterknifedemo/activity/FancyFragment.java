package top.betterramon.butterknifedemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;
import top.betterramon.butterknifedemo.R;

/**
 * Created by Ramon on 2019/5/7.
 */
public class FancyFragment extends Fragment {
    @BindView(R.id.test_lv)
    ListView testLv;

    // 绑定多个视图
    @BindViews({R.id.button1, R.id.button2})
    public List<Button> buttonList;

    private Unbinder unbinder;

    // 绑定数组
    @BindArray(R.array.data_array)
    String[] data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fancy_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        // do something with views.
        testLv.setAdapter(new LvAdapter(getContext()));
        return view;
    }

    static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override
        public void apply(@NonNull View view, int index) {
            view.setEnabled(false);
        }
    };

    // 这种方式可以提供参数
    static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override public void set(View view, Boolean value, int index) {
            view.setEnabled(value);
        }
    };

    @OnLongClick(R.id.button1)
    boolean button1OnLongClick() {
        // 对多个视图操作
        ButterKnife.apply(buttonList, DISABLE);
        Toast.makeText(getActivity(), " button list disable",Toast.LENGTH_SHORT).show();
        return true;
    }

    @OnClick(R.id.button2)
    void button2Click() {
        Toast.makeText(getActivity(), " button2 clicked",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 和 fragment 进行解绑
        unbinder.unbind();
    }

    class LvAdapter extends BaseAdapter {
        private LayoutInflater mInfater;

        public LvAdapter(Context context) {
            mInfater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int i) {
            return data[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view != null) {
                holder = (ViewHolder) view.getTag();
            } else {
                view = mInfater.inflate(R.layout.listview_item, viewGroup, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            holder.itemTv.setText(data[i]);
            return view;
        }

    }

    static class ViewHolder {
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.item_tv)
        TextView itemTv;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @OnItemClick(value = R.id.test_lv)
    void onItemClick(int position) {
        Toast.makeText(getContext(), "positon is " + position, Toast.LENGTH_SHORT).show();
    }
}
