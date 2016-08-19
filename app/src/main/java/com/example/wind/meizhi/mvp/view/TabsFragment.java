package com.example.wind.meizhi.mvp.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.wind.meizhi.R;
import com.example.wind.meizhi.ui.BaseFragment;
import com.example.wind.meizhi.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Summers on 2016/8/12.
 */
public class TabsFragment extends BaseFragment {
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.tabs)
    TabLayout tabs;

    public static final String MENU_NEWS = "news";
    public static final String MENU_PIC = "pic";

    public static final int TYPE_ZHIHU = 1024;
    public static final int TYPE_FRESH = 1025;

    private List<RecyclerFragment> fragments = new ArrayList<>();
    private TabPagerAdapter adapter;
    private String menuType;

    public static TabsFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TYPE, type);
        TabsFragment tabsFragment = new TabsFragment();
        tabsFragment.setArguments(bundle);
        return tabsFragment;
    }

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.fragment_news_tab;
    }

    @Override
    protected void initViews() {
        adapter = new TabPagerAdapter(getChildFragmentManager());
        initFragments();
        pager.setAdapter(adapter);
        if (MENU_PIC.equals(menuType)) {
            tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        tabs.setupWithViewPager(pager);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initFragments() {
        menuType = getArguments().getString(Constants.TYPE);
        List<String> mTitles = null;
        if (MENU_NEWS.equals(menuType)) {
            mTitles = new ArrayList<>();
            fragments.add(new ZhihuFragment());
            fragments.add(new FreshFragment());
            mTitles.add(getString(R.string.zhihu_news));
            mTitles.add(getString(R.string.fresh_news));


        }else if (MENU_PIC.equals(menuType)) {
            String[] titles = new String[]{
                    getString(R.string.gank),
                    getString(R.string.db_rank),
                    getString(R.string.db_leg),
                    getString(R.string.db_silk),
                    getString(R.string.db_breast),
                    getString(R.string.db_butt)};
            mTitles = Arrays.asList(titles);
            fragments.add(PictureFragment.newInstance(PictureFragment.TYPE_GANK));
            fragments.add(PictureFragment.newInstance(PictureFragment.TYPE_DB_RANK));
            fragments.add(PictureFragment.newInstance(PictureFragment.TYPE_DB_LEG));
            fragments.add(PictureFragment.newInstance(PictureFragment.TYPE_DB_SILK));
            fragments.add(PictureFragment.newInstance(PictureFragment.TYPE_DB_BREAST));
            fragments.add(PictureFragment.newInstance(PictureFragment.TYPE_DB_BUTT));
            if (fragments.size() != titles.length) {
                throw new IllegalArgumentException("You need add all fragments in "+getClass().getSimpleName());
            }

        }

        adapter.setFragments(fragments, mTitles);

    }

    @Override
    protected void initData() {

    }

    private class TabPagerAdapter extends FragmentPagerAdapter {
        private List<RecyclerFragment> fragments;
        private List<String> titles;


        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setFragments(List<RecyclerFragment> fragments, List<String> titles) {
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
