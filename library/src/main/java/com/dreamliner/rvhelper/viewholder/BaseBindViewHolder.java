package com.dreamliner.rvhelper.viewholder;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.dreamliner.rvhelper.interfaces.OnItemClickListener;
import com.dreamliner.rvhelper.interfaces.OnItemLongClickListener;

/**
 * Created by chenzj on 2017/3/21.
 */

public class BaseBindViewHolder<Binding extends ViewDataBinding, T> extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnLongClickListener {

    private final Binding mBinding;

    private T mItem;
    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnItemLongClickListener;

    public BaseBindViewHolder(Binding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public Binding getBinding() {
        return mBinding;
    }

    @Override
    public void onClick(View v) {
        if (null != mOnItemClickListener) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mOnItemClickListener.onItemClick(v, position, mItem);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (null != mOnItemLongClickListener) {
            int position = getAdapterPosition();
            return position == RecyclerView.NO_POSITION || mOnItemLongClickListener.onItemLongClick(v, position, mItem);
        }
        return false;
    }

    public void setItem(T item) {
        mItem = item;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }
}
