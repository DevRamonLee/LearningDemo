package ramon.lee.androidui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ramon.lee.androidui.fragment.CustomViewFragment;
import ramon.lee.androidui.fragment.LayoutFragment;

public class MainActivity extends FragmentActivity {
    DemoCollectionAdapter demoCollectionAdapter;
    ViewPager2 viewPager;
    TabLayout tabLayout;
    String[] tabNames = {"Layout", "CustomView"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        demoCollectionAdapter = new DemoCollectionAdapter(this);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(demoCollectionAdapter);

        tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNames[position]);
            }
        }
        ).attach();
    }

    public class DemoCollectionAdapter extends FragmentStateAdapter {
        public DemoCollectionAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;
            if (position == 0) {
                fragment =new LayoutFragment();
            } else {
                fragment = new CustomViewFragment();
            }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}