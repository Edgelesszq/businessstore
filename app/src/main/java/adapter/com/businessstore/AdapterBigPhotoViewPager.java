package adapter.com.businessstore;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import com.businessstore.model.PictureInfo;
import com.businessstore.view.fragment.BigPhotoFragment;

import java.util.ArrayList;
import java.util.List;

import activity.com.businessstore.R;

public class AdapterBigPhotoViewPager extends FragmentPagerAdapter {


   private final List<PictureInfo> urlList;

    public AdapterBigPhotoViewPager(FragmentManager fm, List<PictureInfo> urlList) {
        super(fm);
        this.urlList=urlList;

    }

    @Override
    public Fragment getItem(int position) {

        return BigPhotoFragment.newInstance(urlList.get(position).getUrllarge());
    }

    @Override
    public int getCount() {
        return urlList.size();
    }
}
