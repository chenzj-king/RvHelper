package com.dreamliner.rvhelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.dreamliner.ptrlib.PtrClassicFrameLayout;
import com.dreamliner.ptrlib.PtrDefaultHandler;
import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.ptrlib.PtrHandler;
import com.dreamliner.rvhelper.interfaces.OnMoreListener;
import com.dreamliner.rvhelper.interfaces.OnRefreshListener;
import com.dreamliner.rvhelper.util.FloatUtil;

public class OptimumRecyclerview extends FrameLayout {

    protected int ITEM_LEFT_TO_LOAD_MORE = 10;

    protected RecyclerView mRecycler;
    protected ViewStub mProgress;
    protected ViewStub mMoreProgress;
    protected ViewStub mEmpty;
    protected View mProgressView;
    protected View mMoreProgressView;
    protected View mEmptyView;

    protected boolean mClipToPadding;
    protected int mPadding;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected int mScrollbarStyle;
    protected int mEmptyId;
    protected int mMoreProgressId;

    protected LAYOUT_MANAGER_TYPE layoutManagerType;

    protected RecyclerView.OnScrollListener mInternalOnScrollListener;
    protected RecyclerView.OnScrollListener mExternalOnScrollListener;

    protected OnRefreshListener mOnRefreshListener;
    protected OnMoreListener mOnMoreListener;
    protected boolean isLoadingMore;
    protected PtrClassicFrameLayout mPtrLayout;

    protected int mSuperRecyclerViewMainLayout;
    private int mProgressId;

    private int[] lastScrollPositions;

    //下拉刷新的头部相关信息
    private int mPtrBgColor;
    private int mDurationToClose;
    private int mDurationToCloseHeader;
    private boolean mKeepHeaderWhenREfresh;
    private boolean mPullToFresh;
    private float mRatioOfHedaerHeightToRefresh;
    private float mResistance;

    public PtrClassicFrameLayout getPtrLayout() {
        return mPtrLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecycler;
    }

    public OptimumRecyclerview(Context context) {
        super(context);
        initView();
    }

    public OptimumRecyclerview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView();
    }

    public OptimumRecyclerview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs);
        initView();
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray optimumRvArr = getContext().obtainStyledAttributes(attrs, R.styleable.superrecyclerview);
        TypedArray ptrArr = getContext().obtainStyledAttributes(attrs, R.styleable.PtrFrameLayout);

        try {
            //初始化rv相关
            mSuperRecyclerViewMainLayout = optimumRvArr.getResourceId(R.styleable.superrecyclerview_mainLayoutId, R.layout
                    .layout_progress_recyclerview);
            mClipToPadding = optimumRvArr.getBoolean(R.styleable.superrecyclerview_recyclerClipToPadding, false);
            mPadding = (int) optimumRvArr.getDimension(R.styleable.superrecyclerview_recyclerPadding, -1.0f);
            mPaddingTop = (int) optimumRvArr.getDimension(R.styleable.superrecyclerview_recyclerPaddingTop, 0.0f);
            mPaddingBottom = (int) optimumRvArr.getDimension(R.styleable.superrecyclerview_recyclerPaddingBottom, 0.0f);
            mPaddingLeft = (int) optimumRvArr.getDimension(R.styleable.superrecyclerview_recyclerPaddingLeft, 0.0f);
            mPaddingRight = (int) optimumRvArr.getDimension(R.styleable.superrecyclerview_recyclerPaddingRight, 0.0f);
            mScrollbarStyle = optimumRvArr.getInt(R.styleable.superrecyclerview_scrollbarStyle, -1);
            mEmptyId = optimumRvArr.getResourceId(R.styleable.superrecyclerview_layout_empty, 0);
            mMoreProgressId = optimumRvArr.getResourceId(R.styleable.superrecyclerview_layout_moreProgress, R.layout
                    .layout_more_progress);
            mProgressId = optimumRvArr.getResourceId(R.styleable.superrecyclerview_layout_progress, R.layout.layout_progress);

            //初始化uptr相关
            mPtrBgColor = ptrArr.getInt(R.styleable.PtrFrameLayout_ptr_bg_color, 0xf1f1f1);
            mDurationToClose = ptrArr.getInt(R.styleable.PtrFrameLayout_ptr_duration_to_close, 200);
            mDurationToCloseHeader = ptrArr.getInt(R.styleable.PtrFrameLayout_ptr_duration_to_close_header, 1000);
            mKeepHeaderWhenREfresh = ptrArr.getBoolean(R.styleable.PtrFrameLayout_ptr_duration_to_close, true);
            mPullToFresh = ptrArr.getBoolean(R.styleable.PtrFrameLayout_ptr_duration_to_close, false);
            mRatioOfHedaerHeightToRefresh = ptrArr.getFloat(R.styleable.PtrFrameLayout_ptr_ratio_of_header_height_to_refresh, 1.2f);
            mResistance = ptrArr.getFloat(R.styleable.PtrFrameLayout_ptr_resistance, 1.7f);
        } finally {
            optimumRvArr.recycle();
            ptrArr.recycle();
        }
    }

    private void initView() {
        if (isInEditMode()) {
            return;
        }
        View v = LayoutInflater.from(getContext()).inflate(mSuperRecyclerViewMainLayout, this);

        mProgress = (ViewStub) v.findViewById(android.R.id.progress);

        mProgress.setLayoutResource(mProgressId);
        mProgressView = mProgress.inflate();

        mMoreProgress = (ViewStub) v.findViewById(R.id.more_progress);
        mMoreProgress.setLayoutResource(mMoreProgressId);
        if (mMoreProgressId != 0)
            mMoreProgressView = mMoreProgress.inflate();
        mMoreProgress.setVisibility(View.GONE);

        mEmpty = (ViewStub) v.findViewById(R.id.empty);
        mEmpty.setLayoutResource(mEmptyId);
        if (mEmptyId != 0)
            mEmptyView = mEmpty.inflate();
        mEmpty.setVisibility(View.GONE);

        inituptrView(v);
        initRecyclerView(v);
    }

    private void inituptrView(View v) {
        mPtrLayout = (PtrClassicFrameLayout) v.findViewById(R.id.ptr_layout);
        mPtrLayout.setEnabled(false);

        mPtrLayout.setBackgroundColor(mPtrBgColor);
        mPtrLayout.setDurationToClose(mDurationToClose);
        mPtrLayout.setDurationToCloseHeader(mDurationToCloseHeader);
        mPtrLayout.setKeepHeaderWhenRefresh(mKeepHeaderWhenREfresh);
        mPtrLayout.setPullToRefresh(mPullToFresh);
        mPtrLayout.setRatioOfHeaderHeightToRefresh(mRatioOfHedaerHeightToRefresh);
        mPtrLayout.setResistance(mResistance);

        mPtrLayout.setLastUpdateTimeRelateObject(this);
    }

    /**
     * Implement this method to customize the AbsListView
     */
    protected void initRecyclerView(View view) {
        View recyclerView = view.findViewById(android.R.id.list);

        if (recyclerView instanceof RecyclerView)
            mRecycler = (RecyclerView) recyclerView;
        else
            throw new IllegalArgumentException("SuperRecyclerView works with a RecyclerView!");


        mRecycler.setClipToPadding(mClipToPadding);
        mInternalOnScrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                processOnMore();

                if (mExternalOnScrollListener != null)
                    mExternalOnScrollListener.onScrolled(recyclerView, dx, dy);
//                if (mSwipeDismissScrollListener != null)
//                    mSwipeDismissScrollListener.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mExternalOnScrollListener != null)
                    mExternalOnScrollListener.onScrollStateChanged(recyclerView, newState);

//                if (mSwipeDismissScrollListener != null)
//                    mSwipeDismissScrollListener.onScrollStateChanged(recyclerView, newState);
            }
        };
        mRecycler.addOnScrollListener(mInternalOnScrollListener);

        if (!FloatUtil.compareFloats(mPadding, -1.0f)) {
            mRecycler.setPadding(mPadding, mPadding, mPadding, mPadding);
        } else {
            mRecycler.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
        }

        if (mScrollbarStyle != -1) {
            mRecycler.setScrollBarStyle(mScrollbarStyle);
        }
    }

    private void processOnMore() {
        RecyclerView.LayoutManager layoutManager = mRecycler.getLayoutManager();
        int lastVisibleItemPosition = getLastVisibleItemPosition(layoutManager);
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();

        if (((totalItemCount - lastVisibleItemPosition) <= ITEM_LEFT_TO_LOAD_MORE ||
                (totalItemCount - lastVisibleItemPosition) == 0 && totalItemCount > visibleItemCount)
                && !isLoadingMore) {

            isLoadingMore = true;
            if (mOnMoreListener != null) {
                mMoreProgress.setVisibility(View.VISIBLE);
                mOnMoreListener.onMoreAsked(mRecycler.getAdapter().getItemCount(), ITEM_LEFT_TO_LOAD_MORE, lastVisibleItemPosition);
            }
        }
    }

    private int getLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int lastVisibleItemPosition = -1;
        if (layoutManagerType == null) {
            if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
            } else if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
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

    /**
     * @param adapter                       The new adapter to set, or null to set no adapter
     * @param compatibleWithPrevious        Should be set to true if new adapter uses the same {@android.support.v7.widget
     *                                      .RecyclerView.ViewHolder}
     *                                      as previous one
     * @param removeAndRecycleExistingViews If set to true, RecyclerView will recycle all existing Views. If adapters
     *                                      have stable ids and/or you want to animate the disappearing views, you may
     *                                      prefer to set this to false
     */
    private void setAdapterInternal(RecyclerView.Adapter adapter, boolean compatibleWithPrevious,
                                    boolean removeAndRecycleExistingViews) {
        if (compatibleWithPrevious)
            mRecycler.swapAdapter(adapter, removeAndRecycleExistingViews);
        else
            mRecycler.setAdapter(adapter);

        mProgress.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);

        // TODO: 2016/6/12 默认关闭下拉刷新
        //mPtrLayout.setRefreshing(false);
        mPtrLayout.refreshComplete();
        if (null != adapter)
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    super.onItemRangeChanged(positionStart, itemCount);
                    update();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    update();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    update();
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                    update();
                }

                @Override
                public void onChanged() {
                    super.onChanged();
                    update();
                }

                private void update() {
                    mProgress.setVisibility(View.GONE);
                    mMoreProgress.setVisibility(View.GONE);
                    isLoadingMore = false;
                    // TODO: 2016/6/12  数据更新之后.禁用一下刷新.
                    //mPtrLayout.setRefreshing(false);
                    mPtrLayout.refreshComplete();
                    if (mRecycler.getAdapter().getItemCount() == 0 && mEmptyId != 0) {
                        mEmpty.setVisibility(View.VISIBLE);
                    } else if (mEmptyId != 0) {
                        mEmpty.setVisibility(View.GONE);
                    }
                }
            });

        if (mEmptyId != 0) {
            mEmpty.setVisibility(null != adapter && adapter.getItemCount() > 0
                    ? View.GONE
                    : View.VISIBLE);
        }
    }

    /**
     * Set the layout manager to the recycler
     */
    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecycler.setLayoutManager(manager);
    }

    /**
     * Set the adapter to the recycler
     * Automatically hide the progressbar
     * Set the refresh to false
     * If adapter is empty, then the emptyview is shown
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        setAdapterInternal(adapter, false, true);
    }

    /**
     * @param adapter                       The new adapter to , or null to set no adapter.
     * @param removeAndRecycleExistingViews If set to true, RecyclerView will recycle all existing Views. If adapters
     *                                      have stable ids and/or you want to animate the disappearing views, you may
     *                                      prefer to set this to false.
     */
    public void swapAdapter(RecyclerView.Adapter adapter, boolean removeAndRecycleExistingViews) {
        setAdapterInternal(adapter, true, removeAndRecycleExistingViews);
    }

    /**
     * Remove the adapter from the recycler
     */
    public void clear() {
        mRecycler.setAdapter(null);
    }

    /**
     * Show the progressbar
     */
    public void showProgress() {
        hideRecycler();
        if (mEmptyId != 0) mEmpty.setVisibility(View.INVISIBLE);
        mProgress.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the progressbar and show the recycler
     */
    public void showRecycler() {
        hideProgress();
        if (mRecycler.getAdapter().getItemCount() == 0 && mEmptyId != 0) {
            mEmpty.setVisibility(View.VISIBLE);
        } else if (mEmptyId != 0) {
            mEmpty.setVisibility(View.GONE);
        }
        mRecycler.setVisibility(View.VISIBLE);
    }

    public void showMoreProgress() {
        mMoreProgress.setVisibility(View.VISIBLE);
    }

    public void hideMoreProgress() {
        mMoreProgress.setVisibility(View.GONE);
    }

    /**
     * Hide the progressbar
     */
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    /**
     * Hide the recycler
     */
    public void hideRecycler() {
        mRecycler.setVisibility(View.GONE);
    }

    /**
     * Set the scroll listener for the recycler
     */
    public void setOnScrollListener(RecyclerView.OnScrollListener listener) {
        mExternalOnScrollListener = listener;
    }

    /**
     * Add the onItemTouchListener for the recycler
     */
    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecycler.addOnItemTouchListener(listener);
    }

    /**
     * Remove the onItemTouchListener for the recycler
     */
    public void removeOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecycler.removeOnItemTouchListener(listener);
    }

    /**
     * @return the recycler adapter
     */
    public RecyclerView.Adapter getAdapter() {
        return mRecycler.getAdapter();
    }

    /**
     * Sets the onRefresh listener
     */
    public void setRefreshListener(OnRefreshListener onRefreshListener) {
        mPtrLayout.setEnabled(true);
        this.mOnRefreshListener = onRefreshListener;

        mPtrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (null != mOnRefreshListener) {
                    mOnRefreshListener.onRefresh(frame);
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    /**
     * Sets the More listener
     *
     * @param max Number of items before loading more
     */
    public void setupMoreListener(OnMoreListener onMoreListener, int max) {
        mOnMoreListener = onMoreListener;
        ITEM_LEFT_TO_LOAD_MORE = max;
    }

    public void setOnMoreListener(OnMoreListener onMoreListener) {
        mOnMoreListener = onMoreListener;
    }

    public void setNumberBeforeMoreIsCalled(int max) {
        ITEM_LEFT_TO_LOAD_MORE = max;
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    /**
     * Enable/Disable the More event
     */
    public void setLoadingMore(boolean isLoadingMore) {
        this.isLoadingMore = isLoadingMore;
    }

    /**
     * Remove the moreListener
     */
    public void removeMoreListener() {
        mOnMoreListener = null;
    }


    public void setOnTouchListener(OnTouchListener listener) {
        mRecycler.setOnTouchListener(listener);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecycler.addItemDecoration(itemDecoration);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int index) {
        mRecycler.addItemDecoration(itemDecoration, index);
    }

    public void removeItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecycler.removeItemDecoration(itemDecoration);
    }

    /**
     * @return inflated progress view or null
     */
    public View getProgressView() {
        return mProgressView;
    }

    /**
     * @return inflated more progress view or null
     */
    public View getMoreProgressView() {
        return mMoreProgressView;
    }

    /**
     * @return inflated empty view or null
     */
    public View getEmptyView() {
        return mEmptyView;
    }

    /**
     * Animate a scroll by the given amount of pixels along either axis.
     *
     * @param dx Pixels to scroll horizontally
     * @param dy Pixels to scroll vertically
     */
    public void smoothScrollBy(int dx, int dy) {
        mRecycler.smoothScrollBy(dx, dy);
    }

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }
}
