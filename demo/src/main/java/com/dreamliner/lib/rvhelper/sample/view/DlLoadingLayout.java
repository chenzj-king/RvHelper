package com.dreamliner.lib.rvhelper.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dreamliner.lib.rvhelper.sample.R;
import com.dreamliner.rvhelper.loading.LoadingLayout;

/**
 * @author chenzj
 * @Title: DlLoadingLayout
 * @Description: 类的描述 -
 * @date 2016/10/9 22:57
 * @email admin@chenzhongjin.cn
 */
public class DlLoadingLayout extends LoadingLayout {

    private ProgressBar mLoadingPb;
    private TextView mLoadingTipTv;

    public DlLoadingLayout(Context context) {
        this(context, null);
    }

    public DlLoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DlLoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLoadingPb = (ProgressBar) findViewById(R.id.loading_pb);
        mLoadingTipTv = (TextView) findViewById(R.id.loading_tip_tv);
        mLoadingTipTv.setText("自定义的加载中...");
    }

    @Override
    public void onShowLoading() {
        //((AnimationDrawable) mLoadingPb.getDrawable()).start();
    }

    @Override
    public void onHideLoading() {
        //((AnimationDrawable) mLoadingPb.getDrawable()).stop();
    }
}
