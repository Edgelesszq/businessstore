package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.businessstore.Config;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.util.NoDoubleClickListener;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StatusBarUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


public class AccountUpadatePasswordActivity extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private EditText oldpsw,newpsw1,newpsw2;
    private LoginResult loginResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_updatepassword);
        loginResult = SharedPreferencesUtil.getObject(mContext,"loginResult");

        initview();
    }
    public void initview(){
        mContext = this;
        setTitleView(R.drawable.backimage,R.string.update_password,R.string.accomplish);
        mTitleLefeBackImg.setOnClickListener(this);
        mTitleRightText.setOnClickListener(this);
        oldpsw = findViewById(R.id.edit_old_psw);

        newpsw2 = findViewById(R.id.edit_new_psw_again);
        newpsw1 = findViewById(R.id.edit_new_psw);
        mTitleRightText.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {


            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.title_right_text:

                break;
        }
    }
}