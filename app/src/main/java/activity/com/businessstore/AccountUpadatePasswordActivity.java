package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.businessstore.Config;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.util.ActivityUtil;
import com.businessstore.util.NoDoubleClickListener;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StatusBarUtil;
import com.businessstore.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


public class AccountUpadatePasswordActivity extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private EditText oldpsw,newpsw1,newpsw2;
    private LoginResult loginResult;
    private TextView forget_password_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_updatepassword);
        loginResult = SharedPreferencesUtil.getObject(mContext,"loginResult");

        initview();
    }
    public void initview(){
        mContext = this;
        setTitleView(R.drawable.backimage,R.string.update_password,R.string.accomplish);
        mTitleLefeBackImg.setOnClickListener(this);
        mTitleRightText.setOnClickListener(this);
        oldpsw = findViewById(R.id.edit_old_psw);

        newpsw2 = findViewById(R.id.edit_new_psw_again);
        newpsw1 = findViewById(R.id.edit_new_psw);

        forget_password_btn=findViewById(R.id.forget_password_btn);
        forget_password_btn.setOnClickListener(this);
        mTitleRightText.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                String oldPwd=oldpsw.getText().toString().trim();
                String newPwd=newpsw1.getText().toString().trim();
                String newPwd2=newpsw2.getText().toString().trim();
                if(StringUtil.isBlank(oldPwd)||StringUtil.isBlank(newPwd)||StringUtil.isBlank(newPwd2)){

                }
                else if (newPwd.equals(newPwd2)) {
                    mTitleRightText.setClickable(false);
                    showDialogprogressBarWithString("正在修改");
                    OkGo.<String>post(Config.URL + "/user/editPassword")
                            .tag(this)
                            .params("sellerPwd",newPwd)
                            .params("oldPwd",oldPwd)
                            .params("sellerId",loginResult.getSellerId())
                            .params("appKey",loginResult.getAppKey())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    dissmissDialogprogressBarWithString();
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
                else {
                    Toast.makeText(mContext,"两次密码不一致",Toast.LENGTH_SHORT).show();

                }


            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.forget_password_btn:
                ActivityUtil.startActivity(AccountUpadatePasswordActivity.this,ForgetPasswordOneActivity.class);

                break;
                default:
                    break;
        }
    }
}