package activity.com.businessstore;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends BaseActivity {
    private TextView upload_btn;
    NavigationView navView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    private ImageView nav_personal_btn;
    private Button main_loginbtn;
    private FrameLayout myaccount_icon,myorder_icon,setting_icon;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView=findViewById(R.id.nav_view);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout=findViewById(R.id.drawerLayout);

        initview();
          /*  navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            drawerLayout.closeDrawers();
            return true;
            }
        });*/
    }
    public void initview(){
        nav_personal_btn=findViewById(R.id.nav_personal_btn);
        nav_personal_btn.setOnClickListener(this);//侧滑按钮

        main_loginbtn=findViewById(R.id.main_loginbtn);
        main_loginbtn.setOnClickListener(this);

        View navHeaderView= navView.getHeaderView(0);
        myaccount_icon=navHeaderView.findViewById(R.id.myaccount_icon);
        myaccount_icon.setOnClickListener(this);//我的账户

        myorder_icon=navHeaderView.findViewById(R.id.myorder_icon);
        myorder_icon.setOnClickListener(this);

        setting_icon=navHeaderView.findViewById(R.id.setting_icon);
        setting_icon.setOnClickListener(this);

        upload_btn=findViewById(R.id.upload_btn);
        upload_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nav_personal_btn:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.main_loginbtn:
                Intent intentlogin=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intentlogin);
                break;
            case R.id.myaccount_icon:
                Intent myaccount_intent=new Intent(MainActivity.this,AccountMainActivity.class);
                startActivity(myaccount_intent);
                break;
            case R.id.myorder_icon:
                Intent myorder_intent=new Intent(MainActivity.this,OrderMainActivity.class);
                startActivity(myorder_intent);
                break;
            case R.id.setting_icon:
                Intent setting_intent=new Intent(MainActivity.this,SettingMainActivity.class);
                startActivity(setting_intent);
                break;
            case R.id.upload_btn:
                Intent upload_btn_intent=new Intent(MainActivity.this,OrderMainActivity.class);
                startActivity(upload_btn_intent);
                break;

            default:
                break;
        }
    }
}
