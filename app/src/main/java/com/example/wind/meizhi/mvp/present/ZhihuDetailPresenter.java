package com.example.wind.meizhi.mvp.present;

import com.example.wind.meizhi.mvp.interf.NewsDetailPresenter;
import com.example.wind.meizhi.mvp.interf.NewsDetailView;
import com.example.wind.meizhi.mvp.interf.NewsModel;
import com.example.wind.meizhi.mvp.interf.OnLoadDetailListener;
import com.example.wind.meizhi.mvp.model.ZhihuDetail;
import com.example.wind.meizhi.mvp.model.ZhihuModel;
import com.example.wind.meizhi.mvp.model.ZhihuStory;
import com.example.wind.meizhi.mvp.view.ZhihuDetailActivity;
import com.example.wind.meizhi.ui.BaseActivity;

/**
 * Created by Summers on 2016/8/19.
 */
public class ZhihuDetailPresenter implements NewsDetailPresenter<ZhihuStory>,OnLoadDetailListener<ZhihuDetail> {
    private NewsDetailView<ZhihuDetail> newsDetailView;
    private NewsModel<ZhihuStory, ZhihuDetail> newsModel;

    public ZhihuDetailPresenter(NewsDetailView<ZhihuDetail> newsDetailView, BaseActivity activity) {
        this.newsDetailView = newsDetailView;
        this.newsModel = new ZhihuModel(activity);

    }

    @Override
    public void loadNewsDetail(ZhihuStory newsItem) {
        newsDetailView.showProgress();
        newsModel.getNewsDetail(newsItem,this);
    }

    @Override
    public void onDetailSuccess(ZhihuDetail detailNews) {
        newsDetailView.showDetail(detailNews);

    }

    @Override
    public void onFailure(String msg, Exception e) {
        newsDetailView.showLoadFailed(msg);
        newsDetailView.hideProgress();

    }
}
