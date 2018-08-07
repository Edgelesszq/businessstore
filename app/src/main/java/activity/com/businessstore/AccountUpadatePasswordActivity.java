package activity.com.businessstore;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.businessstore.util.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


public class AccountUpadatePasswordActivity extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private EditText oldpsw,newpsw1,newpsw2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_updatepassword);
        initview();
    }
    public void initview(){
        mContext = this;
        setTitleView(R.drawable.backimage,R.string.update_password,R.string.accomplish);
        mTitleLefeBackImg.setOnClickListener(this);
        mTitleRightText.setOnClickListener(this);
        oldpsw = findViewById(R.id.edit_old_psw);
        newpsw1 = findViewById(R.id.edit_new_psw);
        newpsw2 = findViewById(R.id.edit_new_psw_again);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.title_right_text:
                Log.d("log1","right");
                OkGo.<String>put("http://192.168.0.140/wuji/api/user/login")
                        .tag(this)
                        .params("phone","18682572151")
                        .params("password","123456")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Log.d("log1",response.body().toString().trim());
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                            }
                        });
                break;
        }
    }
}