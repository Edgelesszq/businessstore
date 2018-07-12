package activity.com.businessstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.businessstore.util.CustomPopWindow;
import com.businessstore.util.DpConversion;

import java.util.List;

import adapter.com.businessstore.AdapterMainActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends BaseActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks{
    private Context mContext;
    private TextView upload_btn;
    //    private NavigationView navView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    //适配器
    private AdapterMainActivity mAdapterMainActivity;
    private RecyclerView mRecyclerView;
    private List<String> mList;
    private CircleImageView circleImageView;

    private ImageView nav_personal_btn, mMainSearchImgview;
    private Button main_loginbtn;
    private FrameLayout myaccount_icon, myorder_icon, setting_icon, third_party_domian,store_address;
    //自定义popwindow对象
    private CustomPopWindow popWindow;
    private boolean mPopwindowIsShow;





    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
//        navView=findViewById(R.id.nav_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = findViewById(R.id.drawerLayout);

        initview();
        initAdapter();
          /*  navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            drawerLayout.closeDrawers();
            return true;
            }
        });*/
    }

    private void initAdapter() {
        mAdapterMainActivity = new AdapterMainActivity(mContext, mList);
        mRecyclerView.setAdapter(mAdapterMainActivity);
        mAdapterMainActivity.setOnItemClickListener(new AdapterMainActivity.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                // RecyclerView Item 的点击事件回调
                    Intent intent = new Intent(MainActivity.this,MainCommodityDetailsActivity.class);
                    startActivity(intent);
                    Toast.makeText(mContext, "Item 的点击事件", Toast.LENGTH_SHORT).show();
            }
        });

        GridLayoutManager layoutManage = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(layoutManage);
    }

    public void initview() {
        mRecyclerView = findViewById(R.id.main_recyclerview);

        //主界面搜索按钮
        mMainSearchImgview = findViewById(R.id.main_search_imgview);
        mMainSearchImgview.setOnClickListener(this);

        nav_personal_btn = findViewById(R.id.nav_personal_btn);
        nav_personal_btn.setOnClickListener(this);//侧滑按钮

        main_loginbtn = findViewById(R.id.main_loginbtn);
        main_loginbtn.setOnClickListener(this);

//        View navHeaderView= navView.getHeaderView(0);
//        myaccount_icon=navHeaderView.findViewById(R.id.myaccount_icon);
        myaccount_icon = findViewById(R.id.myaccount_icon);
        myaccount_icon.setOnClickListener(this);//我的账户

//        myorder_icon=navHeaderView.findViewById(R.id.myorder_icon);
        myorder_icon = findViewById(R.id.myorder_icon);
        myorder_icon.setOnClickListener(this);//我的订单

//        setting_icon=navHeaderView.findViewById(R.id.setting_icon);
        setting_icon = findViewById(R.id.setting_icon);
        setting_icon.setOnClickListener(this);//设置

        upload_btn = findViewById(R.id.upload_btn);
        upload_btn.setOnClickListener(this);

//        third_party_domian = navHeaderView.findViewById(R.id.third_party_domian);
        third_party_domian = findViewById(R.id.third_party_domian);
        third_party_domian.setOnClickListener(this);//从第三方导入域

        circleImageView = findViewById(R.id.image_head);
        circleImageView.setOnClickListener(this);//头像

        store_address=findViewById(R.id.store_address);
        store_address.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.title_left_back_img:
//                drawerLayout.openDrawer(GravityCompat.START);
//                break;
//            case R.id.title_right_search_img:
//                Toast.makeText(mContext,"搜索按钮",Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.title_right_message_img:
//                Toast.makeText(mContext,"消息按钮",Toast.LENGTH_SHORT).show();
//                break;
            //主界面搜索商品按钮
            case R.id.main_search_imgview:
                Intent intentMainSearch = new Intent(MainActivity.this, MainSearchActivity.class);
                startActivity(intentMainSearch);
                break;
            //第三方导入域
            case R.id.third_party_domian:
                Intent intentDomain = new Intent(MainActivity.this, thirdPartyDomianMianActivity.class);
                startActivity(intentDomain);
                break;

            case R.id.nav_personal_btn:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            //登录按钮
            case R.id.main_loginbtn:
                Intent intentlogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentlogin);
                break;
            case R.id.image_head:
                Intent circleImageView = new Intent(MainActivity.this, AccountMainActivity.class);
                startActivity(circleImageView);
                break;
            //我的账号
            case R.id.myaccount_icon:
                Intent myaccount_intent = new Intent(MainActivity.this, AccountMainActivity.class);
                startActivity(myaccount_intent);
                break;
            case R.id.store_address:
                Intent test_intent = new Intent(MainActivity.this, StoreAdressActivity.class);
                startActivity(test_intent);
                break;
            //我的订单
            case R.id.myorder_icon:
                Intent myorder_intent = new Intent(MainActivity.this, OrderMainActivity.class);
                startActivity(myorder_intent);
                break;
            //设置按钮
            case R.id.setting_icon:
                Intent setting_intent = new Intent(MainActivity.this, SettingMainActivity.class);
                startActivity(setting_intent);
                break;
            //上传商品
            case R.id.upload_btn:
                Intent upload_btn_intent = new Intent(MainActivity.this, CommodityUploadActivity.class);
                startActivity(upload_btn_intent);
                break;
            //popwindow Item 的点击事件
            case R.id.main_recyclerview_item_more_pop_editer:
                Toast.makeText(mContext, "编辑", Toast.LENGTH_SHORT).show();
                popWindow.dismiss();
                mPopwindowIsShow = true;
                break;
            case R.id.main_recyclerview_item_more_pop_delete:
                Toast.makeText(mContext, "删除", Toast.LENGTH_SHORT).show();
                popWindow.dismiss();
                mPopwindowIsShow = true;
                break;
            case R.id.main_recyclerview_item_more_pop_share:
                Toast.makeText(mContext, "分享", Toast.LENGTH_SHORT).show();
                popWindow.dismiss();
                mPopwindowIsShow = true;
                break;

            default:
                break;
        }
    }


    public void showPopWindow(final View mButton1) {

        //三个点的绝对坐标
        int[] location = new int[2];
        mButton1.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
        int x = location[0];//--->x坐标,
        int y = location[1];//--->y坐标
        //三个点控件的宽高
        int morewidth = mButton1.getWidth();
        int moreheight = mButton1.getHeight();
        //获取屏幕宽高
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        int screenHeight = wm.getDefaultDisplay().getHeight();
        //三个点与屏幕底部的高度
        int showHeight = screenHeight - moreheight - y;


        popWindow = new CustomPopWindow(mContext, this);
        //popwindow
        final View view = popWindow.getContentView().findViewById(R.id.main_recyclerview_item_more_layout);
        //pop上面的尖角
        ImageView popTopImg = popWindow.getContentView().findViewById(R.id.main_recyclerview_item_more_head);
        ImageView popTopDown = popWindow.getContentView().findViewById(R.id.main_recyclerview_item_more_head_down);
        popTopDown.setVisibility(View.GONE);
        //获取popwindowde 宽高
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();

        //判断三个点与屏幕底部高度是否大于popwindowde高度。
        if (showHeight >= height) {
            popWindow.show(mButton1, -(width - morewidth),
                    DpConversion.dp2px(mContext, -8));
            mPopwindowIsShow = true;
        } else {
            popTopImg.setVisibility(View.GONE);
            popTopDown.setVisibility(View.VISIBLE);
            popWindow.show(mButton1, -(width - morewidth),
                    -(height + moreheight + DpConversion.dp2px(mContext, -8)));
            mPopwindowIsShow = true;
        }


    }

    public void onDestroy() {
        //在该生命周期的时候调用该方法，
        mAdapterMainActivity.onDestroy();
        super.onDestroy();
    }

    //easypermission 权限申请
    private void requestPermissions() {
        String[] perms = {};
        //判断有没有权限
        if (EasyPermissions.hasPermissions(this, perms))
        {
            // 如果有权限了, 就做你该做的事情
             }
            else {
            // 如果没有权限, 就去申请权限
            // this: 上下文 /
            // Dialog显示的正文
            // RC_CAMERA_AND_RECORD_AUDIO请求码, 用于回调的时候判断是哪次申请
            // perms 就是你要申请的权限
         //   EasyPermissions.requestPermissions(this, "写上你需要用权限的理由, 是给用户看的", RC_CAMERA_AND_RECORD_AUDIO, perms);
            }
    }







    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    //权限请求成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    //权限请求失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
