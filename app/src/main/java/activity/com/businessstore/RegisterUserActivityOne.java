package activity.com.businessstore;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.businessstore.util.StatusBarUtil;


public class RegisterUserActivityOne extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private TextView register_one_ensure;
    private ImageView left_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_one);
        StatusBarUtil.transparencyBar(this); //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this); //设置白底黑字
        mContext = this;
        initview();
    }
    public void initview(){
        register_one_ensure=findViewById(R.id.register_one_ensure);
        register_one_ensure.setOnClickListener(this);

        left_back=findViewById(R.id.left_back);
        left_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_one_ensure:
                Intent intent=new Intent(RegisterUserActivityOne.this,RegisterUserActivityTwo.class);
                startActivity(intent);
                break;
            case R.id.left_back:
                finish();
        }
    }
}
