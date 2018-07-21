package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


public class ForgetPasswordThreeActivity extends BaseActivity implements View.OnClickListener{
    private TextView forget_one_tips;
    private Context mContext;
    FrameLayout update_password_complete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_three);
        mContext = this;
        initview();
    }
    public void initview(){
        setTitleView(R.drawable.backimage,R.string.reset_password);
        mTitleLefeBackImg.setOnClickListener(this);
        update_password_complete=findViewById(R.id.update_password_complete);
        update_password_complete.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.update_password_complete:
                Intent completeUpdateIntent=new Intent(ForgetPasswordThreeActivity.this,ForgetPasswordFourActivity.class);
                startActivity(completeUpdateIntent);
                break;

        }
    }
}
