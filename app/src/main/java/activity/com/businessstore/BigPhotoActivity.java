package activity.com.businessstore;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.businessstore.util.LogUtil;
import com.businessstore.util.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;

import adapter.com.businessstore.AdapterBigPhotoViewPager;

public class BigPhotoActivity extends BaseActivity {
    private ViewPager viewPager;
    private TextView tvNum;
    private ArrayList<String> urlList;
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
        AdapterBigPhotoViewPager viewPagerAdapter = new AdapterBigPhotoViewPager(getSupportFragmentManager(),urlList);

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(posi);
       // tvNum.setText(String.valueOf(position + 1+posi) + "/" + urlList.size());
        tvNum.setText(posi+1+"/" + urlList.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                tvNum.setText(position + 1 + "/" + urlList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initParam() {
        //需要加载的网络图片
        String[] urls = {
                "http://a.hiphotos.baidu.com/image/pic/item/00e93901213fb80e3b0a611d3fd12f2eb8389424.jpg",
                "http://b.hiphotos.baidu.com/image/pic/item/5243fbf2b2119313999ff97a6c380cd790238d1f.jpg",
                "http://f.hiphotos.baidu.com/image/pic/item/43a7d933c895d1430055e4e97af082025baf07dc.jpg",
                "http://a.hiphotos.baidu.com/image/pic/item/00e93901213fb80e3b0a611d3fd12f2eb8389424.jpg",
                "http://b.hiphotos.baidu.com/image/pic/item/5243fbf2b2119313999ff97a6c380cd790238d1f.jpg",
                "http://f.hiphotos.baidu.com/image/pic/item/43a7d933c895d1430055e4e97af082025baf07dc.jpg",
                "http://a.hiphotos.baidu.com/image/pic/item/00e93901213fb80e3b0a611d3fd12f2eb8389424.jpg",
                "http://b.hiphotos.baidu.com/image/pic/item/5243fbf2b2119313999ff97a6c380cd790238d1f.jpg",
                "http://f.hiphotos.baidu.com/image/pic/item/43a7d933c895d1430055e4e97af082025baf07dc.jpg",
        };

        urlList = new ArrayList<>();
        Collections.addAll(urlList, urls);
    }
}
