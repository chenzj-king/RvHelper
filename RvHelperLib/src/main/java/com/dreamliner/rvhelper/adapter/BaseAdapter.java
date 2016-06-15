package com.dreamliner.rvhelper.adapter;

import com.dreamliner.rvhelper.viewholder.BaseViewHolder;

/**
 * @author chenzj
 * @Title: BaseAdapter
 * @Description: 类的描述 -
 * @date 2016/6/12 09:05
 * @email admin@chenzhongjin.cn
 */
public abstract class BaseAdapter<T, VH extends BaseViewHolder> extends BaseDataAdapter<T, VH> {

    private static final String TAG = "BaseAdapter";

    @Override
    public final void onBindViewHolder(VH holder, int position) {
        bindView(holder, position);
    }

    protected abstract void bindView(VH holder, int position);

}

