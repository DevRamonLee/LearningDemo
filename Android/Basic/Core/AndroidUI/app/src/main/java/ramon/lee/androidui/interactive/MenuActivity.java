package ramon.lee.androidui.interactive;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ramon.lee.androidui.R;

public class MenuActivity extends AppCompatActivity {

    // 点击菜单选项的常量Id  
    private static final int menu1 = 1;
    private static final int menu2 = 2;
    private static final int menu3 = 3;
    private static final int menu4 = 4;
    private static final int menu5 = 5;
    private static final int menu6 = 6;
    private static final int menu7 = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }


    /** 
     * @param group 
     *            : 选项组号，一般都设置成0就OK啦 
     * @param itenId 
     *            : 选项的Id 很重要 
     * @param order 
     *            :顺序，一般来说都设置0就行了 
     * @param titelRes 
     *            : 选项的标题名字 
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, menu1, 0, "Menu1");
        menu.add(0, menu2, 0, "Menu2");
        menu.add(0, menu3, 0, "Menu3");
        menu.add(0, menu4, 0, "Menu4");
        menu.add(0, menu5, 0, "Menu5");
        menu.add(0, menu6, 0, "Menu6");
        menu.add(0, menu7, 0, "Menu7");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case menu1:
                Toast.makeText(this, "你点击Menu1", Toast.LENGTH_LONG).show();
            break;
            case menu2:
                Toast.makeText(this, "你点击Menu2", Toast.LENGTH_LONG).show();
                break;
            case menu3:
                Toast.makeText(this, "你点击Menu3", Toast.LENGTH_LONG).show();
                break;
            case menu4:
                Toast.makeText(this, "你点击Menu4", Toast.LENGTH_LONG).show();
                break;
            case menu5:
                Toast.makeText(this, "你点击Menu5", Toast.LENGTH_LONG).show();
                break;
            case menu6:
                Toast.makeText(this, "你点击Menu6", Toast.LENGTH_LONG).show();
                break;
            case menu7:
                Toast.makeText(this, "你点击Menu7", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
}