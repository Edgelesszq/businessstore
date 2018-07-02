package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

public class thirdPartyDomianTwoActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private FrameLayout mNextDomainTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_party_domain_two);
        mContext = this;
        initview();
    }

    private void initview() {
        setTitleView(R.drawable.backimage,R.string.mine);
        mTitleLefeBackImg.setOnClickListener(this);
        mNextDomainTwo = findViewById(R.id.next_domain_two);
        mNextDomainTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.next_domain_two:
                Intent intent = new Intent(this,thirdPartyDomianThreeActivity.class);
                startActivity(intent);
                break;
        }

    }
}
