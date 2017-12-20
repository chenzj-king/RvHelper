package com.dreamliner.rvhelper.adapter;

import com.dreamliner.rvhelper.interfaces.ItemClickListener;
import com.dreamliner.rvhelper.interfaces.ItemLongListener;
import com.dreamliner.rvhelper.viewholder.BaseViewHolder;

/**
 * Created by chenzj on 2017/12/20.
 */

public abstract class BaseNormalAdapter<T, VH extends BaseViewHolder> extends BaseDataAdapter<T, BaseViewHolder> {

    //普通adapter相关点击接口
    protected ItemClickListener mItemClickListener;
    protected ItemLongListener mItemLongListener;

    protected abstract void bindView(VH holder, int position);

    public BaseNormalAdapter() {
        super();
    }

    public BaseNormalAdapter(ItemClickListener itemClickListener) {
        super();
        mItemClickListener = itemClickListener;
    }

    public BaseNormalAdapter(ItemLongListener itemLongListener) {
        super();
        mItemLongListener = itemLongListener;
    }

    public BaseNormalAdapter(ItemClickListener itemClickListener, ItemLongListener itemLongListener) {
        super();
        mItemClickListener = itemClickListener;
        mItemLongListener = itemLongListener;
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
