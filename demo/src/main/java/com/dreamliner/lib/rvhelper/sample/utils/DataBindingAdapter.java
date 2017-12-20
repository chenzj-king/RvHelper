package com.dreamliner.lib.rvhelper.sample.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.dreamliner.lib.rvhelper.sample.AppContext;
import com.dreamliner.lib.rvhelper.sample.R;

import java.util.ArrayList;
import java.util.List;


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


    @BindingAdapter(value = {"indicator", "data", "sliderClick"}, requireAll = false)
    public static void setSlider(SliderLayout sliderLayout, PagerIndicator indicator, String data,
                                 BaseSliderView.OnSliderClickListener onSliderClickListener) {

        DisplayMetrics displayMetrics = AppContext.getInstance().getResources().getDisplayMetrics();
        float width = displayMetrics.widthPixels;
        ViewGroup.LayoutParams lp = sliderLayout.getLayoutParams();
        lp.height = (int) (166 * width / 375);
        sliderLayout.setLayoutParams(lp);
        sliderLayout.requestLayout();

        //清空旧的
        sliderLayout.removeAllSliders();
        List<String> banners = new ArrayList<>();
        banners.add("http://jewely-image.b0.upaiyun.com/images/2017-02-22/148773250858acff1c6b69f.jpg");
        banners.add("http://jewely-image.b0.upaiyun.com/images/2017-02-22/148774660958ad363167649.jpg");
        banners.add("http://jewely-image.b0.upaiyun.com/ueditor/2017-02-28/1488248833.jpg");
        banners.add("https://static.darryring.com/ueditor/2017-03-28/1490687105.jpg");
        for (String banner : banners) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(AppContext.getInstance());
            defaultSliderView.image(banner).empty(R.drawable.bg_image_loading).error(R.drawable.bg_image_loading)
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop).setOnSliderClickListener(onSliderClickListener);
            defaultSliderView.bundle(new Bundle());
            defaultSliderView.getBundle().putSerializable("extra", banner);
            sliderLayout.addSlider(defaultSliderView);
        }

        indicator.setDefaultIndicatorColor(ContextCompat.getColor(AppContext.getInstance(), R.color.pagerIndicator_select_color),
                ContextCompat.getColor(AppContext.getInstance(), R.color.pagerIndicator_unSelect_color));
        sliderLayout.setCustomIndicator(indicator);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3 * 1000);
        sliderLayout.setRecoverCycleDuration(200);
        sliderLayout.startAutoCycle();
    }
}

