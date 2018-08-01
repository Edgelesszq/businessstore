package activity.com.businessstore;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.businessstore.util.StatusBarUtil;


public class AccountUpadatePasswordActivity extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_updatepassword);

        initview();
    }
    public void initview(){
        mContext = this;
        setTitleView(R.drawable.backimage,R.string.update_password,R.string.accomplish);
        mTitleLefeBackImg.setOnClickListener(this);



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
