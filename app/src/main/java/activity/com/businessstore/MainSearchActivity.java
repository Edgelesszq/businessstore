package activity.com.businessstore;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.businessstore.model.SearchHistory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import adapter.com.businessstore.AdapterSearchActivity;

public class MainSearchActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private SearchView searchView;
    private TextView cancle;
    private List<SearchHistory> searchHistories = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        mContext = this;
        initView();

        RecyclerView recyclerView = findViewById(R.id.recycler_searchhistory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        AdapterSearchActivity adapterSearchActivity = new AdapterSearchActivity(searchHistories);
        recyclerView.setAdapter(adapterSearchActivity);

    }

    private void initView() {
        cancle = findViewById(R.id.mainsearchacvivity_cancle);
        cancle.setOnClickListener(this);

        SearchHistory history1 = new SearchHistory("Test");
        searchHistories.add(history1);
//        searchView =  findViewById(R.id.searchView);
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
}
