package com.example.wind.meizhi.mvp.view;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.wind.meizhi.R;
import com.example.wind.meizhi.mvp.interf.NewsDetailPresenter;
import com.example.wind.meizhi.mvp.interf.NewsDetailView;
import com.example.wind.meizhi.mvp.model.ZhihuDetail;
import com.example.wind.meizhi.mvp.model.ZhihuStory;
import com.example.wind.meizhi.mvp.model.ZhihuTop;
import com.example.wind.meizhi.mvp.present.ZhihuDetailPresenter;
import com.example.wind.meizhi.net.DB;
import com.example.wind.meizhi.ui.BaseActivity;
import com.example.wind.meizhi.utils.Constants;
import com.example.wind.meizhi.utils.Imager;
import com.example.wind.meizhi.utils.UI;

import butterknife.Bind;

/**
 * Created by Summers on 2016/8/18.
 */
public class ZhihuDetailActivity extends BaseActivity implements NewsDetailView<ZhihuDetail>{
    @Bind(R.id.detail_img)
    ImageView detailImg;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.web_container)
    FrameLayout webContainer;
    private WebView webView;
    private ZhihuDetail zhihuDetail;

    @Override
    protected void initLayout() {
        layoutId = R.layout.activity_news_detail;

    }

    @Override
    public void initViews() {
        super.initViews();
        int id = getIntent().getIntExtra(Constants.ID,0);
        ZhihuStory story = DB.getById(mRealm, id, ZhihuStory.class);
        zhihuDetail = DB.getById(mRealm,id,ZhihuDetail.class);

        if(story == null){
            toolbarLayout.setTitle(DB.getById(mRealm,id, ZhihuTop.class).getTitle());
        }else{
            toolbarLayout.setTitle(story.getTitle());
        }
        NewsDetailPresenter<ZhihuStory> presenter = new ZhihuDetailPresenter(this, this);
        initWebView();
        if (zhihuDetail == null) {
            presenter.loadNewsDetail(story);
        }else {
            showDetail(zhihuDetail);
        }
    }

    private void initWebView() {
        webView = new WebView(this);
        webContainer.addView(webView);
        webView.setVisibility(View.INVISIBLE);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(final WebView view, int newProgress) {
                super.onProgressChanged(view,newProgress);
                if(newProgress == 100){
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.setVisibility(View.VISIBLE);
                            hideProgress();

                        }
                    },300);
                }
            }


        });
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDetail(ZhihuDetail detailNews) {
        zhihuDetail = detailNews;
        Imager.load(this, detailNews.getImage(), detailImg);
        //add css style to webView
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + detailNews.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
        initFAB();


    }

    private void initFAB() {
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);

    }

    @Override
    public void showLoadFailed(String msg) {
        UI.showSnackLong(webContainer, R.string.load_fail);

    }
}
