package activity.com.businessstore;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.businessstore.Config;
import com.businessstore.model.LoginResult;

import com.businessstore.model.Json;
import com.businessstore.model.JsonRegister;
import com.businessstore.util.NoDoubleClickListener;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.ToastViewUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


public class RegisterUserActivityTwo extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private TextView submit_btn,text_verification_again,timer_tv;//提交按钮  和验证按钮//倒计时
    private EditText verification;
    private String verifiCode,verifiCode2,sellerId,sellerNum;
    LoginResult loginData;
    private TextView sellerNum_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_verification_two);
        mContext = this;
        loginData=SharedPreferencesUtil.getObject(getApplicationContext(),"loginResult");
        if(loginData.getAppKey()!=null){
            Re_request();
        }
        else {
            starttimer();
        }
        initview();

    }

    private void Re_request() {

            OkGo.<String>get(Config.URL+"/user /getCode/")
                    .params("sellerId",loginData.getSellerId())
                    .params("sellerNum",loginData.getSellerNum())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Toast.makeText(mContext,"请求成功",Toast.LENGTH_SHORT).show();
                            starttimer();
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                        }
                    });

    }

    public void initview(){
        setTitleView(R.drawable.backimage,R.string.get_verification_code);
        mTitleLefeBackImg.setOnClickListener(this);

        verification = findViewById(R.id.edit_verification);
        text_verification_again = findViewById(R.id.text_verification_again);
        text_verification_again.setOnClickListener(this);

        timer_tv=findViewById(R.id.timer_tv);


        sellerNum_tv=findViewById(R.id.sellerNum_tv);
        sellerNum_tv.setText(loginData.getSellerNum());



        submit_btn=findViewById(R.id.submit_btn);   //下一步按钮
        submit_btn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                verifiCode = verification.getText().toString().trim();
                verifiCode2=loginData.getVerifiCode();
                final LayoutInflater inflater = getLayoutInflater();

                if(verifiCode.equals(verifiCode2)){
                    showDialogprogressBarWithString("正在驗證...");
                    OkGo.<String>get(Config.URL + "/user/verifiCode")
                            .tag(this)
                            .params("sellerId",loginData.getSellerId())
                            .params("sellerNum",loginData.getSellerNum())
                            .params("verifiCode",verifiCode)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.d("loglog",response.body());

                                    String responseData = response.body().toString().trim();
                                    Gson gson = new Gson();
                                    Json<JsonRegister> dataInfo = gson.fromJson(responseData,
                                            new TypeToken<Json<JsonRegister>>(){}.getType());
                                    int code =dataInfo.getCode();
                                    if (code==0){
                                        dissmissDialogprogressBarWithString();
                                        Intent intent=new Intent(RegisterUserActivityTwo.this,RegisterUserActivitythree.class);
                                        startActivity(intent);
                                    }else {
                                        dissmissDialogprogressBarWithString();
                                    }

                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    dissmissDialogprogressBarWithString();
                                    ToastViewUtils.toastShowLoginMessage("發生未知錯誤!",mContext,inflater);
                                }
                            });
                }else {
                    dissmissDialogprogressBarWithString();
                    ToastViewUtils.toastShowLoginMessage("驗證碼錯誤!",mContext,inflater);
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


            case R.id.text_verification_again:
                Re_request();
                text_verification_again.setClickable(false);
                text_verification_again.setTextColor(getResources().getColor(R.color.nav_fontcolor));
                break;
        }
    }
    public void starttimer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text_verification_again.setTextColor(getResources().getColor(R.color.nav_fontcolor));

                        text_verification_again.setClickable(false);

                    }
                });

                int i=60;
                while (i>0){

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    i--;
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timer_tv.setVisibility(View.VISIBLE);
                            timer_tv.setText(finalI +"s");
                            if (finalI==0){
                                text_verification_again.setClickable(true);
                                timer_tv.setVisibility(View.GONE);
                                text_verification_again.setTextColor(getResources().getColor(R.color.nav_color));
                            }
                        }
                    });

                }

            }
        }).start();
    }

}
