package me.liuning.easy.application;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import me.liuning.core.crash.CrashHandler;
import me.liuning.core.hotfix.HotFixManager;


public class BaseApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        HotFixManager.getInstance().init(this);
       HotFixManager.getInstance().loadPatch();
    }
}
