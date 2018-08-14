package com.businessstore.view.header;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

import activity.com.businessstore.R;

/**
 * Created by joe on 2018/6/11.
 */

@SuppressLint("AppCompatCustomView")
public class RefreshHeaderView2 extends LinearLayout implements SwipeRefreshTrigger, SwipeTrigger {
    TextView test_textview;

    public RefreshHeaderView2(Context context) {
        super(context);
    }

    public RefreshHeaderView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshHeaderView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    private void init() {


        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view= View.inflate(getContext(),R.layout.refreshheader_item,null);
        addView(view,lp);
        test_textview=view.findViewById(R.id.test_textview);

    }

    @Override
    public void onRefresh() {
        test_textview.setText("");
    }

    @Override
    public void onPrepare() {
        test_textview.setText("aa");
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled >= getHeight()) {
                test_textview.setText("RELEASE TO REFRESH");
            } else {
                test_textview.setText("SWIPE TO REFRESH");
            }
        } else {
            test_textview.setText("REFRESH RETURNING");
        }
    }

    @Override
    public void onRelease() {
    }

    @Override
    public void onComplete() {
        test_textview.setText("COMPLETE");
    }

    @Override
    public void onReset() {
        test_textview.setText("");
    }
}