package activity.com.businessstore;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.businessstore.model.PictureInfo;
import com.businessstore.util.LogUtil;
import com.businessstore.util.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapter.com.businessstore.AdapterBigPhotoViewPager;
import adapter.com.businessstore.ViewPagerAdapter;

public class BigPhotoActivity extends BaseActivity {
    private ViewPager viewPager;
    private TextView tvNum;
    private List<String> stringList;
    private int posi=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bigphoto_item);

        initParam();
        initView();

    }

    private void initView() {

        try {
            posi=getIntent().getIntExtra("posi",0);

        }catch (Exception e){
            return;
        }
        LogUtil.d("test",""+posi);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tvNum = (TextView) findViewById(R.id.text_num);
//        AdapterBigPhotoViewPager viewPagerAdapter = new AdapterBigPhotoViewPager(getSupportFragmentManager(),stringList);
//        viewPager.setAdapter(viewPagerAdapter);
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(this,stringList);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(posi);
        tvNum.setText(posi+1+"/" + stringList.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                tvNum.setText(position + 1 + "/" + stringList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initParam() {
        //需要加载的网络图片

        List<PictureInfo> mList = getIntent().getParcelableArrayListExtra("pictureInfoList");
        stringList = new ArrayList<>();
        for (int i = 0; i < mList.size();i++) {
            stringList.add(mList.get(i).getUrllarge());
        }
    }
}
