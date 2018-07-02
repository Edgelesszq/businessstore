package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

public class thirdPartyDomianThreeActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private FrameLayout mFinishDomainThree;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_party_domain_three);
        mContext = this;
        initview();
    }

    private void initview() {
        setTitleView(R.drawable.backimage,R.string.my_domain);
        mTitleLefeBackImg.setOnClickListener(this);
        mFinishDomainThree = findViewById(R.id.finish_domain_three);
        mFinishDomainThree.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
                case R.id.finish_domain_three:
                    Intent intent = new Intent(this,thirdPartyDomianFourActivity.class);
                    startActivity(intent);
                    break;
        }

    }
}
