package com.dreamliner.rvhelper.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dreamliner.rvhelper.interfaces.OnItemClickListener;
import com.dreamliner.rvhelper.interfaces.OnItemLongClickListener;
import com.dreamliner.rvhelper.viewholder.BaseBindViewHolder;

/**
 * Created by chenzj on 2017/3/21.
 */

public class BaseDBAdapter<T> extends BaseDataDBAdapter<T> {

    protected int mLayoutRes;

    public BaseDBAdapter(@LayoutRes int layoutRes) {
        mLayoutRes = layoutRes;
    }

    public BaseDBAdapter(OnItemClickListener<T> onItemClickListener, @LayoutRes int layoutRes) {
        super(onItemClickListener);
        mLayoutRes = layoutRes;
    }

    public BaseDBAdapter(OnItemLongClickListener<T> onItemLongClickListener, @LayoutRes int layoutRes) {
        super(onItemLongClickListener);
        mLayoutRes = layoutRes;
    }

    public BaseDBAdapter(OnItemClickListener<T> onItemClickListener, OnItemLongClickListener<T> onItemLongClickListener,
                         @LayoutRes int layoutRes) {
        super(onItemClickListener, onItemLongClickListener);
        mLayoutRes = layoutRes;
    }

    @Override
    public BaseBindViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new BaseBindViewHolder<>(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                mLayoutRes, parent, false));
    }
}
