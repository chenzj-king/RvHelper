package com.dreamliner.loadmore;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.dreamliner.rvhelper.adapter.BaseDataAdapter;

import static com.dreamliner.loadmore.LoadMoreRecycleViewContainer.LAYOUT_MANAGER_TYPE.GRID;
import static com.dreamliner.loadmore.LoadMoreRecycleViewContainer.LAYOUT_MANAGER_TYPE.STAGGERED_GRID;


/**
 * RecyclerView also wanna endless scroll to load more data.
 */
public class LoadMoreRecycleViewContainer extends LoadMoreContainerBase {

    private RecyclerView mRecyclerView;
    private BaseDataAdapter<?, ?> mAdapter;

    // for memory profile
    private LinearLayoutManager mLinearLayoutManager;
    protected LAYOUT_MANAGER_TYPE layoutManagerType;

    protected int ITEM_LEFT_TO_LOAD_MORE = 10;
    private int[] lastScrollPositions;
    protected boolean isLoadingMore;

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

            // for memory profile
            int mFirstVisibleItem, mVisibleItemCount, mTotalItemCount;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // TODO: 2016/9/21 应该区分是不同的LayOutManager来计算第一个显示的item和最后一个显示的item
                getLinearLayoutManager();
                if (mLinearLayoutManager == null) {
                    return;
                }

                mVisibleItemCount = recyclerView.getChildCount();
                mTotalItemCount = mLinearLayoutManager.getItemCount();
                mFirstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

                if ((mTotalItemCount - mVisibleItemCount) <= (mFirstVisibleItem + mVisibleThreshold)) {
                    onReachBottom();
                }
            }
        });
    }

    private void processOnMore() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        int lastVisibleItemPosition = getLastVisibleItemPosition(layoutManager);
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();

        if (((totalItemCount - lastVisibleItemPosition) <= ITEM_LEFT_TO_LOAD_MORE ||
                (totalItemCount - lastVisibleItemPosition) == 0 && totalItemCount > visibleItemCount)
                && !isLoadingMore) {

            isLoadingMore = true;

            /*
            if (mOnMoreListener != null) {
                mOnMoreListener.onMoreAsked(mRecyclerView.getAdapter().getItemCount(), ITEM_LEFT_TO_LOAD_MORE,
                lastVisibleItemPosition);
            }*/
        }
    }

    private int getLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int lastVisibleItemPosition = -1;
        if (layoutManagerType == null) {
            if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = GRID;
            } else if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = STAGGERED_GRID;
            } else {
                throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager " +
                        "and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LINEAR:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                lastVisibleItemPosition = caseStaggeredGrid(layoutManager);
                break;
        }
        return lastVisibleItemPosition;
    }

    private int caseStaggeredGrid(RecyclerView.LayoutManager layoutManager) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        if (lastScrollPositions == null)
            lastScrollPositions = new int[staggeredGridLayoutManager.getSpanCount()];

        staggeredGridLayoutManager.findLastVisibleItemPositions(lastScrollPositions);
        return findMax(lastScrollPositions);
    }


    private int findMax(int[] lastPositions) {
        int max = Integer.MIN_VALUE;
        for (int value : lastPositions) {
            if (value > max)
                max = value;
        }
        return max;
    }

    @Override
    protected void addFooterView(View view) {
        mAdapter.addFooterView(view);
    }

    @Override
    protected void removeFooterView(View view) {
        mAdapter.removeFooterView(view);
    }

    @Override
    protected Object retrieveListView() {
        return getRecyclerView();
    }

    protected LinearLayoutManager getLinearLayoutManager() {
        if (mLinearLayoutManager != null) {
            return mLinearLayoutManager;
        }
        if (mRecyclerView == null) {
            return null;
        }
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            mLinearLayoutManager = (LinearLayoutManager) layoutManager;
            return mLinearLayoutManager;
        }
        return null;
    }

    public void setRecyclerViewAdapter(BaseDataAdapter<?, ?> adapter) {
        mAdapter = adapter;
    }

    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public int getItemLeftToLoadMore() {
        return ITEM_LEFT_TO_LOAD_MORE;
    }

    public void setItemLeftToLoadMore(int itemLeftToLoadMore) {
        this.ITEM_LEFT_TO_LOAD_MORE = itemLeftToLoadMore;
    }

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }
}
