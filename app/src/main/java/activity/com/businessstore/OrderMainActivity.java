package activity.com.businessstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
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
import com.businessstore.util.CustomPopWindow;
import com.businessstore.util.CustomPopWindow2;
import com.businessstore.util.DpConversion;
import com.businessstore.util.StatusBarUtil;
import com.businessstore.view.dialog.DialogStyleOne;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.feezu.liuli.timeselector.TimeSelector;

import java.util.List;

import adapter.com.businessstore.AdapterOrderRecycler;

/**
 * Created by joe on 2018/6/11.
 */

public class OrderMainActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, View.OnClickListener {
    private Context mContext;
    private SwipeToLoadLayout swipeToLoadLayout;
    private LinearLayout searchLinerLayout, title_layout;

    private FrameLayout select_time_icon, select_order_icon, processed_orders, unprocessed_orders;
    //    private Toolbar toolbar;
    private ImageView time_down, time_up, order_down, order_up;
    private static Boolean TimeIcon = true, OrderIcon = true;
    private LinearLayout order_test;
    TimeSelector timeSelector;
    //定单完成的recyclerView 适配器
    private EditText order_search_edit;//搜索输入框
    private ImageView clean_iv;//清空图标
    private TextView cancel_tv;//取消按钮

    private RecyclerView recyclerview_completed;
    private AdapterOrderRecycler adapterOrderRecyclerCompleted;

    private CustomPopWindow2 popWindow;
    private boolean mPopwindowIsShow;

    private List<String> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_order_main);
        mContext = this;

//        toolbar=findViewById(R.id.toolbar2);
//        setSupportActionBar(toolbar);
        initview();
        initAdapter();
    }

    private void initAdapter() {
        recyclerview_completed = findViewById(R.id.swipe_target);
        adapterOrderRecyclerCompleted = new AdapterOrderRecycler(mContext, mlist);
        recyclerview_completed.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        recyclerview_completed.setAdapter(adapterOrderRecyclerCompleted);
        adapterOrderRecyclerCompleted.setOnItemClickListener(new AdapterOrderRecycler.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(mContext, "Item 的点击事件", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderMainActivity.this, OrderCommodityDetailsActivity.class);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManage = new LinearLayoutManager(mContext);
        layoutManage.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview_completed.setLayoutManager(layoutManage);
    }

    @SuppressLint("WrongViewCast")
    public void initview() {
        setTitleView(R.drawable.backimage, true);
        mTitleLefeBackImg.setOnClickListener(this);
        //上下刷新
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        mTitleCenterSearchImg.setOnClickListener(this);
        //主界面LinearLayout

        //搜索框
        searchLinerLayout = findViewById(R.id.search_LinearLayout);
        order_search_edit = findViewById(R.id.order_search_edit);
        clean_iv = findViewById(R.id.clean_iv);
        cancel_tv = findViewById(R.id.cancel_tv);
        clean_iv.setOnClickListener(this);
        cancel_tv.setOnClickListener(this);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    clean_iv.setVisibility(View.VISIBLE);
                } else {
                    clean_iv.setVisibility(View.GONE);
                }
            }
        };
        order_search_edit.addTextChangedListener(watcher);

        order_search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if (order_search_edit.getText().toString().length() <= 0) {
                        //  Toast.makeText(AtActivity.this,"请输入用户昵称",Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            search();
                        }
                    }).start();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    // 隐藏软键盘
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
                return true;
            }
        });

        title_layout = findViewById(R.id.title_layout);


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
        // autoRefresh();
    }

    private void search() {
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_back_img:
                this.finish();
                break;

            case R.id.title_center_search_img:
                searchLinerLayout.setVisibility(View.VISIBLE);
                title_layout.setVisibility(View.GONE);
                break;
            case R.id.cancel_tv:
                searchLinerLayout.setVisibility(View.GONE);
                title_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.clean_iv:
                order_search_edit.setText("");
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
            case R.id.order_recyclerview_item_more_pop_delete:

                final DialogStyleOne dialogStyleOne = new DialogStyleOne(mContext);
                dialogStyleOne.setYesOnclickListener("是", new DialogStyleOne.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        dialogStyleOne.dismiss();
                    }
                });
                dialogStyleOne.setNoOnclickListener("否", new DialogStyleOne.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialogStyleOne.dismiss();
                    }
                });
                dialogStyleOne.show();

                break;
            case R.id.order_recyclerview_item_more_pop_editer:
                Toast.makeText(mContext,"触发点击事件",Toast.LENGTH_SHORT).show();
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

    public void showPopWindow(final View mButton1, int position) {

        //三个点的绝对坐标
        int[] location = new int[2];
        mButton1.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
        int x = location[0];//--->x坐标,
        int y = location[1];//--->y坐标
        //三个点控件的宽高
        int morewidth = mButton1.getWidth();
        int moreheight = mButton1.getHeight();
        //获取屏幕宽高
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        int screenHeight = wm.getDefaultDisplay().getHeight();
        //三个点与屏幕底部的高度
        int showHeight = screenHeight - moreheight - y;

        popWindow = new CustomPopWindow2(mContext, this, position);
        //popwindow
        final View view = popWindow.getContentView().findViewById(R.id.order_recyclerview_item_more_layout);
        //pop上面的尖角
        ImageView popTopImg = popWindow.getContentView().findViewById(R.id.order_recyclerview_item_more_head);
        ImageView popTopDown = popWindow.getContentView().findViewById(R.id.order_recyclerview_item_more_head_down);
        popTopDown.setVisibility(View.GONE);
        //获取popwindowde 宽高
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();

        //判断三个点与屏幕底部高度是否大于popwindowde高度。
        if (showHeight >= height) {
            popWindow.show(mButton1, -(width - morewidth),
                    DpConversion.dp2px(mContext, -8));
            mPopwindowIsShow = true;
        } else {
            popTopImg.setVisibility(View.GONE);
            popTopDown.setVisibility(View.VISIBLE);
            popWindow.show(mButton1, -(width - morewidth),
                    -(height + moreheight + DpConversion.dp2px(mContext, -8)));
            mPopwindowIsShow = true;
        }


    }
}