package activity.com.businessstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


public class ForgetPasswordThreeActivity extends BaseActivity{
    private TextView forget_one_tips;
    FrameLayout update_password_complete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_three);
        initview();
    }
    public void initview(){
        update_password_complete=findViewById(R.id.update_password_complete);
        update_password_complete.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.update_password_complete:
                Intent completeUpdateIntent=new Intent(ForgetPasswordThreeActivity.this,ForgetPasswordFourActivity.class);
                startActivity(completeUpdateIntent);
                break;

        }
    }
}
