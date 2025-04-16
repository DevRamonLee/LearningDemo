package ramon.lee.androidui.interactive;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
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
    private static final int subMenu1 = 8;
    private static final int subMenu2 = 9;


    private Button btnContextMenu1;
    private Button btnContextMenu2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnContextMenu1 = findViewById(R.id.btn_show_context_menu1);
        btnContextMenu2 = findViewById(R.id.btn_show_context_menu2);
        registerForContextMenu(btnContextMenu1);
        registerForContextMenu(btnContextMenu2);
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
        SubMenu M3 = menu.addSubMenu("Menu0");
        M3.add(0, subMenu1, 0, "SubMenu1");
        M3.add(0, subMenu2, 0, "SubMenu2");

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
            case subMenu1:
                Toast.makeText(this, "你点击SubMenu1", Toast.LENGTH_LONG).show();
                break;
            case subMenu2:
                Toast.makeText(this, "你点击SubMenu2", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.btn_show_context_menu1) {
            menu.add(0, menu1, 0, "Menu1");
            menu.add(0, menu2, 0, "Menu2");
            menu.add(0, menu3, 0, "Menu3");
        } else if (v.getId() == R.id.btn_show_context_menu2) {
            menu.add(0, menu4, 0, "Menu4");
            menu.add(0, menu5, 0, "Menu5");
            menu.add(0, menu6, 0, "Menu6");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case menu1:
                Toast.makeText(this, "你 点击的是Menu1", Toast.LENGTH_LONG).show();
                btnContextMenu1.setText("你点击的是Menu1");
                break;

            case menu2:
                Toast.makeText(this, "你点击的是Menu2", Toast.LENGTH_LONG).show();
                btnContextMenu1.setText("你点击的是Menu2");
                break;
            case menu3:
                Toast.makeText(this, "你点击的是Menu3", Toast.LENGTH_LONG).show();
                btnContextMenu1.setText("你点击的是Menu3");
                break;
            case menu4:
                Toast.makeText(this, "你点击的是Menu4", Toast.LENGTH_LONG).show();
                btnContextMenu2.setText("你点击的是Menu4");
                break;
            case menu5:
                Toast.makeText(this, "你点击的是Menu5", Toast.LENGTH_LONG).show();
                btnContextMenu2.setText("你点击的是Menu5");
                break;
            case menu6:
                Toast.makeText(this, "你点击的是Menu6", Toast.LENGTH_LONG).show();
                btnContextMenu2.setText("你点击的是Menu6");
                break;
        }
        return true;
    }
}