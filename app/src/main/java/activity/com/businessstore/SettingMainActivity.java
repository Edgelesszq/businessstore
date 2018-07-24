package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.businessstore.util.StatusBarUtil;


public class SettingMainActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    //意见反馈、版本更新
    private FrameLayout mFeedback,mVersionUpgrades;
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
            case R.id.version_upgrades:

                break;

        }
    }
}
