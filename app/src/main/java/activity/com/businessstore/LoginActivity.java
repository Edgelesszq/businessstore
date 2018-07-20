package activity.com.businessstore;


import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.businessstore.util.StatusBarUtil;


public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private TextView register_btn,title,content;
    private TextView forget_password_btn;
    private ImageView left_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initview();
    }
    public void initview(){
        StatusBarUtil.transparencyBar(this); //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this); //设置白底黑字
        AssetManager mgr=getAssets();//得到AssetManager
        Typeface tf=Typeface.createFromAsset(mgr, "fonts/Roboto-Regular.ttf");//根据路径得到Typeface

        title=findViewById(R.id.text_login_title);
        title.setTypeface(tf);//设置字体
        content=findViewById(R.id.text_login_content);
        content.setTypeface(tf);
        register_btn=findViewById(R.id.register_btn);
        register_btn.setOnClickListener(this);
        forget_password_btn=findViewById(R.id.forget_password_btn);
        forget_password_btn.setOnClickListener(this);
        left_back=findViewById(R.id.left_back);
        left_back.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_btn:
                Intent registerintent=new Intent(LoginActivity.this,RegisterUserActivityOne.class);
                startActivity(registerintent);
                break;
            case R.id.forget_password_btn:
                Intent forget_password_intent=new Intent(LoginActivity.this,ForgetPasswordOneActivity.class);
                startActivity(forget_password_intent);
                break;
            case R.id.left_back:
                finish();
        }
    }
}
