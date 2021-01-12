package me.liuning.easy;


import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import me.liuning.core.dialog.BaseDialog;
import me.liuning.core.ioc.annotation.OnClick;
import me.liuning.core.ioc.annotation.ViewBind;
import me.liuning.framework.ui.SkinActivity;
import me.liuning.framework.ui.navigationbar.DefaultNavigationBar;

public class MainActivity extends SkinActivity {

    @ViewBind(R.id.id_main_test)
    TextView mTestView;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle() {
        DefaultNavigationBar navigationBar = new
                DefaultNavigationBar.Builder(this)
                .setTitle("投稿")
                .build();
    }

    @Override
    protected void initView() {
        mTestView.setText("注入");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.id_main_test, R.id.id_main_start_test, R.id.id_main_open_dialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_main_test:
                Toast.makeText(this, "显示", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_main_start_test:
                start(TestActivity.class);
                break;

            case R.id.id_main_open_dialog:

                BaseDialog dialog = new BaseDialog.Builder(this)
                        .contentView(R.layout.detail_comment_dialog)
                        .setText(R.id.submit_btn, "评议")
                        .cancelable(true)
                        .addDefaultAnimation()
                        .build();
                dialog.show();

                final EditText commentEt = dialog.getView(R.id.comment_editor);
                dialog.setOnclickListener(R.id.submit_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,
                                commentEt.getText().toString().trim(), Toast.LENGTH_LONG).show();
                    }
                });
            default:
                break;
        }
    }

}
