package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.businessstore.Config;
import com.businessstore.model.LoginResult;

import com.businessstore.model.Json;
import com.businessstore.util.NoDoubleClickListener;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StringUtil;
import com.businessstore.util.ToastViewUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


public class RegisterUserActivityOne extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private TextView register_one_ensure, title_tips;
    private ImageView left_back;
    private ToggleButton togglePwd;
    private EditText register_password, register_country, register_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_one);

        mContext = this;
        initview();
    }

    public void initview() {
        setTitleView(R.drawable.backimage, R.string.register);
        mTitleLefeBackImg.setOnClickListener(this);

        title_tips = findViewById(R.id.title_tips);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/msyh.ttf");//设置字体雅黑
        title_tips.setTypeface(typeface);


        register_email = findViewById(R.id.register_email);   // 邮箱输入框

        register_country = findViewById(R.id.register_country); //国家输入框


        register_password = findViewById(R.id.register_password);//密码输入框
        TextWatcher passwordwatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    togglePwd.setVisibility(View.VISIBLE);
                } else {
                    togglePwd.setVisibility(View.GONE);

                }
            }
        };
        register_password.addTextChangedListener(passwordwatcher);


        togglePwd = findViewById(R.id.togglePwd);//查看密码按钮
        togglePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    register_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    register_password.setSelection(register_password.getText().length());

                } else {
                    //否则隐藏密码
                    register_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    register_password.setSelection(register_password.getText().length());
                }
            }
        });
        register_one_ensure = findViewById(R.id.register_one_ensure);
        register_one_ensure.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                final String email = register_email.getText().toString().trim();
                String password = register_password.getText().toString().trim();
                String country = register_country.getText().toString().trim();
                LayoutInflater inflater = getLayoutInflater();
                if (email.equals("")) {
                    ToastViewUtils.toastShowLoginMessage("请输入邮箱或手机号！", getApplicationContext(), inflater);

                } else if (password.equals("")) {
                    ToastViewUtils.toastShowLoginMessage("请输入密码！", getApplicationContext(), inflater);

                } else if (country.equals("")) {
                    ToastViewUtils.toastShowLoginMessage("请输入国家！", getApplicationContext(), inflater);
                } else {
                    if (!StringUtil.isPhoneRegex(email) && !StringUtil.isEmailRegex(email)) {
                        ToastViewUtils.toastShowLoginMessage("账号格式错误", getApplicationContext(), inflater);
                    } else if (!StringUtil.isPasswordRegex(password)) {
                        ToastViewUtils.toastShowLoginMessage("密码格式错误！", getApplicationContext(), inflater);
                    } else {
//

                        showDialogprogressBarWithString("正在注冊...");
                        OkGo.<String>post(Config.URL + "/user/register")
                                .tag(this)
                                .params("sellerNum", email)
                                .params("sellerPwd", password)
                                .params("sellerCountry", country)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        Log.d("loglog", response.body());
                                        String responseData = response.body().toString().trim();
                                        Gson gson = new Gson();
//
                                        Json<LoginResult> DataInfo = gson.fromJson(responseData,
                                                new TypeToken<Json<LoginResult>>() {
                                                }.getType());

                                        if (DataInfo.getCode()==0){

                                            SharedPreferencesUtil.putObject(mContext,"loginResult",DataInfo.getData());
                                            dissmissDialogprogressBarWithString();
                                            Intent intent = new Intent(RegisterUserActivityOne.this,
                                                    RegisterUserActivityTwo.class);
                                            startActivity(intent);
                                        }else {
                                            dissmissDialogprogressBarWithString();
                                            Toast.makeText(mContext,DataInfo.getMsg().toString(),Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onError(Response<String> response) {
                                        super.onError(response);
                                        dissmissDialogprogressBarWithString();
                                        Toast.makeText(mContext,"发生未知错误！",Toast.LENGTH_SHORT).show();

                                    }


                                });

                    }
                }

            }
        });//下一步按钮

    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.title_left_back_img:
                finish();
        }
    }


}
