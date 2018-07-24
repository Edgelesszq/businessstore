package activity.com.businessstore;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.businessstore.util.StatusBarUtil;


public class ForgetPasswordFourActivity extends BaseActivity  implements View.OnClickListener{
    //private TextView forget_one_tips;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_successandfail_modify);
        StatusBarUtil.transparencyBar(this); //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this); //设置白底黑字
        mContext = this;
        initview();
    }
    public void initview(){
        setTitleView(R.drawable.backimage,R.string.Settingcompleted);
        mTitleLefeBackImg.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_back_img:
                this.finish();
                break;
        }
    }
}
