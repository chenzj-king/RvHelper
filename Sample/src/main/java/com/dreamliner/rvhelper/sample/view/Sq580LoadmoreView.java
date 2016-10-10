package com.dreamliner.rvhelper.sample.view;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamliner.loadingdrawable.LoadingView;
import com.dreamliner.loadmore.LoadMoreContainer;
import com.dreamliner.loadmore.LoadMoreUIHandler;
import com.dreamliner.rvhelper.sample.R;

/**
 * @author chenzj
 * @Title: Sq580LoadmoreView
 * @Description: 类的描述 -
 * @date 2016/10/9 22:18
 * @email admin@chenzhongjin.cn
 */
public class Sq580LoadmoreView extends RelativeLayout implements LoadMoreUIHandler {

    private LoadingView mLoadingView;
    private TextView mFooterTv;

    public Sq580LoadmoreView(Context context) {
        this(context, null);
    }

    public Sq580LoadmoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Sq580LoadmoreView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupViews();
    }

    private void setupViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_sq580_footerview, this);
        mLoadingView = (LoadingView) findViewById(R.id.footer_indicatorview);
        mFooterTv = (TextView) findViewById(R.id.footer_tv);
    }

    @Override
    public void onLoading(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mLoadingView.setVisibility(VISIBLE);
        mFooterTv.setText(R.string.cube_views_load_more_loading);
    }

    @Override
    public void onLoadFinish(LoadMoreContainer container, boolean empty, boolean hasMore) {
        if (!hasMore) {
            if (empty) {
                setFooterTv(R.string.cube_views_load_more_loaded_empty);
            } else {
                setFooterTv(R.string.cube_views_load_more_loaded_no_more);
            }
        } else {
            setVisibility(INVISIBLE);
        }
    }

    @Override
    public void onWaitToLoadMore(LoadMoreContainer container) {
        setFooterTv(R.string.cube_views_load_more_click_to_load_more);
    }

    @Override
    public void onLoadError(LoadMoreContainer container, int errorCode, String errorMessage) {
        setFooterTv(R.string.cube_views_load_more_error);
    }

    private void setFooterTv(@StringRes int stringId) {
        setVisibility(VISIBLE);
        mLoadingView.setVisibility(GONE);
        mFooterTv.setVisibility(VISIBLE);
        mFooterTv.setText(stringId);
    }
}
