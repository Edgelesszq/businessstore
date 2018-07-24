package activity.com.businessstore;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.businessstore.util.StatusBarUtil;


public class AccountUpadateNameActivity extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private ImageView clean_iv;
    private EditText phonenum_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_updatepname);

        initview();
    }
    public void initview(){
        mContext = this;
        setTitleView(R.drawable.backimage,R.string.update_name,R.string.save);
        mTitleLefeBackImg.setOnClickListener(this);
        mTitleRightText.setOnClickListener(this);

        clean_iv=findViewById(R.id.clean_iv);
        clean_iv.setOnClickListener(this);
        phonenum_et=findViewById(R.id.phonenum_et);
        phonenum_et.setOnClickListener(this);

        final String phonenumstr=getIntent().getStringExtra("Name").trim();
        phonenum_et.setText(phonenumstr);
        phonenum_et.setSelection(phonenum_et.getText().length());
        TextWatcher watcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0&&s.toString()!=phonenumstr&&!s.toString().equals(phonenumstr)){
                    clean_iv.setVisibility(View.VISIBLE);
                    mTitleRightText.setClickable(true);
                    mTitleRightText.setTextColor(getBaseContext().getResources().getColor(R.color.nav_color));

                }
                else if(s.length()>0&&s.toString()==phonenumstr&&s.toString().equals(phonenumstr))
                {
                    clean_iv.setVisibility(View.VISIBLE);
                    mTitleRightText.setClickable(false);
                }

                else{
                    clean_iv.setVisibility(View.GONE);
                    mTitleRightText.setClickable(false);
                        //mTitleRightText.setTextColor(R.color.nav_color);
                    mTitleRightText.setTextColor(getBaseContext().getResources().getColor(R.color.nav_fontcolor));



                }
            }
        };
        phonenum_et.addTextChangedListener(watcher);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left_back_img:

                this.finish();
                break;
            case R.id.clean_iv:
                phonenum_et.setText("");
                break;
            case R.id.title_right_text:
                Toast.makeText(this,"sssss",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
