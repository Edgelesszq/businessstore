package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.businessstore.Config;
import com.businessstore.model.Json;
import com.businessstore.model.JsonRegister;
import com.businessstore.model.LoginResult;
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


public class ForgetPasswordOneActivity extends BaseActivity implements View.OnClickListener{
    private TextView forget_one_ensure;
    private Context mContext;
    private EditText sellerNum_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_one);




        mContext = this;
        initview();
    }
    public void initview(){
        setTitleView(R.drawable.backimage,R.string.forget_the_password);
        mTitleLefeBackImg.setOnClickListener(this);

        sellerNum_et=findViewById(R.id.sellernum_et);


        forget_one_ensure=findViewById(R.id.forget_one_ensure);
        forget_one_ensure.setOnClickListener(new NoDoubleClickListener() {

            @Override
            public void onNoDoubleClick(View v) {
               String inputsellerNum= sellerNum_et.getText().toString().trim();
                final LayoutInflater inflater = getLayoutInflater();
               if(StringUtil.isBlank(inputsellerNum)){
                    ToastViewUtils.toastShowLoginMessage("請輸入驗證碼！", getApplicationContext(), inflater);

               }
               else {
                   showDialogprogressBarWithString("正在請求...");
                   OkGo.<String>get(Config.URL+"/user/getCodeByForgetPwd/")
                           .params("sellerNum",inputsellerNum)
                           .execute(new StringCallback() {
                               @Override
                               public void onSuccess(Response<String> response) {
                                   String responseData = response.body().toString().trim();
                                   Gson gson = new Gson();
                                   Json<LoginResult> dataInfo = gson.fromJson(responseData,
                                           new TypeToken<Json<LoginResult>>(){}.getType());
                                   int code =dataInfo.getCode();
                                   switch (code){
                                       case 0:
                                           SharedPreferencesUtil.putObject(getApplicationContext(),"forgetPassword",dataInfo.getData());
                                           dissmissDialogprogressBarWithString();
                                           Toast.makeText(getApplicationContext(),"驗證碼已發送",Toast.LENGTH_SHORT).show();

                                           Intent ensure=new Intent(ForgetPasswordOneActivity.this,ForgetPasswordTwoActivity.class);
                                           startActivity(ensure);
                                           break;
                                       case 1:
                                           dissmissDialogprogressBarWithString();

                                           Toast.makeText(getApplicationContext(),"請求失敗",Toast.LENGTH_SHORT).show();

                                           break;
                                       default:
                                           dissmissDialogprogressBarWithString();
                                           Toast.makeText(getApplicationContext(),"發生未知錯誤",Toast.LENGTH_SHORT).show();

                                           break;

                                   }
                               }
                           });
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
