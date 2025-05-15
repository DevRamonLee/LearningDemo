package ramon.lee.androidui.animation.property.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ramon.lee.androidui.R;

public class ScreenSlidePageFragment extends Fragment {
    public static ScreenSlidePageFragment newInstance(int position) {
        Bundle args = new Bundle();
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        args.putInt("position",position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        TextView titleView = rootView.findViewById(R.id.title);
        int position = this.getArguments().getInt("position");
        titleView.setText("Page number "+position);
        return rootView;
    }
}
