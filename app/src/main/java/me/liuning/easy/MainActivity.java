package me.liuning.easy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.liuning.core.ioc.ViewInject;
import me.liuning.core.ioc.annotation.OnClick;
import me.liuning.core.ioc.annotation.ViewBind;

public class MainActivity extends AppCompatActivity {

    @ViewBind(R.id.id_main_test)
    TextView mTestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewInject.inject(this);
        mTestView.setText("注入");
    }

    @OnClick({R.id.id_main_test})
    public void onClick(View view) {
        Toast.makeText(this, "显示", Toast.LENGTH_SHORT).show();
    }
}
