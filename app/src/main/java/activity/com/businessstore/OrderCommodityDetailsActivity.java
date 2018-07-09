package activity.com.businessstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.businessstore.util.DpConversion;

import java.util.ArrayList;
import java.util.List;

import adapter.com.businessstore.AdapterCommodityDetailsActivityListView;

public class OrderCommodityDetailsActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private LinearLayout mImgViewLinearLayout;
    private ListView mListView;
    int leght = 9;
    private AdapterCommodityDetailsActivityListView adapterCommodityDetailsActivityListView;
    private List<String> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_main_commodity_details);
        mContext = this;
        initView();
        initAdapter();
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

        //循环画九张展示图
        mImgViewLinearLayout = findViewById(R.id.commodity_details_linearlayout);
        int frishFornumber = 1;
        if (leght % 3 == 0) {
            frishFornumber = leght / 3;
        } else {
            frishFornumber = leght / 3 + 1;
        }
        for (int i = 0; i < frishFornumber; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            mImgViewLinearLayout.addView(linearLayout);
            for (int j = 0; j < (i == frishFornumber - 1 ? leght - i * 3 : 3); j++) {
                ImageView imageView = new ImageView(this);
                int linearLayoutwidth = (screenWidth - DpConversion.dp2px(mContext, 36))/3;
                LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(linearLayoutwidth, linearLayoutwidth);
                imageView.setLayoutParams(imgParams);
                imageView.setBackgroundColor(0xffF08080);
                imgParams.setMargins(DpConversion.dp2px(mContext, 3), DpConversion.dp2px(mContext, 3), DpConversion.dp2px(mContext, 3), DpConversion.dp2px(mContext, 3));
                linearLayout.addView(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,"图片点击事件",Toast.LENGTH_SHORT).show();
                    }
                });
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
        }


    }
}
