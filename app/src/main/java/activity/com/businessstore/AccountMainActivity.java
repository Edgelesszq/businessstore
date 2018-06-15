package activity.com.businessstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.businessstore.view.popwindow.CommonPopupWindow;
import com.businessstore.view.popwindow.CommonUtil;

import de.hdodenhof.circleimageview.CircleImageView;


public class AccountMainActivity extends BaseActivity implements View.OnClickListener,CommonPopupWindow.ViewInterface {
    private TextView update_password,update_phonenum;
    private CommonPopupWindow popupWindow;
    private CircleImageView HeadPortrait_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);
        initview();
    }
    public void initview(){
        update_password=findViewById(R.id.update_password_textview);
        update_phonenum=findViewById(R.id.update_phoneNum_text);
        update_password.setOnClickListener(this);
        update_phonenum.setOnClickListener(this);

        HeadPortrait_update=findViewById(R.id.HeadPortrait_update);//修改头像
        HeadPortrait_update.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.update_password_textview:
                Intent intent=new Intent(AccountMainActivity.this,AccountUpadatePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.update_phoneNum_text:
                Intent intent2=new Intent(AccountMainActivity.this,AccountUpadatePhoneNumActivity.class);
                startActivity(intent2);
                break;
            case R.id.HeadPortrait_update:
                showPopWindowUpdate_HeadPortrait(view);
                break;
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
}
