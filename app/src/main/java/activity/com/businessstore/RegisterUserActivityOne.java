package activity.com.businessstore;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.businessstore.util.NoDoubleClickListener;
import com.businessstore.util.ToastViewUtils;

import java.util.regex.Pattern;


public class RegisterUserActivityOne extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private TextView register_one_ensure,title_tips;
    private ImageView left_back;
    private ToggleButton togglePwd;
    private EditText register_password,register_country,register_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_one);

        mContext = this;
        initview();
    }
    public void initview(){
        setTitleView(R.drawable.backimage,R.string.register);
        mTitleLefeBackImg.setOnClickListener(this);

        title_tips=findViewById(R.id.title_tips);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/msyh.ttf");//设置字体雅黑
        title_tips.setTypeface(typeface);



        register_email=findViewById(R.id.register_email);   // 邮箱输入框

        register_country=findViewById(R.id.register_country); //国家输入框


        register_password=findViewById(R.id.register_password);//密码输入框
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
        register_password.addTextChangedListener(passwordwatcher);


        togglePwd=findViewById(R.id.togglePwd);//查看密码按钮
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
        register_one_ensure=findViewById(R.id.register_one_ensure);
        register_one_ensure.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                String email=register_email.getText().toString().trim();
                String password=register_password.getText().toString().trim();
                String country=register_country.getText().toString().trim();
                LayoutInflater inflater=getLayoutInflater();
                if(email.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入邮箱或手机号！",getApplicationContext(),inflater);

                }
                else if(password.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入密码！",getApplicationContext(),inflater);

                }
                else if(country.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入国家！",getApplicationContext(),inflater);
                }
                else{
                    if (!isPhoneRegex(email)&&!isEmailRegex(email)){
                        ToastViewUtils.toastShowLoginMessage("账号格式错误",getApplicationContext(),inflater);
                    }
                    else if (!isPasswordRegex(password)){
                        ToastViewUtils.toastShowLoginMessage("密码格式错误！",getApplicationContext(),inflater);
                    }else {
                        Intent intent = new Intent(RegisterUserActivityOne.this, RegisterUserActivityTwo.class);
                        startActivity(intent);
                    }
                }

            }
        });//下一步按钮

    }


    @Override
    public void onClick(View view) {



        switch (view.getId()){
            case R.id.title_left_back_img:
                finish();
        }
    }

    /**
     * 判断是否是合法手机号码（手机号码段详见：http://baike.baidu.com/view/781667.htm#2）
     *
     * @param phone：手机号码
     * @return boolean
     */
    private boolean isPhoneRegex(String phone) {
        String phonePattern = "^1\\d{10}$";
        return Pattern.matches(phonePattern, phone);
    }

    /**
     * 判断是否是合法邮箱
     *
     * @param email：邮箱
     * @return boolean
     */
    private boolean isEmailRegex(String email) {
        String emailPattern = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        return Pattern.matches(emailPattern, email);
    }

    /**
     * 判断是否是合法密码（以字母开头，允许6~18字节，允许字母数字下划线）
     *
     * @param password：密码
     * @return boolean
     */
    private boolean isPasswordRegex(String password) {
        String passwordPattern = "^[a-z0-9_-]{7,19}$";
        return Pattern.matches(passwordPattern, password);
    }
}
