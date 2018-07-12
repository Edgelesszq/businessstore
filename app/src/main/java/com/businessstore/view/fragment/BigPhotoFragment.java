package com.businessstore.view.fragment;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.businessstore.util.ToastUtils;

import activity.com.businessstore.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class BigPhotoFragment extends Fragment {
    private String url;
    private PhotoView mPhotoView;

    /**
     * 获取这个fragment需要展示图片的url
     * @param url
     * @return
     */
    public static BigPhotoFragment newInstance(String url) {
        BigPhotoFragment fragment = new BigPhotoFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bigphoto, container, false);
        mPhotoView = view.findViewById(R.id.photoview);
        //设置缩放类型，默认ScaleType.CENTER（可以不设置）
        mPhotoView.setScaleType(ImageView.ScaleType.CENTER);
        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ToastUtils.showToast(getContext(),"长按事件");
                return true;
            }
        });
        mPhotoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                ToastUtils.showToast(getContext(),"点击事件，真实项目中可关闭activity");
            }
        });
        Glide.with(getContext())
                .load(url)
//                .placeholder(R.mipmap.ic_launcher_round)//加载过程中图片未显示时显示的本地图片
//                .error(R.mipmap.ic_launcher_round)//加载异常时显示的图片
//                .centerCrop()//图片图填充ImageView设置的大小
//                .fitCenter()//缩放图像测量出来等于或小于ImageView的边界范围,该图像将会完全显示
                .into(mPhotoView);
        return view;
    }

}
