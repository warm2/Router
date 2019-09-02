package com.warm.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.warm.demo.databinding.ActivityMainBinding;
import com.warm.demo.login.rx.RxLogin;
import com.warm.router.Router;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxLogin.isLogin = false;
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.bt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Router.init();
                Router.newRequest("test/user/detail")
                        .putLong("id", 1)
                        .build()
                        .start(MainActivity.this);
            }
        });
        mBinding.bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this,DetailActivity.class));
                Router.newRequest("test/detail")
                        .putInt("type", 1)
                        .build()
                        .start(MainActivity.this);
            }
        });
        mBinding.bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newRequest("https://www.jianshu.com/p/d57abb5b87f3")
                        .putInt("type", 1)
                        .build()
                        .start(MainActivity.this);
            }
        });
        mBinding.bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newRequest("myapp://demo.app/userdetail?id=10001")
                        .putInt("type", 1)
                        .putLongArray("ids", new long[]{1, 2, 3})
                        .build()
                        .start(MainActivity.this);
            }
        });

        mBinding.bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.build("app2/car")
                        .start(MainActivity.this);
            }
        });

        mBinding.bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.build("apphybrid/web")
                        .start(MainActivity.this);
            }
        });
    }
}