package activity.com.businessstore;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.businessstore.util.LogUtil;
import com.businessstore.util.StatusBarUtil;
import com.businessstore.util.ToastUtils;

import org.feezu.liuli.timeselector.TimeSelector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joe on 2018/6/11.
 */

public class AddressItemActivity extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private ImageView location_icon;
    private TextView location_btn,select_other;
    private LocationClient mLocationClient;
    private MyLocationListener mMyLocationListener=new MyLocationListener();
    private List<String> permissionlist=new ArrayList<>(); //权限
   private  Intent callback_intent;
   private int status=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.address_selector_item);
        mContext = this;

        initview();
        requestpermissions();
        InitLocation();
    }


    private void initview() {
    setTitleView(R.drawable.backimage,R.string.select_address);
    mTitleLefeBackImg.setOnClickListener(this);
        select_other=findViewById(R.id.select_other);
        location_btn=findViewById(R.id.location_btn);
        location_icon=findViewById(R.id.location_icon);
        select_other.setOnClickListener(this);
        location_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_other:
            {




                break;
            }
            case R.id.location_btn:
            {

                location_btn.setText("正在定位...");
                mLocationClient.start();

                break;
            }
            case R.id.title_left_back_img:
            {

                callback_intent  =new Intent();
                if (status==0&&select_other.getText().equals("选择其它位置")==true){
                    callback_intent.putExtra("dataReturn",location_btn.getText());
                }
                else {
                    callback_intent.putExtra("dataReturn",select_other.getText());
                }
                setResult(RESULT_OK,callback_intent);
                finish();
                break;
            }
            default:
                break;
        }

    }



    private void InitLocation() {

        mLocationClient = new LocationClient(this.getApplicationContext());
        mLocationClient.registerLocationListener(mMyLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
//可选，设置返回经纬度坐标类型，默认gcj02
//gcj02：国测局坐标；
//bd09ll：百度经纬度坐标；
//bd09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(50000);
//可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
//可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5*60*1000);
//可选，7.2版本新增能力
//如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient.setLocOption(option);
        // 设置定位参数
        mLocationClient.start();
        LogUtil.d("address",""+4);


    }
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuffer stringBuffer=new StringBuffer();
            LogUtil.d("address", "" + 35);
            if (bdLocation.getCity()==null){
                ToastUtils.show(AddressItemActivity.this,R.string.location_fail,Toast.LENGTH_SHORT);
                mLocationClient.stop();
            }
            String province = bdLocation.getProvince();    //获取省份
            String city = bdLocation.getCity();    //获取城市
            String district = bdLocation.getDistrict();    //获取区县
            String street = bdLocation.getStreet();    //获取街道信息
            stringBuffer.append(province+"  ");
            stringBuffer.append(city+"  ");
            stringBuffer.append(district+"  ");
            stringBuffer.append(street);
            final String curstr=stringBuffer.toString();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  //  current_location.setText(curstr);
                    location_btn.setText(curstr);
                    mLocationClient.stop();
                }
            });


        }
    }
    //权限管理
    private void requestpermissions() {

        if (ContextCompat.checkSelfPermission(AddressItemActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(AddressItemActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(AddressItemActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionlist.isEmpty()) {
            String[] permissions = permissionlist.toArray(new String[permissionlist.size()]);
            ActivityCompat.requestPermissions(AddressItemActivity.this, permissions, 1);
        } else {

            LogUtil.d("address",""+1);

        }
    }

    @Override
    public void onBackPressed() {
        callback_intent  =new Intent();
        if (status==0&&select_other.getText().equals("选择其它位置")==true){
            callback_intent.putExtra("dataReturn",location_btn.getText());
        }
        else {
            callback_intent.putExtra("dataReturn",select_other.getText());
        }
        setResult(RESULT_OK,callback_intent);
        super.onBackPressed();

    }
}