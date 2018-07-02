package activity.com.businessstore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class CommodityUploadActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_upload);
        initView();
    }

    private void initView() {
        setTitleView(R.drawable.backimage,R.string.commodity_upload,R.string.save);
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
