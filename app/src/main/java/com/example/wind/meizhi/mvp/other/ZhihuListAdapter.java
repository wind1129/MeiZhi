package com.example.wind.meizhi.mvp.other;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.wind.meizhi.R;
import com.example.wind.meizhi.mvp.interf.OnListFragmentInteract;
import com.example.wind.meizhi.mvp.model.ZhihuStory;
import com.example.wind.meizhi.mvp.model.ZhihuTop;
import com.example.wind.meizhi.mvp.view.BannerView;
import com.example.wind.meizhi.net.DB;
import com.example.wind.meizhi.ui.BaseActivity;
import com.example.wind.meizhi.utils.Imager;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * Created by Summers on 2016/8/16.
 */
public class ZhihuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RealmChangeListener {
    private static final int TYPE_BANNER = 0;
    /**
     * header is a title to display date
     */
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    /**
     * footer is to show load more hint
     */
    private static final int TYPE_FOOTER = 3;

    private List<ZhihuStory> zhihuStories;
    private List<ZhihuTop> tops;
    public ConvenientBanner<ZhihuTop> banner;
    private OnListFragmentInteract mListener;
    private Realm mRealm;

    public static int textGrey;
    public static int textDark;

    public ZhihuListAdapter(OnListFragmentInteract listener, BaseActivity activity) {
        mListener = listener;
        mRealm = activity.mRealm;
        zhihuStories = DB.findAll(mRealm, ZhihuStory.class);
        tops = DB.findAll(mRealm, ZhihuTop.class);
        mRealm.addChangeListener(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_BANNER) {
            View view = inflater.inflate(R.layout.fragment_news_banner, parent, false);
            return new BannerViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = inflater.inflate(R.layout.footer_loading, parent, false);
            return new FooterViewHolder(view);

        } else {
            View view = inflater.inflate(R.layout.fragment_news_item, parent, false);
            return new ViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        textGrey = ContextCompat.getColor(context, R.color.darker_gray);
        textDark = ContextCompat.getColor(context, android.support.design.R.color.abc_primary_text_material_light);

        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            if (position == 1) {
                viewHolder.header.setText(context.getString(R.string.hottest));
                viewHolder.header.setVisibility(View.VISIBLE);
                viewHolder.mItem.setVisibility(View.GONE);
                viewHolder.mItem.setClickable(false);
                return;
            } else {
                viewHolder.zhihuStory = zhihuStories.get(position - 2);

                viewHolder.header.setVisibility(View.GONE);
                viewHolder.mItem.setVisibility(View.VISIBLE);
            }

            Imager.load(context, viewHolder.zhihuStory.getImages().get(0).getVal(), viewHolder.mImage);
            viewHolder.mTitle.setText(viewHolder.zhihuStory.getTitle());
            viewHolder.mTitle.setTextColor(textDark);


        } else if (holder instanceof BannerViewHolder) {
            final BannerViewHolder itemHolder = (BannerViewHolder) holder;
            itemHolder.banner.setPages(new CBViewHolderCreator<BannerView>() {
                @Override
                public BannerView createHolder() {
                    return new BannerView();
                }
            }, tops);
        }

    }

    @Override
    public int getItemCount() {
        return zhihuStories.size() + 2;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        } else if (position == zhihuStories.size() + 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        public final ConvenientBanner<ZhihuTop> banner;

        public BannerViewHolder(View view) {
            super(view);
            banner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView header;
        public final ImageView mImage;
        public final TextView mTitle;
        public final View mItem;
        public ZhihuStory zhihuStory;

        public ViewHolder(View view) {
            super(view);
            header = (TextView) view.findViewById(R.id.story_header);
            mImage = (ImageView) view.findViewById(R.id.story_img);
            mTitle = (TextView) view.findViewById(R.id.news_title);
            mItem = view.findViewById(R.id.news_item);
        }

    }

    @Override
    public void onChange() {

    }
}
