package com.dreamliner.rvhelper.sample.ui.activity.main.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamliner.rvhelper.adapter.BaseAdapter;
import com.dreamliner.rvhelper.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenzj
 * @Title: TextAdapter
 * @Description: 类的描述 -
 * @date 2016/6/13 09:58
 * @email admin@chenzhongjin.cn
 */
public class TextAdapter extends BaseAdapter<String, TextAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getView(R.layout.item_text, parent));
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindView(ViewHolder holder, int position) {
        String itemData = getItem(position);
        holder.mTextTv.setText("" + itemData);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_text_textview)
        TextView mTextTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}

