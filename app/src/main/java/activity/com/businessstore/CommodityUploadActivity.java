package activity.com.businessstore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class CommodityUploadActivity extends BaseActivity implements View.OnClickListener {
    private EditText editTitle;
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
//        final String editContext = editTitle.getText().toString();
        final Editable editContext = editTitle.getText();
        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == editContext){
                    mTitleRightText.setTextColor(Color.parseColor("#FDBA43"));
                    Log.d("lllll","同");
                }else {
                    mTitleRightText.setTextColor(Color.parseColor("#000000"));
                    Log.d("lllll","不同");
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
        }

    }


}
