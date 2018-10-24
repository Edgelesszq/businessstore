package activity.com.businessstore;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.businessstore.Config;
import com.businessstore.model.City;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hp.hpl.sparta.xpath.Step;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import adapter.com.businessstore.AdapterAddressSearch;

public class StoreAdressActivity extends BaseActivity implements View.OnClickListener {
    private EditText sh;
    private RecyclerView recyclerView;
    private AdapterAddressSearch addressSearch;
    private List<City> list;
    private MyDatabaseHelper dbHelper;
    private Context mContext;
    private LoginResult loginResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_address2);
        mContext = this;
        loginResult = SharedPreferencesUtil.getObject(mContext,"loginResult");

        sh = findViewById(R.id.sh);
        setTitleView(R.drawable.backimage, R.string.my_address);
        mTitleLefeBackImg.setOnClickListener(this);

        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            City city = new City("636624", "纽约", "newYork");
            list.add(city);
        }


        recyclerView = findViewById(R.id.tips_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        addressSearch = new AdapterAddressSearch(getApplicationContext(), list);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(addressSearch);
        addressSearch.setOnmyItemClickListener(new AdapterAddressSearch.OnmyItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                showDialogprogressBarWithString("正在修改");
                OkGo.<String>post(Config.URL + "/user/editUserInfo")
                        .tag(this)
                        .params("zipCode",list.get(position).getZipCode())
                        .params("sellerId",loginResult.getSellerId())
                        .params("appKey",loginResult.getAppKey())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Gson gson = new Gson();
                                Json<LoginResult> jsondata = gson.fromJson(response.body(),new TypeToken<Json<LoginResult>>(){}.getType());
                                if (jsondata.getCode() == 0){
                                    dissmissDialogprogressBarWithString();
                                    ToastUtils.showShortToast(mContext,jsondata.getMsg());
                                    loginResult.setZipCode(jsondata.getData().getZipCode());
                                    loginResult.setCityName(jsondata.getData().getCityName());
                                    loginResult.setStateName(jsondata.getData().getStateName());
                                    SharedPreferencesUtil.putObject(mContext,"loginResult",loginResult);
                                    finish();
                                }else {
                                    ToastUtils.showShortToast(mContext,jsondata.getMsg());
                                }
                            }
                        });
            }
        });
        TextWatcher editWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 6 && s.length() > 1) {
                    recyclerView.setVisibility(View.VISIBLE);
                    list.clear();
                    dbHelper = new MyDatabaseHelper(mContext, "ZipCodeCity.db", null, 1);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor cursor = db.rawQuery("select * from tbl_city " +
                            "where zipCode like '%" + s.toString().trim() + "%' " +
                            "OR stateName like '%" + s.toString().trim() + "%' " +
                            "OR cityName like '%" + s.toString().trim() + "%'", null);
                    List<City> list2 = new ArrayList<>();
                    if (cursor.moveToFirst()) {
                        do {
                            String zipCode = cursor.getString(cursor.getColumnIndex("zipCode"));
                            String stateName = cursor.getString(cursor.getColumnIndex("stateName"));
                            String cityName = cursor.getString(cursor.getColumnIndex("cityName"));
                            City city = new City(zipCode, stateName, cityName);
                            list2.add(city);
                        } while (cursor.moveToNext());
                    }else {
                        Log.d("database","未查询到该城市及zipCode");
                    }
                    list.addAll(list2);
                    cursor.close();

                    addressSearch.notifyDataSetChanged();
                } else {
                    recyclerView.setVisibility(View.GONE);

                }

            }
        };
        sh.addTextChangedListener(editWatcher);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_back_img:
                finish();
        }

    }
}
