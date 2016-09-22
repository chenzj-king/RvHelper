package com.dreamliner.rvhelper.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dreamliner.rvhelper.interfaces.ItemClickListener;
import com.dreamliner.rvhelper.interfaces.ItemLongListener;

/**
 * @author chenzj
 * @Title: BaseViewHolder
 * @Description: 类的描述 - ViewHolder基类.实现了相关的点击回调
 * @date 2016/6/14 15:18
 * @email admin@chenzhongjin.cn
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private ItemClickListener mItemClickListener;
    private ItemLongListener mItemLongListener;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(View itemView, ItemClickListener itemClickListener) {
        super(itemView);
        mItemClickListener = itemClickListener;
    }

    public BaseViewHolder(View itemView, ItemLongListener itemLongListener) {
        super(itemView);
        mItemLongListener = itemLongListener;
    }

    public BaseViewHolder(View itemView, ItemClickListener itemClickListener, ItemLongListener itemLongListener) {
        super(itemView);
        mItemClickListener = itemClickListener;
        mItemLongListener = itemLongListener;
    }

    @Override
    public void onClick(View v) {
        if (null != mItemClickListener) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (null != mItemLongListener) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mItemLongListener.onLongClick(v, getAdapterPosition());
            }
            return true;
        }
        return false;
    }
}

