package com.dreamliner.rvhelper.empty;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dreamliner.rvhelper.R;
import com.dreamliner.rvhelper.view.CustomizedClickableSpan;

import static com.dreamliner.rvhelper.util.StatusConstant.DEFAULT_NULL;
import static com.dreamliner.rvhelper.util.StatusConstant.NET_ERROR;
import static com.dreamliner.rvhelper.util.StatusConstant.NO_RESULT;

/**
 * @author chenzj
 * @Title: DefaultEmptyLayout
 * @Description: 类的描述 -
 * @date 2016/10/8 23:19
 * @email admin@chenzhongjin.cn
 */
public class DefaultEmptyLayout extends EmptyLayout {

    private TextView mEmtptTipTv;
    private CustomizedClickableSpan mClickableSpan;

    private OnClickListener mOnClickListener;

    public DefaultEmptyLayout(Context context) {
        this(context, null);
    }

    public DefaultEmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultEmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mEmtptTipTv = (TextView) findViewById(R.id.empty_status_tv);
    }

    private void setEmptyTv(TextView textView, String tipStr, String clickStr) {
        if (!TextUtils.isEmpty(clickStr)) {
            SpannableString spStr = new SpannableString(tipStr + clickStr);
            mClickableSpan = new CustomizedClickableSpan(R.color.new_bg, getContext().getApplicationContext(), mOnClickListener);
            spStr.setSpan(mClickableSpan, tipStr.length(), tipStr.length() + clickStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            textView.setText(spStr);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @Override
    public void setEmptyType(int type) {
        switch (type) {
            case NET_ERROR:
                setEmptyTv(mEmtptTipTv, "亲,网络有点差哦", "重新加载");
                break;
            case NO_RESULT:
                setEmptyTv(mEmtptTipTv, "亲，暂无数据", "重新加载");
                break;
            case DEFAULT_NULL:
                setEmptyTv(mEmtptTipTv, "", "");
                break;
        }
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }
}
