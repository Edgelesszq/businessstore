package com.businessstore;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.businessstore.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import activity.com.businessstore.R;
import adapter.com.businessstore.ShowImagesAdapter;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2017/5/3.
 * 嵌套了viewpager的图片浏览
 */

public class ShowImagesDialog extends Dialog {
    private int posi;
    private View mView ;
    private Context mContext;
    private ShowImagesViewPager mViewPager;
    private TextView mIndexText;
    private List<String> mImgUrls;
    private List<String> mTitles;
    private List<View> mViews;
    private ShowImagesAdapter mAdapter;

    public ShowImagesDialog(@NonNull Context context, List<String> imgUrls,int posi) {
        super(context, R.style.transparentBgDialog);
        this.mContext = context;
        this.mImgUrls = imgUrls;
        this.posi=posi;
        initView();
        initData();
    }

    private void initView() {
        mView = View.inflate(mContext, R.layout.dialog_images_brower, null);
        mViewPager =  mView.findViewById(R.id.vp_images);
        mIndexText =  mView.findViewById(R.id.tv_image_index);
        mTitles = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        wl.height = Config.EXACT_SCREEN_HEIGHT;
        wl.width = Config.EXACT_SCREEN_WIDTH;
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
    }

    private void initData() {
        PhotoViewAttacher.OnPhotoTapListener listener = new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                dismiss();
            }
        };
        for (int i =0; i < mImgUrls.size(); i++) {
            final PhotoView photoView = new PhotoView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(layoutParams);
            photoView.setOnPhotoTapListener(listener);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"sssssssssssssss",Toast.LENGTH_SHORT).show();
                }
            });
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(mContext,"长按事件",Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
          /* Glide.with(mContext)
                    .load(mImgUrls.get(i))
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    photoView.setImageDrawable(resource);
                }
            });*/
            Glide.with(mContext)
                    .load(mImgUrls.get(i))
//                .placeholder(R.mipmap.ic_launcher_round)//加载过程中图片未显示时显示的本地图片
//                .error(R.mipmap.ic_launcher_round)//加载异常时显示的图片
//                .centerCrop()//图片图填充ImageView设置的大小
//                .fitCenter()//缩放图像测量出来等于或小于ImageView的边界范围,该图像将会完全显示

                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            photoView.setImageDrawable(resource);
                        }
                    });




            mViews.add(photoView);
            mTitles.add(i + "");
        }

        mAdapter = new ShowImagesAdapter(mViews, mTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(posi);
        mIndexText.setText(posi+1 + "/" + mImgUrls.size());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mIndexText.setText(position + 1 + "/" + mImgUrls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
