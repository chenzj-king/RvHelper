package com.dreamliner.rvhelper.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.dreamliner.rvhelper.BR;
import com.dreamliner.rvhelper.interfaces.OnItemClickListener;
import com.dreamliner.rvhelper.interfaces.OnItemLongClickListener;
import com.dreamliner.rvhelper.viewholder.BaseBindViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.dreamliner.rvhelper.adapter.BaseDataAdapter.FOOTER_TYPE;

/**
 * Created by chenzj on 2017/3/15.
 */

public abstract class BaseDataDBAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ViewGroup mViewGroup;

    private List<T> mDataList;
    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnItemLongClickListener;
    private Decorator mDecorator;

    private View mFooterView;
    protected int mSpanCount = 1;
    private GridLayoutManager.SpanSizeLookup mGridSpanSizeLookup;
    private GridLayoutManager mGridLayoutManager;

    private final Object mLock = new Object();

    public BaseDataDBAdapter() {
        super();
        mDataList = new ArrayList<>();
    }

    public BaseDataDBAdapter(OnItemClickListener<T> onItemClickListener) {
        this();
        mOnItemClickListener = onItemClickListener;
    }

    public BaseDataDBAdapter(OnItemLongClickListener<T> onItemLongClickListener) {
        this();
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public BaseDataDBAdapter(OnItemClickListener<T> onItemClickListener, OnItemLongClickListener<T> onItemLongClickListener) {
        this();
        mOnItemClickListener = onItemClickListener;
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public Context getContext() {
        return mContext;
    }

    public void addFooterView(View view) {
        mFooterView = view;
    }

    public void removeFooterView(View view) {
        mFooterView = null;
    }

    public GridLayoutManager.SpanSizeLookup getGridSpanSizeLookup() {
        return new GridSpanSizeLookup();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            mGridLayoutManager = ((GridLayoutManager) layoutManager);
            if (mGridSpanSizeLookup == null) {
                mSpanCount = mGridLayoutManager.getSpanCount();
                mGridSpanSizeLookup = getGridSpanSizeLookup();
            }
            mGridLayoutManager.setSpanSizeLookup(mGridSpanSizeLookup);
        }
    }

    @Override
    public int getItemCount() {
        if (null != mDataList) {
            int dataSize = mDataList.size();
            return dataSize == 0 ? 0 : dataSize + (null != mFooterView ? 1 : 0);
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (null != mFooterView && position == getItemCount() - 1) {
            return FOOTER_TYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == mViewGroup) {
            mViewGroup = parent;
        }
        if (null == mContext) {
            mContext = parent.getContext();
        }
        switch (viewType) {
            case FOOTER_TYPE:
                return new RecyclerView.ViewHolder(mFooterView) {
                };
            default:
                return createDBViewHolder(parent, viewType);
        }
    }

    protected abstract BaseBindViewHolder createDBViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = holder.getItemViewType();
        switch (itemViewType) {
            case FOOTER_TYPE:
                //不需要进行操作
                break;
            default:
                final Object item = getItem(position);
                if (holder instanceof BaseBindViewHolder) {
                    BaseBindViewHolder baseBindViewHolder = (BaseBindViewHolder) holder;
                    baseBindViewHolder.getBinding().setVariable(BR.item, item);
                    baseBindViewHolder.getBinding().setVariable(BR.itemClick, getOnItemClickListener());
                    baseBindViewHolder.getBinding().setVariable(BR.itemLongClick, getOnItemLongClickListener());
                    baseBindViewHolder.getBinding().setVariable(BR.position, position);
                    baseBindViewHolder.getBinding().executePendingBindings();
                    if (null != mDecorator) {
                        mDecorator.decorator(baseBindViewHolder, position, itemViewType);
                    }
                    break;
                }
        }
    }

    /******************
     * DataList相关操作
     *****************/
    public void add(@NonNull T object) {
        synchronized (mLock) {
            if (null != mDataList) {
                mDataList.add(object);
            }
        }
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(@NonNull Collection<? extends T> collection) {
        synchronized (mLock) {
            if (null != mDataList) {
                mDataList.addAll(collection);
            }
        }
        if (getItemCount() - collection.size() != 0) {
            notifyItemRangeInserted(getItemCount() - collection.size(), collection.size());
        } else {
            notifyDataSetChanged();
        }
    }

    @SafeVarargs
    public final void addAll(@NonNull T... items) {
        synchronized (mLock) {
            if (null != mDataList) {
                Collections.addAll(mDataList, items);
            }
        }
        if (getItemCount() - items.length != 0) {
            notifyItemRangeInserted(getItemCount() - items.length, items.length);
        } else {
            notifyDataSetChanged();
        }
    }

    public void insert(@NonNull T object, int index) {
        if (index < 0 || index > getItemCount()) {
            Log.i(TAG, "insert: index error");
            return;
        }
        synchronized (mLock) {
            if (null != mDataList) {
                mDataList.add(index, object);
            }
        }
        notifyItemInserted(index);
    }

    public void insertAll(@NonNull Collection<? extends T> collection, int index) {
        if (index < 0 || index > getItemCount()) {
            Log.i(TAG, "insertAll: index error");
            return;
        }
        synchronized (mLock) {
            if (null != mDataList) {
                mDataList.addAll(index, collection);
            }
        }
        notifyItemRangeInserted(index, collection.size());
    }

    public void remove(int index) {

        if (index < 0 || index >= getItemCount()) {
            Log.i(TAG, "remove: index error");
            return;
        }
        synchronized (mLock) {
            mDataList.remove(index);
        }
        notifyItemRemoved(index);
    }

    public boolean remove(@NonNull T object) {
        int removeIndex = -1;
        boolean removeSuccess = false;
        synchronized (mLock) {
            for (int index = 0; index < getItemCount(); index++) {
                if (object.equals(getItem(index))) {
                    removeIndex = index;
                }
            }
            if (mDataList != null) {
                removeSuccess = mDataList.remove(object);
            }
        }
        if (removeSuccess) {
            notifyItemRemoved(removeIndex);
            return true;
        }
        return false;
    }

    public void clear() {
        synchronized (mLock) {
            if (mDataList != null) {
                mDataList.clear();
            }
        }
        notifyDataSetChanged();
    }

    public void sort(Comparator<? super T> comparator) {
        synchronized (mLock) {
            if (mDataList != null) {
                Collections.sort(mDataList, comparator);
            }
        }
        notifyDataSetChanged();
    }

    public void update(@NonNull List<T> mDatas) {
        synchronized (mLock) {
            this.mDataList = mDatas;
        }
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mDataList.get(position);
    }

    public int getPosition(T item) {
        return mDataList.indexOf(item);
    }

    public List<T> getData() {
        if (null != mDataList) {
            return mDataList;
        } else {
            return new ArrayList<>();
        }
    }

    /******************
     * click等相关操作
     *****************/
    public OnItemClickListener<T> getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public OnItemLongClickListener<T> getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    public void setDecorator(Decorator decorator) {
        mDecorator = decorator;
    }

    /******************
     * GridSpan默认实现
     *****************/
    class GridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            if (position == getItemCount() - 1 && mFooterView != null) {
                return mSpanCount;
            } else {
                return 1;
            }
        }
    }

    /******************
     * 扩展的自定义设置
     *****************/
    public interface Decorator {
        void decorator(BaseBindViewHolder holder, int position, int viewType);
    }
}
