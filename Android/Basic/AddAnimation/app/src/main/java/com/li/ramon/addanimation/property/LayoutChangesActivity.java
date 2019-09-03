package com.li.ramon.addanimation.property;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.li.ramon.addanimation.R;

public class LayoutChangesActivity extends AppCompatActivity {
    private ViewGroup mContainerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_changes);
        mContainerView = (ViewGroup) findViewById(R.id.container);

    }

    private void addItem() {
        TextView newView;
        newView = new TextView(LayoutChangesActivity.this);
        newView.setText("test layout change");
        mContainerView.addView(newView, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_layout_changes,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                addItem();
        }
        return super.onOptionsItemSelected(item);
    }
}
