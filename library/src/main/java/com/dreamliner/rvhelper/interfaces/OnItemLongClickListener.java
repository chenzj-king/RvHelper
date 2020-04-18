package com.dreamliner.rvhelper.interfaces;

import android.view.View;

/**
 * Created by chenzj on 2017/3/21.
 */
public interface OnItemLongClickListener<T> {
    boolean onItemLongClick(View v, int position, T t);
}
