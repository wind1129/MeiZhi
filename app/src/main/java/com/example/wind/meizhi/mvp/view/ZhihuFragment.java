package com.example.wind.meizhi.mvp.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.wind.meizhi.MainActivity;
import com.example.wind.meizhi.R;
import com.example.wind.meizhi.mvp.interf.NewsView;
import com.example.wind.meizhi.mvp.interf.OnListFragmentInteract;
import com.example.wind.meizhi.mvp.model.ZhihuJson;
import com.example.wind.meizhi.mvp.other.ZhihuListAdapter;
import com.example.wind.meizhi.mvp.present.NewsPresenter;
import com.example.wind.meizhi.mvp.present.ZhihuDataPresenter;
import com.example.wind.meizhi.ui.BaseActivity;
import com.example.wind.meizhi.utils.Constants;
import com.example.wind.meizhi.utils.SPUtil;
import com.example.wind.meizhi.utils.UI;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

/**
 * Created by Summers on 2016/8/15.
 */
public class ZhihuFragment extends RecyclerFragment implements NewsView<ZhihuJson> ,OnListFragmentInteract {
    private static final int PRELOAD_COUNT = 1;
    private static final String TAG = "test";

    private LinearLayoutManager layoutManager;
    private ZhihuListAdapter adapter;
    private BaseActivity mActivity;
    private ConvenientBanner banner;

    private NewsPresenter presenter;


    @Override
    protected void initViews() {
        super.initViews();
        type = TabsFragment.TYPE_ZHIHU;
        mActivity = (BaseActivity) getActivity();
        layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ZhihuListAdapter(this, mActivity);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    onListScrolled();

                }
            }
        });
    }

    private void onListScrolled() {
        initBanner();
        firstPosition = layoutManager.findFirstVisibleItemPosition();
        lastPosition = layoutManager.findLastVisibleItemPosition();
        if (lastPosition + PRELOAD_COUNT == adapter.getItemCount()) {
            presenter.loadBefore();
        }

    }

    @Override
    protected void initData() {
        presenter = new ZhihuDataPresenter(this, (BaseActivity) getActivity());
        initBanner();
        onRefresh();

    }

    private void initBanner() {
        if (null == banner) {
            if (recyclerView.getChildCount() != 0 && layoutManager.findFirstVisibleItemPosition() == 0) {
                banner = (ConvenientBanner) layoutManager.findViewByPosition(0);
                banner.setScrollDuration(1000);
                banner.startTurning(5000);
            }
        }
    }

    @Override
    public void onRefresh() {
        presenter.loadNews();
    }

    @Override
    public void onListFragmentInteraction(RecyclerView.ViewHolder viewHolder) {
        if(viewHolder instanceof  ZhihuListAdapter.ViewHolder){
            ZhihuListAdapter.ViewHolder holder = (ZhihuListAdapter.ViewHolder) viewHolder;
            Intent intent = new Intent(getActivity(), ZhihuDetailActivity.class);
            intent.putExtra(Constants.ID, holder.zhihuStory.getId());
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(),holder.mImage,getString(R.string.shared_img));
            ActivityCompat.startActivity(getActivity(),intent,optionsCompat.toBundle());

            holder.mTitle.setTextColor(ZhihuListAdapter.textGrey);

        }
    }

    @Override
    public void showProgress() {
        showProgress(true);

    }

    @Override
    public void addNews(ZhihuJson news) {
        adapter.addNews(news);

    }

    @Override
    public void hideProgress() {
        showProgress(false);

    }

    @Override
    public void loadFailed(String msg) {
        if(isAlive()){
            UI.showSnack(((MainActivity) getActivity()).getDrawerLayout(), R.string.load_fail);
        }
    }

    @Override
    public void onDestroyView() {
        OkHttpUtils.getInstance().cancelTag(mActivity);
        SPUtil.save(type + Constants.POSITION, firstPosition);
        if (banner!=null){
            banner.stopTurning();
            Log.i(TAG, "onDestroyView: stop banner");
        }
        super.onDestroyView();
    }
}
