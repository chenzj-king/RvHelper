package com.dreamliner.rvhelper.sample.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamliner.rvhelper.loading.LoadingLayout;
import com.dreamliner.rvhelper.sample.R;

/**
 * @author chenzj
 * @Title: Sq580LoadLayout
 * @Description: 类的描述 -
 * @date 2016/10/9 22:57
 * @email admin@chenzhongjin.cn
 */
public class Sq580LoadingLayout extends LoadingLayout {

    private ImageView mLoadingIv;
    private TextView mLoadingTipTv;

    public Sq580LoadingLayout(Context context) {
        super(context);
    }

    public Sq580LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Sq580LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Sq580LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLoadingIv = (ImageView) findViewById(R.id.loading_iv);
        mLoadingTipTv = (TextView) findViewById(R.id.loading_tip_tv);
        mLoadingTipTv.setText("自定义的加载中...");
    }

    @Override
    public void onShowLoading() {
        ((AnimationDrawable) mLoadingIv.getDrawable()).start();
    }

    @Override
    public void onHideLoading() {
        ((AnimationDrawable) mLoadingIv.getDrawable()).stop();
    }
}
