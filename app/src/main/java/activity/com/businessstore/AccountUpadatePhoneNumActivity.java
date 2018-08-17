package activity.com.businessstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.businessstore.Config;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.util.ActivityUtil;
import com.businessstore.util.NoDoubleClickListener;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StatusBarUtil;
import com.businessstore.util.StringUtil;
import com.businessstore.util.ToastViewUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


public class AccountUpadatePhoneNumActivity extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private ImageView clean_iv;
    private EditText phonenum_et;
    private LoginResult loginResult;
    private String phonenumstr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_updatephonenum);
        loginResult = SharedPreferencesUtil.getObject(mContext,"loginResult");
        initview();
    }
    public void initview(){
        mContext = this;
        setTitleView(R.drawable.backimage,R.string.update_phonenum,R.string.save);

        mTitleRightText.setClickable(false);
        clean_iv=findViewById(R.id.clean_iv);
        clean_iv.setOnClickListener(this);
        phonenum_et=findViewById(R.id.phonenum_et);
        phonenum_et.setOnClickListener(this);
        phonenumstr=loginResult.getSellerTel();
        phonenum_et.setText(phonenumstr);
        phonenum_et.setSelection(phonenum_et.getText().length());
        TextWatcher watcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0&&s.toString()!=phonenumstr&&!s.toString().equals(phonenumstr)){
                    clean_iv.setVisibility(View.VISIBLE);
                    mTitleRightText.setClickable(true);
                    mTitleRightText.setTextColor(getBaseContext().getResources().getColor(R.color.nav_color));
                    mTitleRightText.setOnClickListener(new NoDoubleClickListener() {




                        @Override
                        public void onNoDoubleClick(View v) {
                            String phoneNum=phonenum_et.getText().toString().trim();
                            Log.d("loglog",phoneNum);

                            mTitleRightText.setClickable(false);
                            OkGo.<String>post(Config.URL + "/user/editUserInfo")
                                    .tag(this)
                                    .params("headImg",loginResult.getSellerHead())
                                    .params("sellerName",loginResult.getSellerName())
                                    .params("sellerTel",phoneNum)
                                    .params("telOpen",loginResult.getTelOpen())
                                    .params("sellerId",loginResult.getSellerId())
                                    .params("appKey",loginResult.getAppKey())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {
                                            Log.d("loglog",response.body());
                                            String responedata = response.body().toString().trim();
                                            Gson gson = new Gson();
                                            Json<LoginResult> jsondata = gson.fromJson(responedata, new TypeToken<Json<LoginResult>>() {}.getType());
                                            if (jsondata.getCode()==0){
                                                SharedPreferencesUtil.putObject(mContext,"loginResult",jsondata.getData());
                                    /*Intent intent = new Intent(AccountUpadatePhoneNumActivity.this,
                                            AccountMainActivity.class);
                                    startActivity(intent);*/
                                                finish();
                                            }else{
                                                Toast.makeText(mContext,jsondata.getMsg(),Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }
                    });

                }
                else if(s.length()>0&&s.toString()==phonenumstr&&s.toString().equals(phonenumstr))
                {
                    clean_iv.setVisibility(View.VISIBLE);
                    mTitleRightText.setClickable(false);
                }

                else{
                    clean_iv.setVisibility(View.GONE);
                    mTitleRightText.setClickable(false);
                        //mTitleRightText.setTextColor(R.color.nav_color);
                    mTitleRightText.setTextColor(getBaseContext().getResources().getColor(R.color.nav_fontcolor));



                }
            }
        };
        phonenum_et.addTextChangedListener(watcher);



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left_back_img:

                this.finish();
                break;
            case R.id.clean_iv:
                phonenum_et.setText("");
                break;

        }
    }
}
