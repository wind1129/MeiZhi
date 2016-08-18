package com.example.wind.meizhi.mvp.model;

import android.util.Log;

import com.example.wind.meizhi.mvp.interf.NewsModel;
import com.example.wind.meizhi.mvp.interf.OnLoadDataListener;
import com.example.wind.meizhi.mvp.interf.OnLoadDetailListener;
import com.example.wind.meizhi.net.API;
import com.example.wind.meizhi.net.Json;
import com.example.wind.meizhi.net.Net;
import com.example.wind.meizhi.ui.BaseActivity;
import com.example.wind.meizhi.utils.Constants;
import com.example.wind.meizhi.utils.DateUtil;
import com.example.wind.meizhi.utils.SPUtil;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Date;

import io.realm.Realm;
import io.realm.Sort;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Summers on 2016/8/17.
 */
public class ZhihuModel implements NewsModel<ZhihuStory, ZhihuDetail> {
    private BaseActivity mActivity;
    private Realm realm;
    private int type;
    private String date;
    private static final String TAG = "test";


    public ZhihuModel(BaseActivity activity) {
        mActivity = activity;
        realm = mActivity.mRealm;

    }

    @Override
    public void getNews(final int type, final OnLoadDataListener listener) {
           this.type = type;
        Callback<ZhihuJson> callback = new Callback<ZhihuJson>() {
            @Override
            public ZhihuJson parseNetworkResponse(Response response) throws Exception {
                ZhihuJson zhihuJson = Json.parseZhihuNews(response.body().string());
                date = zhihuJson.getDate();
                if (type == API.TYPE_BEFORE) {
                    SPUtil.save(Constants.DATE, date);
                }
                Thread.sleep(3000);
                return zhihuJson;
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(ZhihuJson zhihuJson) {
                if (mActivity.isFinishing()) {
                    Log.i(TAG, "onResponse: isFinishing");
                    return;
                }
                saveZhihu(zhihuJson);
                listener.onSuccess();

            }
        };
        getData(callback);


    }

    private void saveZhihu(ZhihuJson zhihuJson) {
        if (null != zhihuJson) {
            realm.beginTransaction();
            if (type == API.TYPE_LATEST) {
                realm.where(ZhihuTop.class).findAll().clear();
            }
            realm.copyToRealmOrUpdate(zhihuJson);
            realm.commitTransaction();
            //realm.where(ZhihuJson.class).findAllSorted(Constants.DATE, Sort.DESCENDING);

        }


    }

    private void getData(Callback<ZhihuJson> callback) {
        if (type == API.TYPE_LATEST) {
            Net.get(API.NEWS_LATEST, callback, mActivity);

        } else if (type == API.TYPE_BEFORE) {
            date = SPUtil.get(Constants.DATE, DateUtil.parseStandardDate(new Date()));
            Net.get(API.NEWS_BEFORE + date, callback, mActivity);
        }

    }

    @Override
    public void getNewsDetail(ZhihuStory newsItem, OnLoadDetailListener<ZhihuDetail> listener) {

    }
}
