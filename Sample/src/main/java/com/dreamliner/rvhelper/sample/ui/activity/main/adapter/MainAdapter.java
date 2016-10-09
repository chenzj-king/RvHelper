package com.dreamliner.rvhelper.sample.ui.activity.main.adapter;

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
 * @Title: MainAdapter
 * @Description: 类的描述 -
 * @date 2016/6/13 09:58
 * @email admin@chenzhongjin.cn
 */
public class MainAdapter extends BaseAdapter<String, MainAdapter.ViewHolder> {

    public MainAdapter() {
        super();
    }

    public MainAdapter(ItemClickListener itemClickListener) {
        super(itemClickListener);
    }

    @Override
    public ViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_text, parent), getItemClickListener());
    }

    @Override
    protected void bindView(ViewHolder holder, int position) {
        String itemData = getItem(position);
        holder.mTextTv.setText("" + itemData);
    }


    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.item_text_textview)
        TextView mTextTv;

        public ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView, itemClickListener);
            ButterKnife.bind(this, itemView);
            mTextTv.setOnClickListener(this);
        }
    }

}

