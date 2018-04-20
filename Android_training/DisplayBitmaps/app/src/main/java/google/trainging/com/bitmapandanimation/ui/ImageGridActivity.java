package google.trainging.com.bitmapandanimation.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import google.trainging.com.bitmapandanimation.R;

public class ImageGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction  tr = manager.beginTransaction();
        Fragment fragment = new ImageGridFragment();
        tr.add(R.id.fragment_container,fragment);
        tr.commit();
    }
}
