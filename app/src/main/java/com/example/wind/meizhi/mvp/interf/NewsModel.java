package com.example.wind.meizhi.mvp.interf;


import com.example.wind.meizhi.mvp.other.NewsDetail;
import com.example.wind.meizhi.mvp.other.NewsItem;

/**
 * deals with the data work
 */
public interface NewsModel<I extends NewsItem, D extends NewsDetail> {

    void getNews(int type, OnLoadDataListener listener);

    void getNewsDetail(I newsItem, OnLoadDetailListener<D> listener);

}