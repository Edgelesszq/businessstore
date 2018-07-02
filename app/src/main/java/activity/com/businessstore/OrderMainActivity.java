package activity.com.businessstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.businessstore.util.LogUtil;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.feezu.liuli.timeselector.TimeSelector;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

/**
 * Created by joe on 2018/6/11.
 */

public class OrderMainActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, View.OnClickListener {
    private Context mContext;
    private SwipeToLoadLayout swipeToLoadLayout;
//    private MaterialSearchView search_view;
//    private  FrameLayout search_icon;

    private FrameLayout select_time_icon, select_order_icon, processed_orders, unprocessed_orders;
    //    private Toolbar toolbar;
    private ImageView time_down, time_up, order_down, order_up;
    private static Boolean TimeIcon = true, OrderIcon = true;
    private LinearLayout order_test;
    TimeSelector timeSelector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_order_main);
        mContext = this;

//        toolbar=findViewById(R.id.toolbar2);
//        setSupportActionBar(toolbar);
        initview();
    }

    @SuppressLint("WrongViewCast")
    public void initview() {
        setTitleView(R.drawable.backimage, true);
        mTitleLefeBackImg.setOnClickListener(this);
        //上下刷新
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        //搜索框
//        mTitleCenterSearchView = findViewById(R.id.search_view);
        mTitleCenterSearchView.setHint("搜索商品");
        mTitleCenterSearchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        mTitleCenterSearchView.showSuggestions();
//        search_icon = findViewById(R.id.search_icon);
        mTitleCenterSearchImg.setOnClickListener(this);
        mTitleCenterSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mTitleCenterSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                mTitleCenterSearchImg.setVisibility(View.VISIBLE);
            }
        });


        //选择订单，选择时间
        select_time_icon = findViewById(R.id.select_time_icon);
        select_order_icon = findViewById(R.id.select_order_icon);
        order_up = findViewById(R.id.select_order_up);
        order_down = findViewById(R.id.select_order_down);
        time_up = findViewById(R.id.select_time_up);
        time_down = findViewById(R.id.select_time_down);
        select_time_icon.setOnClickListener(this);
        select_order_icon.setOnClickListener(this);
        order_test = findViewById(R.id.order_test);
        processed_orders = findViewById(R.id.processed_orders);//订单选择界面
        unprocessed_orders = findViewById(R.id.unprocessed_orders);


        timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                Toast.makeText(getApplicationContext(), time, Toast.LENGTH_SHORT).show();
            }
        }, "1970-1-1 00:00", "2030-12-1 12:00", "00:00", "12:00");
        timeSelector.setMode(TimeSelector.MODE.YMD);
        autoRefresh();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_back_img:
                this.finish();
                break;

            case R.id.title_center_search_img:
                mTitleCenterSearchView.showSearch();
                mTitleCenterSearchImg.setVisibility(View.GONE);
                break;
            case R.id.select_time_icon:
                if (TimeIcon) {

                    timeSelector.show();
                }
                break;
            case R.id.select_order_icon:
                if (OrderIcon) {
                    order_up.setVisibility(View.VISIBLE);
                    order_down.setVisibility(View.GONE);
                    order_test.setVisibility(View.VISIBLE);
                    order_test.bringToFront();

                    OrderIcon = false;
                } else {
                    order_up.setVisibility(View.GONE);
                    order_down.setVisibility(View.VISIBLE);
                    order_test.setVisibility(View.GONE);

                    OrderIcon = true;
                }

                break;
        }
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
                // mAdapter.add("REFRESH:\n" + new Date());
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
                //mAdapter.add("LOAD MORE:\n" + new Date());
            }
        }, 2000);
    }

    private void autoRefresh() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }
}