package ramon.lee.androidui.animation.property;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ramon.lee.androidui.R;
import ramon.lee.androidui.animation.property.activities.CrossFadeActivity;
import ramon.lee.androidui.animation.property.activities.LayoutChangesActivity;
import ramon.lee.androidui.animation.property.activities.ScreenSlidePagerActivity;
import ramon.lee.androidui.animation.property.activities.ZoomActivity;
import ramon.lee.androidui.animation.property.card.CardFlipActivity;

public class PropertyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);
        findViewById(R.id.cross_in_fade_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PropertyActivity.this, CrossFadeActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.view_pager_slide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PropertyActivity.this, ScreenSlidePagerActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.card_animation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PropertyActivity.this, CardFlipActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.zoom_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PropertyActivity.this, ZoomActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.layout_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PropertyActivity.this, LayoutChangesActivity.class);
                startActivity(intent);
            }
        });
    }
}
