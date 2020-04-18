package com.dreamliner.rvhelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.dreamliner.lib.rvhelper.R;
import com.dreamliner.loadmore.LoadMoreHandler;
import com.dreamliner.loadmore.LoadMoreRecycleViewContainer;
import com.dreamliner.loadmore.LoadMoreUIHandler;
import com.dreamliner.ptrlib.PtrClassicFrameLayout;
import com.dreamliner.ptrlib.PtrDefaultHandler;
import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.ptrlib.PtrHandler;
import com.dreamliner.ptrlib.PtrUIHandler;
import com.dreamliner.ptrlib.util.PtrCLog;
import com.dreamliner.rvhelper.adapter.BaseDataAdapter;
import com.dreamliner.rvhelper.empty.EmptyLayout;
import com.dreamliner.rvhelper.interfaces.OnRefreshListener;
import com.dreamliner.rvhelper.loading.LoadingLayout;
import com.dreamliner.rvhelper.util.FloatUtil;

import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.dreamliner.rvhelper.util.StatusConstant.NET_ERROR;
import static com.dreamliner.rvhelper.util.StatusConstant.NO_RESULT;

public class OptimumRecyclerView extends FrameLayout {

    private static final String TAG = "OptimumRecyclerView";

    private static boolean isDebug = true;
    //主界面
    private PtrClassicFrameLayout mPtrLayout;
    private LoadMoreRecycleViewContainer mLoadMoreContainer;
    private RecyclerView mRecyclerView;

    //下拉刷新回调
    private OnRefreshListener mOnRefreshListener;
    private LoadMoreHandler mLoadMoreHandler;

    //加载中页面
    private boolean isLoading = false;
    private ViewStub mLoadingViewStub;
    private LoadingLayout mLoadingLayout;

    //空白页面
    private int mEmptyType = -1;
    private ViewStub mEmptyViewStub;
    private EmptyLayout mEmptyLayout;

    private int mEmptyId;
    private int mLoadingId;
    private boolean mClipToPadding;
    private int mPadding;
    private int mPaddingTop;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mScrollbarStyle;

    private boolean mLoadingSwitch;
    private boolean mEmptySwitch;

    //下拉刷新的头部相关信息
    private int mPtrBgColor;
    private int mDurationToClose;
    private int mDurationToCloseHeader;
    private boolean mKeepHeaderWhenRefresh;
    private boolean mPullToFresh;
    private float mRatioOfHeaderHeightToRefresh;
    private float mResistance;

    public PtrClassicFrameLayout getPtrLayout() {
        return mPtrLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public OptimumRecyclerView(Context context) {
        super(context);
        initView();
    }

    public OptimumRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView();
    }

    public OptimumRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs);
        initView();
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray optimumRvArr = getContext().obtainStyledAttributes(attrs, R.styleable.OptimumRecyclerView);
        TypedArray ptrArr = getContext().obtainStyledAttributes(attrs, R.styleable.PtrFrameLayout);

        try {
            //初始化rv相关
            mEmptyId = optimumRvArr.getResourceId(R.styleable.OptimumRecyclerView_rvhelp_layout_empty, R.layout.layout_default_empty);
            mLoadingId = optimumRvArr.getResourceId(R.styleable.OptimumRecyclerView_rvhelp_layout_loading,
                    R.layout.layout_default_loading);

            mClipToPadding = optimumRvArr.getBoolean(R.styleable.OptimumRecyclerView_rvhelp_recyclerClipToPadding, false);
            mPadding = (int) optimumRvArr.getDimension(R.styleable.OptimumRecyclerView_rvhelp_recyclerPadding, -1.0f);
            mPaddingTop = (int) optimumRvArr.getDimension(R.styleable.OptimumRecyclerView_rvhelp_recyclerPaddingTop, 0.0f);
            mPaddingBottom = (int) optimumRvArr.getDimension(R.styleable.OptimumRecyclerView_rvhelp_recyclerPaddingBottom, 0.0f);
            mPaddingLeft = (int) optimumRvArr.getDimension(R.styleable.OptimumRecyclerView_rvhelp_recyclerPaddingLeft, 0.0f);
            mPaddingRight = (int) optimumRvArr.getDimension(R.styleable.OptimumRecyclerView_rvhelp_recyclerPaddingRight, 0.0f);
            mScrollbarStyle = optimumRvArr.getInt(R.styleable.OptimumRecyclerView_rvhelp_scrollbarStyle, -1);

            mLoadingSwitch = optimumRvArr.getBoolean(R.styleable.OptimumRecyclerView_rvhelp_loading, true);
            mEmptySwitch = optimumRvArr.getBoolean(R.styleable.OptimumRecyclerView_rvhelp_empty, true);

            //初始化uptr相关
            mPtrBgColor = ptrArr.getInt(R.styleable.PtrFrameLayout_ptr_bg_color, 0xf1f1f1);
            mDurationToClose = ptrArr.getInt(R.styleable.PtrFrameLayout_ptr_duration_to_close, 200);
            mDurationToCloseHeader = ptrArr.getInt(R.styleable.PtrFrameLayout_ptr_duration_to_close_header, 1000);
            mKeepHeaderWhenRefresh = ptrArr.getBoolean(R.styleable.PtrFrameLayout_ptr_duration_to_close, true);
            mPullToFresh = ptrArr.getBoolean(R.styleable.PtrFrameLayout_ptr_duration_to_close, false);
            mRatioOfHeaderHeightToRefresh = ptrArr.getFloat(R.styleable.PtrFrameLayout_ptr_ratio_of_header_height_to_refresh, 1.2f);
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
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_rvhelper, this);

        //初始化加载中界面
        mLoadingViewStub = (ViewStub) v.findViewById(R.id.loading_view_stub);
        mLoadingViewStub.setLayoutResource(mLoadingId);
        if (0 != mLoadingId) {
            View loadingView = mLoadingViewStub.inflate();
            if (loadingView instanceof LoadingLayout) {
                mLoadingLayout = (LoadingLayout) loadingView;
            }
        }

        //初始化空白页面界面
        mEmptyViewStub = (ViewStub) v.findViewById(R.id.empty_view_stub);
        mEmptyViewStub.setLayoutResource(mEmptyId);
        if (0 != mEmptyId) {
            View emptyView = mEmptyViewStub.inflate();
            if (emptyView instanceof EmptyLayout) {
                mEmptyLayout = (EmptyLayout) emptyView;
            }
        }

        initPtrView(v);
        initRecyclerView(v);
        initLoadMoreView(v);

        //默认先显示加载中界面
        if (mLoadingSwitch) {
            showLoadingView();
        }
    }

    private void initPtrView(View v) {
        mPtrLayout = (PtrClassicFrameLayout) v.findViewById(R.id.ptr_layout);
        mPtrLayout.setEnabled(false);
        mPtrLayout.setBackgroundColor(mPtrBgColor);
        mPtrLayout.setDurationToClose(mDurationToClose);
        mPtrLayout.setDurationToCloseHeader(mDurationToCloseHeader);
        mPtrLayout.setKeepHeaderWhenRefresh(mKeepHeaderWhenRefresh);
        mPtrLayout.setPullToRefresh(mPullToFresh);
        mPtrLayout.setRatioOfHeaderHeightToRefresh(mRatioOfHeaderHeightToRefresh);
        mPtrLayout.setResistance(mResistance);

        mPtrLayout.setLastUpdateTimeRelateObject(getContext());
    }

    /**
     * Implement this method to customize the AbsListView
     */
    protected void initRecyclerView(View view) {
        View recyclerView = view.findViewById(android.R.id.list);

        if (recyclerView instanceof RecyclerView)
            mRecyclerView = (RecyclerView) recyclerView;
        else
            throw new IllegalArgumentException("OptimumRecyclerview works with a RecyclerView!");

        mRecyclerView.setClipToPadding(mClipToPadding);

        if (!FloatUtil.compareFloats(mPadding, -1.0f)) {
            mRecyclerView.setPadding(mPadding, mPadding, mPadding, mPadding);
        } else {
            mRecyclerView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
        }
        if (mScrollbarStyle != -1) {
            mRecyclerView.setScrollBarStyle(mScrollbarStyle);
        }
    }

    private void initLoadMoreView(View v) {
        mLoadMoreContainer = (LoadMoreRecycleViewContainer) v.findViewById(R.id.load_more_container);
        mLoadMoreContainer.setEnableLoadMore(false);
    }

    public static void setDebug(boolean debug) {
        isDebug = debug;
    }

    /**
     * Set the layout manager to the recycler
     */
    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

    /**
     * Set the adapter to the recyclerview
     */
    public void setAdapter(RecyclerView.Adapter adapter) {

        if (null != adapter) {
            mRecyclerView.setAdapter(adapter);
            adapter.registerAdapterDataObserver(
                    new RecyclerView.AdapterDataObserver() {
                        @Override
                        public void onChanged() {
                            super.onChanged();
                            loadEmptyView();
                        }

                        @Override
                        public void onItemRangeChanged(int positionStart, int itemCount) {
                            super.onItemRangeChanged(positionStart, itemCount);
                            loadEmptyView();
                        }

                        @Override
                        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                            super.onItemRangeChanged(positionStart, itemCount, payload);
                            loadEmptyView();
                        }

                        @Override
                        public void onItemRangeInserted(int positionStart, int itemCount) {
                            super.onItemRangeInserted(positionStart, itemCount);
                            loadEmptyView();
                        }

                        @Override
                        public void onItemRangeRemoved(int positionStart, int itemCount) {
                            super.onItemRangeRemoved(positionStart, itemCount);
                            loadEmptyView();
                        }

                        @Override
                        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                            loadEmptyView();
                        }

                        private void loadEmptyView() {
                            PtrCLog.i(TAG, "try loadEmptyView and hide the hideProgress");
                            hideLoadingView();
                            mPtrLayout.refreshComplete();
                            if (null != mEmptyLayout) {
                                if (getAdapter().getItemCount() == 0 && mEmptySwitch) {
                                    showEmptyView();
                                } else {
                                    hideEmptyView();
                                }
                            }
                        }
                    }
            );
        }
    }

    /**
     * Add the onItemTouchListener for the recycler
     */

    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecyclerView.addOnItemTouchListener(listener);
    }

    /**
     * Remove the onItemTouchListener for the recycler
     */
    public void removeOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecyclerView.removeOnItemTouchListener(listener);
    }

    /**
     * @return the recyclerview adapter
     */
    public RecyclerView.Adapter getAdapter() {
        return mRecyclerView.getAdapter();
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mRecyclerView.getLayoutManager();
    }

    /**
     * Set the onRefresh listener
     */
    public void setRefreshListener(OnRefreshListener onRefreshListener) {
        mPtrLayout.setEnabled(true);
        mOnRefreshListener = onRefreshListener;
        mPtrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (null != mOnRefreshListener) {
                    mOnRefreshListener.onRefresh(frame);
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return !isLoading && PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }
        });
    }

    public void setRefreshListener(OnRefreshListener onRefreshListener, @NonNull View headerView) {
        setRefreshListener(onRefreshListener);
        setHeaderView(headerView);
    }

    private void setHeaderView(@NonNull View headerView) {
        if (!(headerView instanceof PtrUIHandler)) {
            throw new RuntimeException("headerView must implements PtrUIHandler");
        }
        mPtrLayout.setEnabled(true);
        mPtrLayout.setHeaderView(headerView);
        mPtrLayout.addPtrUIHandler((PtrUIHandler) headerView);
    }

    public OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public void refreshComplete() {
        mPtrLayout.refreshComplete();
    }

    public LoadMoreHandler getLoadMoreHandler() {
        return mLoadMoreHandler;
    }

    public LoadMoreRecycleViewContainer getLoadMoreContainer() {
        return mLoadMoreContainer;
    }

    public void setLoadMoreContainer(LoadMoreRecycleViewContainer loadMoreContainer) {
        mLoadMoreContainer = loadMoreContainer;
    }

    public void setLoadMoreHandler(LoadMoreHandler loadMoreHandler) {
        mLoadMoreHandler = loadMoreHandler;
        if (null == mLoadMoreContainer.getFooterView()) {
            setDefaultLoadMoreHandler(loadMoreHandler);
        } else {
            mLoadMoreContainer.setLoadMoreHandler(mLoadMoreHandler);
        }
    }

    public void setDefaultLoadMoreHandler(LoadMoreHandler loadMoreHandler) {
        mLoadMoreHandler = loadMoreHandler;

        //配置loadmore
        mLoadMoreContainer.setEnableLoadMore(true);
        if (null == mRecyclerView.getAdapter()) {
            throw new NullPointerException("loadMoreContainer must init after set recyclerView adapter");
        }
        mLoadMoreContainer.setRecyclerViewAdapter(mRecyclerView.getAdapter());
        mLoadMoreContainer.useDefaultFooter();
        mLoadMoreContainer.setAutoLoadMore(true);
        mLoadMoreContainer.setShowLoadingForFirstPage(true);
        mLoadMoreContainer.setLoadMoreHandler(mLoadMoreHandler);
    }

    public void setLoadMoreHandler(LoadMoreHandler loadMoreHandler, View loadmoreView) {
        if (!(loadmoreView instanceof LoadMoreUIHandler)) {
            throw new RuntimeException("loadMoreView must implements LoadMoreUIHandler");
        }

        mLoadMoreHandler = loadMoreHandler;
        //配置loadmore
        mLoadMoreContainer.setEnableLoadMore(true);
        if (null == mRecyclerView.getAdapter()) {
            throw new NullPointerException("loadMoreContainer must init after set recyclerView adapter");
        }
        mLoadMoreContainer.setRecyclerViewAdapter(mRecyclerView.getAdapter());

        mLoadMoreContainer.setLoadMoreView(loadmoreView);
        mLoadMoreContainer.setLoadMoreUIHandler((LoadMoreUIHandler) loadmoreView);

        mLoadMoreContainer.setAutoLoadMore(true);
        mLoadMoreContainer.setShowLoadingForFirstPage(true);
        mLoadMoreContainer.setLoadMoreHandler(mLoadMoreHandler);
    }

    public void setNumberBeforeMoreIsCalled(int max) {
        mLoadMoreContainer.setItemLeftToLoadMore(max);
    }

    public void loadMoreFinish(boolean emptyResult, boolean hasMore) {
        mLoadMoreContainer.loadMoreFinish(emptyResult, hasMore);
    }

    public void loadMoreError(int errorCode, String errorMessage) {
        mLoadMoreContainer.loadMoreError(errorCode, errorMessage);
    }

    public void setOnTouchListener(OnTouchListener listener) {
        mRecyclerView.setOnTouchListener(listener);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int index) {
        mRecyclerView.addItemDecoration(itemDecoration, index);
    }

    public void removeItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.removeItemDecoration(itemDecoration);
    }

    public void showLoadingView() {
        isLoading = true;
        mLoadingViewStub.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mEmptyViewStub.setVisibility(View.GONE);
        doDefaultLoadingView(true);
    }

    public void hideLoadingView() {
        isLoading = false;
        mLoadingViewStub.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmptyViewStub.setVisibility(View.VISIBLE);
        doDefaultLoadingView(false);
    }

    private void doDefaultLoadingView(boolean isLoading) {
        if (null == mLoadingLayout) {
            return;
        }
        if (isLoading) {
            mLoadingLayout.onShowLoading();
        } else {
            mLoadingLayout.onHideLoading();
        }
    }

    public void setEmptyType(int type) {
        if (null == mEmptyLayout) {
            return;
        }
        mEmptyType = type;
        mEmptyLayout.setEmptyType(type);
    }

    public int getEmptyType() {
        return mEmptyType;
    }

    public void showEmptyView() {
        mRecyclerView.setVisibility(View.GONE);
        mEmptyViewStub.setVisibility(View.VISIBLE);
    }

    public void hideEmptyView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmptyViewStub.setVisibility(View.GONE);
    }

    /**
     * @return inflated progress view or null
     */
    public LoadingLayout getLoadingLayout() {
        return mLoadingLayout;
    }

    /**
     * @return inflated empty view or null
     */
    public EmptyLayout getEmptyLayout() {
        return mEmptyLayout;
    }

    public void move(int n) {
        if (n < 0 || n >= getAdapter().getItemCount()) {
            PtrCLog.e(TAG, "move: index error");
            return;
        }
        mRecyclerView.stopScroll();
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getRecyclerView().getLayoutManager();
        linearLayoutManager.scrollToPositionWithOffset(n, 0);
    }

    public void setEmptyOnClick(OnClickListener emptyOnClick) {
        if (null != mEmptyLayout) {
            mEmptyLayout.setOnClickListener(emptyOnClick);
        }
    }

    private boolean isValidate(Collection<?> collection) {
        return collection != null && collection.size() > 0;
    }

    private <T> BaseDataAdapter<T, RecyclerView.ViewHolder> getBaseDataAdapter() {
        if (null != getRecyclerView().getAdapter() && getRecyclerView().getAdapter() instanceof BaseDataAdapter) {
            return (BaseDataAdapter<T, RecyclerView.ViewHolder>) getRecyclerView().getAdapter();
        } else {
            throw new NullPointerException("you must set adapter");
        }
    }

    public <T> void loadSuccess(List<T> dataList) {
        loadSuccess(dataList, NO_RESULT);
    }

    public <T> void loadSuccess(List<T> dataList, int emptyType) {
        if (isValidate(dataList)) {
            BaseDataAdapter<T, RecyclerView.ViewHolder> baseDataAdapter = getBaseDataAdapter();
            baseDataAdapter.update(dataList);
        } else {
            setEmptyType(emptyType);
            getBaseDataAdapter().clear();
        }
    }

    public <T> boolean loadSuccess(boolean isFirst, List<T> dataList, int total) {
        return loadSuccess(isFirst, dataList, -1, total, NO_RESULT);
    }

    public <T> boolean loadSuccess(boolean isFirst, List<T> dataList, int total, int emptyType) {
        return loadSuccess(isFirst, dataList, -1, total, emptyType);
    }

    public <T> boolean loadSuccess(boolean isFirst, List<T> dataList, int viewCount, int total, int emptyType) {
        boolean hasNext = false;
        BaseDataAdapter<T, RecyclerView.ViewHolder> baseDataAdapter = getBaseDataAdapter();
        if (isFirst) {
            if (isValidate(dataList)) {
                baseDataAdapter.update(dataList);
            } else {
                setEmptyType(emptyType);
                baseDataAdapter.clear();
            }
        } else {
            if (isValidate(dataList)) {
                baseDataAdapter.addAll(dataList);
            } else {
                loadMoreFinish(false, true);
            }
        }
        if (viewCount == -1) {
            //兼容没有head的那种场景
            viewCount = baseDataAdapter.getData().size();
        }
        if (viewCount >= total) {
            loadMoreFinish(false, false);
            baseDataAdapter.notifyDataSetChanged();
        } else {
            hasNext = true;
            loadMoreFinish(false, true);
        }
        return hasNext;
    }

    public <T> boolean loadPageSuccess(boolean isFirst, List<T> dataList, int page, int maxPage) {
        return loadPageSuccess(isFirst, dataList, page, maxPage, NO_RESULT);
    }

    public <T> boolean loadPageSuccess(boolean isFirst, List<T> dataList, int page, int maxPage, int emptyType) {
        boolean hasNext = false;
        BaseDataAdapter<T, RecyclerView.ViewHolder> baseDataAdapter = getBaseDataAdapter();
        if (isFirst) {
            if (isValidate(dataList)) {
                baseDataAdapter.update(dataList);
            } else {
                setEmptyType(emptyType);
                baseDataAdapter.clear();
            }
        } else {
            if (isValidate(dataList)) {
                baseDataAdapter.addAll(dataList);
            } else {
                loadMoreFinish(false, true);
            }
        }
        if (page >= maxPage) {
            loadMoreFinish(false, false);
            baseDataAdapter.notifyDataSetChanged();
        } else {
            hasNext = true;
            loadMoreFinish(false, true);
        }
        return hasNext;
    }

    public void loadFail() {
        loadFail(NET_ERROR);
    }

    public void loadFail(int emptyType) {
        loadFail(true, emptyType);
    }

    public void loadFail(boolean isFirst, int emptyType) {
        if (isFirst) {
            setEmptyType(emptyType);
            getBaseDataAdapter().clear();
        } else {
            loadMoreError(-1, "");
        }
    }
}
