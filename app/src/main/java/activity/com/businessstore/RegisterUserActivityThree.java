package activity.com.businessstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class RegisterUserActivityThree extends BaseActivity  {
    private TextView register_three_ensure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_create_storelink_three);
        initview();
    }
    public void initview(){
        register_three_ensure=findViewById(R.id.register_three_ensure);
        register_three_ensure.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_three_ensure:
                Intent testfourintent=new Intent(RegisterUserActivityThree.this,RegisterUserActivityFour.class);
                startActivity(testfourintent);
                break;
        }
    }
}
