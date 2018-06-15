package activity.com.businessstore;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class RegisterUserActivityFour extends BaseActivity  {
    private TextView submit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_verification_four);
        initview();
    }
    public void initview(){
        submit_btn=findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit_btn:
                Intent intent=new Intent(RegisterUserActivityFour.this,RegisterUserActivitySuccess.class);
                startActivity(intent);
        }
    }

}
