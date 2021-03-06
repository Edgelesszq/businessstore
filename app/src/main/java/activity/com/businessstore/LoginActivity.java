package activity.com.businessstore;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.businessstore.Config;
import com.businessstore.model.Json;
import com.businessstore.model.LoginResult;
import com.businessstore.util.ActivityUtil;
import com.businessstore.util.AppManager;
import com.businessstore.util.NoDoubleClickListener;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StringUtil;
import com.businessstore.util.ToastViewUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import cn.jpush.android.api.JPushInterface;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TextView register_btn, title, content;
    private TextView forget_password_btn, login_btn;
    private ImageView see_password;
    private EditText login_password, login_account;//登录账号和密码
    private Context mcontext;
    private long exitTime = 0; //退出程序
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mcontext = this;
        initview();
        initData();
    }

    private void initData() {
        dbHelper = new MyDatabaseHelper(this, "ZipCodeCity.db", null, 1);
        dbHelper.getWritableDatabase();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='tbl_city'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('12201', 'NewYork(NY)', 'Albany')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('30301', 'Georgia(GA)', 'Atlanta')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('21401', 'Maryland(MD)', 'Annapolis')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('21201', 'Maryland(MD)', 'Baltimore')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('35201', 'Alabama(AL)', 'Birmingham')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('14201', 'NewYork(NY)', 'Buffalo')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('60601', 'Illinois(IL)', 'CHICAGO')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('45201', 'Ohio(OH)', 'Cincinnati')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('44101', 'Ohio(OH)', 'Cleveland')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('43085', 'Ohio(OH)', 'Columbus')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('71953', 'Arkansas(AR)', 'Dallas')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('80002', 'Colorado(CO)', 'Denver')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('99701', 'Alaska(AK)', 'Fairbanks')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('19019', 'Pennsylvania(PA)', 'Philidelphia')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('96801', 'Hawii(HI)', 'Honolulu')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('46201', 'Indiana(IN)', 'Indianapolis')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('32099', 'Florida(FL)', 'Jacksonville')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('64101', 'Missouri(MO)', 'Kansas City')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('90001', 'California(CA)', 'Los Angeles')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('89101', 'Navada(NV)', 'Las Vegas')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('55199', 'minnesota(MN)', 'Minneapolis')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('10001', 'NewYork(NY)', 'New York')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('70112', 'Louisana(LA)', 'New orleaans')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('68046', 'Nebraska(NE)', 'Omaha')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('85001', 'Arizona(AZ)', 'Phoenix')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('15122', 'Pennsylvania(PA)', 'Pittsburgh')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('84101', 'Utah(UT)', 'Salt Lake City')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('94203', 'California(CA)', 'Sacramento')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('92101', 'California(CA)', 'San Diego')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('94101', 'California(CA)', 'San Francisco')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('95101', 'California(CA)', 'San Jose')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('55101', 'Minnesota(MN)', 'Saint Paul')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('63101', 'Missouri(MO)', 'Saint Louis')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('98101', 'Washington(WA)', 'Seattle')");
                    db.execSQL("INSERT INTO `tbl_city` VALUES ('33601', 'Florida(FL)', 'Tampa')");
                    Log.d("database","ZipCodeCity表已成功创建");
                }
            }else {
                Log.d("database","ZipCodeCity表已经存在");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void initview() {

        AssetManager mgr = getAssets();//得到AssetManager
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/Roboto-Regular.ttf");//根据路径得到Typeface
        title = findViewById(R.id.text_login_title);
        title.setTypeface(tf);//设置字体
        content = findViewById(R.id.text_login_content);
        content.setTypeface(tf);
        register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(this);
        forget_password_btn = findViewById(R.id.forget_password_btn);
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
        login_account = findViewById(R.id.login_account);
        TextWatcher usernamewatcher = new TextWatcher() {
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

        login_password = findViewById(R.id.login_password);
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
        login_password.addTextChangedListener(passwordwatcher);
        ///


        login_btn = findViewById(R.id.login_btn);//登录按钮

        login_btn.setOnClickListener(new NoDoubleClickListener() {

            @Override
            public void onNoDoubleClick(View v) {

                String account = login_account.getText().toString().trim();
                String password = login_password.getText().toString().trim();
                final LayoutInflater inflater = getLayoutInflater();
                if (StringUtil.isBlank(account)) {
                    ToastViewUtils.toastShowLoginMessage("请输入账号！", getApplicationContext(), inflater);

                } else if (StringUtil.isBlank(password)) {
                    ToastViewUtils.toastShowLoginMessage("请输入密码！", getApplicationContext(), inflater);

                } else {
                    showDialogprogressBarWithString("正在登录...");

                    OkGo.<String>post(Config.URL + "/user/login")
                            .tag(this)
                            .params("sellerNum", account)
                            .params("sellerPwd", password)
                            .params("registrationId", JPushInterface.getRegistrationID(mcontext))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    Log.d("loglog", response.body());
                                    String responseData = response.body().toString().trim();
                                    Gson gson = new Gson();
                                    //把泛型指定为User类型解析
                                    Json<LoginResult> loginResultJson = gson.fromJson(responseData,
                                            new TypeToken<Json<LoginResult>>() {
                                            }.getType());

                                    if (loginResultJson.getCode()==0) {
                                        SharedPreferencesUtil.putObject(getApplicationContext(),"loginResult",loginResultJson.getData());
                                        switch (loginResultJson.getData().getNumActiva()){
                                            case 1:
                                                if(loginResultJson.getData().getShopName()==null){
                                                    ToastViewUtils.toastShowLoginMessage("尚未开店！", getApplicationContext(), inflater);
                                                    Intent openstoreIntent = new Intent(LoginActivity.this,
                                                            RegisterUserActivitythree.class);
                                                    dissmissDialogprogressBarWithString();
                                                    startActivity(openstoreIntent);
                                                    break;
                                                }
                                                /*else if(loginResultJson.getData().getSellerDomain()==null){
                                                    ToastViewUtils.toastShowLoginMessage("尚未绑定域名！", getApplicationContext(), inflater);
                                                    Intent bindingIntent = new Intent(LoginActivity.this,
                                                            RegisterUserActivityFour.class);
                                                    dissmissDialogprogressBarWithString();
                                                    startActivity(bindingIntent);
                                                    break;
                                                }*/
                                                else {
                                                    ToastViewUtils.toastShowLoginMessage("登录成功！", getApplicationContext(), inflater);
                                                    SharedPreferencesUtil.putObject(mcontext,"loginResult",loginResultJson.getData());
                                                   /* Intent mainIntent = new Intent(LoginActivity.this,
                                                            MainActivity.class);*/
                                                    dissmissDialogprogressBarWithString();
                                                    AppManager.getAppManager().finishAllActivity();
                                                    ActivityUtil.skipActivity(LoginActivity.this,MainActivity.class);
                                                    /*startActivity(mainIntent);*/
                                                    break;
                                                }


                                            case 0:
                                                ToastViewUtils.toastShowLoginMessage("尚未激活！", getApplicationContext(), inflater);
                                                Intent registerInternt = new Intent(LoginActivity.this,
                                                        RegisterUserActivityTwo.class);
                                                registerInternt.putExtra("NumActiva",0);
                                                dissmissDialogprogressBarWithString();
                                                startActivity(registerInternt);
                                                break;

                                                default:
                                                    break;

                                        }

                                    }
                                    else if(loginResultJson.getCode()==1){
                                        dissmissDialogprogressBarWithString();
                                        ToastViewUtils.toastShowLoginMessage("账号或密码错误", getApplicationContext(), inflater);
                                    }
                                    else if(loginResultJson.getCode()==2){
                                        dissmissDialogprogressBarWithString();
                                        ToastViewUtils.toastShowLoginMessage("账号已被冻结！", getApplicationContext(), inflater);
                                    }

                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);

                                }
                            });

                }
            }
        });


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.register_btn:
                Intent registerintent = new Intent(LoginActivity.this, RegisterUserActivityOne.class);
                startActivity(registerintent);
                break;
            case R.id.forget_password_btn:
                Intent forget_password_intent = new Intent(LoginActivity.this, ForgetPasswordOneActivity.class);
                startActivity(forget_password_intent);
                break;


        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
