package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.businessstore.Config;
import com.businessstore.util.NoDoubleClickListener;
import com.businessstore.util.StatusBarUtil;
import com.lzy.okgo.OkGo;


public class RegisterUserActivityFour extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private TextView register_three_ensure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_create_storelink_four);
        mContext = this;

        initview();
    }
    public void initview(){
        setTitleView(R.drawable.backimage,R.string.create_store_link);
        mTitleLefeBackImg.setOnClickListener(this);
        register_three_ensure=findViewById(R.id.register_three_ensure);
        register_three_ensure.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
               /* OkGo.<String>post(Config.URL+"")
                        .params();*/
                Intent testfourintent = new Intent(RegisterUserActivityFour.this, RegisterUserActivitySuccess.class);
                startActivity(testfourintent);


            }
        });
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
