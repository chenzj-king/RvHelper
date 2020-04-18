package com.dreamliner.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author huqiu.lhq
 */
public abstract class LoadMoreContainerBase extends LinearLayout implements LoadMoreContainer {

    protected int mVisibleThreshold = 0;

    private LoadMoreUIHandler mLoadMoreUIHandler;
    private LoadMoreHandler mLoadMoreHandler;
    private boolean mIsLoading;
    private boolean mHasMore = false;
    private boolean mAutoLoadMore = true;
    private boolean mLoadError = false;
    private boolean mListEmpty = true;
    private boolean mShowLoadingForFirstPage = false;
    protected View mFooterView;

    public LoadMoreContainerBase(Context context) {
        super(context);
    }

    public LoadMoreContainerBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mFooterView != null) {
            addFooterView(mFooterView);
        }
    }

    public void setVisibleThreshold(int count) {
        mVisibleThreshold = count;
    }

    /**
     * @deprecated It's totally wrong. Use {@link #useDefaultFooter} instead.
     */
    @Deprecated
    public void useDefaultHeader() {
        useDefaultFooter();
    }

    public void useDefaultFooter() {
        LoadMoreDefaultFooterView footerView = new LoadMoreDefaultFooterView(getContext());
        footerView.setVisibility(VISIBLE);
        setLoadMoreView(footerView);
        setLoadMoreUIHandler(footerView);
    }

    private void tryToPerformLoadMore() {
        if (mIsLoading) {
            return;
        }

        // no more content and also not load for first page
        if (!mHasMore && !(mListEmpty && mShowLoadingForFirstPage)) {
            return;
        }

        mIsLoading = true;

        if (null != mLoadMoreUIHandler) {
            mLoadMoreUIHandler.onLoading(this);
        }
        final LoadMoreContainerBase loadMoreContainerBase = this;
        if (null != mLoadMoreHandler) {
            post(new Runnable() {
                @Override
                public void run() {
                    mLoadMoreHandler.onLoadMore(loadMoreContainerBase);
                }
            });
        }
    }

    protected void onReachBottom() {
        // if has error, just leave what it should be
        if (mLoadError) {
            return;
        }
        if (mAutoLoadMore) {
            tryToPerformLoadMore();
        } else {
            if (mHasMore) {
                mLoadMoreUIHandler.onWaitToLoadMore(this);
            }
        }
    }

    @Override
    public void setShowLoadingForFirstPage(boolean showLoading) {
        mShowLoadingForFirstPage = showLoading;
    }

    @Override
    public void setAutoLoadMore(boolean autoLoadMore) {
        mAutoLoadMore = autoLoadMore;
    }

    @Override
    public void setLoadMoreView(View view) {
        // has not been initialized
        if (retrieveListView() == null) {
            mFooterView = view;
            return;
        }
        // remove previous
        if (mFooterView != null && mFooterView != view) {
            removeFooterView(view);
        }

        // add current
        mFooterView = view;
        mFooterView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToPerformLoadMore();
            }
        });

        addFooterView(view);
    }

    @Override
    public void setLoadMoreUIHandler(LoadMoreUIHandler handler) {
        mLoadMoreUIHandler = handler;
    }

    @Override
    public void setLoadMoreHandler(LoadMoreHandler handler) {
        mLoadMoreHandler = handler;
    }

    /**
     * page has loaded
     *
     * @param emptyResult
     * @param hasMore
     */
    @Override
    public void loadMoreFinish(boolean emptyResult, boolean hasMore) {
        mLoadError = false;
        mListEmpty = emptyResult;
        mIsLoading = false;
        mHasMore = hasMore;

        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadFinish(this, emptyResult, hasMore);
        }
    }

    @Override
    public void loadMoreError(int errorCode, String errorMessage) {
        mIsLoading = false;
        mLoadError = true;
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadError(this, errorCode, errorMessage);
        }
    }

    public View getFooterView() {
        return mFooterView;
    }

    public abstract void addFooterView(View view);

    public abstract void removeFooterView(View view);

    public abstract void refreshList();

    protected abstract Object retrieveListView();
}