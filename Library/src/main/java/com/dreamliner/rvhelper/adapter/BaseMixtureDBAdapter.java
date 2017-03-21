package com.dreamliner.rvhelper.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dreamliner.rvhelper.R;
import com.dreamliner.rvhelper.interfaces.OnItemClickListener;
import com.dreamliner.rvhelper.interfaces.OnItemLongClickListener;
import com.dreamliner.rvhelper.viewholder.BaseBindViewHolder;

/**
 * @author chenzj
 * @Title: BaseAdapter
 * @Description: 类的描述 -
 * @date 2016/6/12 09:05
 * @email admin@chenzhongjin.cn
 */
public abstract class BaseMixtureDBAdapter<T> extends BaseDataDBAdapter<T> {

    private static final int NO_FOUND_TYPE = -1;

    private SparseIntArray mItemTypeToLayoutMap = new SparseIntArray();

    public BaseMixtureDBAdapter(SparseIntArray itemTypeToLayoutMap) {
        mItemTypeToLayoutMap = itemTypeToLayoutMap;
    }

    public BaseMixtureDBAdapter(OnItemClickListener<T> onItemClickListener, SparseIntArray itemTypeToLayoutMap) {
        super(onItemClickListener);
        mItemTypeToLayoutMap = itemTypeToLayoutMap;
    }

    public BaseMixtureDBAdapter(OnItemLongClickListener<T> onItemLongClickListener, SparseIntArray itemTypeToLayoutMap) {
        super(onItemLongClickListener);
        mItemTypeToLayoutMap = itemTypeToLayoutMap;
    }

    public BaseMixtureDBAdapter(OnItemClickListener<T> onItemClickListener, OnItemLongClickListener<T> onItemLongClickListener,
                                SparseIntArray itemTypeToLayoutMap) {
        super(onItemClickListener, onItemLongClickListener);
        mItemTypeToLayoutMap = itemTypeToLayoutMap;
    }

    @Override
    protected BaseBindViewHolder createDBViewHolder(ViewGroup parent, int viewType) {
        return new BaseBindViewHolder<>(
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutRes(viewType), parent, false));
    }

    @LayoutRes
    protected int getLayoutRes(int viewType) {
        if (mItemTypeToLayoutMap.get(viewType, NO_FOUND_TYPE) != NO_FOUND_TYPE) {
            return mItemTypeToLayoutMap.get(viewType);
        }
        return R.layout.item_databinding_null;
    }
}

