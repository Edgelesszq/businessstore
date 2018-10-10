package com.businessstore.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import activity.com.businessstore.R;

public class DialogStyleThree extends Dialog {
    private Button btn_no,btn_yes;
    private TextView content;
    private String nostr,yesstr,contentstr;

    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    public DialogStyleThree(@NonNull Context context) {
        super(context,R.style.dialog_style);
    }

    public void setNoOnclickListener(onNoOnclickListener onNoOnclickListener) {
    }

    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }
    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            nostr = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }
    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesstr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_style_3);


        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }
    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });
    }
    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {

        if (contentstr!= null) {
            content.setText(contentstr);
        }
        //如果设置按钮的文字
        if (yesstr != null) {
            btn_yes.setText(yesstr);
        }
        if (nostr != null) {
            btn_no.setText(nostr);
        }
    }
    /**
     * 初始化界面控件
     */
    private void initView() {
        btn_yes = (Button) findViewById(R.id.dialog_s1_yes);
        btn_no = (Button) findViewById(R.id.dialog_s1_no);
        content = (TextView) findViewById(R.id.dialog_s1_content);
    }

    public void setNostr(String nostr) {
        this.nostr = nostr;
    }

    public void setYesstr(String yesstr) {
        this.yesstr = yesstr;
    }

    public void setContentstr(String contentstr) {
        this.contentstr = contentstr;
    }
}
