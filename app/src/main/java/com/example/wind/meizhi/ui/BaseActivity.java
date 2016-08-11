package com.example.wind.meizhi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.wind.meizhi.R;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Summers on 2016/8/11.
 */
public abstract class BaseActivity extends AppCompatActivity{
    public Realm mRealm;
    protected int layoutId = R.layout.activity_main;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initLayout();
        super.onCreate(savedInstanceState);
        initViews();
    }

    protected abstract void initLayout();
    public void initViews(){
        setContentView(layoutId);
        mRealm = Realm.getDefaultInstance();
        ButterKnife.bind(this);
        initAppBar();
    }

    private void initAppBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
        }
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
        mRealm.close();
    }
}
