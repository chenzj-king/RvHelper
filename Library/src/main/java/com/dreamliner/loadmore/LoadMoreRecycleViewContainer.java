package com.dreamliner.loadmore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.dreamliner.rvhelper.adapter.BaseDataAdapter;

import static com.dreamliner.rvhelper.util.LayoutManagerUtil.getLastVisibleItemPosition;


/**
 * RecyclerView also wanna endless scroll to load more data.
 */
public class LoadMoreRecycleViewContainer extends LoadMoreContainerBase {

    private RecyclerView mRecyclerView;
    private BaseDataAdapter<?, ?> mAdapter;

    protected int mItemLeftToLoadMore = 10;

    protected boolean isEnableLoadMore = false;

    public LoadMoreRecycleViewContainer(Context context) {
        super(context);
    }

    public LoadMoreRecycleViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        View view = null;
        for (int i = 0; i < getChildCount(); i++) {
            View tempView = getChildAt(i);
            if (tempView instanceof RecyclerView) {
                view = tempView;
                break;
            }
        }

        mRecyclerView = (RecyclerView) view;
        if (null == mRecyclerView) {
            return;
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (isEnableLoadMore()) {
                    RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
                    if (null == layoutManager) {
                        return;
                    }
                    int lastVisibleItemPosition = getLastVisibleItemPosition(layoutManager);
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();

                    if (((totalItemCount - lastVisibleItemPosition) <= mItemLeftToLoadMore ||
                            (totalItemCount - lastVisibleItemPosition) == 0 && totalItemCount > visibleItemCount)) {
                        onReachBottom();
                    }
                }
            }
        });
    }

    @Override
    public void addFooterView(View view) {
        if (null != mAdapter) {
            mAdapter.addFooterView(view);
        }
    }

    @Override
    public void removeFooterView(View view) {
        if (null != mAdapter) {
            mAdapter.removeFooterView(view);
        }
    }

    @Override
    public void refreshList() {
        if (null != mFooterView)
            mFooterView.setVisibility(GONE);
    }

    @Override
    protected Object retrieveListView() {
        return getRecyclerView();
    }

    public void setRecyclerViewAdapter(BaseDataAdapter<?, ?> adapter) {
        mAdapter = adapter;
    }

    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public int getItemLeftToLoadMore() {
        return mItemLeftToLoadMore;
    }

    public void setItemLeftToLoadMore(int itemLeftToLoadMore) {
        this.mItemLeftToLoadMore = itemLeftToLoadMore;
    }

    public boolean isEnableLoadMore() {
        return isEnableLoadMore;
    }

    public void setEnableLoadMore(boolean enableLoadMore) {
        isEnableLoadMore = enableLoadMore;
        if (!enableLoadMore) {
            removeFooterView(null);
        }
    }
}
