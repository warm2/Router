package com.bingo.apphybrid;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bingo.apphybrid.databinding.ActivityWebBinding;
import com.bingo.demo.approuterpath.AppHybrid;
import com.bingo.router.annotations.Route;


@Route(pathClass = AppHybrid.Web.class)
public class WebActivity extends AppCompatActivity {

    private ActivityWebBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        mBinding.web.loadUrl("file:///android_asset/scheme.html");
    }
}
