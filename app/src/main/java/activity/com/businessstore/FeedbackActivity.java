package activity.com.businessstore;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.businessstore.Config;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StatusBarUtil;
import com.businessstore.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private EditText opinionCon;
    private TextView submitRecommendations;
    private LoginResult loginResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_feedback);
        loginResult = SharedPreferencesUtil.getObject(this,"loginResult");

        initView();
    }

    private void initView() {
        setTitleView(R.drawable.backimage,R.string.feedback);
        mTitleLefeBackImg.setOnClickListener(this);

        opinionCon = findViewById(R.id.opinionCon);
        submitRecommendations = findViewById(R.id.submit_recommendations);
        submitRecommendations.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.submit_recommendations:
                submit();
                break;
            default:
                break;
        }

    }

    private void submit() {
        showDialogprogressBarWithString("正在提交");
        OkGo.<String>post(Config.URL + "/feedback/submitOpinion")
                .tag(this)
                .params("sellerId",loginResult.getSellerId())
                .params("appKey",loginResult.getAppKey())
                .params("opinionCon",opinionCon.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        Json<String> jsondata = gson.fromJson(response.body(),new TypeToken<Json<String>>(){}.getType());
                        dissmissDialogprogressBarWithString();
                        if (jsondata.getCode() == 0){
                            ToastUtils.showShortToast(mContext,jsondata.getMsg());
                            opinionCon.setText("");
                        }else {
                            ToastUtils.showShortToast(mContext,jsondata.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShortToast(mContext,"请求错误");
                    }
                });
    }
}
