package com.dreamliner.rvhelper.sample.view;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;

import com.dreamliner.rvhelper.sample.AppContext;
import com.dreamliner.rvhelper.sample.R;

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
    private WeakReference<OnClickListener> mOnClickListener;

    public CustomizedClickableSpan(String text, OnClickListener onClickListener) {
        this.mTextStr = text;
        mOnClickListener = new WeakReference<>(onClickListener);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(AppContext.getInstance().getResources().getColor(R.color.new_bg));
    }

    @Override
    public void onClick(View widget) {
        OnClickListener onClickListener = mOnClickListener.get();
        if (null != onClickListener) {
            onClickListener.onClick(widget);
        }
    }
}
