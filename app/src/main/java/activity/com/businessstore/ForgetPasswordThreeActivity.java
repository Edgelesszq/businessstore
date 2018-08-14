package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.businessstore.Config;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.util.NoDoubleClickListener;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StatusBarUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


public class ForgetPasswordThreeActivity extends BaseActivity implements View.OnClickListener{
    private TextView forget_one_tips;
    private Context mContext;
    private EditText newPassword_et,confirm_et;

    FrameLayout update_password_complete;
    private LoginResult forgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_three);
        forgetPassword= SharedPreferencesUtil.getObject(getApplicationContext(),"forgetPassword");

        mContext = this;
        initview();
    }
    public void initview(){
        setTitleView(R.drawable.backimage,R.string.reset_password);
        mTitleLefeBackImg.setOnClickListener(this);

        newPassword_et=findViewById(R.id.newPassword_et);
        confirm_et=findViewById(R.id.confirm_et);
        update_password_complete=findViewById(R.id.update_password_complete);
        update_password_complete.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                String newpassword=newPassword_et.getText().toString().trim();
                String confirm=confirm_et.getText().toString().trim();
                if(newpassword.equals(confirm)){
                    OkGo.<String>post(Config.URL+"/user/resetPassword/")
                            .params("sellerId",forgetPassword.getSellerId())
                            .params("sellerNum",forgetPassword.getSellerNum())
                            .params("sellerPwd",newpassword)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    String responseData = response.body().toString().trim();
                                    Gson gson = new Gson();
                                    Json<LoginResult> dataInfo = gson.fromJson(responseData,
                                            new TypeToken<Json<LoginResult>>(){}.getType());
                                    int code =dataInfo.getCode();
                                    switch (code)
                                    {
                                        case 0:
                                            Intent completeUpdateIntent=new Intent(ForgetPasswordThreeActivity.this,ForgetPasswordFourActivity.class);
                                            startActivity(completeUpdateIntent);
                                            break;
                                        case 1:
                                            Toast.makeText(getApplicationContext(),"密码修改失败！",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
                else {
                    Toast.makeText(getApplicationContext(),"密码不一致",Toast.LENGTH_SHORT).show();
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


        }
    }
}
