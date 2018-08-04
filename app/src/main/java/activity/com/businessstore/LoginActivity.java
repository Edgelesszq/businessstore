package activity.com.businessstore;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
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

import com.businessstore.model.DataInfo;
import com.businessstore.model.User;
import com.businessstore.util.NoDoubleClickListener;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StringUtil;
import com.businessstore.util.ToastViewUtils;
import com.businessstore.view.toast.ToastView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private TextView register_btn,title,content;
    private TextView forget_password_btn,login_btn;
    private ImageView see_password;
    private EditText login_password,login_account;//登录账号和密码
    private Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mcontext=this;
        initview();
    }
    public void initview(){

        AssetManager mgr=getAssets();//得到AssetManager
        Typeface tf=Typeface.createFromAsset(mgr, "fonts/Roboto-Regular.ttf");//根据路径得到Typeface

        title=findViewById(R.id.text_login_title);
        title.setTypeface(tf);//设置字体
        content=findViewById(R.id.text_login_content);
        content.setTypeface(tf);
        register_btn=findViewById(R.id.register_btn);
        register_btn.setOnClickListener(this);
        forget_password_btn=findViewById(R.id.forget_password_btn);
        forget_password_btn.setOnClickListener(this);


        final ToggleButton togglePwd = findViewById(R.id.togglePwd);
        togglePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    login_password.setSelection(login_password.getText().length());

                } else {
                    //否则隐藏密码
                    login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    login_password.setSelection(login_password.getText().length());
                }
            }
        });

        //用户账号和密码输入框
        login_account=findViewById(R.id.login_account);
        TextWatcher usernamewatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        };

        login_password=findViewById(R.id.login_password);
        TextWatcher passwordwatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0){
                    togglePwd.setVisibility(View.VISIBLE);
                }
                else {
                    togglePwd.setVisibility(View.GONE);

                }

            }
        };
        login_password.addTextChangedListener(passwordwatcher);
         ///


        login_btn=findViewById(R.id.login_btn);//登录按钮

        login_btn.setOnClickListener(new NoDoubleClickListener() {

            @Override
            public void onNoDoubleClick(View v) {
                String account= login_account.getText().toString().trim();
                String password= login_password.getText().toString().trim();
                final LayoutInflater inflater=getLayoutInflater();
                if(StringUtil.isBlank(account)){
                    ToastViewUtils.toastShowLoginMessage("请输入账号！",getApplicationContext(),inflater);

                }
                else if(StringUtil.isBlank(password)){
                    ToastViewUtils.toastShowLoginMessage("请输入密码！",getApplicationContext(),inflater);

                }
                else {
                    Intent activityIntent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(activityIntent);

                    /*OkGo.<String>post("http://192.168.0.140/wuji/api/user/login")
                         .tag(this)
                            .params("phone",account)
                            .params("password",password)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.d("loglog",response.body());
                                    String responseData = response.body().toString().trim();
                                    Gson gson = new Gson();

                                    //把泛型指定为User类型解析
                                    DataInfo<User> dataInfo = gson.fromJson(responseData,new TypeToken<DataInfo<User>>(){}.getType());
                                    Log.d("loglog",dataInfo.toString());

                                    if (dataInfo.getCode().equals("0")) {
                                        String id = dataInfo.getData().getId();
                                        String ukey = dataInfo.getData().getUkey();
                                        //缓存
                                        SharedPreferencesUtil.setParam(mcontext,"id",id);
                                        SharedPreferencesUtil.setParam(mcontext,"ukey",ukey);
                                        ToastViewUtils.toastShowLoginMessage("成功！", getApplicationContext(), inflater);
                                        Intent activityIntent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(activityIntent);
                                    }else {
                                        ToastViewUtils.toastShowLoginMessage("账号或密码错误",getApplicationContext(),inflater);
                                    }
=======
                                    JsonData jsonData= gson.fromJson(responseData,JsonData.class);
                                    String id = jsonData.getData().getUserInfo().getId();
                                    String ukey = jsonData.getData().getUserInfo().getUkey();
//                                    HashMap<String,String> map = new HashMap<>();
//                                    map.put("id",id);
//                                    map.put("Ukey",ukey);
                                   *//* Log.d("loglog",jsonData.getData().getCode());
                                    Log.d("loglog",jsonData.getData().getUserInfo().getId());
                                    Log.d("loglog",jsonData.getData().getUserInfo().getUkey());*//*
                                    SharedPreferencesUtil.setParam(mcontext,"id",id);
                                    SharedPreferencesUtil.setParam(mcontext,"ukey",ukey);
                                    ToastViewUtils.toastShowLoginMessage("成功！",getApplicationContext(),inflater);
                                    Intent activityIntent=new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(activityIntent);



                                }
                            });*/


                }
            }
        });


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.register_btn:
                Intent registerintent=new Intent(LoginActivity.this,RegisterUserActivityOne.class);
                startActivity(registerintent);
                break;
            case R.id.forget_password_btn:
                Intent forget_password_intent=new Intent(LoginActivity.this,ForgetPasswordOneActivity.class);
                startActivity(forget_password_intent);
                break;






        }
    }
}
