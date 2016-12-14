package com.dreamliner.rvhelper.sample.ui.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamliner.rvhelper.adapter.BaseAdapter;
import com.dreamliner.rvhelper.interfaces.ItemClickListener;
import com.dreamliner.rvhelper.sample.R;
import com.dreamliner.rvhelper.viewholder.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenzj
 * @Title: TypeAdapter
 * @Description: 类的描述 -
 * @date 2016/10/11 14:21
 * @email admin@chenzhongjin.cn
 */
public class TypeAdapter extends BaseAdapter<String, TypeAdapter.ViewHolder> {

    public TypeAdapter(ItemClickListener itemClickListener) {
        super(itemClickListener);
    }

    @Override
    public ViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_type, parent), getItemClickListener());
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindView(ViewHolder holder, int position) {
        String itemData = getItem(position);
        holder.mTypeTv.setText("" + itemData);
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.type_tv)
        TextView mTypeTv;

        ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView, itemClickListener);
            ButterKnife.bind(this, itemView);
            mTypeTv.setOnClickListener(this);
        }
    }
}
