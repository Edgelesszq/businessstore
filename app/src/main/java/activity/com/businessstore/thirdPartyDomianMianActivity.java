package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.businessstore.util.StatusBarUtil;

public class thirdPartyDomianMianActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private FrameLayout mNextDomainMain;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_party_domain_main);
        mContext = this;
        StatusBarUtil.transparencyBar(this); //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this); //设置白底黑字
        initview();
    }

    private void initview() {
        setTitleView(R.drawable.backimage,R.string.mine);
        mTitleLefeBackImg.setOnClickListener(this);
        mNextDomainMain = findViewById(R.id.next_domian_main);
        mNextDomainMain.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.next_domian_main:
                Intent intent = new Intent(this,thirdPartyDomianTwoActivity.class);
                startActivity(intent);
                break;

        }

    }
}
