package me.liuning.easy;


import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.liuning.core.ioc.annotation.OnClick;
import me.liuning.core.ioc.annotation.ViewBind;
import me.liuning.framework.ui.SkinActivity;

public class MainActivity extends SkinActivity {

    @ViewBind(R.id.id_main_test)
    TextView mTestView;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        mTestView.setText("注入");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.id_main_test, R.id.id_main_start_test})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_main_test:
                Toast.makeText(this, "显示", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_main_start_test:
                start(TestActivity.class);
                break;
            default:
                break;
        }
    }

}
