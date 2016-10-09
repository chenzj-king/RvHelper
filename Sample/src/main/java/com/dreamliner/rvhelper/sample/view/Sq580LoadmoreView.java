package com.dreamliner.rvhelper.sample.view;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamliner.loadmore.LoadMoreContainer;
import com.dreamliner.loadmore.LoadMoreUIHandler;
import com.dreamliner.rvhelper.sample.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * @author chenzj
 * @Title: Sq580LoadmoreView
 * @Description: 类的描述 -
 * @date 2016/10/9 22:18
 * @email admin@chenzhongjin.cn
 */
public class Sq580LoadmoreView extends RelativeLayout implements LoadMoreUIHandler {

    private AVLoadingIndicatorView mFooterIndicatorView;
    private TextView mFooterTv;

    public Sq580LoadmoreView(Context context) {
        super(context);
        setupViews();
    }

    private void setupViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_sq580_footerview, this);
        mFooterIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.footer_indicatorview);
        mFooterTv = (TextView) findViewById(R.id.footer_tv);
    }

    @Override
    public void onLoading(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mFooterIndicatorView.setVisibility(VISIBLE);
        mFooterTv.setVisibility(GONE);
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
        mFooterIndicatorView.setVisibility(GONE);
        mFooterTv.setVisibility(VISIBLE);
        mFooterTv.setText(stringId);
    }
}
