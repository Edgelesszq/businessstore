package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.businessstore.Config;
import com.businessstore.model.City;
import com.businessstore.model.Json;
import com.businessstore.model.JsonRegister;
import com.businessstore.model.LoginResult;

import com.businessstore.util.NoDoubleClickListener;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.ToastViewUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.mob.tools.utils.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapter.com.businessstore.AdapterCityArray;


/**
 * Created by joe on 2018/6/12.
 */

public class RegisterUserActivitythree extends BaseActivity implements View.OnClickListener {
    // private FrameLayout address_frameLayout;
    private Context mContext;
    private TextView register_one_ensure2;
    private ImageView img_btn;
    private AutoCompleteTextView zipcode;
    private EditText storename_et, phonenum_et, worktime_et, detailsaddress_et;
    private List<City> list;
    private LoginResult loginResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_three);
        mContext = this;
        loginResult = SharedPreferencesUtil.getObject(getApplicationContext(), "loginResult");
        initview();
    }

    public void initview() {
        setTitleView(R.drawable.backimage, R.string.open_store);
        mTitleLefeBackImg.setOnClickListener(this);

        storename_et = findViewById(R.id.storename_et);//店名
        phonenum_et = findViewById(R.id.phonenum_et);//电话号码
        worktime_et = findViewById(R.id.worktime_et);//工作时间
        zipcode = findViewById(R.id.address_textView);//zipcode
        detailsaddress_et = findViewById(R.id.detailsaddress_et);//详细地址
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            City city = new City("636624", "newyork", "newyork");
            list.add(city);

        }

      /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,list);*/
        AdapterCityArray cityArray = new AdapterCityArray(list, R.layout.address_item_city_auto, mContext);

        zipcode.setAdapter(cityArray);

        register_one_ensure2 = findViewById(R.id.register_one_ensure2);
        register_one_ensure2.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                String store_name = storename_et.getText().toString().trim();//店名
                String phone_num = phonenum_et.getText().toString().trim();//电话号
                String work_time = worktime_et.getText().toString().trim();//营业时间
                String address_str = zipcode.getText().toString().trim();//zipcode
                String address_details = detailsaddress_et.getText().toString().trim();
                LayoutInflater inflater = getLayoutInflater();
                if (store_name.equals("")) {
                    ToastViewUtils.toastShowLoginMessage("请输入店名！", getApplicationContext(), inflater);
                } else if (phone_num.equals("")) {
                    ToastViewUtils.toastShowLoginMessage("请输入电话号码！", getApplicationContext(), inflater);

                } else if (work_time.equals("")) {
                    ToastViewUtils.toastShowLoginMessage("请输入工作时间！", getApplicationContext(), inflater);

                } else if (address_str.equals("")) {
                    ToastViewUtils.toastShowLoginMessage("请输入店铺位置！", getApplicationContext(), inflater);
                }else if(address_str.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入zipcode！", getApplicationContext(), inflater);
                } else {
                    showDialogprogressBarWithString("正在提交...");
                    OkGo.<String>post(Config.URL + "/user/openStore/")
                            .params("sellerId", loginResult.getSellerId())
                            .params("sellerName", loginResult.getSellerName())
                            .params("shopName", store_name)
                            .params("sellerTel", phone_num)
                            .params("shopTime", work_time)
                            .params("zipCode", address_str)
                            .params("detailedAddress", address_details)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    String responseData = response.body().toString().trim();
                                    Gson gson = new Gson();
                                    Json<JsonRegister> dataInfo = gson.fromJson(responseData,
                                            new TypeToken<Json<JsonRegister>>() {
                                            }.getType());
                                    int code = dataInfo.getCode();
                                    Log.d("loglog", responseData);
                                    if (code == 0) {
                                        dissmissDialogprogressBarWithString();
                                        Intent intent = new Intent(RegisterUserActivitythree.this,
                                                RegisterUserActivityFour.class);
                                        startActivity(intent);
                                    } else if (code == 1) {
                                        dissmissDialogprogressBarWithString();
                                        Toast.makeText(mContext, "开店失败!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                }
                            });

                }
            }
        });

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.title_left_back_img: {
                this.finish();
            }
            break;

        }

    }


}
