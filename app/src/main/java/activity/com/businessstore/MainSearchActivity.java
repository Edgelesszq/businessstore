package activity.com.businessstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.businessstore.Config;
import com.businessstore.model.Goods;
import com.businessstore.model.GoodsList;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.model.PictureInfo;
import com.businessstore.util.ACache;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.ToastUtils;
import com.businessstore.view.popwindow.CustomPopWindow;
import com.businessstore.util.DpConversion;
import com.businessstore.util.GsonUtil;
import com.businessstore.view.dialog.DialogStyleOne;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import adapter.com.businessstore.AdapterSearchActivity;
import adapter.com.businessstore.AdapterSearchResultActivity;
import cn.sharesdk.onekeyshare.OnekeyShare;

import static com.businessstore.util.SharedPreferencesUtil.getObject;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainSearchActivity extends BaseActivity implements View.OnClickListener,OnRefreshListener, OnLoadMoreListener {

    private static final int REFRESH_WHAT = 7778;
    private static final int LOADMORE_WHAT = 7779;
    private Context mContext;
    private SearchView searchView;
    private List<String> searchHistories = new ArrayList<>();
    private ACache mCache;
    private String edt_title, edt_content,location,goodsName;
    private Double edt_price,edt_price2;
    private int pubPrice, pubNumber, edt_number,goodsId;
    private EditText mEt_string_input;      //搜索框
    private ImageView search_clear;         //清除搜索框内容
    private TextView clear_history, history, cancle;         //清除历史记录，历史记录,返回
    private RecyclerView recyclerView, mRecyclerView;      //历史记录显示
    private LinearLayoutManager layoutManager;
    private AdapterSearchActivity adapterSearchActivity;    //适配器
    private ScrollView scrollView;
    private AdapterSearchResultActivity mAdapterMainActivity;   //搜索结果适配器
    private List<Goods> mList = new ArrayList<>();
    private LinearLayout linearLayout;
    private List<String> arrayset = new ArrayList<>();
    private CustomPopWindow popWindow;      //三个点更多操作
    private boolean mPopwindowIsShow;
    private LoginResult loginResult;
    private SwipeToLoadLayout swipeToLoadLayout;
    private int count = 2;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case REFRESH_WHAT:
                    List<Goods> refreshList = (List<Goods>) message.obj;
                    if (mList != null && refreshList != null) {
                        mList.clear();
                        mList.addAll(refreshList);
                        count = 2;
                        mAdapterMainActivity.notifyDataSetChanged();
                    }
                    break;
                case LOADMORE_WHAT:
                    List<Goods> loadmoreList = (List<Goods>) message.obj;
                    if (mList != null && loadmoreList != null) {
                        mList.addAll(loadmoreList);
                        count++;
                        mAdapterMainActivity.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        mContext = this;
        loginResult = SharedPreferencesUtil.getObject(this, "loginResult");

        mCache = ACache.get(this);
        initView();

        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapterSearchActivity = new AdapterSearchActivity(mContext, searchHistories);
        recyclerView.setAdapter(adapterSearchActivity);
        adapterSearchActivity.setItemClickListener(new AdapterSearchActivity.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                goodsName = searchHistories.get(position);
                
                visibility();
            }
        });
        readDatas();//初始读取历史记录
        initAdapter();

        mEt_string_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String sInput = mEt_string_input.getText().toString().trim();
                //判断是否是搜索键（解决搜索2次的问题）
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //隐藏键盘
                    ((InputMethodManager) mEt_string_input.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                    if (sInput.length() != 0) {
                        String newhistory = mEt_string_input.getText().toString();
                        if (searchHistories.contains(newhistory)) {
                            searchHistories.remove(searchHistories.lastIndexOf(newhistory));      //删除重复元素
                        }
                        searchHistories.add(0, newhistory);//加入在第0位
                        save(searchHistories);
                        readDatas();
                    }
                    goodsName = sInput;
                    visibility();

                    return true;
                }
                return false;
            }
        });
    }

    private void visibility() {
        scrollView.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        OkGo.<String>get(Config.URL + "/goods/searchGoods")
                .tag(this)
                .params("sellerId", loginResult.getSellerId())
                .params("appKey", loginResult.getAppKey())
                .params("goodsName",goodsName)
                //页数（每页有固定的商品数）
                .params("p", 1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        Json<GoodsList> jsonData = gson.fromJson(response.body(), new TypeToken<Json<GoodsList>>() {
                        }.getType());
                        if (jsonData.getCode() == 0) {
                            List<Goods> goodsList = jsonData.getData().getList();
                            mList.clear();
                            mList.addAll(goodsList);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapterMainActivity.notifyDataSetChanged();
                                }
                            });
                        } else if (jsonData.getCode() == 1) {
                            ToastUtils.showShortToast(mContext, jsonData.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    private void initView() {
        cancle = findViewById(R.id.mainsearchacvivity_cancle);
        cancle.setOnClickListener(this);

        mEt_string_input = findViewById(R.id.edit_search);
        recyclerView = findViewById(R.id.recycler_searchhistory);
        search_clear = findViewById(R.id.img_searchclear);
        search_clear.setOnClickListener(this);
        clear_history = findViewById(R.id.text_clearhistoryall);
        clear_history.setOnClickListener(this);
        scrollView = findViewById(R.id.scrollView_searchhistory);
        linearLayout = findViewById(R.id.search_visibility);
        mRecyclerView = findViewById(R.id.swipe_target);
        history = findViewById(R.id.text_history);
        history.setOnClickListener(this);

        swipeToLoadLayout = findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    private void initAdapter() {
        mAdapterMainActivity = new AdapterSearchResultActivity(mContext, mList);
        mRecyclerView.setAdapter(mAdapterMainActivity);
        mAdapterMainActivity.setOnItemClickListener(new AdapterSearchResultActivity.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                // RecyclerView Item 的点击事件回调
                Intent intent = new Intent(MainSearchActivity.this, MainCommodityDetailsActivity.class);
                startActivity(intent);
                Toast.makeText(mContext, "Item 的点击事件", Toast.LENGTH_SHORT).show();
            }
        });

        GridLayoutManager layoutManage = new GridLayoutManager(mContext, 1);
        mRecyclerView.setLayoutManager(layoutManage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mainsearchacvivity_cancle:
                this.finish();
                break;
            case R.id.img_searchclear:
                mEt_string_input.setText("");
                break;
            case R.id.text_clearhistoryall:
                clear();
                break;
            case R.id.main_recyclerview_item_more_pop_editer:
                //编辑
                editor();
                break;
            case R.id.main_recyclerview_item_more_pop_delete:
                //删除
                delete();
                break;
            case R.id.main_recyclerview_item_more_pop_share:
                //分享
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();
                // title标题，微信、QQ和QQ空间等平台使用
                oks.setTitle("分享测试标题");
                // titleUrl QQ和QQ空间跳转链接
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("我是分享文本");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                oks.setImagePath("http://img5.imgtn.bdimg.com/it/u=471547240,4067919551&fm=27&gp=0.jpg");//确保SDcard下面存在此张图片
                // url在微信、微博，Facebook等平台中使用
                oks.setUrl("http://sharesdk.cn");
                // 启动分享GUI
                oks.show(this);
                popWindow.dismiss();
                mPopwindowIsShow = true;
                break;

            default:
                break;
        }

    }

    /**
     * 点击删除
     */
    private void delete() {
        final DialogStyleOne dialogStyleOne = new DialogStyleOne(this);
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
        popWindow.dismiss();
        mPopwindowIsShow = true;
    }

    /**
     * 点击编辑
     */
    private void editor() {
        int position = popWindow.getPosition();
        edt_title = mList.get(position).getGoodsName();
        edt_content = mList.get(position).getGoodsInfo();
        edt_price = mList.get(position).getMaxprice();
        edt_number = mList.get(position).getGoodsStock();
        edt_price2 = mList.get(position).getMinPrice();
        location = mList.get(position).getTradPosition();
        goodsId = mList.get(position).getGoodsId();
        List<PictureInfo> pictureInfoList = mList.get(position).getPictureInfo();
        ArrayList<PictureInfo> maList = new ArrayList<>(pictureInfoList);
        Intent editor = new Intent(MainSearchActivity.this, CommodityUploadActivity.class);
        editor.putExtra("editor_title", edt_title);
        editor.putExtra("editor_content", edt_content);
        editor.putExtra("editor_price", edt_price);
        editor.putExtra("editor_number", edt_number);
        editor.putExtra("editor_price2", edt_price2);
        editor.putExtra("editor_location", location);
        editor.putExtra("editor_goodsId", goodsId);
        editor.putParcelableArrayListExtra("editor_picture", maList);
        startActivity(editor);
        popWindow.dismiss();
        mPopwindowIsShow = true;
    }

    /**
     * 点击save事件
     *
     * @param
     */
    public void save(final List<String> searchHistories) {

//        mCache.put("testString",mEt_string_input.getText().toString());
        String flilistArray = GsonUtil.getGson().toJson(searchHistories);
        mCache.put("key", flilistArray);
    }

    /**
     * 点击read事件
     *
     * @param
     */

    private void readDatas() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONArray result = mCache.getAsJSONArray("key");
                if (result == null) {
                    history.setVisibility(View.GONE);
                    return;
                }
                Type mType = new TypeToken<List<String>>() {
                }.getType();
                arrayset = GsonUtil.getGson().fromJson(result.toString(), mType);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        searchHistories.clear();
                        searchHistories.addAll(arrayset);
//                            searchHistories = arrayset;
                        history.setVisibility(View.VISIBLE);
                        adapterSearchActivity.notifyDataSetChanged();

                    }
                });
            }
        }).start();

    }


    /**
     * 点击clear事件
     *
     * @param
     */
    public void clear() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCache.clear();
                        searchHistories.clear();
                        history.setVisibility(View.GONE);
                        adapterSearchActivity.notifyDataSetChanged();
                    }
                });

            }
        }).start();

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


        popWindow = new CustomPopWindow(mContext, this, position);
        //popwindow
        final View view = popWindow.getContentView().findViewById(R.id.main_recyclerview_item_more_layout);
        //pop上面的尖角
        ImageView popTopImg = popWindow.getContentView().findViewById(R.id.main_recyclerview_item_more_head);
        ImageView popTopDown = popWindow.getContentView().findViewById(R.id.main_recyclerview_item_more_head_down);
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

    @Override
    public void onDestroy() {
        //在该生命周期的时候调用该方法，
        mAdapterMainActivity.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onLoadMore() {
        OkGo.<String>get(Config.URL + "/goods/searchGoods")
                .tag(this)
                .params("sellerId", loginResult.getSellerId())
                .params("appKey", loginResult.getAppKey())
                .params("goodsName",goodsName)
                .params("p", count)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        Json<GoodsList> jsonData = gson.fromJson(response.body(), new TypeToken<Json<GoodsList>>() {
                        }.getType());
                        if (jsonData.getCode() == 0) {
                            List<Goods> goodsList = jsonData.getData().getList();
                            Message loadmoreMessage = new Message();
                            loadmoreMessage.what = LOADMORE_WHAT;
                            loadmoreMessage.obj = goodsList;
                            handler.sendMessage(loadmoreMessage);
                            swipeToLoadLayout.setLoadingMore(false);
                        } else if (jsonData.getCode() == 1) {
                            ToastUtils.showShortToast(mContext, jsonData.getMsg());
                            swipeToLoadLayout.setLoadingMore(false);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShortToast(mContext, "加载失败！");
                        swipeToLoadLayout.setLoadingMore(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        OkGo.<String>get(Config.URL + "/goods/searchGoods")
                .tag(this)
                .params("sellerId", loginResult.getSellerId())
                .params("appKey", loginResult.getAppKey())
                //页数（每页有固定的商品数）
                .params("goodsName",goodsName)
                .params("p", 1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        Json<GoodsList> jsonData = gson.fromJson(response.body(), new TypeToken<Json<GoodsList>>() {
                        }.getType());
                        if (jsonData.getCode() == 0) {
                            List<Goods> goodsList = jsonData.getData().getList();
                            Message refreshMessage = new Message();
                            refreshMessage.what = REFRESH_WHAT;
                            refreshMessage.obj = goodsList;
                            handler.sendMessage(refreshMessage);
                            swipeToLoadLayout.setRefreshing(false);
                        } else if (jsonData.getCode() == 1) {
                            ToastUtils.showShortToast(mContext, jsonData.getMsg());
                            swipeToLoadLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShortToast(mContext, "刷新失败！");
                        swipeToLoadLayout.setRefreshing(false);
                    }

                });
    }
}
