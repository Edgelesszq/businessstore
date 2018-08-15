package com.businessstore.view.popwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import activity.com.businessstore.R;

/**
 * @param
 * @author ldm
 * @description 自定义PopupWindow
 *
 */
public class CustomPopWindow2 extends PopupWindow {
    //上下文
    private Context mContext;
    // PopupWindow中控件点击事件回调接口
    private View.OnClickListener mOnClickListener;
    //PopupWindow布局文件中的Button
    private Button alarm_pop_btn;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @description 构造方法
     * @author ldm
     * @param
     */
    public CustomPopWindow2(Context mContext, View.OnClickListener listener,int position) {
        super(mContext);
        this.mContext = mContext;
        this.mOnClickListener = listener;
        setPosition(position);
        //获取布局文件
        View mContentView = LayoutInflater.from(mContext).inflate(R.layout.order_recyclerview_item_pop_layout, null);
        //设置布局
        setContentView(mContentView);
        // 设置弹窗的宽度和高度
//        setWidth(width);
//        setHeight(height);
        //设置能否获取到焦点
        setFocusable(true);
        //设置PopupWindow进入和退出时的动画效果
//        setAnimationStyle(R.style.popwindow_exit_anim_style);
        setTouchable(true); // 默认是true，设置为false，所有touch事件无响应，而被PopupWindow覆盖的Activity部分会响应点击
        // 设置弹窗外可点击,此时点击PopupWindow外的范围，Popupwindow不会消失
        setOutsideTouchable(true);
        //外部是否可以点击，设置Drawable原因可以参考：http://blog.csdn.net/harvic880925/article/details/49278705
        setBackgroundDrawable(new BitmapDrawable());
        // 设置弹窗的布局界面
        initUI();
    }

    /**
     * 初始化弹窗列表
     */
    private void initUI() {
        //编辑的点击事件
        getContentView().findViewById(R.id.order_recyclerview_item_more_pop_editer).setOnClickListener(mOnClickListener);
        //删除的点击事件
        getContentView().findViewById(R.id.order_recyclerview_item_more_pop_delete).setOnClickListener(mOnClickListener);




       /* //获取到按钮
        alarm_pop_btn = (Button) getContentView().findViewById(R.id.alarm_pop_btn);
        //设置按钮点击事件
        alarm_pop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mOnClickListener) {
                    mOnClickListener.dispose();
                }
            }
        });*/
    }

    /**
     * 显示弹窗列表界面
     */
    public void show(View view,int x,int y) {
//        int[] location = new int[2];
//        view.getLocationOnScreen(location);
        //Gravity.BOTTOM设置在view下方，还可以根据location来设置PopupWindowj显示的位置
        showAsDropDown(view, x, y);
    }

    /**
     * @param
     * @author ldm
     * @description 点击事件回调处理接口
     * @time 2016/7/29 15:30
     */
    public interface IPopuWindowListener {
        void dispose();
    }
}
