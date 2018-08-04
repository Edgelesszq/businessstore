package activity.com.businessstore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.businessstore.model.City;

import java.util.ArrayList;
import java.util.List;

import adapter.com.businessstore.AdapterAddressSearch;

public class StoreAdressActivity2 extends BaseActivity implements View.OnClickListener{
    private EditText sh;
    private RecyclerView recyclerView;
    private AdapterAddressSearch addressSearch;
    private List<City> list;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_address2);
        sh=findViewById(R.id.sh);
        setTitleView(R.drawable.backimage,R.string.my_address);
        mTitleLefeBackImg.setOnClickListener(this);

        list=new ArrayList<>();
        for(int i=0;i<20;i++){
            City city=new City("636624","纽约","newYork");
            list.add(city);
        }




        recyclerView=findViewById(R.id.tips_recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        addressSearch=new AdapterAddressSearch(getApplicationContext(),list);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(addressSearch);
        addressSearch.setOnmyItemClickListener(new AdapterAddressSearch.OnmyItemClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
        });
        TextWatcher editWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>3){

                    recyclerView.setVisibility(View.VISIBLE);
                    if(s.length()==4){
                        list.clear();
                        List<City>   list2=new ArrayList<>();
                        for(int i=0;i<5;i++){
                            City city=new City("636624","纽约","newYork");
                            list2.add(city);
                        }
                        list.addAll(list2);
                        addressSearch.notifyDataSetChanged();
                    }
                    if(s.length()==5){
                        list.clear();
                      List<City>  list2=new ArrayList<>();
                        for(int i=0;i<6;i++){
                            City city=new City("636624","纽约","newYork");
                            list2.add(city);
                        }
                        list.addAll(list2);
                        addressSearch.notifyDataSetChanged();
                    }
                    else {
                        list.clear();
                        List<City>  list2=new ArrayList<>();
                            for(int i=0;i<8;i++){
                                City city=new City("636624","纽约","newYork");
                                list2.add(city);
                            }
                            list.addAll(list2);
                            addressSearch.notifyDataSetChanged();

                    }

                }
                else {
                    recyclerView.setVisibility(View.GONE);

                }

            }
        };
        sh.addTextChangedListener(editWatcher);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_left_back_img:
                finish();
        }

    }
}
