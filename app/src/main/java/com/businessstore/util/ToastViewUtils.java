package com.businessstore.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.businessstore.view.toast.ToastView;

import activity.com.businessstore.R;

public class ToastViewUtils{
    static Toast toast;
    /**
     * 将Toast封装在一个方法中，在其它地方使用时直接输入要弹出的内容即可
     */
    public static void toastShowLoginMessage( String messages,Context mcontext,LayoutInflater inflater) {
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        View view = inflater.inflate(R.layout.toast_style, null); //加載layout下的布局
        TextView text = view.findViewById(R.id.tvTextToast);
        text.setText(messages); //toast内容
        if(toast==null){
            toast = new Toast(mcontext);
            toast.setGravity(Gravity.CENTER, 0, 0);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
            toast.setDuration(Toast.LENGTH_SHORT);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法

            toast.setView(view); //添加视图文件
            //toast.setToastBackground(R.color.white,R.color.black);

        }
        toast.show();
        toast=null;

    }
}
