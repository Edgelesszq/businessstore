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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.businessstore.Config;
import com.businessstore.model.Goods;
import com.businessstore.model.GoodsList;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.model.PictureInfo;
import com.businessstore.util.ActivityUtil;
import com.businessstore.util.StringUtil;
import com.businessstore.view.popwindow.CustomPopWindow;
import com.businessstore.util.DpConversion;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StatusBarUtil;
import com.businessstore.view.dialog.DialogStyleOne;
import com.businessstore.view.popwindow.CommonPopupWindow;
import com.businessstore.view.popwindow.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import adapter.com.businessstore.Adapter2MainActivity;
import adapter.com.businessstore.AdapterMainActivity;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends BaseActivity implements View.OnClickListener,
        CommonPopupWindow.ViewInterface {

    private Context mContext;
    private TextView upload_btn,user_name,user_num,user_address;
    //    private NavigationView navView;
    private String edt_title, edt_content,location;
    private Double edt_price,edt_price2;
    private int pubPrice,pubNumber,edt_number;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    //适配器
    private AdapterMainActivity nAdapterMainActivity;
    private Adapter2MainActivity mAdapterMainActivity;
    private RecyclerView mRecyclerView;
    private List<Goods> mList;
    private List<PictureInfo> mPList;
    private CircleImageView circleImageView;
    private CommonPopupWindow popupWindow;
    private ImageView nav_personal_btn, mMainSearchImgview,qrcode,message_imgview;
    private Button main_loginbtn;
    private FrameLayout myaccount_icon, myorder_icon, setting_icon, third_party_domian,
            store_address,mystore;



    //自定义popwindow对象
    private CustomPopWindow popWindow;
    private boolean mPopwindowIsShow;
    //用户信息
    private LoginResult loginResult;

    private long exitTime = 0; //退出程序

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginResult = SharedPreferencesUtil.getObject(this,"loginResult");


       if (loginResult==null||loginResult.getNumActiva()==0){
           ActivityUtil.skipActivity(MainActivity.this,LoginActivity.class);
       }

       else {


           setContentView(R.layout.activity_main);
           mContext = this;
//        navView=findViewById(R.id.nav_view);
           StatusBarUtil.StatusBarLightMode_white(this);
           toolbar = findViewById(R.id.toolbar);
           setSupportActionBar(toolbar);
           getSupportActionBar().setDisplayShowTitleEnabled(false);
           drawerLayout = findViewById(R.id.drawerLayout);
           initview();
           initGoods();
           initAdapter();
       }

          /*  navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            drawerLayout.closeDrawers();
            return true;
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginResult = SharedPreferencesUtil.getObject(this,"loginResult");

//        initview();
//        initGoods();
//        initAdapter();

    }

    private void initGoods() {
        mList = new ArrayList<>();
        mPList = new ArrayList<>();

/*        Gson gson = new Gson();
        Json<GoodsList> jsonData = gson.fromJson(Config.TEST_JSON, new TypeToken<Json<GoodsList>>(){}.getType());
        List<Goods> goodsList = jsonData.getData().getList();
        for (int i=0;i<goodsList.size();i++) {
            mList.add(goodsList.get(i));
        }*/

//        Log.d("loglog",jsonData.getData().getList().get(0).getGoodsInfo());
//        Log.d("loglog",jsonData.getData().getList().get(0).getPictureInfo().get(1).getUrllarge());
        OkGo.<String>get(Config.URL + "/goods/goodsList")
                .tag(this)
//                .params("sellerId",loginResult.getSellerId())
//                .params("appKey",loginResult.getAppKey())
                .params("p",1)//页数（每页有固定的商品数）
                .params("page",0)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d("loglog",response.body());
                        String responseData = response.body().toString().trim();
                        Gson gson = new Gson();
                        Json<GoodsList> jsonData = gson.fromJson(responseData, new TypeToken<Json<GoodsList>>(){}.getType());
                        List<Goods> goodsList = jsonData.getData().getList();
                        for (int i=0;i<goodsList.size();i++) {
                            mList.add(goodsList.get(i));
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            mAdapterMainActivity.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    private void initAdapter() {
        mAdapterMainActivity = new Adapter2MainActivity(mContext, mList);
        mRecyclerView.setAdapter(mAdapterMainActivity);
        mAdapterMainActivity.setOnItemClickListener(new AdapterMainActivity.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                // RecyclerView Item 的点击事件回调
                Intent intent = new Intent(MainActivity.this,
                        MainCommodityDetailsActivity.class);
                startActivity(intent);
                Toast.makeText(mContext, "Item 的点击事件", Toast.LENGTH_SHORT).show();
            }
        });

        GridLayoutManager layoutManage = new GridLayoutManager(mContext, 1);
        mRecyclerView.setLayoutManager(layoutManage);
    }

    public void initview() {
        message_imgview=findViewById(R.id.message_imgview);//我的消息界面
        message_imgview.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.main_recyclerview);

        qrcode=findViewById(R.id.qrcode);//二维码
        qrcode.setOnClickListener(this);
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

        store_address = findViewById(R.id.store_address);
        store_address.setOnClickListener(this);

        user_name = findViewById(R.id.text_user_name);
        user_name.setOnClickListener(this);//昵称

        user_num = findViewById(R.id.text_user_number);//账号

        user_address = findViewById(R.id.text_user_address);//店铺位置


        //设置头像
        if (loginResult.getSellerHead()!=null){
//            circleImageView.setImageDrawable(user.getSellerHead());
            Glide.with(this).load(loginResult.getSellerHead()).into(circleImageView);
        }
        if (loginResult.getSellerHead()==null){
            Glide.with(this).load(R.drawable.qidong).into(circleImageView);

        }
        //设置用户名
        if (StringUtil.isBlank(loginResult.getSellerName())){
            user_name.setText(loginResult.getSellerNum());
        }
        if (!StringUtil.isBlank(loginResult.getSellerName())){
            user_name.setText(loginResult.getSellerName());
        }
        //设置我的账号
        if (loginResult.getSellerNum()!=null){
            user_num.setText(loginResult.getSellerNum());
        }
        //设置店的位置
        if(loginResult.getDetailedAddress()!=null){
            user_address.setText(loginResult.getDetailedAddress());
        }
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
                Intent intentMainSearch = new Intent(MainActivity.this,
                        MainSearchActivity.class);
                startActivity(intentMainSearch);
                break;
            //第三方导入域
            case R.id.third_party_domian:
                Intent intentDomain = new Intent(MainActivity.this,
                        thirdPartyDomianMianActivity.class);
                startActivity(intentDomain);
                break;

            case R.id.nav_personal_btn:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            //登录按钮
            case R.id.main_loginbtn:
                Intent intentlogin = new Intent(MainActivity.this,
                        LoginActivity.class);
                startActivity(intentlogin);
                break;
            case R.id.image_head:
//                OkGo.<String>get(Config.URL+"")
                Intent circleImageView = new Intent(MainActivity.this,
                        AccountMainActivity.class);
                startActivity(circleImageView);
                break;
            case R.id.text_user_name:
                Intent usernametext = new Intent(MainActivity.this,
                        AccountMainActivity.class);
                startActivity(usernametext);
                break;
            //我的账号
            case R.id.myaccount_icon:
                Intent myaccount_intent = new Intent(MainActivity.this,
                        AccountMainActivity.class);
                startActivity(myaccount_intent);
                break;
            case R.id.store_address:
                Intent test_intent = new Intent(MainActivity.this,
                        StoreAdressActivity.class);
                startActivity(test_intent);
                break;
            //我的订单
            case R.id.myorder_icon:
                Intent myorder_intent = new Intent(MainActivity.this,
                        OrderMainActivity.class);
                startActivity(myorder_intent);
                break;
            //设置按钮
            case R.id.setting_icon:
                Intent setting_intent = new Intent(MainActivity.this,
                        SettingMainActivity.class);
                startActivity(setting_intent);
                break;
            //上传商品
            case R.id.upload_btn:
                Intent upload_btn_intent = new Intent(MainActivity.this,
                        CommodityUploadActivity.class);
                startActivity(upload_btn_intent);
                break;
            //popwindow Item 的点击事件
            case R.id.main_recyclerview_item_more_pop_editer:
//                Toast.makeText(mContext, "编辑", Toast.LENGTH_SHORT).show();
                editor();
                break;
            case R.id.main_recyclerview_item_more_pop_delete:
//                Toast.makeText(mContext, "删除", Toast.LENGTH_SHORT).show();
                final DialogStyleOne dialogStyleOne = new DialogStyleOne(this);
                dialogStyleOne.setYesOnclickListener("是", new DialogStyleOne.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        dialogStyleOne.dismiss();
                    }
                });
                dialogStyleOne.setNoOnclickListener("否", new DialogStyleOne.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialogStyleOne.dismiss();
                    }
                });
                dialogStyleOne.show();
                popWindow.dismiss();
                mPopwindowIsShow = true;
                break;
            case R.id.main_recyclerview_item_more_pop_share:
//                Toast.makeText(mContext, "分享", Toast.LENGTH_SHORT).show();
//                showPopWindowUpdate_HeadPortrait(view);
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();

                // title标题，微信、QQ和QQ空间等平台使用
                oks.setTitle("分享测试标题");
                // titleUrl QQ和QQ空间跳转链接
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("我是分享文本");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                oks.setImagePath("http://img5.imgtn.bdimg.com/it/u=471547240,4067919551&fm=27&gp=0.jpg");//确保SDcard下面存在此张图片
                // url在微信、微博，Facebook等平台中使用
                oks.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网使用
//                oks.setComment("我是测试评论文本");
                // 启动分享GUI
                oks.show(this);
                popWindow.dismiss();
                mPopwindowIsShow = true;
                break;
            case R.id.qrcode:
                Toast.makeText(this,"二维码",Toast.LENGTH_SHORT).show();
                break;

            case R.id.message_imgview:
                Intent mymessageIntent=new Intent(MainActivity.this,
                        MyMessageActivity.class);
                startActivity(mymessageIntent);

            default:
                break;
        }
    }


    public void showPopWindow(final View mButton1,int position) {

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


        popWindow = new CustomPopWindow(mContext, this, position);
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
        //mAdapterMainActivity.onDestroy();
        super.onDestroy();
    }


    public void showPopWindowUpdate_HeadPortrait(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.popwindow_share, null);
        //测量View的宽高
        CommonUtil.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popwindow_share)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.8f)//取值范围0.0f-1.0f 值越小越暗
                // .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popwindow_share:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    public void editor(){
        int position = popWindow.getPosition();
        mList.get(position);
        edt_title = mList.get(position).getGoodsName();
        edt_content = mList.get(position).getGoodsInfo();
        edt_price = mList.get(position).getMaxprice();
        edt_number = mList.get(position).getGoodsStock();
        pubPrice = mList.get(position).getPriceOpen();
        pubNumber = mList.get(position).getStockOpen();
        edt_price2 = mList.get(position).getMinPrice();
        location = mList.get(position).getTradPosition();
        List<PictureInfo> pictureInfoList= mList.get(position).getPictureInfo();

        Intent editor = new Intent(MainActivity.this,
                CommodityUploadActivity.class);
        editor.putExtra("editor_title", edt_title);
        editor.putExtra("editor_content", edt_content);
        editor.putExtra("editor_price", edt_price);
        editor.putExtra("editor_number", edt_number);
        editor.putExtra("pub_price", pubPrice);
        editor.putExtra("pub_number", pubNumber);
        editor.putExtra("editor_price2",edt_price2);
        editor.putExtra("editor_location",location);
        editor.putExtra("editor_picture",(Serializable)pictureInfoList);

        startActivity(editor);
        popWindow.dismiss();
        mPopwindowIsShow = true;
    }

}
