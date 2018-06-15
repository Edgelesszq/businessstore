package com.businessstore.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by joe on 2018/6/12.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();

    }
    public  static Context getContext(){
        return context;
    }
}
