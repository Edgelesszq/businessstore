package activity.com.businessstore;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class RegisterUserActivityOne extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private TextView register_one_ensure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_one);
        mContext = this;
        initview();
    }
    public void initview(){
        register_one_ensure=findViewById(R.id.register_one_ensure);
        register_one_ensure.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_one_ensure:
                Intent intent=new Intent(RegisterUserActivityOne.this,RegisterUserActivityTwo.class);
                startActivity(intent);
                break;
        }
    }
}
