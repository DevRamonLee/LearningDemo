package ramon.lee.androidui.layout.fragment;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ramon.lee.androidui.R;

/**
 * Created by meng.li on 2019/1/14.
 * SlidingPanelLayoutActivity 实例二： 左滑菜单结合 Fragment
 */

public class LeftMenuFragment extends Fragment {
    private BookMarkListener bookMarkListener;
    private ListView leftMenuList;
    private String[] bookMarks = {"menu-0", "menu-1", "menu-2"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_menu_fragment, container, false);
        leftMenuList = view.findViewById(R.id.left_fragment_menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_expandable_list_item_1,bookMarks);
        leftMenuList.setAdapter(adapter);
        leftMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(bookMarkListener != null && (getActivity() instanceof BookMarkListener)) {
                    bookMarkListener.onChangeBookMark(bookMarks[i]);
                }
            }
        });
        return view;
    }

    public void setListener(BookMarkListener listener) {
        this.bookMarkListener = listener;
    }

    public interface  BookMarkListener {
        void onChangeBookMark(String bookMark);
    }
}
