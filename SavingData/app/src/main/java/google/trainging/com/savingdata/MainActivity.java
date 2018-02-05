package google.trainging.com.savingdata;

import android.content.Intent;

import google.trainging.com.savingdata.database.DatabaseActivity;
import google.trainging.com.savingdata.file.FileActivity;
import google.trainging.com.savingdata.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 分别使用preference 文件 数据库 保存数据Demo
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button preference_btn;
    private Button file_btn;
    private Button database_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        preference_btn = (Button) findViewById(R.id.sharedpreference);
        file_btn = (Button) findViewById(R.id.android_file);
        database_btn = (Button) findViewById(R.id.android_database);

        preference_btn.setOnClickListener(this);
        file_btn.setOnClickListener(this);
        database_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sharedpreference:
                Intent preferenceIntent = new Intent(MainActivity.this, PreferenceActivity.class);
                startActivity(preferenceIntent);
                break;
            case R.id.android_file:
                Intent fileIntent = new Intent(MainActivity.this, FileActivity.class);
                startActivity(fileIntent);
                break;
            case R.id.android_database:
                Intent dbIntent = new Intent(MainActivity.this, DatabaseActivity.class);
                startActivity(dbIntent);
                break;
        }
    }
}
