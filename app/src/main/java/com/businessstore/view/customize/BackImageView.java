package com.businessstore.view.customize;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import activity.com.businessstore.R;


/**
 * Created by joe on 2018/6/14.
 */


public class BackImageView extends FrameLayout implements View.OnClickListener {
    public BackImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.base_bcak, this);
        ImageView back_img=findViewById(R.id.back);
        back_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                ((Activity)getContext()).finish();
        }
    }
}
