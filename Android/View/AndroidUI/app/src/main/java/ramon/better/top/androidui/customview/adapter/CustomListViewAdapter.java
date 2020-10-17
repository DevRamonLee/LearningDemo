package ramon.better.top.androidui.customview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ramon.better.top.androidui.R;

/**
 * Created by meng.li on 2019/1/15.
 * 自定义视图实例三：继承控件，实现一个可以横向滑动删除列表项的 ListView
    定义适配器类
 */

public class CustomListViewAdapter extends ArrayAdapter<String> {
    public CustomListViewAdapter(Context context, int textViewResourceId,
                List<String> objects) {
        super(context, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.custom_listview_item, null);
        } else {
            view = convertView;
        }

        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
        contentTv.setText(getItem(position));
        return view;
    }
}
