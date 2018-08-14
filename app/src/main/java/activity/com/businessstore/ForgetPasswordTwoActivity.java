package activity.com.businessstore;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.businessstore.Config;
import com.businessstore.model.LoginResult;
import com.businessstore.util.NoDoubleClickListener;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


public class ForgetPasswordTwoActivity extends BaseActivity implements View.OnClickListener  {
    private TextView forget_two_ensure,sellerNum_tv,timer_tv,text_verification_again;
    private Context mContext;
    private EditText verifiCode_et;
    private LoginResult forgetPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_two);
        forgetPassword= SharedPreferencesUtil.getObject(getApplicationContext(),"forgetPassword");
        starttimer();
        mContext = this;
        initview();
    }
    public void initview(){
        setTitleView(R.drawable.backimage,R.string.get_verification_code);
        mTitleLefeBackImg.setOnClickListener(this);
        forget_two_ensure=findViewById(R.id.forget_two_ensure);
        verifiCode_et=findViewById(R.id.verifiCode_et);

        timer_tv=findViewById(R.id.timer_tv);

        sellerNum_tv=findViewById(R.id.sellerNum_tv);
        sellerNum_tv.setText(forgetPassword.getSellerNum());

        text_verification_again=findViewById(R.id.text_verification_again);
        text_verification_again.setOnClickListener(this);


        forget_two_ensure.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {

                String inputCode=verifiCode_et.getText().toString().trim();
                String verifiCode=forgetPassword.getVerifiCode();
                if (verifiCode.equals(inputCode)){
                    OkGo.<String>get(Config.URL+"/user/resetPassword/")
                            .params("sellerId",forgetPassword.getSellerId())
                            .params("sellerNum",forgetPassword.getSellerNum())
                            .params("verifiCode",inputCode)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Intent ensurethree=new Intent(ForgetPasswordTwoActivity.this,ForgetPasswordThreeActivity.class);
                                    startActivity(ensurethree);
                                }
                            });

                }
                else {
                    Toast.makeText(getApplicationContext(),"验证码错误",Toast.LENGTH_SHORT).show();
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
                text_verification_again.setTextColor(getResources().getColor(R.color.nav_color));
                break;
                default:
                    break;


        }
    }
    public void starttimer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                text_verification_again.setClickable(false);

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
    } private void Re_request() {

        OkGo.<String>get(Config.URL+"/user/getCode/")
                .params("sellerId",forgetPassword.getSellerId())
                .params("sellerNum",forgetPassword.getSellerNum())
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


}
