package me.liuning.easy;

import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import me.liuning.core.hotfix.HotFixManager;
import me.liuning.core.log.LogUtils;
import me.liuning.framework.ui.SkinActivity;

public class TestActivity extends SkinActivity implements View.OnClickListener {
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_test);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        findViewById(R.id.id_test_crash).setOnClickListener(this);
        findViewById(R.id.id_test_fix).setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_test_crash:
                LogUtils.debug("show  error");
                int value = 2/0;
                Toast.makeText(this, "崩了", Toast.LENGTH_LONG).show();
                break;
            case R.id.id_test_fix:
                File file = new File(Environment.getExternalStorageDirectory(), "fix.dex");
                try {
                    HotFixManager.getInstance().loadPatch(file.getAbsolutePath());
                    Toast.makeText(this, "加载成功", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "加载失败", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
