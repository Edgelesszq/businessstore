package com.businessstore.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import activity.com.businessstore.R;

public class DialogStyleProgressBar extends Dialog {

    private TextView content_tv;
    private String content;

    public DialogStyleProgressBar(@NonNull Context context, int themeResId, String content) {
      //  super(context, R.style.dialog_style);
        super(context, R.style.withStringDialog);
        this.content = content;
    }

    public DialogStyleProgressBar(@NonNull Context context) {
        super(context,R.style.dialog_style);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progressbar_string);


        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件


    }

    private void initData() {

        if (content!= null) {
            content_tv.setText(content);
        }

    }
    /**
     * 初始化界面控件
     */
    private void initView() {

        content_tv = (TextView) findViewById(R.id.content_tv);
    }


}
