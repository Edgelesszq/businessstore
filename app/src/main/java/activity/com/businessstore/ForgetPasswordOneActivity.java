package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.businessstore.util.StatusBarUtil;


public class ForgetPasswordOneActivity extends BaseActivity implements View.OnClickListener{
    private TextView forget_one_ensure;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_one);
        StatusBarUtil.transparencyBar(this); //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this); //设置白底黑字
        mContext = this;
        initview();
    }
    public void initview(){
        setTitleView(R.drawable.backimage,R.string.forget_the_password);
        mTitleLefeBackImg.setOnClickListener(this);
        forget_one_ensure=findViewById(R.id.forget_one_ensure);
        forget_one_ensure.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.forget_one_ensure:
                Intent ensure=new Intent(ForgetPasswordOneActivity.this,ForgetPasswordTwoActivity.class);
                startActivity(ensure);
                break;

        }
    }
}
