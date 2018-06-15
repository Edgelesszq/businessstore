package activity.com.businessstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class ForgetPasswordOneActivity extends BaseActivity{
    private TextView forget_one_ensure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_one);
        initview();
    }
    public void initview(){
        forget_one_ensure=findViewById(R.id.forget_one_ensure);
        forget_one_ensure.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forget_one_ensure:
                Intent ensure=new Intent(ForgetPasswordOneActivity.this,ForgetPasswordTwoActivity.class);
                startActivity(ensure);
                break;

        }
    }
}
