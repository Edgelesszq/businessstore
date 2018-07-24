package activity.com.businessstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.businessstore.util.ToastViewUtils;

import java.util.Collections;

import chihane.jdaddressselector.AddressProvider;
import chihane.jdaddressselector.AddressSelector;
import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.OnAddressSelectedListener;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;


/**
 * Created by joe on 2018/6/12.
 */

public class RegisterUserActivitythree extends BaseActivity implements OnAddressSelectedListener, View.OnClickListener{
   // private FrameLayout address_frameLayout;
    private Context mContext;
    private TextView address_textView,register_one_ensure2;
    private ImageView img_btn;

     private EditText storename_et,phonenum_et,worktime_et,detailsaddress_et;


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

        register_one_ensure2=findViewById(R.id.register_one_ensure2);
        register_one_ensure2.setOnClickListener(this);

        storename_et=findViewById(R.id.storename_et);//店名

        phonenum_et=findViewById(R.id.phonenum_et);//电话号码

        worktime_et=findViewById(R.id.worktime_et);//工作时间

        detailsaddress_et=findViewById(R.id.detailsaddress_et);//详细地址

        AddressSelector selector = new AddressSelector(this);
        selector.setOnAddressSelectedListener(this);
        selector.setAddressProvider(new AddressProvider() {
            @Override
            public void provideProvinces(AddressReceiver<Province> addressReceiver) {
                Province province = new Province();
                province.id = 1;
                province.name = "测试用省份";
                addressReceiver.send(Collections.singletonList(province));
            }

            @Override
            public void provideCitiesWith(int i, AddressReceiver<City> addressReceiver) {
                City city = new City();
                city.province_id = i;
                city.id = 2;
                city.name = "测试用城市";
                addressReceiver.send(Collections.singletonList(city));
            }

            @Override
            public void provideCountiesWith(int i, AddressReceiver<County> addressReceiver) {
                County county = new County();
                county.city_id = i;
                county.id = 3;
                county.name = "测试用乡镇";
                addressReceiver.send(Collections.singletonList(county));
            }

            @Override
            public void provideStreetsWith(int i, AddressReceiver<Street> addressReceiver) {
                Street street = new Street();
                street.county_id = i;
                street.id = 4;
                street.name = "测试用街道";
                addressReceiver.send(Collections.singletonList(street));
            }
        });


        address_textView=findViewById(R.id.address_textView);
        img_btn=findViewById(R.id.img_btn);
        img_btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        String store_name=storename_et.getText().toString().trim();
        String phone_num=phonenum_et.getText().toString().trim();
        String work_time=worktime_et.getText().toString().trim();
        String address_str=address_textView.getText().toString().trim();
        String address_details=detailsaddress_et.getText().toString().trim();

        switch (view.getId()){
            case R.id.title_left_back_img:
            {
                this.finish();
            }
            break;
          case R.id.img_btn:
          {

              BottomDialog dialog = new BottomDialog(RegisterUserActivitythree.this);
              dialog.setOnAddressSelectedListener(RegisterUserActivitythree.this);
              dialog.show();

          }
          break;
            case R.id.register_one_ensure2:
            {
                LayoutInflater inflater=getLayoutInflater();
                if(store_name.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入店名！",getApplicationContext(),inflater);
                    break;
                }
                else if(phone_num.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入电话号码！",getApplicationContext(),inflater);
                    break;

                }else if(work_time.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入工作时间！",getApplicationContext(),inflater);
                    break;

                }else if(address_str.equals("")){
                    ToastViewUtils.toastShowLoginMessage("请输入店铺位置！",getApplicationContext(),inflater);
                    break;
                }
                else {

                Intent intent=new Intent(RegisterUserActivitythree.this,RegisterUserActivityFour.class);
                startActivity(intent);
                }
            }
            break;
      }

    }

    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        String s = (province == null ? "" : province.name)
                + (city == null ? "" : "" + city.name)
                + (county == null ? "" : "" + county.name)
                + (street == null ? "" : "" + street.name);
        address_textView.setText(s);
    }
}
