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
 * @Title: CommonAdapter
 * @Description: 类的描述 -
 * @date 2016/6/19 11:52
 * @email admin@chenzhongjin.cn
 */
public class CommonAdapter extends BaseAdapter<String, CommonAdapter.ViewHolder> {

    public CommonAdapter(ItemClickListener itemClickListener) {
        super(itemClickListener);
    }

    @Override
    public ViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_text, parent), getItemClickListener());
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void bindView(ViewHolder viewHolder, int position) {
        String itemData = getItem(position);
        viewHolder.mTextTv.setText("" + itemData);
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.item_text_textview)
        TextView mTextTv;

        ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView, itemClickListener);
            ButterKnife.bind(this, itemView);
            mTextTv.setOnClickListener(this);
        }
    }
}
