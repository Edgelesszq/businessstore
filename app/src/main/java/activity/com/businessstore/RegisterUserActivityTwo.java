package activity.com.businessstore;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class RegisterUserActivityTwo extends BaseActivity implements View.OnClickListener{
    private Context mContext;
    private TextView submit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_verification_two);
        mContext = this;
        initview();
    }
    public void initview(){
        setTitleView(R.drawable.backimage,R.string.get_verification_code);
        mTitleLefeBackImg.setOnClickListener(this);
        submit_btn=findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left_back_img:
                this.finish();
                break;
            case R.id.submit_btn:
                Intent intent=new Intent(RegisterUserActivityTwo.this,RegisterUserActivitythree.class);
                startActivity(intent);
                break;
        }
    }

}
