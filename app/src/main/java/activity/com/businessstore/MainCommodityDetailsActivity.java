package activity.com.businessstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.businessstore.Config;
import com.businessstore.ShowImagesDialog;
import com.businessstore.util.DpConversion;
import com.businessstore.util.NoScrollListview;
import com.businessstore.util.StatusBarUtil;
import com.businessstore.view.scrollview.ObservableScrollView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import adapter.com.businessstore.AdapterCommodityDetailsActivityListView;

public class MainCommodityDetailsActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private LinearLayout mImgViewLinearLayout;
    private ListView mListView;
    int lenght = 9;
    private AdapterCommodityDetailsActivityListView adapterCommodityDetailsActivityListView;
    private List<String> mDatas;
    static int position=0;
    private List<String> urlList;
    private ObservableScrollView details_scrollview;
    private TextView price,symbol;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_commodity_details);
        mContext = this;
        StatusBarUtil.transparencyBar(this); //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this); //设置白底黑字
        getDeviceDensity();
        initdata();
        initView();
        initAdapter();


    }

    private void initdata() {
        urlList=new ArrayList<>();
        urlList.add("http://a.hiphotos.baidu.com/image/pic/item/00e93901213fb80e3b0a611d3fd12f2eb8389424.jpg");
        urlList.add("http://a.hiphotos.baidu.com/image/pic/item/00e93901213fb80e3b0a611d3fd12f2eb8389424.jpg");
        urlList.add("http://a.hiphotos.baidu.com/image/pic/item/00e93901213fb80e3b0a611d3fd12f2eb8389424.jpg");
        urlList.add("http://a.hiphotos.baidu.com/image/pic/item/00e93901213fb80e3b0a611d3fd12f2eb8389424.jpg");
    }

    private void initAdapter() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("ASD");
        }

        mListView = findViewById(R.id.commodity_details_listview);
        adapterCommodityDetailsActivityListView = new AdapterCommodityDetailsActivityListView(mDatas,mContext);
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
//        int screenHeight = wm.getDefaultDisplay().getHeight();

        mTitleLefeBackImg.setOnClickListener(this);
        mTitleRightSearchImg.setOnClickListener(this);
        //循环画九张展示图
        mImgViewLinearLayout = findViewById(R.id.commodity_details_linearlayout);
        int frishFornumber = 1;
        if (lenght % 3 == 0) {
            frishFornumber = lenght / 3;
        } else {
            frishFornumber = lenght / 3 + 1;
        }
        for (int i = 0; i < frishFornumber; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            mImgViewLinearLayout.addView(linearLayout);

            for (int j = 0; j < (i == frishFornumber - 1 ? lenght - i * 3 : 3); j++) {
                position++;
                ImageView imageView = new ImageView(this);
                int linearLayoutwidth = (screenWidth - DpConversion.dp2px(mContext, 36))/3;
                LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(linearLayoutwidth, linearLayoutwidth);
                imageView.setLayoutParams(imgParams);
                imageView.setBackgroundColor(0xffF08080);
                imgParams.setMargins(DpConversion.dp2px(mContext, 3), DpConversion.dp2px(mContext, 3), DpConversion.dp2px(mContext, 3), DpConversion.dp2px(mContext, 3));

                linearLayout.addView(imageView);


                final int finalI = i*3+j;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,""+finalI +"" ,Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainCommodityDetailsActivity.this,BigPhotoActivity.class);
                        intent.putExtra("posi",finalI);
                        startActivity(intent);
                      // new ShowImagesDialog(mContext,urlList,finalI).show();
                    }
                });
                position++;
            }
        }




        /*for (int i = 0; i < leght; i++) {
            LinearLayout linearLayout = null;
            if (i % 3 == 0) {
                linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setBackgroundColor(0Xffff000);
            }

            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(100, 100);
            imageView.setLayoutParams(imgParams);
            imageView.setBackgroundColor(0xffff0000);
            imgParams.setMargins(0, 0, 10, 10);
            linearLayout.addView(imageView);
            if (i % 3 == 2 && i == leght - 1) {
                mImgViewLinearLayout.addView(linearLayout);
            }


        }*/

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

        }


    }
}
