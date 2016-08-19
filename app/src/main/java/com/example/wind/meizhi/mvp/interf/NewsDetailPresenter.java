package com.example.wind.meizhi.mvp.interf;

import com.example.wind.meizhi.mvp.other.NewsItem;

/**
 * Created by Summers on 2016/8/19.
 */
public interface NewsDetailPresenter<T extends NewsItem> {
    void loadNewsDetail(T newsItem);
}
