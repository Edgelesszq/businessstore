package activity.com.businessstore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CommodityUploadActivity extends BaseActivity implements View.OnClickListener {
    private EditText editTitle,editContent;
    private ImageView numberMinus,numberAdd;
    private TextView number;
    private int intNumber;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_upload);
        initView();
    }

    private void initView() {
        setTitleView(R.drawable.backimage,R.string.commodity_upload,R.string.save);
        mTitleLefeBackImg.setOnClickListener(this);
        editTitle = findViewById(R.id.edit_commodity_title);
        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    mTitleRightText.setTextColor(Color.parseColor("#FDBA43"));
            }
        });
        editContent = findViewById(R.id.edit_commodity_content);
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTitleRightText.setTextColor(Color.parseColor("#FDBA43"));
            }
        });
        numberMinus = findViewById(R.id.img_number_minus);
        numberAdd = findViewById(R.id.img_number_add);
        numberMinus.setOnClickListener(this);
        numberAdd.setOnClickListener(this);
        number = findViewById(R.id.text_number);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.img_number_minus:
                addNumber();
                break;
            case R.id.img_number_add:
                minusNumber();
                break;

                default:
                    break;
        }

    }

    public void addNumber(){
        intNumber = Integer.parseInt(number.getText().toString().trim());
        if (intNumber > 0){
            number.setText(String.valueOf(intNumber-1));
        }else {
            number.setText("0");
        }
    }

    public void minusNumber(){
        intNumber = Integer.parseInt(number.getText().toString().trim());
        number.setText(String.valueOf(intNumber+1));
    }
}
