package activity.com.businessstore;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.businessstore.util.ActivityUtil;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.skipActivity(this,MainActivity.class);
    }
}
