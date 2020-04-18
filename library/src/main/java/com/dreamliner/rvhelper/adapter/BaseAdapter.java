package com.dreamliner.rvhelper.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.dreamliner.rvhelper.interfaces.ItemClickListener;
import com.dreamliner.rvhelper.interfaces.ItemLongListener;
import com.dreamliner.rvhelper.viewholder.BaseViewHolder;
import com.dreamliner.rvhelper.viewholder.FooterViewHolder;

/**
 * @author chenzj
 * @Title: BaseAdapter
 * @Description: 类的描述 -
 * @date 2016/6/12 09:05
 * @email admin@chenzhongjin.cn
 */
public abstract class BaseAdapter<T, VH extends BaseViewHolder> extends BaseNormalAdapter<T, VH> {

    public BaseAdapter() {
        super();
    }

    public BaseAdapter(ItemClickListener itemClickListener) {
        super();
        mItemClickListener = itemClickListener;
    }

    public BaseAdapter(ItemLongListener itemLongListener) {
        super();
        mItemLongListener = itemLongListener;
    }

    public BaseAdapter(ItemClickListener itemClickListener, ItemLongListener itemLongListener) {
        super();
        mItemClickListener = itemClickListener;
        mItemLongListener = itemLongListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof FooterViewHolder)) {
            bindView((VH) holder, position);
        }
    }
}

