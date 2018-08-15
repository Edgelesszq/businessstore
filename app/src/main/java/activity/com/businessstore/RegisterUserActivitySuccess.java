package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.businessstore.model.LoginResult;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StatusBarUtil;

import java.util.Collections;




/**
 * Created by joe on 2018/6/12.
 */

public class RegisterUserActivitySuccess extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private TextView login_btn,toast_tv;
    private LoginResult loginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_successandfail_register);
        mContext = this;
        initview();
        loginData= SharedPreferencesUtil.getObject(this,"loginResult");
        if(loginData.getAppKey()!=null){
            autoLogin(true);
        }


    }
    public  void autoLogin(final boolean isRun){

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=10;

                try {

                    while (i>0&&isRun){
                        i--;

                        final int finalI = i;
                        if(i==0){

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent loginintent=new Intent(RegisterUserActivitySuccess.this,MainActivity.class);
                                    startActivity(loginintent);
                                }
                            });
                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    toast_tv.setText("注冊完成,將于"+ finalI +"自動登錄！");
                                }
                            });
                        }

                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void initview(){
        setTitleView(R.drawable.backimage,R.string.register_completed);
        mTitleLefeBackImg.setOnClickListener(this);

        login_btn=findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        toast_tv=findViewById(R.id.toast_tv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.login_btn:
                autoLogin(false);
                Intent loginintent=new Intent(RegisterUserActivitySuccess.this,LoginActivity.class);
                startActivity(loginintent);
                break;
                default:
                    break;
        }
    }





}
