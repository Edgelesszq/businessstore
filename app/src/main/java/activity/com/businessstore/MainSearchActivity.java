package activity.com.businessstore;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.businessstore.model.SearchHistory;
import com.businessstore.util.ACache;
import com.businessstore.util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import adapter.com.businessstore.AdapterSearchActivity;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainSearchActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private SearchView searchView;
    private TextView cancle;
    private List<String> searchHistories = new ArrayList<>();
    private ACache mCache;
    private EditText mEt_string_input;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdapterSearchActivity adapterSearchActivity;
    private List<String> arrayset = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        mContext = this;

        mCache = ACache.get(this);
        initView();

        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapterSearchActivity = new AdapterSearchActivity(searchHistories);
        recyclerView.setAdapter(adapterSearchActivity);

        readDatas();


        mEt_string_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //判断是否是搜索键（解决搜索2次的问题）
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    //隐藏键盘
                    ((InputMethodManager)mEt_string_input.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
                    //存取数据
/*                    save();
                    read();*/
//                    SearchHistory  history = new SearchHistory(mEt_string_input.getText().toString());
                    if (mEt_string_input.getText().toString().trim().length()!=0){
                        String newhistory = mEt_string_input.getText().toString();
                        if (searchHistories.contains(newhistory)){
                            searchHistories.remove(searchHistories.lastIndexOf(newhistory));//删除该元素
                        }
                        searchHistories.add(0,newhistory);//加入在第0位
                        save(searchHistories);
                        readDatas();
                    }

                    return true;
                }
                return false;
            }
        });
    }

    private void initView() {
        cancle = findViewById(R.id.mainsearchacvivity_cancle);
        cancle.setOnClickListener(this);
        mEt_string_input = findViewById(R.id.edit_search);
        recyclerView = findViewById(R.id.recycler_searchhistory);
        



//        readDatas();
//        read();
//        history = new SearchHistory("test");
//        searchHistories.add(history);

        //第一种：根据属性：
        /*Class<?> c = searchView.getClass();
        try {
            Field f=c.getDeclaredField("mSearchPlate");//通过反射，获得类对象的一个属性对象
            f.setAccessible(true);//设置此私有属性是可访问的
            View v=(View) f.get(searchView);//获得属性的值
            v.setBackgroundResource(R.drawable.search_bg);//设置此view的背景
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mainsearchacvivity_cancle:
                this.finish();
                break;
        }

    }

    /**
     *
     * 点击save事件
     *
     * @param
     */
    public void save(final List<String> searchHistories){

//        mCache.put("testString",mEt_string_input.getText().toString());
        String flilistArray = GsonUtil.getGson().toJson(searchHistories);
        mCache.put("key", flilistArray);
        Log.d("laze","存储save");
    }

/*    private void saveDates(final Set<SearchHistory> searchHistories){
        new Thread(new Runnable() {
            @Override
            public void run() {

                String flilistArray = GsonUtil.getGson().toJson(searchHistories);
                mCache.put("key", flilistArray);
                Log.d("laze","存储");
            }
        }).start();

    }*/

    /**
     *
     * 点击read事件
     *
     * @param
     */
    public void read(){
//        String testString = mCache.getAsString("testString");
//        if (testString == null){
//            Toast.makeText(this,"没有此记录",Toast.LENGTH_SHORT).show();
//        }
//        history = new SearchHistory("test");
//        searchHistories.add(history);
//        Log.d("laze","调用了读取");
    }

    private void readDatas() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                    JSONArray result = mCache.getAsJSONArray("key");
                    if(result == null){
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

                            adapterSearchActivity.notifyDataSetChanged();

                        }
                    });
            }
        }).start();

    }



    /**
     *
     * 点击clear事件
     *
     * @param
     */
    public void clear(){
        mCache.clear();
    }
}
