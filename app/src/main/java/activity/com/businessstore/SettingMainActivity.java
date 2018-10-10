package activity.com.businessstore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.businessstore.util.ActivityUtil;
import com.businessstore.util.AppManager;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StatusBarUtil;
import com.businessstore.util.ToastUtils;


public class SettingMainActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    //意见反馈、版本更新
    private FrameLayout mFeedback,mVersionUpgrades;
    private TextView login_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);
        mContext = this;

        initview();
    }

    public void initview() {
        setTitleView(R.drawable.backimage, R.string.setting);
        mTitleLefeBackImg.setOnClickListener(this);
        //意见反馈
        mFeedback = findViewById(R.id.feedback);
        mFeedback.setOnClickListener(this);
        //版本更新
        mVersionUpgrades = findViewById(R.id.version_upgrades);
        mVersionUpgrades.setOnClickListener(this);
        //退出登录
        login_out=findViewById(R.id.login_out);
        login_out.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.feedback:
                Intent intent = new Intent(SettingMainActivity.this,FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.login_out:
                SharedPreferencesUtil.removeSP(getApplicationContext(),"loginResult");
                AppManager.getAppManager().finishAllActivity();
                ActivityUtil.skipActivity(this,LoginActivity.class);
                break;
            case R.id.version_upgrades:
                ToastUtils.showShortToast(mContext,"暂无更新");
                break;
            default:break;
        }
    }
}
