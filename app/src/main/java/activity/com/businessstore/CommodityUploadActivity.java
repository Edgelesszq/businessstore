package activity.com.businessstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonToken;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.businessstore.Config;
import com.businessstore.MainConstant;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.businessstore.PictureSelectorConfig;
import com.businessstore.model.Goods;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.util.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import adapter.com.businessstore.GridViewAdapter;

public class CommodityUploadActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout location_upload;
    private List<String> permissionlist = new ArrayList<>(); //权限
    private LocationClient mLocationClient;
    private TextView current_location;//当前定位Textview
    private EditText editTitle, editContent, editPrice,editPriceMin,editnumber;
    private ImageView numberMinus, numberAdd;
    private int pubprice,pubnumber;
    private EditText number;
    private int intNumber;
    private Context mContext;
    private GridView gridView;
    private ArrayList<String> mPiclist = new ArrayList<>();//上传的图片凭证的数据源
    private GridViewAdapter mGridViewAddImgAdapter;//展示上传的图片的适配器

    private ToggleButton switch_btn_price,switch_btn_goods_num;
    private LoginResult loginResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_upload);
        mContext = this;

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (switch_btn_price.isChecked() == true){
            pubprice = 0;
        }else { pubprice = 1; }
        if (switch_btn_goods_num.isChecked() ==true){
            pubnumber = 0;
        }else {pubnumber = 1;}
        loginResult = SharedPreferencesUtil.getObject(mContext,"loginResult");
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        setTitleView(R.drawable.backimage, R.string.commodity_upload, R.string.save);
        mTitleLefeBackImg.setOnClickListener(this);

        editTitle = findViewById(R.id.edit_commodity_title);//标题

        editContent = findViewById(R.id.edit_commodity_content);//内容
        editPrice = findViewById(R.id.edit_price);//价格
        editPriceMin = findViewById(R.id.edit_price2);//优惠价格
        number = findViewById(R.id.text_number);//商品数量
        mTitleRightText.setOnClickListener(this);//保存的监听事件

        number.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_UP){
                    number.setFocusable(true);
                }

                return false;
            }
        });



        //判断是否有数据（是否是编辑而不是上传）
        if (getIntent().getStringExtra("editor_title") != null) {
            String edt_title = getIntent().getStringExtra("editor_title");
            String edt_content = getIntent().getStringExtra("editor_content");
            Double edt_price_max = getIntent().getDoubleExtra("editor_price",0);
            Double edt_price_min = getIntent().getDoubleExtra("editor_price2",0);
            int edt_number = getIntent().getIntExtra("editor_number",0);
            int spubprice = getIntent().getIntExtra("pub_price",0);
            int spubnum = getIntent().getIntExtra("pub_number",0);

            editTitle.setText(edt_title);
            editContent.setText(edt_content);
            editPrice.setText(edt_price_max+"");
            editPriceMin.setText(edt_price_min+"");
            number.setText(edt_number+"");

        }

        current_location = findViewById(R.id.current_location);//当前定位未知
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
                        //最多添加9张图片
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

        switch_btn_price=findViewById(R.id.switch_btn_price);//是否公开价格

        switch_btn_price.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pubprice = 0;
                } else {
                    pubprice = 1;
                }
            }
        });
        switch_btn_goods_num=findViewById(R.id.switch_btn_goods_num);//是否公开商品个数
        switch_btn_goods_num.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pubnumber = 0;
                } else {
                    pubnumber = 0;
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
                number.setSelection(number.getText().length());

                addNumber();
                break;
            case R.id.img_number_add:
                number.setSelection(number.getText().length());

                minusNumber();
                break;
            case R.id.title_right_text:
                save();
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
    private void save() {
        Log.d("loglog",loginResult.getAppKey());
        List<File> files = new ArrayList<>();

        for (int i=0;i<mPiclist.size();i++){
            File file=new File(mPiclist.get(i));
            files.add(file);
        }
        /*for (int i = 0;i<files.size();i++){
            Log.d("loglog",files.get(i)+"");
        }*/
        PostRequest<String> request = OkGo.<String>post(Config.URL + "/goods/addGoods")
                .tag(this)
                .addFileParams("pictrueInfo",files)
                .params("sellerId",loginResult.getSellerId())
                .params("appKey",loginResult.getAppKey())
                .params("goodsName",editTitle.getText().toString().trim())
                .params("goodsInfo",editContent.getText().toString().trim())
                .params("tradPosition",current_location.getText().toString().trim())
                .params("maxPrice",editPrice.getText().toString().trim())
                .params("minPrice",editPriceMin.getText().toString().trim())
                .params("priceOpen",pubprice)
                .params("goodsStock",number.getText().toString().trim())
                .params("stockOpen",pubnumber);

/*                for (int i = 0;i<mPiclist.size();i++){
                    request.params("pictrueInfo",files[i]);
                }*/

        request.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Log.d("loglog","x"+response.body().toString());
                                String responeseData = response.body().toString().trim();
                                Gson gson = new Gson();
                                Json<Goods> jsonData = gson.fromJson(responeseData,new TypeToken<Json<Goods>>(){}.getType());
                                if (jsonData.getCode() ==0){
                                    Intent intent = new Intent(CommodityUploadActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(mContext,jsonData.getMsg(),Toast.LENGTH_SHORT).show();
                                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Toast.makeText(mContext,"请求错误",Toast.LENGTH_SHORT).show();
            }
        });
    }

}

