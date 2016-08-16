package com.example.wind.meizhi.mvp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.example.wind.meizhi.R;
import com.example.wind.meizhi.mvp.model.ZhihuTop;
import com.example.wind.meizhi.utils.Imager;

/**
 * Created by Summers on 2016/8/16.
 */
public class BannerView implements Holder<ZhihuTop> {
    private View view;

    @Override
    public View createView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.card_item_big, null);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, ZhihuTop entity) {
        final ImageView imageView = (ImageView) view.findViewById(R.id.story_img);
        TextView textView = (TextView) view.findViewById(R.id.news_title);
        Imager.loadWithHighPriority(entity.getImage(), imageView);
        textView.setText(entity.getTitle());

    }
}
