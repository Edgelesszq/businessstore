package activity.com.businessstore;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by joe on 2018/6/12.
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
        private ImageView back;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.base_bcak);
        back=findViewById(R.id.back);
        back.setOnClickListener((View.OnClickListener) getApplicationContext());
    }


    @Override
     public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                Toast.makeText(getApplicationContext(),"ssss",Toast.LENGTH_SHORT);
               finish();
                break;
        }
    }
}
