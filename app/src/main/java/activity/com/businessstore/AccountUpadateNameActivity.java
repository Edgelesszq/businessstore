package activity.com.businessstore;

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
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StatusBarUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


public class AccountUpadateNameActivity extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private ImageView clean_iv;
    private EditText phonenum_et;
    private LoginResult user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_updatepname);

        mContext=this;
        initview();
    }
    public void initview(){
        mContext = this;
        setTitleView(R.drawable.backimage,R.string.update_name,R.string.save);
        mTitleLefeBackImg.setOnClickListener(this);
        mTitleRightText.setOnClickListener(this);

        clean_iv=findViewById(R.id.clean_iv);//清除按钮
        clean_iv.setOnClickListener(this);
        phonenum_et=findViewById(R.id.phonenum_et);//修改昵称框
        phonenum_et.setOnClickListener(this);

        final String phonenumstr=getIntent().getStringExtra("Name").trim();
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
            case R.id.title_right_text:
                user = SharedPreferencesUtil.getObject(mContext,"loginResult");
                user.setSellerName(phonenum_et.getText().toString());
                OkGo.<String>put(Config.URL + "/user/editUserInfo")
                        .tag(this)
                        .params("headImg",user.getSellerHead())
                        .params("sellerName",user.getSellerName())
                        .params("sellerPhone",user.getSellerTel())
                        .params("telopen",user.getTelOpen())
                        .params("sellerId",user.getSellerId())
                        .params("appKey",user.getAppKey())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Log.d("loglog",response.body());
                                String responedata = response.body().toString().trim();
                                Gson gson = new Gson();
                                Json<LoginResult> jsondata = gson.fromJson(responedata, new TypeToken<Json<LoginResult>>() {}.getType());
                                if (jsondata.getCode()==0){
                                    SharedPreferencesUtil.putObject(mContext,"loginResult",jsondata.getData());
                                    Log.d("loglog",jsondata.getData().getSellerName());
                                    Intent intent = new Intent(AccountUpadateNameActivity.this,
                                            AccountMainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(mContext,jsondata.getMsg(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                break;
        }
    }
}
