package me.liuning.easy.application;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;


public class BaseApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
