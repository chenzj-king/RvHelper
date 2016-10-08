package com.dreamliner.rvhelper.loading;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamliner.rvhelper.R;

/**
 * @author chenzj
 * @Title: DefaultLoadindLayout
 * @Description: 类的描述 -
 * @date 2016/10/8 23:31
 * @email admin@chenzhongjin.cn
 */
public class DefaultLoadindLayout extends LoadingLayout {

    private ImageView mLoadingIv;
    private TextView mLoadingTipTv;

    public DefaultLoadindLayout(Context context) {
        super(context);
    }

    public DefaultLoadindLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultLoadindLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DefaultLoadindLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLoadingIv = (ImageView) findViewById(R.id.loading_iv);
        mLoadingTipTv = (TextView) findViewById(R.id.loading_tip_tv);
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
