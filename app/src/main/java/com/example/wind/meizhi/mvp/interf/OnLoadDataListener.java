package com.example.wind.meizhi.mvp.interf;

/**
 * when news loaded, this interface is called
 */
public interface OnLoadDataListener {
    void onSuccess();
    void onFailure(String msg);
}
