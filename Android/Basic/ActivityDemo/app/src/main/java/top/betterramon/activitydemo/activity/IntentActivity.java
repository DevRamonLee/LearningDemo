package top.betterramon.activitydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import top.betterramon.activitydemo.R;

/**
 *  IntentFilter 匹配规则
 */
public class IntentActivity extends AppCompatActivity {
    private static final String TAG = "IntentActivityTag";
    private Button openMapBtn;
    private Button showSelectorBtn;
    private Button pickContractBtn;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        init();
    }

    private void init() {
        mContext = IntentActivity.this;
        openMapBtn = (Button)findViewById(R.id.open_map_btn);
        openMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });
        showSelectorBtn = (Button)findViewById(R.id.show_selector_btn);
        showSelectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapshowSelector();
            }
        });
        pickContractBtn = (Button)findViewById(R.id.pick_contract_btn);
        pickContractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickContract();
            }
        });
    }

    // open a map app.
    private void openMap() {
        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        if (verifyResolves(mapIntent)) {
            startActivity(mapIntent);
        }
    }

    // verify resolves
    private boolean verifyResolves(Intent intent) {
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activites = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activites.size() > 0;
        return isIntentSafe;
    }

    // show app selector.
    private void openMapshowSelector() {
        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        String title = "please select";
        //显示应用选择界面
        Intent chooser = Intent.createChooser(mapIntent, title);
        if (verifyResolves(mapIntent)) {
            startActivity(chooser);
        }
    }

    static final int PICK_CONTACT_REQUEST = 1; // 请求码，用来标识你的请求
    // 选择联系人
    private void pickContract() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    // 获取选择结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 验证请求码
        if (requestCode == PICK_CONTACT_REQUEST) {
            // resultCode是由被请求 Activity 通过其 setResult() 方法返回。用于标识处理结果，一般成功返回 RESULT_OK
            if (resultCode == RESULT_OK) {
                // 获取选择的联系人的 URI
                Uri contactUri = data.getData();
                // 只需要号码列
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                // 通过 uri 查询数据
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();
                // 获取 NUMBER 在一行数据中的下标位置
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                Toast.makeText(mContext, "The numer you picked is " + number, Toast.LENGTH_SHORT).show();
            }
        }
    }
}



