package com.example.wind.meizhi.mvp.present;


import com.example.wind.meizhi.mvp.interf.NewsModel;
import com.example.wind.meizhi.mvp.interf.NewsView;
import com.example.wind.meizhi.mvp.interf.OnLoadDataListener;
import com.example.wind.meizhi.mvp.model.ZhihuDetail;
import com.example.wind.meizhi.mvp.model.ZhihuJson;
import com.example.wind.meizhi.mvp.model.ZhihuModel;
import com.example.wind.meizhi.mvp.model.ZhihuStory;
import com.example.wind.meizhi.net.API;
import com.example.wind.meizhi.ui.BaseActivity;

/**
 * Created by Summers on 2016/8/17.
 */
public class ZhihuDataPresenter implements NewsPresenter,OnLoadDataListener {
    private NewsView<ZhihuJson> mNewsView;
    private NewsModel<ZhihuStory, ZhihuDetail> mNewsModel;

    public ZhihuDataPresenter(NewsView<ZhihuJson> newsView, BaseActivity activity) {
        this.mNewsView = newsView;
        mNewsModel = new ZhihuModel(activity);

    }

    @Override
    public void loadNews() {
        mNewsView.showProgress();
        mNewsModel.getNews(API.TYPE_LATEST, this);

    }

    @Override
    public void loadBefore() {
        mNewsModel.getNews(API.TYPE_BEFORE, this);
    }

    @Override
    public void onSuccess() {
        mNewsView.addNews(null);
        mNewsView.hideProgress();
    }

    @Override
    public void onFailure(String msg) {

    }
}
