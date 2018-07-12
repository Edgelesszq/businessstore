package activity.com.businessstore;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.businessstore.util.LogUtil;
import com.businessstore.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import adapter.com.businessstore.GridViewAdapter;

public class CommodityUploadActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout location_upload;
    private List<String> permissionlist=new ArrayList<>(); //权限
    private LocationClient mLocationClient;
    private MyLocationListener mMyLocationListener=new MyLocationListener();
    private TextView current_location;//当前定位Textview

    private EditText editTitle,editContent;
    private ImageView numberMinus,numberAdd;
    private TextView number;
    private int intNumber;
    private Context mContext;
    private GridView gridView;
    private ArrayList<String> mPiclist = new ArrayList<>();//上传的图片凭证的数据源
    private GridViewAdapter mGridViewAddImgAdapter;//展示上传的图片的适配器



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_upload);
        mContext = this;

        initView();
    }

    private void initView() {
        setTitleView(R.drawable.backimage,R.string.commodity_upload,R.string.save);
        mTitleLefeBackImg.setOnClickListener(this);

        editTitle = findViewById(R.id.edit_commodity_title);
        current_location=findViewById(R.id.current_location);
//        final String editContext = editTitle.getText().toString();
        final Editable editContext = editTitle.getText();
        location_upload=findViewById(R.id.location_upload);
        location_upload.setOnClickListener(this);
        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    mTitleRightText.setTextColor(Color.parseColor("#FDBA43"));
            }
        });
        editContent = findViewById(R.id.edit_commodity_content);
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTitleRightText.setTextColor(Color.parseColor("#FDBA43"));
            }
        });

        numberMinus = findViewById(R.id.img_number_minus);
        numberAdd = findViewById(R.id.img_number_add);
        numberMinus.setOnClickListener(this);
        numberAdd.setOnClickListener(this);
        number = findViewById(R.id.text_number);

        gridView = findViewById(R.id.grid_pic);
        mGridViewAddImgAdapter = new GridViewAdapter(mContext,mPiclist);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == parent.getChildCount() - 1){
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过9张，才能点击
                    if (mPiclist.size() == MainConstant.MAX_SELECT_PIC_NUM) {
                        //最多添加5张图片
                        viewPluImg(position);
                    } else {
                        //添加凭证图片
                        selectPic(MainConstant.MAX_SELECT_PIC_NUM - mPiclist.size());
                    }
                }else {
                    viewPluImg(position);
                }
            }
        });

    }
    //权限管理
    private void requestpermissions() {

        if (ContextCompat.checkSelfPermission(CommodityUploadActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(CommodityUploadActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(CommodityUploadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionlist.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionlist.isEmpty()) {
            String[] permissions = permissionlist.toArray(new String[permissionlist.size()]);
            ActivityCompat.requestPermissions(CommodityUploadActivity.this, permissions, 1);
        } else {
            InitLocation();
            LogUtil.d("address",""+1);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.location_upload:
                requestpermissions();
                mLocationClient.start();
                break;
            case R.id.img_number_minus:
                addNumber();
                break;
            case R.id.img_number_add:
                minusNumber();
                break;

                default:
                    break;
        }

    }
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuffer stringBuffer=new StringBuffer();
            LogUtil.d("address", "" + 35);
            if (bdLocation.getCity()==null){
                ToastUtils.show(CommodityUploadActivity.this,R.string.location_fail,Toast.LENGTH_SHORT);
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
                    current_location.setText(curstr);
                    mLocationClient.stop();
                }
            });


        }
    }

    public void addNumber(){
        intNumber = Integer.parseInt(number.getText().toString().trim());
        if (intNumber > 0){
            number.setText(String.valueOf(intNumber-1));
        }else {
            number.setText("0");
        }
    }

    public void minusNumber(){
        intNumber = Integer.parseInt(number.getText().toString().trim());
        number.setText(String.valueOf(intNumber+1));
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, mPiclist);
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }

    /**
     * 打开相册或者照相机选择凭证图片，最多9张
     *
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mPiclist.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == MainConstant.REQUEST_CODE_MAIN && resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
            mPiclist.clear();
            mPiclist.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
    }
}

