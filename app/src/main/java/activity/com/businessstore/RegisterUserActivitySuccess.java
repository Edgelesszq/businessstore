package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.businessstore.util.StatusBarUtil;

import java.util.Collections;

import chihane.jdaddressselector.AddressProvider;
import chihane.jdaddressselector.AddressSelector;
import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.OnAddressSelectedListener;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;


/**
 * Created by joe on 2018/6/12.
 */

public class RegisterUserActivitySuccess extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private TextView login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_successandfail_register);
        mContext = this;

        initview();
    }
    public void initview(){
        setTitleView(R.drawable.backimage,R.string.register_completed);
        mTitleLefeBackImg.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_back_img:
                this.finish();
                break;
        }
    }



}
