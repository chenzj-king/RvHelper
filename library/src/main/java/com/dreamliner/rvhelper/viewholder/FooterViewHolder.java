package com.dreamliner.rvhelper.viewholder;

import android.view.View;

import com.dreamliner.rvhelper.interfaces.ItemClickListener;
import com.dreamliner.rvhelper.interfaces.ItemLongListener;

/**
 * @author chenzj
 * @Title: FooterViewHolder
 * @Description: 类的描述 -
 * @date 2016/9/25 19:57
 * @email admin@chenzhongjin.cn
 */
public class FooterViewHolder extends BaseViewHolder {

    public FooterViewHolder(View itemView) {
        super(itemView);
    }

    public FooterViewHolder(View itemView, ItemClickListener itemClickListener) {
        super(itemView, itemClickListener);
    }

    public FooterViewHolder(View itemView, ItemLongListener itemLongListener) {
        super(itemView, itemLongListener);
    }

    public FooterViewHolder(View itemView, ItemClickListener itemClickListener, ItemLongListener itemLongListener) {
        super(itemView, itemClickListener, itemLongListener);
    }
}
