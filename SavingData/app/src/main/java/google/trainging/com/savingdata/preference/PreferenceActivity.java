package google.trainging.com.savingdata.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import google.trainging.com.savingdata.R;

public class PreferenceActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edit_preference;
    private Button save_btn;
    private Button read_btn;
    private TextView read_preference;

    private static final String prference_key = "preference_key";

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        initView();
    }

    public void initView(){
        edit_preference = (EditText) findViewById(R.id.editText);
        save_btn = (Button) findViewById(R.id.save);
        read_btn = (Button) findViewById(R.id.read);
        read_preference = (TextView) findViewById(R.id.textView);
        save_btn.setOnClickListener(this);
        read_btn.setOnClickListener(this);
        /*方法1：可以指定preference的名字 路径 /data/data/package name/shared/*/
        //sharedPref = getSharedPreferences("shared_file", Context.MODE_PRIVATE);
        /*方法2：获取一个默认的preference，在只需要一个文件时这样用 名字为：preference.PreferenceActivity.xml*/
        sharedPref = getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.save:
                saveData(prference_key,edit_preference.getText().toString());
                break;
            case R.id.read:
                read_preference.setText(readData(prference_key));
                break;
            default:
                break;
        }
    }

    public void saveData(String key,String value){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public String readData(String key){
        return sharedPref.getString(key,"not set value");
    }
}
