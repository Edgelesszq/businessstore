package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
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
import com.businessstore.model.GoodsDetails;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.model.PictureInfo;
import com.businessstore.model.Reply;
import com.businessstore.util.DpConversion;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.ToastUtils;
import com.businessstore.view.scrollview.ObservableScrollView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import adapter.com.businessstore.AdapterCommodityDetailsActivityListView;

public class MainCommodityDetailsActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private LinearLayout mImgViewLinearLayout;
    private ListView mListView;
    private int goodsId;
    private AdapterCommodityDetailsActivityListView adapterCommodityDetailsActivityListView;
    private List<Reply> mDatas;
    private int position=0;
    private List<String> urlList;
    private ObservableScrollView details_scrollview;
    private TextView price,symbol,sellerName,goodsName,goodsContent,commentNum;
    private LoginResult loginResult;
    private ImageView head;
    private List<PictureInfo> pictureInfoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_commodity_details);
        mContext = this;
        //取出缓存
        loginResult = SharedPreferencesUtil.getObject(mContext,"loginResult");

        getDeviceDensity();
        initView();
        initData();
        initAdapter();

    }

    /**
     * 初始化数据
     */
    private void initData() {

        Intent intent = getIntent();
        goodsId = intent.getIntExtra("goodsId",0);
        String nameString = intent.getStringExtra("goodsName");
        String contentString = intent.getStringExtra("goodsInfo");
        int stockDouble = intent.getIntExtra("goodsMinPrice",0);
        Double minPriceDouble = intent.getDoubleExtra("goodsMinPrice",0);
        Double maxPriceDouble = intent.getDoubleExtra("goodsMaxPrice",0);

        goodsName.setText(nameString);
        goodsContent.setText(contentString);
        String pr = minPriceDouble+"";
        price.setText(pr);
        if (loginResult.getSellerName() != null) {
            sellerName.setText(loginResult.getSellerName());
        }
        //加载头像
        if (loginResult.getSellerHead()==null){
            Glide.with(mContext).load(R.drawable.qidong)
                    .into(head);
        }
        else {
            Glide.with(mContext).load(loginResult.getSellerHead())
                    .into(head);
        }

    }

    private void initAdapter() {
        mDatas= new ArrayList<>();
        allInfo();

        mListView = findViewById(R.id.commodity_details_listview);
        adapterCommodityDetailsActivityListView = new AdapterCommodityDetailsActivityListView(mDatas,mContext,goodsId);
        mListView.setAdapter(adapterCommodityDetailsActivityListView);


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
        setTitleViewdetail(R.drawable.backimage, " "," ", R.mipmap.mian_commodity_details_share);

        price=findViewById(R.id.price);
        symbol=findViewById(R.id.symbol);
        head = findViewById(R.id.img_head);
        sellerName = findViewById(R.id.text_user_name);
        goodsName = findViewById(R.id.goods_name);
        goodsContent = findViewById(R.id.goods_content);
        pictureInfoList = getIntent().getParcelableArrayListExtra("goodsPictureInfo");
        commentNum = findViewById(R.id.comment_num);

        details_scrollview=findViewById(R.id.details_scrollview);
        details_scrollview.setScrollViewListener(new ObservableScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                if(y>90){
                    price.setVisibility(View.GONE);
                    symbol.setVisibility(View.GONE);
                    setTitleViewdetail(R.drawable.backimage,symbol.getText().toString(),price.getText().toString(),R.mipmap.mian_commodity_details_share);
                }
                else if(y<=90) {
                    price.setVisibility(View.VISIBLE);
                    symbol.setVisibility(View.VISIBLE);
                    setTitleViewdetail(R.drawable.backimage, " "," ", R.mipmap.mian_commodity_details_share);

                }

            }
        });
        //获取屏幕宽
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        int screenHeight = wm.getDefaultDisplay().getHeight();
        mTitleLefeBackImg.setOnClickListener(this);
        mTitleRightSearchImg.setOnClickListener(this);
        //循环画九张展示图
        mImgViewLinearLayout = findViewById(R.id.commodity_details_linearlayout);
        int frishFornumber = 1;
        if (pictureInfoList.size() % 3 == 0) {
            frishFornumber = pictureInfoList.size() / 3;
        } else {
            frishFornumber = pictureInfoList.size() / 3 + 1;
        }
        for (int i = 0; i < frishFornumber; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            mImgViewLinearLayout.addView(linearLayout);

            for (int j = 0; j < (i == frishFornumber - 1 ? pictureInfoList.size() - i * 3 : 3); j++) {

                ImageView imageView = new ImageView(this);
                int linearLayoutwidth = (screenWidth - DpConversion.dp2px(mContext, 36))/3;
                LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(linearLayoutwidth, linearLayoutwidth);
                imageView.setLayoutParams(imgParams);
                imageView.setBackgroundColor(0xffF08080);
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                imgParams.setMargins(DpConversion.dp2px(mContext, 3), DpConversion.dp2px(mContext, 3), DpConversion.dp2px(mContext, 3), DpConversion.dp2px(mContext, 3));
                Glide.with(this)
                        .load(pictureInfoList.get(position).getUrllarge())
                        .into(imageView);
                linearLayout.addView(imageView);

                final int finalI = i*3+j;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<PictureInfo> mPictureInfoList = new ArrayList<>();
                        mPictureInfoList.addAll(pictureInfoList);
                        Intent intent=new Intent(MainCommodityDetailsActivity.this,BigPhotoActivity.class);
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
            case R.id.title_right_img:
                Toast.makeText(mContext, "分享", Toast.LENGTH_SHORT).show();
                break;
                default:
                    break;

        }
    }

    /**
     * 请求商品内所有信息（商品信息和留言评论）
     */
    private void allInfo(){

        showDialogprogressBarWithString("正在加载");
        OkGo.<String>get(Config.URL + "/goods/getGoodsById")
                .params("sellerId",loginResult.getSellerId())
                .params("appKey",loginResult.getAppKey())
                .params("goodsId",goodsId)
                .params("page",1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        Json<GoodsDetails> jsonData = gson.fromJson(response.body(),new TypeToken<Json<GoodsDetails>>(){}.getType());
                        if (jsonData.getCode() == 0){
                            dissmissDialogprogressBarWithString();
                            mDatas.clear();
                            mDatas.addAll(jsonData.getData().getComment());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String format = getResources().getString(R.string.message_number);
                                    String result = String.format(format,mDatas.size());
                                    commentNum.setText(result);
                                    adapterCommodityDetailsActivityListView.notifyDataSetChanged();
                                    recalculate();
                                }
                            });
                        }else if (jsonData.getCode() == 1){
                            dissmissDialogprogressBarWithString();
                            ToastUtils.showShortToast(mContext,jsonData.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dissmissDialogprogressBarWithString();
                        ToastUtils.showShortToast(mContext,"请求错误");
                    }
                });
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
