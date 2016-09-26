package com.dreamliner.rvhelper.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamliner.rvhelper.interfaces.ItemClickListener;
import com.dreamliner.rvhelper.interfaces.ItemLongListener;
import com.dreamliner.rvhelper.viewholder.BaseViewHolder;
import com.dreamliner.rvhelper.viewholder.FooterViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author chenzj
 * @Title: BaseDataAdapter
 * @Description: 类的描述 - 专门用于处理数据用的Adapter.从这边开始扩展
 * @date 2016/6/12 09:05
 * @email admin@chenzhongjin.cn
 */
public abstract class BaseDataAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String TAG = "BaseDataAdapter";

    private Context mContext;

    private List<T> mDatas;
    private ItemClickListener mItemClickListener;
    private ItemLongListener mItemLongListener;

    protected int FOOTER_TPYE = Integer.MAX_VALUE;
    private View mFooterView;

    private final Object mLock = new Object();

    public BaseDataAdapter() {
        super();
        mDatas = new ArrayList<>();
    }

    public BaseDataAdapter(ItemClickListener itemClickListener) {
        this();
        mItemClickListener = itemClickListener;
    }

    public BaseDataAdapter(ItemLongListener itemLongListener) {
        this();
        mItemLongListener = itemLongListener;
    }

    public BaseDataAdapter(ItemClickListener itemClickListener, ItemLongListener itemLongListener) {
        this();
        mItemClickListener = itemClickListener;
        mItemLongListener = itemLongListener;
    }

    public Context getContext() {
        return mContext;
    }

    public void addFooterView(View view) {
        mFooterView = view;
        // TODO: 2016/9/25 addFooterView;
    }

    public void removeFooterView(View view) {
        mFooterView = null;
        // TODO: 2016/9/25 removeFooterView;
    }

    protected View getView(@LayoutRes int layoutId, ViewGroup parent) {
        if (null == mContext) {
            mContext = parent.getContext();
        }
        return LayoutInflater.from(mContext).inflate(layoutId, parent, false);
    }

    @Override
    public int getItemViewType(int position) {
        if (null != mFooterView && position == getItemCount() - 1) {
            return FOOTER_TPYE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_TPYE) {
            return new FooterViewHolder(mFooterView);
        } else {
            return createCustomViewHolder(parent, viewType);
        }
    }

    public abstract VH createCustomViewHolder(ViewGroup parent, int viewType);

    protected abstract void bindView(VH holder, int position);

    @Override
    public int getItemCount() {
        if (null != mDatas) {
            return mDatas.size() + (null != mFooterView ? 1 : 0);
        } else {
            return 0;
        }
    }

    public void add(@NonNull T object) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.add(object);
            }
        }
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(@NonNull Collection<? extends T> collection) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.addAll(collection);
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
            if (null != mDatas) {
                Collections.addAll(mDatas, items);
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
            if (null != mDatas) {
                mDatas.add(index, object);
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
            if (null != mDatas) {
                mDatas.addAll(index, collection);
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
            mDatas.remove(index);
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
            if (mDatas != null) {
                removeSuccess = mDatas.remove(object);
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
            if (mDatas != null) {
                mDatas.clear();
            }
        }
        notifyDataSetChanged();
    }

    public void sort(Comparator<? super T> comparator) {
        synchronized (mLock) {
            if (mDatas != null) {
                Collections.sort(mDatas, comparator);
            }
        }
        notifyDataSetChanged();
    }

    public void update(@NonNull List<T> mDatas) {
        synchronized (mLock) {
            this.mDatas = mDatas;
        }
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    public int getPosition(T item) {
        return mDatas.indexOf(item);
    }

    public List<T> getData() {
        if (null != mDatas) {
            return mDatas;
        } else {
            return new ArrayList<>();
        }
    }

    public ItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public ItemLongListener getItemLongListener() {
        return mItemLongListener;
    }

    public void setItemLongListener(ItemLongListener itemLongListener) {
        mItemLongListener = itemLongListener;
    }
}
