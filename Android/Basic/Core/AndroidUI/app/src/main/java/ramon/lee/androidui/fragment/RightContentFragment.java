package ramon.lee.androidui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by meng.li on 2019/1/14.
 * SlidingPanelLayoutActivity 实例二： 左滑菜单结合 Fragment
 */

public class RightContentFragment extends Fragment {
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        textView = new TextView(getActivity());
        textView.setTextSize(50);
        return textView;
    }

    public void setContent(String bookMark) {
        textView.setText(bookMark);
    }
}
