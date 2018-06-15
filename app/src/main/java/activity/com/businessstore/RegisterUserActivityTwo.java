package activity.com.businessstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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

public class RegisterUserActivityTwo extends BaseActivity implements OnAddressSelectedListener{
   // private FrameLayout address_frameLayout;
    private TextView address_textView,register_one_ensure2;
    private ImageView img_btn;
    View addressView;
     BottomDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_two);

        initview();
    }
    public void initview(){

        register_one_ensure2=findViewById(R.id.register_one_ensure2);
        register_one_ensure2.setOnClickListener(this);

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
        switch (view.getId()){
          case R.id.img_btn:
          {

              BottomDialog dialog = new BottomDialog(RegisterUserActivityTwo.this);

              dialog.setOnAddressSelectedListener(RegisterUserActivityTwo.this);
              dialog.show();

          }
            case R.id.register_one_ensure2:
            {
                Intent intent=new Intent(RegisterUserActivityTwo.this,RegisterUserActivityThree.class);
                startActivity(intent);
            }
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
