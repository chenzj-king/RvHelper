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
public abstract class BaseMixtureAdapter<T> extends BaseDataAdapter<T, BaseViewHolder> {

    public BaseMixtureAdapter() {
        super();
    }

    public BaseMixtureAdapter(ItemClickListener itemClickListener) {
        super(itemClickListener);
    }

    public BaseMixtureAdapter(ItemLongListener itemLongListener) {
        super(itemLongListener);
    }

    public BaseMixtureAdapter(ItemClickListener itemClickListener, ItemLongListener itemLongListener) {
        super(itemClickListener, itemLongListener);
    }

    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        bindView(holder, position);
    }

    protected abstract void bindView(BaseViewHolder holder, int position);

}

