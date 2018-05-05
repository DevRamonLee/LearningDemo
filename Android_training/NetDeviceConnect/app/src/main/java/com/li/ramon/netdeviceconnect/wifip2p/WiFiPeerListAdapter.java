package com.li.ramon.netdeviceconnect.wifip2p;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.li.ramon.netdeviceconnect.R;

import java.util.List;

/**
 * Created by limeng on 2018/5/1.
 */

public class WiFiPeerListAdapter extends BaseAdapter {
    private static final String TAG = WifiDirectActivity.TAG;

    private LayoutInflater mInflater;
    private WifiDirectActivity mActivity;
    List peers;

    public WiFiPeerListAdapter(WifiDirectActivity activity, List peers) {
        mInflater = LayoutInflater.from(activity);
        this.mActivity = activity;
        this.peers = peers;
    }

    @Override
    public int getCount() {
        return peers.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.list_items, null);
            holder = new ViewHolder();
            holder.item = (TextView) view.findViewById(R.id.peer_item);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/

        holder.item.setText(peers.get(i).toString());
        holder.item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    /*连接到设备*/
                mActivity.connect(i);
            }
        });
        return view;
    }

    class ViewHolder {
        TextView item;
    }
}



