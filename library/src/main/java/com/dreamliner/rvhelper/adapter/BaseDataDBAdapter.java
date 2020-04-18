package com.dreamliner.rvhelper.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.dreamliner.lib.rvhelper.BR;
import com.dreamliner.rvhelper.interfaces.OnItemClickListener;
import com.dreamliner.rvhelper.interfaces.OnItemLongClickListener;
import com.dreamliner.rvhelper.viewholder.BaseBindViewHolder;

/**
 * Created by chenzj on 2017/3/15.
 */

public abstract class BaseDataDBAdapter<T> extends BaseDataAdapter<T, BaseBindViewHolder> {

    //DataBinding相关点击接口
    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnItemLongClickListener;

    private Decorator mDecorator;

    public BaseDataDBAdapter() {
        super();
    }

    public BaseDataDBAdapter(OnItemClickListener<T> onItemClickListener) {
        super();
        mOnItemClickListener = onItemClickListener;
    }

    public BaseDataDBAdapter(OnItemLongClickListener<T> onItemLongClickListener) {
        super();
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public BaseDataDBAdapter(OnItemClickListener<T> onItemClickListener, OnItemLongClickListener<T> onItemLongClickListener) {
        super();
        mOnItemClickListener = onItemClickListener;
        mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = holder.getItemViewType();
        switch (itemViewType) {
            case FOOTER_TYPE:
                //不需要进行操作
                break;
            default:
                final Object item = getItem(position);
                if (holder instanceof BaseBindViewHolder) {
                    BaseBindViewHolder baseBindViewHolder = (BaseBindViewHolder) holder;
                    baseBindViewHolder.setItem(item);
                    baseBindViewHolder.setOnItemClickListener(getOnItemClickListener());
                    baseBindViewHolder.setOnItemLongClickListener(getOnItemLongClickListener());
                    baseBindViewHolder.getBinding().setVariable(BR.item, item);
                    baseBindViewHolder.getBinding().setVariable(BR.position, position);
                    //v2.3.0 加入.减少编写xml的时候要手动打中间生成的view成员变量
                    baseBindViewHolder.getBinding().setVariable(BR.clickListener, baseBindViewHolder);
                    baseBindViewHolder.getBinding().setVariable(BR.onLongClickListener, baseBindViewHolder);
                    //v2.3.0不需要.但旧版本需要兼容
                    baseBindViewHolder.getBinding().setVariable(BR.itemClick, getOnItemClickListener());
                    baseBindViewHolder.getBinding().setVariable(BR.itemLongClick, getOnItemLongClickListener());
                    baseBindViewHolder.getBinding().executePendingBindings();
                    if (null != mDecorator) {
                        mDecorator.decorator(baseBindViewHolder, position, itemViewType);
                    }
                    break;
                }
        }
    }

    //DataBindingAdapter点击
    public OnItemClickListener<T> getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public OnItemLongClickListener<T> getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    public void setDecorator(Decorator decorator) {
        mDecorator = decorator;
    }

    /******************
     * 扩展的自定义设置
     *****************/
    public interface Decorator {
        void decorator(BaseBindViewHolder holder, int position, int viewType);
    }
}
