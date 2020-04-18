package com.dreamliner.lib.rvhelper.sample.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.dreamliner.lib.rvhelper.sample.R;
import com.dreamliner.lib.rvhelper.sample.ui.activity.banner.ImageAdapter;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BindingAdapter;


/**
 * Created by chenzj on 2017/3/8.
 */
public class DataBindingAdapter {

    @BindingAdapter(value = {"imageUrl", "defaultImg", "radius"}, requireAll = false)
    public static void setImage(ImageView imageView, String imageUrl, Drawable defaultImg, int radius) {
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.optionalCenterCrop();
//
//        if (defaultImg != null) {
//            requestOptions.placeholder(defaultImg).error(defaultImg);
//        } else {
//            requestOptions.placeholder(R.drawable.bg_image_loading).error(R.drawable.bg_image_loading);
//        }
//        if (radius > 0) {
//            requestOptions.bitmapTransform(new CenterCrop(imageView.getContext()),
//                    new RoundedCornersTransformation(PixelUtil.dp2px(radius), 0, RoundedCornersTransformation.CornerType.ALL));
//        } else {
//            requestOptions.bitmapTransform(new CenterCrop(imageView.getContext()));
//        }
//        Glide.with(imageView.getContext()).load(imageUrl).apply(requestOptions).into(imageView);
    }


    @BindingAdapter(value = {"data", "bannerListener"}, requireAll = false)
    public static void setSlider(Banner banner, String data, OnBannerListener onBannerListener) {
        List<String> banners = new ArrayList<>();
        banners.add("https://res.darryring.com/activity/drSEM_storeLandingPage/temp/part04_banner01.jpg");
        banners.add("https://res.darryring.com/activity/drSEM_storeLandingPage/temp/part04_banner02.jpg");
        banners.add("https://res.darryring.com/activity/drSEM_storeLandingPage/temp/part04_banner03.jpg");
        banners.add("https://static.darryring.com/ueditor/2017-03-28/1490687105.jpg");

        banner.setAdapter(new ImageAdapter(banners));
        banner.setIndicator(new CircleIndicator(banner.getContext()));
        banner.setIndicatorSelectedColorRes(R.color.colorAccent);
        banner.setIndicatorNormalColorRes(android.R.color.white);
        banner.setIndicatorGravity(IndicatorConfig.Direction.LEFT);
        banner.setIndicatorSpace((int) BannerUtils.dp2px(20));
        banner.setIndicatorMargins(new IndicatorConfig.Margins((int) BannerUtils.dp2px(10)));
        banner.setIndicatorWidth(10, 20);
        //banner.addItemDecoration(new MarginItemDecoration((int) BannerUtils.dp2px(50)));
        //banner.setPageTransformer(new DepthPageTransformer());
        banner.setOnBannerListener(onBannerListener);
        //banner.addOnPageChangeListener(onBannerListener);
        banner.start();
    }
}

