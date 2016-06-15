package com.dreamliner.rvhelper.adapter;

import com.dreamliner.rvhelper.interfaces.ItemClickListener;
import com.dreamliner.rvhelper.interfaces.ItemLongListener;
import com.dreamliner.rvhelper.viewholder.BaseViewHolder;

/**
 * @author chenzj
 * @Title: BaseAdapter
 * @Description: 类的描述 -
 * @date 2016/6/12 09:05
 * @email admin@chenzhongjin.cn
 */
public abstract class BaseAdapter<T, VH extends BaseViewHolder> extends BaseDataAdapter<T, VH> {

    public BaseAdapter(ItemClickListener itemClickListener) {
        super(itemClickListener);
    }

    public BaseAdapter(ItemLongListener itemLongListener) {
        super(itemLongListener);
    }

    public BaseAdapter(ItemClickListener itemClickListener, ItemLongListener itemLongListener) {
        super(itemClickListener, itemLongListener);
    }

    @Override
    public final void onBindViewHolder(VH holder, int position) {
        bindView(holder, position);
    }

    protected abstract void bindView(VH holder, int position);

}

