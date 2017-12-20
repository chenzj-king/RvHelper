package com.dreamliner.rvhelper.adapter;

import android.support.v7.widget.RecyclerView;

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
public abstract class BaseMixtureAdapter<T> extends BaseNormalAdapter<T, BaseViewHolder> {

    public BaseMixtureAdapter() {
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
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof FooterViewHolder)) {
            bindView((BaseViewHolder) holder, position);
        }
    }
}

