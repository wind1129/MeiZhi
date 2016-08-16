package com.example.wind.meizhi.mvp.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.wind.meizhi.mvp.interf.OnListFragmentInteract;
import com.example.wind.meizhi.mvp.other.ZhihuListAdapter;
import com.example.wind.meizhi.ui.BaseActivity;

import java.util.List;

/**
 * Created by Summers on 2016/8/15.
 */
public class ZhihuFragment extends RecyclerFragment implements OnListFragmentInteract {

    private LinearLayoutManager layoutManager;
    private ZhihuListAdapter adapter;
    private BaseActivity mActivity;


    @Override
    protected void initViews() {
        super.initViews();
        type = TabsFragment.TYPE_ZHIHU;
        mActivity = (BaseActivity) getActivity();
        layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ZhihuListAdapter(this, mActivity);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onListFragmentInteraction(RecyclerView.ViewHolder holder) {

    }
}
