package activity.com.businessstore;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class ForgetPasswordTwoActivity extends BaseActivity   {
    private TextView forget_two_ensure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_two);
        initview();
    }
    public void initview(){
        forget_two_ensure=findViewById(R.id.forget_two_ensure);
        forget_two_ensure.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forget_two_ensure:
                Intent ensurethree=new Intent(ForgetPasswordTwoActivity.this,ForgetPasswordThreeActivity.class);
                startActivity(ensurethree);
                break;

        }
    }
}
