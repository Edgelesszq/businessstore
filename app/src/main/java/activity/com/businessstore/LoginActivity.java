package activity.com.businessstore;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private TextView register_btn;
    private TextView forget_password_btn;
    private ImageView left_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initview();
    }
    public void initview(){
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
