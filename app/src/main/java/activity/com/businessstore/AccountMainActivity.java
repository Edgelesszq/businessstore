package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.businessstore.model.LoginResult;
import com.businessstore.util.SharedPreferencesUtil;
import com.businessstore.util.StatusBarUtil;
import com.businessstore.view.popwindow.CommonPopupWindow;
import com.businessstore.view.popwindow.CommonUtil;

import de.hdodenhof.circleimageview.CircleImageView;


public class AccountMainActivity extends BaseActivity implements View.OnClickListener,CommonPopupWindow.ViewInterface {
    private Context mContext;
    private TextView update_password,update_phonenum,name_tv,user_num;
    private CommonPopupWindow popupWindow;
    private CircleImageView HeadPortrait_update;
    private ToggleButton btn_switch;
    private LoginResult user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = SharedPreferencesUtil.getObject(mContext,"loginResult");
        setContentView(R.layout.my_account);

        mContext = this;
        initview();
    }
    public void initview(){
        setTitleView(R.drawable.backimage,R.string.my_account);
        mTitleLefeBackImg.setOnClickListener(this);
        update_password=findViewById(R.id.update_password_textview);
        update_phonenum=findViewById(R.id.update_phoneNum_text);//手机号显示
        update_password.setOnClickListener(this);
        update_phonenum.setOnClickListener(this);
        name_tv=findViewById(R.id.name_tv);//昵称显示
        name_tv.setOnClickListener(this);
        user_num = findViewById(R.id.text_num2);//账号显示


        HeadPortrait_update=findViewById(R.id.HeadPortrait_update);//头像
        HeadPortrait_update.setOnClickListener(this);


        //显示昵称
        if (user.getSellerName()!=null){
            name_tv.setText(user.getSellerName());
        }
        //显示账号
        if (user.getSellerNum()!=null){
            user_num.setText(user.getSellerNum());
        }
        //显示手机号
        if (user.getSellerTel()!=null){
            update_phonenum.setText(user.getSellerTel());
        }
        //是否公开号码
        btn_switch=findViewById(R.id.switch_btn);
        if (user.getTelOpen().equals("0")){
            btn_switch.setChecked(true);
        }else {
            btn_switch.setChecked(false);
        }
        btn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码


                } else {
                    //否则隐藏密码

                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.update_password_textview:
                Intent intent=new Intent(AccountMainActivity.this,AccountUpadatePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.update_phoneNum_text:
                String phonenum=update_phonenum.getText().toString().trim();
                Intent intent2=new Intent(AccountMainActivity.this,AccountUpadatePhoneNumActivity.class);
               intent2.putExtra("phoneNum",phonenum);
                startActivityForResult(intent2,2334);

                // startActivity(intent2);
                break;
            case R.id.HeadPortrait_update:
                showPopWindowUpdate_HeadPortrait(view);
                break;
            case R.id.name_tv:
                String namestr=name_tv.getText().toString().trim();
                Intent updateName=new Intent(this,AccountUpadateNameActivity.class);
                updateName.putExtra("Name",namestr);
                startActivityForResult(updateName,2335);
        }

    }
    public void showPopWindowUpdate_HeadPortrait(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.popwindow_choose_photo_item, null);
        //测量View的宽高
        CommonUtil.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popwindow_choose_photo_item)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
               // .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId){
            case R.layout.popwindow_choose_photo_item:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(resultCode==RESULT_OK){
           switch (requestCode){
               case 2334:
               {
                   update_phonenum.setText(data.getStringExtra("phoneNum"));
               }
           }
       }
    }
}
