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

import com.businessstore.util.ToastViewUtils;

import com.businessstore.util.StatusBarUtil;


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

        register_one_ensure=findViewById(R.id.register_one_ensure);
        register_one_ensure.setOnClickListener(this);//下一步按钮

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

    }


    @Override
    public void onClick(View view) {
        String email=register_email.getText().toString().trim();
        String password=register_password.getText().toString().trim();
        String country=register_country.getText().toString().trim();


        switch (view.getId()){

            case R.id.register_one_ensure:
                LayoutInflater inflater=getLayoutInflater();
                if(email.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入邮箱！",getApplicationContext(),inflater);
                    break;
                }
                else if(password.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入密码！",getApplicationContext(),inflater);
                    break;

                }
                else if(country.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入国家！",getApplicationContext(),inflater);
                    break;
                }
                else{
                    Intent intent=new Intent(RegisterUserActivityOne.this,RegisterUserActivityTwo.class);
                    startActivity(intent);
                    break;
                }

            case R.id.title_left_back_img:
                finish();
        }
    }
}
