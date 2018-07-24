package activity.com.businessstore;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.businessstore.util.StatusBarUtil;


public class ForgetPasswordTwoActivity extends BaseActivity implements View.OnClickListener  {
    private TextView forget_two_ensure;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_two);
        StatusBarUtil.transparencyBar(this); //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this); //设置白底黑字
        mContext = this;
        initview();
    }
    public void initview(){
        setTitleView(R.drawable.backimage,R.string.get_verification_code);
        mTitleLefeBackImg.setOnClickListener(this);
        forget_two_ensure=findViewById(R.id.forget_two_ensure);
        forget_two_ensure.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.forget_two_ensure:
                Intent ensurethree=new Intent(ForgetPasswordTwoActivity.this,ForgetPasswordThreeActivity.class);
                startActivity(ensurethree);
                break;

        }
    }
}
