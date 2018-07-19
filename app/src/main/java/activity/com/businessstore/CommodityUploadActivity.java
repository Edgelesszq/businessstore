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
import android.widget.Switch;
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
    private List<String> permissionlist = new ArrayList<>(); //权限
    private LocationClient mLocationClient;
    private TextView current_location;//当前定位Textview
    private EditText editTitle, editContent, editPrice,editnumber;
    private ImageView numberMinus, numberAdd;
//    private boolean pubprice,pubnumber;
    private Switch sPrice,sNumber;
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
        setTitleView(R.drawable.backimage, R.string.commodity_upload, R.string.save);
        mTitleLefeBackImg.setOnClickListener(this);

        editTitle = findViewById(R.id.edit_commodity_title);
        editContent = findViewById(R.id.edit_commodity_content);
        editPrice = findViewById(R.id.edit_price);
        number = findViewById(R.id.text_number);
        sPrice = findViewById(R.id.switch_price);
        sNumber = findViewById(R.id.switch_number);

        if (getIntent().getStringExtra("editor_title") != null) {
            String edt_title = getIntent().getStringExtra("editor_title");
            String edt_content = getIntent().getStringExtra("editor_content");
            int edt_price = getIntent().getIntExtra("editor_price",0);
            int edt_number = getIntent().getIntExtra("editor_number",0);
            boolean spubprice = getIntent().getBooleanExtra("pub_price",false);
            boolean spubnum = getIntent().getBooleanExtra("pub_number",false);

            editTitle.setText(edt_title);
            editContent.setText(edt_content);
            editPrice.setText(edt_price+"");
            number.setText(edt_number+"");
            sPrice.setChecked(spubprice);
            sNumber.setChecked(spubnum);
        }

        current_location = findViewById(R.id.current_location);
        final Editable editContext = editTitle.getText();
        location_upload = findViewById(R.id.location_upload);
        location_upload.setOnClickListener(this);

        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    mTitleRightText.setClickable(true);

                    mTitleRightText.setTextColor(getBaseContext().getResources().getColor(R.color.nav_color));
                }
                else {
                    mTitleRightText.setClickable(false);
                    mTitleRightText.setTextColor(getBaseContext().getResources().getColor(R.color.nav_fontcolor));

                }
            }
        };
        editTitle.addTextChangedListener(textWatcher);
       /* editContent.addTextChangedListener(textWatcher);
        editnumber.addTextChangedListener(textWatcher);
        editPrice.addTextChangedListener(textWatcher);*/


        numberMinus = findViewById(R.id.img_number_minus);
        numberAdd = findViewById(R.id.img_number_add);
        numberMinus.setOnClickListener(this);
        numberAdd.setOnClickListener(this);


        gridView = findViewById(R.id.grid_pic);
        mGridViewAddImgAdapter = new GridViewAdapter(mContext, mPiclist);
        gridView.setAdapter(mGridViewAddImgAdapter);
        mGridViewAddImgAdapter.setOnItemClickListener(new GridViewAdapter.OnMyItemClickListener() {
            @Override
            public void myclick(View v, int position) {
                mPiclist.remove(position);
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }

            @Override
            public void myOnlongclick(View v, int position) {

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过9张，才能点击
                    if (mPiclist.size() == MainConstant.MAX_SELECT_PIC_NUM) {
                        //最多添加5张图片
                        viewPluImg(position);
                    } else {
                        //添加凭证图片
                        selectPic(MainConstant.MAX_SELECT_PIC_NUM - mPiclist.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.location_upload:
                Intent locationIntent=new Intent(this,AddressItemActivity.class);
               // startActivity(locationIntent);
                startActivityForResult(locationIntent,2333);
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

    public void addNumber() {
        intNumber = Integer.parseInt(number.getText().toString().trim());
        if (intNumber > 0) {
            number.setText(String.valueOf(intNumber - 1));
        } else {
            number.setText("0");
        }
    }

    public void minusNumber(){
        intNumber = Integer.parseInt(number.getText().toString().trim());
        number.setText(String.valueOf(intNumber + 1));
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

                case 2333:

                        String returnData=data.getStringExtra("dataReturn");
                        Toast.makeText(this,returnData,Toast.LENGTH_SHORT).show();

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

