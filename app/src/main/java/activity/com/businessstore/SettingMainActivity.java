package activity.com.businessstore;

import android.os.Bundle;
import android.view.View;


public class SettingMainActivity extends BaseActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);
        initview();
    }
    public void initview(){

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }
}
