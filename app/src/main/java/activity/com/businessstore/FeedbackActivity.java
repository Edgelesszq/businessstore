package activity.com.businessstore;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_feedback);
        initView();
    }

    private void initView() {
        setTitleView(R.drawable.backimage,R.string.feedback);
        mTitleLefeBackImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
        }

    }
}
