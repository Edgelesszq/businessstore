package activity.com.businessstore;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class thirdPartyDomianFiveActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private FrameLayout mFinishDomainFive;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_party_domain_five);
        mContext = this;
        initview();
    }

    private void initview() {
        setTitleView(R.drawable.backimage,R.string.my_domain);
        mTitleLefeBackImg.setOnClickListener(this);
        mFinishDomainFive = findViewById(R.id.finish_domain_five);
        mFinishDomainFive.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.finish_domain_five:
                Toast.makeText(mContext,"完成",Toast.LENGTH_SHORT).show();
            break;
        }

    }
}