package com.example.wind.meizhi.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.wind.meizhi.R;
import com.example.wind.meizhi.ui.BaseFragment;
import com.example.wind.meizhi.utils.Constants;
import com.example.wind.meizhi.utils.SPUtil;

import butterknife.Bind;

/**
 * Created by Summers on 2016/8/15.
 */
public abstract class RecyclerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.list)
    RecyclerView recyclerView;

    boolean isFirst = true;   //whether is first time to enter fragment
    int type;               // type of recyclerView's content
    int lastPosition;       //last visible position
    int firstPosition;      //first visible position

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null) {
            //restoring position when reentering fragment.
            firstPosition = SPUtil.getInt(type + Constants.POSITION);
            if (firstPosition > 0) {
                recyclerView.scrollToPosition(firstPosition);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        SPUtil.save(type + Constants.POSITION, firstPosition);
    }

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.fragment_recycler;
    }

    @Override
    protected void initViews() {
        recyclerView.setHasFixedSize(true);
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(this);

    }

    public void showProgress(final boolean refreshState) {
        if (null != swipeRefresh) {
            swipeRefresh.setRefreshing(refreshState);
        }
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }


}
