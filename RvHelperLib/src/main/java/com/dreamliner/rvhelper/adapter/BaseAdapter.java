package com.dreamliner.rvhelper.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamliner.rvhelper.interfaces.ItemClickListener;
import com.dreamliner.rvhelper.interfaces.ItemLongListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author chenzj
 * @Title: BaseAdapter
 * @Description: 类的描述 -
 * @date 2016/6/12 09:05
 * @email admin@chenzhongjin.cn
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private static final String TAG = "BaseAdapter";

    private Context mContext;

    private List<T> mDatas;
    private ItemClickListener mItemClickListener;
    private ItemLongListener mItemLongListener;

    private final Object mLock = new Object();

    public BaseAdapter() {
        mDatas = new ArrayList<>();
    }

    public BaseAdapter(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public BaseAdapter(ItemLongListener itemLongListener) {
        mItemLongListener = itemLongListener;
    }

    public BaseAdapter(ItemClickListener itemClickListener, ItemLongListener itemLongListener) {
        mItemClickListener = itemClickListener;
        mItemLongListener = itemLongListener;
    }

    public Context getContext() {
        return mContext;
    }

    protected View getView(@LayoutRes int layoutId, ViewGroup parent) {
        if (null == mContext) {
            mContext = parent.getContext();
        }
        return LayoutInflater.from(mContext).inflate(layoutId, parent, false);
    }

    @Override
    public final void onBindViewHolder(VH holder, int position) {
        bindView(holder, position);
    }

    protected abstract void bindView(VH holder, int position);


    @Override
    public int getItemCount() {
        if (null != mDatas) {
            return mDatas.size();
        } else {
            return 0;
        }
    }


    public void add(T object) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.add(object);
            }
        }
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(Collection<? extends T> collection) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.addAll(collection);
            }
        }
        notifyItemRangeInserted(getItemCount() - collection.size(), getItemCount() - 1);
    }

    @SafeVarargs
    public final void addAll(T... items) {
        synchronized (mLock) {
            if (null != mDatas) {
                Collections.addAll(mDatas, items);
            }
        }
        notifyItemRangeInserted(getItemCount() - items.length, getItemCount() - 1);
    }

    public void insert(T object, int index) {
        synchronized (mLock) {
            if (null != mDatas) {
                mDatas.add(index, object);
            }
        }
        notifyItemInserted(index);
    }

    public void remove(int index) {

        if (index > 0 && index < getItemCount()) {
            synchronized (mLock) {
                mDatas.remove(index);
            }
            notifyItemRemoved(index);
        } else {
            Log.i(TAG, "remove: index error");
        }
    }

    public void remove(T object) {
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
        }
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

    public void update(List<T> mDatas) {
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

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setItemLongListener(ItemLongListener itemLongListener) {
        mItemLongListener = itemLongListener;
    }
}

