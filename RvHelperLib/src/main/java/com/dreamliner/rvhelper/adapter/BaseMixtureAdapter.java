package com.dreamliner.rvhelper.adapter;

import com.dreamliner.rvhelper.viewholder.BaseViewHolder;

/**
 * @author chenzj
 * @Title: BaseAdapter
 * @Description: 类的描述 -
 * @date 2016/6/12 09:05
 * @email admin@chenzhongjin.cn
 */
public abstract class BaseMixtureAdapter<T> extends BaseDataAdapter<T, BaseViewHolder> {

    private static final String TAG = "BaseMixtureAdapter";

    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        bindView(holder, position);
    }

    protected abstract void bindView(BaseViewHolder holder, int position);

}

