package com.example.wind.meizhi.mvp.interf;


import com.example.wind.meizhi.mvp.other.NewsDetail;

/**
 * fragment or activity need to implement this to show news detail.
 */
public interface NewsDetailView<T extends NewsDetail> {
    void showProgress();
    void showDetail(T detailNews);
    void hideProgress();
    void showLoadFailed(String msg);
}
