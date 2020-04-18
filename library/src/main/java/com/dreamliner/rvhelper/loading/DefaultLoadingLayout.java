package com.dreamliner.rvhelper.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dreamliner.lib.rvhelper.R;


/**
 * @author chenzj
 * @Title: DefaultLoadindLayout
 * @Description: 类的描述 -
 * @date 2016/10/8 23:31
 * @email admin@chenzhongjin.cn
 */
public class DefaultLoadingLayout extends LoadingLayout {

    private ProgressBar mLoadingIv;
    private TextView mLoadingTipTv;

    public DefaultLoadingLayout(Context context) {
        this(context, null);
    }

    public DefaultLoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultLoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLoadingIv = (ProgressBar) findViewById(R.id.loading_pb);
        mLoadingTipTv = (TextView) findViewById(R.id.loading_tip_tv);
    }

    @Override
    public void onShowLoading() {
        mLoadingIv.setVisibility(VISIBLE);
    }

    @Override
    public void onHideLoading() {
        mLoadingIv.setVisibility(GONE);
    }
}
