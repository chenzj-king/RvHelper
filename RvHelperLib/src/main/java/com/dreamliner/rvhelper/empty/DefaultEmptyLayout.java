package com.dreamliner.rvhelper.empty;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamliner.rvhelper.R;
import com.dreamliner.rvhelper.view.CustomizedClickableSpan;

/**
 * @author chenzj
 * @Title: DefaultEmptyLayout
 * @Description: 类的描述 -
 * @date 2016/10/8 23:19
 * @email admin@chenzhongjin.cn
 */
public class DefaultEmptyLayout extends EmptyLayout {

    private ImageView mEmptyIv;
    private TextView mEmtptTipTv;

    private OnClickListener mOnClickListener;

    public static final int NET_ERROR = Integer.MAX_VALUE;
    public static final int NO_RESULT = Integer.MAX_VALUE - 1;

    public DefaultEmptyLayout(Context context) {
        super(context);
    }

    public DefaultEmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultEmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DefaultEmptyLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mEmptyIv = (ImageView) findViewById(R.id.empty_status_iv);
        mEmtptTipTv = (TextView) findViewById(R.id.empty_status_tv);
    }

    private void setEmptyTv(TextView textView, String tipStr, String clickStr) {
        textView.setText(tipStr);
        if (!TextUtils.isEmpty(clickStr)) {
            SpannableString spStr = new SpannableString(clickStr);
            ClickableSpan clickSpan = new CustomizedClickableSpan(clickStr, getContext().getApplicationContext(), mOnClickListener);
            spStr.setSpan(clickSpan, 0, clickStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            textView.append(spStr);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @Override
    public void setEmptyType(int type) {
        switch (type) {
            case NET_ERROR:
                mEmptyIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_status_net_error));
                setEmptyTv(mEmtptTipTv, "亲,网络有点差哦", "重新加载");
                break;
            case NO_RESULT:
                mEmptyIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_status_empty_result));
                setEmptyTv(mEmtptTipTv, "亲，暂无数据", "");
                break;
        }
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }
}
