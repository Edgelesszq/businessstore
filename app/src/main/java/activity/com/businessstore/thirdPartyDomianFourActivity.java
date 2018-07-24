package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.businessstore.util.StatusBarUtil;

public class thirdPartyDomianFourActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private FrameLayout mVerifyDomainFour;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_party_domain_four);
        mContext = this;

        initview();
    }

    private void initview() {
        setTitleView(R.drawable.backimage,R.string.my_domain);
        mTitleLefeBackImg.setOnClickListener(this);
        mVerifyDomainFour = findViewById(R.id.verify_domain_four);
        mVerifyDomainFour.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
                case R.id.verify_domain_four:
                    Intent intent = new Intent(this,thirdPartyDomianFiveActivity.class);
                    startActivity(intent);
                    break;
        }

    }
}
