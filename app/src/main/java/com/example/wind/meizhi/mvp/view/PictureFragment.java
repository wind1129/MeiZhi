package com.example.wind.meizhi.mvp.view;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.wind.meizhi.mvp.interf.OnLoadDataListener;
import com.example.wind.meizhi.ui.BaseActivity;
import com.example.wind.meizhi.utils.Constants;

/**
 * Created by Summers on 2016/8/19.
 */
public class PictureFragment extends RecyclerFragment implements OnLoadDataListener{
    public static final int TYPE_GANK = 0;
    public static final int TYPE_DB_BREAST = 1;
    public static final int TYPE_DB_BUTT = 2;
    public static final int TYPE_DB_SILK = 3;
    public static final int TYPE_DB_LEG = 4;
    public static final int TYPE_DB_RANK = 5;

    private StaggeredGridLayoutManager layoutManager;
    private BaseActivity context;

    public static PictureFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(Constants.TYPE, type);
        PictureFragment fragment = new PictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews() {
        super.initViews();
        context = (BaseActivity) getContext();
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String msg) {

    }
}
