package activity.com.businessstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.businessstore.Config;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.model.Order;
import com.businessstore.model.OrderDetails;
import com.businessstore.model.OrderInfo;
import com.businessstore.model.PictureInfo;
import com.businessstore.model.Reply;
import com.businessstore.util.DpConversion;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StatusBarUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import adapter.com.businessstore.AdapterCommodityDetailsActivityListView;

public class OrderCommodityDetailsActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private LinearLayout mImgViewLinearLayout;
    private ListView mListView;
    int leght = 9;
    private AdapterCommodityDetailsActivityListView adapterCommodityDetailsActivityListView;
    private List<Reply> mDatas;
    private LoginResult loginResult;
    private int goodsId,orderId,position = 0;
    private OrderInfo orderInfo;
    private ImageView sellerHead,picture;
    private TextView sellerName,price,remark,goodsName,goodsContent,messageNum,tel,data;
    private List<PictureInfo> pictureInfos = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_main_commodity_details);
        mContext = this;
        loginResult = SharedPreferencesUtil.getObject(mContext,"loginResult");
        getDeviceDensity();
        initView();
        initAdapter();
    }

    private void initAdapter() {
        mDatas = new ArrayList<>();

        mListView = findViewById(R.id.commodity_details_listview);
        adapterCommodityDetailsActivityListView = new AdapterCommodityDetailsActivityListView(mDatas,mContext,goodsId);
        mListView.setAdapter(adapterCommodityDetailsActivityListView);


        Log.d("loglog",goodsId+"+" + orderId);
        showDialogprogressBarWithString("正在加载");
        OkGo.<String>get(Config.URL + "/order/getOrderById")
                .tag(this)
                .params("sellerId",loginResult.getSellerId())
                .params("appKey",loginResult.getAppKey())
                .params("goodsId",goodsId)
                .params("orderId",orderId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        Json<OrderDetails> jsondata = gson.fromJson(response.body(),new TypeToken<Json<OrderDetails>>(){}.getType());
                        if (jsondata.getCode() == 0) {
                            dissmissDialogprogressBarWithString();
                            orderInfo = jsondata.getData().getOrderInfo();
                            mDatas.clear();
                            mDatas.addAll(jsondata.getData().getComment());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(mContext).load(orderInfo.getBuyerHead()).into(sellerHead);
                                    sellerName.setText(orderInfo.getBuyerName());
                                    price.setText(orderInfo.getOrderTotal()+"");
                                    remark.setText("买家备注："+orderInfo.getBuyerRemarks());
                                    goodsName.setText(orderInfo.getGoodsName());
                                    goodsContent.setText(orderInfo.getGoodsInfo());
                                    mTitleRightText.setText(orderInfo.getGoodsNum()+"件");
                                    mTitleCenterText.setText(orderInfo.getBuyerNum()+"已下单");
                                    tel.setText(orderInfo.getBuyerContact());
                                    data.setText(orderInfo.getCreatedAt());
                                    messageNum.setText(mDatas.size()+"条留言");
                                    adapterCommodityDetailsActivityListView.notifyDataSetChanged();
                                    recalculate();
                                }
                            });
                        }else if (jsondata.getCode() == 1){
                            dissmissDialogprogressBarWithString();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dissmissDialogprogressBarWithString();
                    }
                });
    }
    /**
     * 获取当前设备的屏幕密度等基本参数
     */
    protected void getDeviceDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Config.EXACT_SCREEN_HEIGHT = metrics.heightPixels;
        Config.EXACT_SCREEN_WIDTH = metrics.widthPixels;
    }

    private void initView() {
        //获取屏幕宽
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
//        int screenHeight = wm.getDefaultDisplay().getHeight();

        setTitleView2(R.drawable.backimage,R.id.title_center_text,R.id.title_right_text);
        mTitleLefeBackImg.setOnClickListener(this);
        mTitleCenterText.setText("195s4744848@163.com已下单");
        mTitleCenterText.setTextSize(16);
        mTitleCenterText.setGravity(View.TEXT_ALIGNMENT_CENTER);
        mTitleRightText.setText("1件");

        sellerHead = findViewById(R.id.order_seller_head);
        sellerName = findViewById(R.id.order_seller_name);
        price = findViewById(R.id.order_price);
        remark = findViewById(R.id.order_remark);
        goodsName = findViewById(R.id.order_goods_name);
        goodsContent = findViewById(R.id.order_goods_content);
        messageNum = findViewById(R.id.order_message_number);
        tel = findViewById(R.id.order_tel);
        data = findViewById(R.id.order_data);
        messageNum = findViewById(R.id.order_message_number);
        Intent intent = getIntent();
        goodsId = intent.getIntExtra("goodsId",0);
        orderId = intent.getIntExtra("orderId",0);
        //循环画九张展示图
        pictureInfos.addAll(intent.<PictureInfo>getParcelableArrayListExtra("pictureInfo"));
        mImgViewLinearLayout = findViewById(R.id.commodity_details_linearlayout);
        int frishFornumber = 1;
        if (pictureInfos.size() % 3 == 0) {
            frishFornumber = pictureInfos.size() / 3;
        } else {
            frishFornumber = pictureInfos.size() / 3 + 1;
        }
        for (int i = 0; i < frishFornumber; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            mImgViewLinearLayout.addView(linearLayout);
            for (int j = 0; j < (i == frishFornumber - 1 ? pictureInfos.size() - i * 3 : 3); j++) {
                ImageView imageView = new ImageView(this);
                int linearLayoutwidth = (screenWidth - DpConversion.dp2px(mContext, 36))/3;
                LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(linearLayoutwidth, linearLayoutwidth);
                imageView.setLayoutParams(imgParams);
                imageView.setBackgroundColor(0xffF08080);
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                imgParams.setMargins(DpConversion.dp2px(mContext, 3), DpConversion.dp2px(mContext, 3), DpConversion.dp2px(mContext, 3), DpConversion.dp2px(mContext, 3));
                Glide.with(this)
                        .load(pictureInfos.get(position).getUrllarge())
                        .into(imageView);
                linearLayout.addView(imageView);
                final int finalI = i*3+j;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<PictureInfo> mPictureInfoList = new ArrayList<>();
                        mPictureInfoList.addAll(pictureInfos);
                        Intent intent=new Intent(OrderCommodityDetailsActivity.this,BigPhotoActivity.class);
                        intent.putExtra("posi",finalI);
                        intent.putParcelableArrayListExtra("pictureInfoList",mPictureInfoList);
                        startActivity(intent);

                    }
                });
                position++;
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_back_img:
                this.finish();
                break;
            default:break;
        }


    }

    /**
     * 重新计算listView的高度
     */
    private void recalculate(){
        // 获取ListView对应的Adapter
        ListAdapter homeAdapter = mListView.getAdapter();
        if (homeAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = homeAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = homeAdapter.getView(i, null, mListView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            int childHeight = listItem.getMeasuredHeight();
            // 统计所有子项的总高度
            totalHeight += childHeight;
        }

        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = totalHeight + (mListView.getDividerHeight() * (homeAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        mListView.setLayoutParams(params);
    }
}
