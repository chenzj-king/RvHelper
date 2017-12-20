package com.dreamliner.lib.rvhelper.sample.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamliner.lib.rvhelper.sample.R;
import com.dreamliner.rvhelper.empty.EmptyLayout;
import com.dreamliner.rvhelper.view.CustomizedClickableSpan;

import static com.dreamliner.rvhelper.util.StatusConstant.DEFAULT_NULL;
import static com.dreamliner.rvhelper.util.StatusConstant.NET_ERROR;
import static com.dreamliner.rvhelper.util.StatusConstant.NO_RESULT;


/**
 * @author chenzj
 * @Title: DlEmptyLayout
 * @Description: 类的描述 -
 * @date 2016/10/9 22:57
 * @email admin@chenzhongjin.cn
 */
public class DlEmptyLayout extends EmptyLayout {

    private ImageView mEmptyIv;
    private TextView mEmtptTipTv;
    private CustomizedClickableSpan mClickableSpan;
    private OnClickListener mOnClickListener;

    public DlEmptyLayout(Context context) {
        this(context, null);
    }

    public DlEmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DlEmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mEmptyIv = (ImageView) findViewById(R.id.empty_status_iv);
        mEmtptTipTv = (TextView) findViewById(R.id.empty_status_tv);
    }

    @Override
    public void setEmptyType(int type) {
        switch (type) {
            case NET_ERROR:
                mEmptyIv.setImageResource(R.drawable.ic_list_status_net_error);
                setEmptyTv(mEmtptTipTv, "亲,网络有点差哦", "自定义重新加载");
                break;
            case NO_RESULT:
                mEmptyIv.setImageResource(R.drawable.ic_list_status_no_result);
                setEmptyTv(mEmtptTipTv, "亲，暂无数据", "自定义重新加载");
                break;
            case DEFAULT_NULL:
                mEmptyIv.setImageDrawable(null);
                setEmptyTv(mEmtptTipTv, "", "");
                break;
        }
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
    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }
}
