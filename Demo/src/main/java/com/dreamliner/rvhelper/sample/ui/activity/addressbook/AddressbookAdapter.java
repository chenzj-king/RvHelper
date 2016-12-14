package com.dreamliner.rvhelper.sample.ui.activity.addressbook;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamliner.rvhelper.adapter.BaseMixtureAdapter;
import com.dreamliner.rvhelper.interfaces.ItemClickListener;
import com.dreamliner.rvhelper.sample.R;
import com.dreamliner.rvhelper.sample.entity.Addressbook;
import com.dreamliner.rvhelper.sample.utils.CollectionUtils;
import com.dreamliner.rvhelper.viewholder.BaseViewHolder;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenzj
 * @Title: AddressbookAdapter
 * @Description: 类的描述 -
 * @date 2016/10/12 14:04
 * @email admin@chenzhongjin.cn
 */
public class AddressbookAdapter extends BaseMixtureAdapter<Addressbook> {

    private HashMap<String, Integer> mHashMap;

    private final int ASSIST_SIGN = 0;
    private final int NEW_SIGN = 1;
    private final int LABEL = 2;
    private final int NORAML = 3;

    public AddressbookAdapter(ItemClickListener itemClickListener) {
        super(itemClickListener);
        mHashMap = new HashMap<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == ASSIST_SIGN) {
            return ASSIST_SIGN;
        } else if (position == NEW_SIGN) {
            return NEW_SIGN;
        } else if (position == LABEL) {
            return LABEL;
        } else if (super.getItemViewType(position) != FOOTER_TYPE) {
            return NORAML;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public BaseViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ASSIST_SIGN) {
            return new AssistSignViewHolder(getView(R.layout.item_addressbook_assist_sign, parent), getItemClickListener());
        } else if (viewType == NEW_SIGN) {
            return new NewSignViewHolder(getView(R.layout.item_addressbook_new_sign, parent), getItemClickListener());
        } else if (viewType == LABEL) {
            return new LabelViewHolder(getView(R.layout.item_addressbook_label, parent), getItemClickListener());
        } else if (viewType == NORAML) {
            return new ViewHolder(getView(R.layout.item_addressbook, parent), getItemClickListener());
        } else {
            return new BaseViewHolder(getView(R.layout.item_null, parent));
        }
    }

    @Override
    protected void bindView(BaseViewHolder baseViewHolder, int position) {
        if (baseViewHolder instanceof ViewHolder) {

            ViewHolder holder = (ViewHolder) baseViewHolder;
            Addressbook itemData = getItem(position);
            holder.mNameTv.setText(itemData.getRealname());

        } else if (baseViewHolder instanceof NewSignViewHolder) {

            NewSignViewHolder holder = (NewSignViewHolder) baseViewHolder;
        }
    }

    @Override
    public void update(@NonNull List<Addressbook> mdatas) {
        mHashMap.clear();
        if (CollectionUtils.isNotNull(mdatas)) {
            for (int i = 0; i < mdatas.size(); i++) {
                String tag = mdatas.get(i).getItemDecorationtag();
                if (mHashMap.containsKey(tag)) {
                    if (i < mHashMap.get(tag)) {
                        mHashMap.put(tag, i);
                    }
                } else {
                    mHashMap.put(tag, i);
                }
            }
        }
        super.update(mdatas);
    }

    public int getFirstPosition(String keyWord) {
        if (mHashMap.containsKey(keyWord))
            return mHashMap.get(keyWord);
        else
            return -1;
    }

    public HashMap<String, Integer> getHashMap() {
        return mHashMap;
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.avatar_iv)
        ImageView mAvatarIv;
        @BindView(R.id.name_tv)
        TextView mNameTv;

        public ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView, itemClickListener);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
    }

    static class AssistSignViewHolder extends BaseViewHolder {

        AssistSignViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView, itemClickListener);
            itemView.setOnClickListener(this);
        }
    }

    static class NewSignViewHolder extends BaseViewHolder {

        NewSignViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView, itemClickListener);
            itemView.setOnClickListener(this);
        }
    }

    static class LabelViewHolder extends BaseViewHolder {

        LabelViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView, itemClickListener);
            itemView.setOnClickListener(this);
        }
    }
}
