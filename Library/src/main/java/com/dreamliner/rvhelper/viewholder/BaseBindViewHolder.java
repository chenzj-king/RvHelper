package com.dreamliner.rvhelper.viewholder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by chenzj on 2017/3/21.
 */

public class BaseBindViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private final T mBinding;

    public BaseBindViewHolder(T binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public T getBinding() {
        return mBinding;
    }
}
