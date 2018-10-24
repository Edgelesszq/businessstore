package activity.com.businessstore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.businessstore.Config;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.util.ActivityUtil;
import com.businessstore.util.AppManager;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StatusBarUtil;
import com.businessstore.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


public class SettingMainActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    //意见反馈、版本更新
    private FrameLayout mFeedback,mVersionUpgrades;
    private TextView login_out;
    private LoginResult loginResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);
        mContext = this;
        loginResult = SharedPreferencesUtil.getObject(this,"loginResult");

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
                loginOut();
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

    private void loginOut() {
        showDialogprogressBarWithString("正在注销");
        OkGo.<String>post(Config.URL + "/user/loginOut")
                .tag(this)
                .params("sellerId",loginResult.getSellerId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        Json<String> data = gson.fromJson(response.body(),new TypeToken<Json<String>>(){}.getType());
                        dissmissDialogprogressBarWithString();
                        if (data.getCode() == 0){
                            SharedPreferencesUtil.removeSP(getApplicationContext(),"loginResult");
                            AppManager.getAppManager().finishAllActivity();
                            ActivityUtil.skipActivity(mContext,LoginActivity.class);
                        }else {
                            ToastUtils.showShortToast(mContext,data.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dissmissDialogprogressBarWithString();
                        ToastUtils.showShortToast(mContext,"请求错误，请重试");
                    }
                });
    }
}
