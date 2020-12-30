package me.liuning.core.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.liuning.core.ioc.ViewInject;

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ViewInject.inject(this);
        initTitle();
        initView();
        initData();
    }


    protected abstract void setContentView();

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initData();

    protected void start(Class<? extends Activity> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        this.startActivity(intent);
    }
}
