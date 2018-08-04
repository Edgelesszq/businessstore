package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.businessstore.model.City;
import com.businessstore.util.NoDoubleClickListener;
import com.businessstore.util.ToastViewUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapter.com.businessstore.AdapterCityArray;


/**
 * Created by joe on 2018/6/12.
 */

public class RegisterUserActivitythree extends BaseActivity implements View.OnClickListener{
   // private FrameLayout address_frameLayout;
    private Context mContext;
    private AutoCompleteTextView address_textView;
    private TextView register_one_ensure2;
    private ImageView img_btn;

     private EditText storename_et,phonenum_et,worktime_et,detailsaddress_et;
     private List<City>  list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_three);
        mContext = this;

        initview();
    }
    public void initview(){
        setTitleView(R.drawable.backimage,R.string.open_store);
        mTitleLefeBackImg.setOnClickListener(this);



        storename_et=findViewById(R.id.storename_et);//店名

        phonenum_et=findViewById(R.id.phonenum_et);//电话号码

        worktime_et=findViewById(R.id.worktime_et);//工作时间

        detailsaddress_et=findViewById(R.id.detailsaddress_et);//详细地址
        list=new ArrayList<>();
        for(int i=0;i<10;i++){




            City city=new City("636624","newyork","newyork");
            list.add(city);

        }


        address_textView=findViewById(R.id.address_textView);
      /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,list);*/
        AdapterCityArray cityArray=new AdapterCityArray(list,R.layout.address_item_city_auto,mContext);

        address_textView.setAdapter(cityArray);

        register_one_ensure2=findViewById(R.id.register_one_ensure2);
        register_one_ensure2.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                String store_name=storename_et.getText().toString().trim();
                String phone_num=phonenum_et.getText().toString().trim();
                String work_time=worktime_et.getText().toString().trim();
                String address_str=address_textView.getText().toString().trim();
                String address_details=detailsaddress_et.getText().toString().trim();
                LayoutInflater inflater=getLayoutInflater();
                if(store_name.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入店名！",getApplicationContext(),inflater);
                }
                else if(phone_num.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入电话号码！",getApplicationContext(),inflater);

                }else if(work_time.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入工作时间！",getApplicationContext(),inflater);

                }else if(address_str.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入店铺位置！",getApplicationContext(),inflater);

                }
                else {

                    Intent intent=new Intent(RegisterUserActivitythree.this,RegisterUserActivityFour.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.title_left_back_img:
            {
                this.finish();
            }
            break;

      }

    }


}
