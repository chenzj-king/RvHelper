package com.dreamliner.rvhelper.view;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;

import com.dreamliner.rvhelper.R;

import java.lang.ref.WeakReference;

/**
 * @author chenzj
 * @Title: CustomizedClickableSpan
 * @Description: 类的描述 -
 * @date 2016/9/19 12:54
 * @email admin@chenzhongjin.cn
 */
public class CustomizedClickableSpan extends ClickableSpan {

    private String mTextStr;
    private Context mContext;
    private WeakReference<OnClickListener> mOnClickListener;

    public CustomizedClickableSpan(String text, Context context, OnClickListener onClickListener) {
        mTextStr = text;
        mContext = context;
        mOnClickListener = new WeakReference<>(onClickListener);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(mContext.getResources().getColor(R.color.new_bg));
    }

    @Override
    public void onClick(View widget) {
        OnClickListener onClickListener = mOnClickListener.get();
        if (null != onClickListener) {
            onClickListener.onClick(widget);
        }
    }
}
