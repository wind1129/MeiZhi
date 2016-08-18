package com.example.wind.meizhi.mvp.interf;


import com.example.wind.meizhi.mvp.other.NewsDetail;

/**
 * when detail loaded, this interface is called
 */
public interface OnLoadDetailListener<T extends NewsDetail> {
    void onDetailSuccess(T detailNews);
    void onFailure(String msg, Exception e);

}
