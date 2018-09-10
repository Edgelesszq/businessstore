package activity.com.businessstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.businessstore.Config;
import com.businessstore.MainConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.businessstore.PictureSelectorConfig;
import com.businessstore.model.Goods;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.model.PictureInfo;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import adapter.com.businessstore.GridViewAdapter;

/**
 * CommodityUploadActivity class
 *
 * @author Wuxi
 * @date 2018/7/10
 */
public class CommodityUploadActivity extends BaseActivity implements View.OnClickListener {

    private TextView currentLocation;
    private EditText editTitle, editContent, editPrice, editPriceMin;
    private int pubprice, pubnumber;
    private EditText number;
    private int intNumber, goodsId;
    private Context mContext;
    private ArrayList<String> mPiclist = new ArrayList<>();
    private ArrayList<String> mPiclistNum = new ArrayList<>();
    private GridViewAdapter mGridViewAddImgAdapter;
    private List<PictureInfo> pictureInfoList;
    private ToggleButton switchBtnPrice, switchBtnGoodsNum;
    private LoginResult loginResult;
    private GridView gridView;

    private boolean codex = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_upload);
        mContext = this;

        initView();
        isEditor();
        initViewOn();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (switchBtnPrice.isChecked()) {
            pubprice = 0;
        } else {
            pubprice = 1;
        }
        if (switchBtnGoodsNum.isChecked()) {
            pubnumber = 0;
        } else {
            pubnumber = 1;
        }
        loginResult = SharedPreferencesUtil.getObject(mContext, "loginResult");
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        setTitleView(R.drawable.backimage, R.string.commodity_upload, R.string.save);
        mTitleLefeBackImg.setOnClickListener(this);
        //标题
        editTitle = findViewById(R.id.edit_commodity_title);
        //内容
        editContent = findViewById(R.id.edit_commodity_content);
        //价格
        editPrice = findViewById(R.id.edit_price);
        //优惠价格
        editPriceMin = findViewById(R.id.edit_price2);
        //商品数量
        number = findViewById(R.id.text_number);
        //保存的监听事件
        mTitleRightText.setOnClickListener(this);
        //是否公开价格
        switchBtnPrice = findViewById(R.id.switch_btn_price);
        //是否公开商品个数
        switchBtnGoodsNum = findViewById(R.id.switch_btn_goods_num);
        //当前定位未知
        currentLocation = findViewById(R.id.current_location);
        LinearLayout locationUpload = findViewById(R.id.location_upload);
        locationUpload.setOnClickListener(this);

        ImageView numberMinus, numberAdd;
        numberMinus = findViewById(R.id.img_number_minus);
        numberAdd = findViewById(R.id.img_number_add);
        numberMinus.setOnClickListener(this);
        numberAdd.setOnClickListener(this);

        gridView = findViewById(R.id.grid_pic);
        mGridViewAddImgAdapter = new GridViewAdapter(mContext, mPiclist);
        gridView.setAdapter(mGridViewAddImgAdapter);

        number.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    number.setFocusable(true);
                }
                return false;
            }
        });
    }

    private void initViewOn() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mTitleRightText.setClickable(true);
                    mTitleRightText.setTextColor(ContextCompat.getColor(mContext, R.color.nav_color));
                } else {
                    mTitleRightText.setClickable(false);
                    mTitleRightText.setTextColor(ContextCompat.getColor(mContext, R.color.nav_fontcolor));
                }
            }
        };
        editTitle.addTextChangedListener(textWatcher);
        mGridViewAddImgAdapter.setOnItemClickListener(new GridViewAdapter.OnMyItemClickListener() {
            @Override
            public void myclick(View v, int position) {
                mPiclist.remove(position);
                if (position < mPiclistNum.size()) {
                    mPiclistNum.remove(position);
                    pictureInfoList.remove(position);
                }
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
        switchBtnPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pubprice = 0;
                } else {
                    pubprice = 1;
                }
            }
        });

        switchBtnGoodsNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

    private void isEditor() {
        String edtTitle = getIntent().getStringExtra("editor_title");
        //判断是否有数据（是否是编辑而不是上传）
        if (edtTitle != null) {
            //是编辑即为false，保存即为修改
            codex = false;
            //提取intent传来的数据
            String edtContent = getIntent().getStringExtra("editor_content");
            Double edtPriceMax = getIntent().getDoubleExtra("editor_price", 0);
            Double edtPriceMin = getIntent().getDoubleExtra("editor_price2", 0);
            int edtNumber = getIntent().getIntExtra("editor_number", 0);
            int spubprice = getIntent().getIntExtra("pub_price", 0);
            int spubnum = getIntent().getIntExtra("pub_number", 0);
            String location = getIntent().getStringExtra("editor_location");
            goodsId = getIntent().getIntExtra("editor_goodsId", 0);
            pictureInfoList = getIntent().getParcelableArrayListExtra("editor_picture");

//            设置属性
            for (int i = 0; i < pictureInfoList.size(); i++) {
                //压缩后的图片路径
                String compressPath = pictureInfoList.get(i).getUrlsmall();
                //把图片添加到将要上传的图片数组中
                mPiclist.add(compressPath);
                mPiclistNum.addAll(mPiclist);
            }

            mGridViewAddImgAdapter.notifyDataSetChanged();

            String max = edtPriceMax.toString();
            String min = edtPriceMin.toString();
            String num = edtNumber + "";
            editTitle.setText(edtTitle);
            editContent.setText(edtContent);
            editPrice.setText(max);
            editPriceMin.setText(min);
            number.setText(num);
            currentLocation.setText(location);
            if (spubprice == 0) {
                switchBtnPrice.setChecked(true);
            } else {
                switchBtnPrice.setChecked(false);
            }
            if (spubnum == 0) {
                switchBtnGoodsNum.setChecked(true);
            } else {
                switchBtnGoodsNum.setChecked(false);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.location_upload:
                Intent locationIntent = new Intent(this, AddressItemActivity.class);
                // startActivity(locationIntent);
                startActivityForResult(locationIntent, 2333);
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
                //判断上传还是修改
                if (codex && mPiclist != null) {
                    save();
                } else if (!codex) {
                    editor();
                } else {
                    ToastUtils.showShortToast(mContext, "上传编辑判别失败");
                }
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

    public void minusNumber() {


        intNumber = Integer.parseInt(number.getText().toString().trim());
        number.setText(String.valueOf(intNumber + 1));
    }

    /**
     * 查看大图
     *
     * @param position 当前点击的位置
     */
    private void viewPluImg(int position) {
        ArrayList<PictureInfo> maList = new ArrayList<>(pictureInfoList);
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, mPiclist);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST_NUM, mPiclistNum);
        intent.putParcelableArrayListExtra(MainConstant.IMG_LIST_NUM_ALL, maList);
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

    /**
     * 处理选择的照片的地址
     *
     * @param picList 图片的地址
     */
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                //压缩后的图片路径
                String compressPath = localMedia.getCompressPath();
                //把图片添加到将要上传的图片数组中
                mPiclist.add(compressPath);
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

                    String returnData = data.getStringExtra("dataReturn");
                    Toast.makeText(this, returnData, Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;
            }
        }
        //查看大图页面删除了图片
        if (requestCode == MainConstant.REQUEST_CODE_MAIN && resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
            //要删除的图片的集合
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST);
            //要删除的图片的集合
            ArrayList<String> toDeletePicListNum = data.getStringArrayListExtra(MainConstant.IMG_LIST_NUM);
            List<PictureInfo> toDeletePiclistNumAll = data.getParcelableArrayListExtra(MainConstant.IMG_LIST_NUM_ALL);
            mPiclist.clear();
            mPiclistNum.clear();
            pictureInfoList.clear();
            mPiclist.addAll(toDeletePicList);
            mPiclistNum.addAll(toDeletePicListNum);
            pictureInfoList.addAll(toDeletePiclistNumAll);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
    }

    private void save() {
        List<File> files = new ArrayList<>();

        for (int i = 0; i < mPiclist.size(); i++) {
            File file = new File(mPiclist.get(i));
            files.add(file);
        }
        showDialogprogressBarWithString("正在上传");
        PostRequest<String> request = OkGo.<String>post(Config.URL + "/goods/addGoods")
                .tag(this)
                .addFileParams("pictrueInfo", files)
                .params("sellerId", loginResult.getSellerId())
                .params("appKey", loginResult.getAppKey())
                .params("goodsName", editTitle.getText().toString().trim())
                .params("goodsInfo", editContent.getText().toString().trim())
                .params("tradPosition", currentLocation.getText().toString().trim())
                .params("maxPrice", editPrice.getText().toString().trim())
                .params("minPrice", editPriceMin.getText().toString().trim())
                .params("priceOpen", pubprice)
                .params("goodsStock", number.getText().toString().trim())
                .params("stockOpen", pubnumber);

        request.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String responeseData = response.body();
                Gson gson = new Gson();
                Json<Goods> jsonData = gson.fromJson(responeseData, new TypeToken<Json<Goods>>() {
                }.getType());
                if (jsonData.getCode() == 0) {
                    dissmissDialogprogressBarWithString();
                    Toast.makeText(mContext, jsonData.getMsg(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CommodityUploadActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    dissmissDialogprogressBarWithString();
                    Toast.makeText(mContext, jsonData.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response<String> response) {
                dissmissDialogprogressBarWithString();
                super.onError(response);
                Toast.makeText(mContext, "请求错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editor() {
        List<File> files = new ArrayList<>();
        if (mPiclistNum != null) {
            for (int i = mPiclistNum.size() - 1; i < mPiclist.size(); i++) {
                File file = new File(mPiclist.get(i));
                files.add(file);
            }
        }

        JSONArray pictureArray = new JSONArray();
        for (int i = 0; i < pictureInfoList.size(); i++) {
            Map<String, String> piclistEdit = new HashMap<>(9);
            piclistEdit.put("width", pictureInfoList.get(i).getWidth() + "");
            piclistEdit.put("height", pictureInfoList.get(i).getHeight() + "");
            piclistEdit.put("urllarge", pictureInfoList.get(i).getUrllarge() + "");
            piclistEdit.put("urlsmall", pictureInfoList.get(i).getUrlsmall() + "");
            JSONObject pictureObject = new JSONObject(piclistEdit);
            pictureArray.put(pictureObject);
        }
        showDialogprogressBarWithString("正在完成编辑");
        PostRequest<String> request = OkGo.<String>post(Config.URL + "/goods/editGoods")
                .tag(this)
                .addFileParams("pictrueInfo", files)
                .params("oldPictureInfo", pictureArray.toString())
                .params("sellerId", loginResult.getSellerId())
                .params("appKey", loginResult.getAppKey())
                .params("goodsId", goodsId)
                .params("goodsName", editTitle.getText().toString().trim())
                .params("goodsInfo", editContent.getText().toString().trim())
                .params("tradPosition", currentLocation.getText().toString().trim())
                .params("maxPrice", editPrice.getText().toString().trim())
                .params("minPrice", editPriceMin.getText().toString().trim())
                .params("priceOpen", pubprice)
                .params("goodsStock", number.getText().toString().trim())
                .params("stockOpen", pubnumber);

        request.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String responeseData = response.body();
                Gson gson = new Gson();
                Json<Goods> jsonData = gson.fromJson(responeseData, new TypeToken<Json<Goods>>() {
                }.getType());
                if (jsonData.getCode() == 0) {
                    dissmissDialogprogressBarWithString();
                    Toast.makeText(mContext, jsonData.getMsg(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CommodityUploadActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    dissmissDialogprogressBarWithString();
                    Toast.makeText(mContext, jsonData.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response<String> response) {
                dissmissDialogprogressBarWithString();
                super.onError(response);
                Toast.makeText(mContext, "请求错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

