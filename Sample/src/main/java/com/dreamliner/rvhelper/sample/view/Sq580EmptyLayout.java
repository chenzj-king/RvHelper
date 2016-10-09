package com.dreamliner.rvhelper.sample.view;

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

import com.dreamliner.rvhelper.empty.EmptyLayout;
import com.dreamliner.rvhelper.sample.R;
import com.dreamliner.rvhelper.view.CustomizedClickableSpan;

import static com.dreamliner.rvhelper.empty.DefaultEmptyLayout.NET_ERROR;
import static com.dreamliner.rvhelper.empty.DefaultEmptyLayout.NO_RESULT;

/**
 * @author chenzj
 * @Title: Sq580EmptyLayout
 * @Description: 类的描述 -
 * @date 2016/10/9 22:57
 * @email admin@chenzhongjin.cn
 */
public class Sq580EmptyLayout extends EmptyLayout {

    private ImageView mEmptyIv;
    private TextView mEmtptTipTv;

    public Sq580EmptyLayout(Context context) {
        super(context);
    }

    public Sq580EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Sq580EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Sq580EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
                mEmptyIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_status_net_error));
                setEmptyTv(mEmtptTipTv, "自定义空白页面-亲,网络有点差哦", "");
                break;
            case NO_RESULT:
                mEmptyIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_status_empty_result));
                setEmptyTv(mEmtptTipTv, "自定义空白页面-亲，暂无数据", "");
                break;
        }
    }

    private void setEmptyTv(TextView textView, String tipStr, String clickStr) {
        textView.setText(tipStr);
        if (!TextUtils.isEmpty(clickStr)) {
            SpannableString spStr = new SpannableString(clickStr);
            ClickableSpan clickSpan = new CustomizedClickableSpan(clickStr, getContext().getApplicationContext(), null);
            spStr.setSpan(clickSpan, 0, clickStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            textView.append(spStr);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
